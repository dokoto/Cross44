package cross;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.badlogic.gdx.utils.TimeUtils;

import cross.utils.inputs.GestureHandler;
import cross.utils.inputs.InputHandler;
import cross.utils.inputs.loaders.BodyEditorLoader;
import cross.utils.primitives.Curve;

public class Cross44 extends ApplicationAdapter {

	public enum Moves {
		ACCELERAR, TURBO, ESTABILIZA_ARRIBA, ESTABILIZA_ABAJO, CONTINUA_MOV, STOP
	};

	public Moves currentMovement = Moves.STOP;
	public float zoom = 1.0f;
	public OrthographicCamera camera;
	public World physicsWorld;
	public Box2DDebugRenderer physicsDebugRenderer;
	public Body chassis, leftBodyWheel, rightBodyWheel, testBodyFromLoader;
	public FixtureDef chassisFixtureDef;
	public FixtureDef wheelFixtureDef;
	public WheelJoint leftWheel, rightWheel;
	private float accumulator = 0;
	private double currentTime = 0;

	private InputMultiplexer inputMultiplexer;
	private InputHandler inputHandler;
	private GestureHandler gestureHandler;

	public static class Consts {
		public final static float SPEED = 225.0f;
		public static final int SCREEN_WIDTH = 1920;
		public static final int SCREEN_HEIGHT = 1080;
		public static final int CAMERA_WIDTH = 800;
		public static final int CAMERA_HEIGHT = 480;
		public static final int CAMERA_DISTANCE = 5;
		public static final boolean GRAVITY_ON = true;
		public final static float TIME_STEP = 1.0f / 60.0f;
		public final static int VELOCITY_ITERATIONS = 6;
		public final static int POSITION_ITERATIONS = 2;
		public final static float GRAVITY = -19.8f;
	}

	@Override
	public void create() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth()
				/ Consts.CAMERA_DISTANCE, Gdx.graphics.getHeight()
				/ Consts.CAMERA_DISTANCE);
		physicsWorld = (Consts.GRAVITY_ON) ? new World(new Vector2(0,
				Consts.GRAVITY), true) : new World(new Vector2(0, 0.0f), true);
		physicsDebugRenderer = new Box2DDebugRenderer();
		test_createPhysicsObjects();

		inputMultiplexer = new InputMultiplexer();
		Gdx.input.setInputProcessor(inputMultiplexer);
		inputHandler = new InputHandler(this);
		gestureHandler = new GestureHandler(this);
		inputMultiplexer.addProcessor(new GestureDetector(gestureHandler));
		inputMultiplexer.addProcessor(inputHandler);
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = Consts.SCREEN_WIDTH / Consts.CAMERA_DISTANCE;
		camera.viewportHeight = Consts.SCREEN_HEIGHT / Consts.CAMERA_DISTANCE;
	}

	private void doPhysicsStep(float deltaTime) {
		// fixed time step
		// max frame time to avoid spiral of death (on slow devices)
		float frameTime = Math.min(deltaTime, 0.25f);
		accumulator += frameTime;
		while (accumulator >= Consts.TIME_STEP) {
			physicsWorld.step(Consts.TIME_STEP, Consts.VELOCITY_ITERATIONS,
					Consts.POSITION_ITERATIONS);
			accumulator -= Consts.TIME_STEP;
		}
	}

	private float getDeltaTime() {
		double newTime = TimeUtils.millis() / 1000.0;
		double frameTime = Math.min(newTime - currentTime, 0.25);
		currentTime = newTime;
		return (float) frameTime;
	}

	@Override
	public void render() {
		float deltaTime = getDeltaTime();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// test_handleKeyBoardInput();
		test_handleMovement(currentMovement);
		physicsDebugRenderer.render(physicsWorld, camera.combined);
		camera.position.x = chassis.getPosition().x;
		camera.position.y = chassis.getPosition().y;
		camera.zoom = zoom;
		camera.update();
		physicsWorld.step(Consts.TIME_STEP, Consts.VELOCITY_ITERATIONS,
				Consts.POSITION_ITERATIONS);
		// doPhysicsStep(deltaTime);
	}

	private void test_createPhysicsObjects() {
		test_createPhysicsWorldBundaries();
		test_createPhysicsGround();
		test_createPhysicsPlayer(10.0f, 55.0f);

		test_createFromLoader();
	}

	private void test_handleMovement(Moves movement) {
		if (movement == Moves.ACCELERAR) {
			leftWheel.enableMotor(true);
			leftWheel.setMotorSpeed(-Consts.SPEED);
		}

		if (movement == Moves.TURBO) {
			chassis.setLinearVelocity(100.0f, chassis.getAngularVelocity());
			leftBodyWheel.setLinearVelocity(100.0f,
					chassis.getAngularVelocity());
			rightBodyWheel.setLinearVelocity(100.0f,
					chassis.getAngularVelocity());
		}

		if (movement == Moves.ESTABILIZA_ARRIBA) {
			chassis.setAngularVelocity(3.0f);
		}

		if (movement == Moves.ESTABILIZA_ABAJO) {
			chassis.setAngularVelocity(-3.0f);
		}

		if (movement == Moves.STOP) {
			leftWheel.enableMotor(true);
			leftWheel.setMotorSpeed(0.0f);
			chassis.setAngularVelocity(0.0f);
		}
	}

	private void test_createPhysicsGround() {
		BodyDef bodyDef = new BodyDef();
		Body edge = physicsWorld.createBody(bodyDef);

		ArrayList<Vector2> sides = new ArrayList<Vector2>();

		sides.add(new Vector2(0.0f, 50.0f));
		sides.add(new Vector2(150.0f, 50.0f));
		sides.add(new Vector2(390.0f, 52.0f));
		sides.add(new Vector2(600.0f, 25.0f));
		sides.add(new Vector2(1200.0f, 50.0f));

		ChainShape chain = new ChainShape();
		chain.createChain(sides.toArray(new Vector2[sides.size()]));

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = chain;
		fixtureDef.density = 10000.5f;
		fixtureDef.friction = 100.0f;

		edge.createFixture(fixtureDef);

		chain.dispose();
		test_createPhysicsGroundLoop();
		test_createPhysicsPiramid(350f, 50f, 25f);
	}

	private void test_createFromLoader() {
		BodyEditorLoader loader = new cross.utils.inputs.loaders.BodyEditorLoader(
				Gdx.files.internal("data/level_test.json"));
		// 1. Create a BodyDef, as usual.
		BodyDef bd = new BodyDef();
		bd.position.set(100, 200);
		bd.type = BodyType.DynamicBody;

		// 2. Create a FixtureDef, as usual.
		FixtureDef fd = new FixtureDef();
		fd.density = 1;
		fd.friction = 0.5f;
		fd.restitution = 0.3f;

		// 3. Create a Body, as usual.
		testBodyFromLoader = physicsWorld.createBody(bd);

		// 4. Create the body fixture automatically by using the loader.
		loader.attachFixture(testBodyFromLoader, "ground", fd, 60);
	}

	private void test_createPhysicsGroundLoop() {
		BodyDef bodyDef = new BodyDef();
		Body edge = physicsWorld.createBody(bodyDef);

		ArrayList<Vector2> sides = Curve.generateCurve(
				new Vector2(360f, 100.0f), new Vector2(400f, 75.0f), 80.0f,
				7.0f, false, false);
		ChainShape chain = new ChainShape();
		chain.createChain(sides.toArray(new Vector2[sides.size()]));

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = chain;
		fixtureDef.density = 10000.5f;
		fixtureDef.friction = 100.0f;

		edge.createFixture(fixtureDef);

		chain.dispose();
	}

	private void test_createPhysicsPiramid(float x, float y, float base) {
		BodyDef bodyDef = new BodyDef();
		Body edge = physicsWorld.createBody(bodyDef);

		ArrayList<Vector2> sides = new ArrayList<Vector2>();

		sides.add(new Vector2(x, y));
		sides.add(new Vector2(x + base, y + base));
		sides.add(new Vector2(x + base + base, y));
		sides.add(new Vector2(x, y));

		ChainShape chain = new ChainShape();
		chain.createChain(sides.toArray(new Vector2[sides.size()]));

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = chain;
		fixtureDef.density = 1000.5f;
		fixtureDef.friction = 100.0f;

		edge.createFixture(fixtureDef);

		chain.dispose();
	}

	private void test_createPhysicsWorldBundaries() {
		BodyDef bodyDef = new BodyDef();
		Body edge = physicsWorld.createBody(bodyDef);

		Vector2 sides[] = new Vector2[5];
		int index = 0;
		sides[index++] = new Vector2(0.0f, 0.0f);
		sides[index++] = new Vector2(Consts.SCREEN_WIDTH, 0.0f);
		sides[index++] = new Vector2(Consts.SCREEN_WIDTH, Consts.SCREEN_HEIGHT);
		sides[index++] = new Vector2(0.0f, Consts.SCREEN_HEIGHT);
		sides[index++] = new Vector2(0.0f, 0.0f);

		ChainShape chain = new ChainShape();
		chain.createChain(sides);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = chain;
		fixtureDef.density = 0.5f;

		edge.createFixture(fixtureDef);
		chain.dispose();
	}

	private void test_createPhysicsPlayer() {
		final float DEFAULT_X_POS = 100.0f;
		final float DEFAULT_Y_POS = 12.0f;
		test_createPhysicsPlayer(DEFAULT_X_POS, DEFAULT_Y_POS);
	}

	private void test_createPhysicsPlayer(float x_pos, float y_pos) {
		final float DEFAULT_RADIUS = 10.4f;
		final float DEFAULT_FRAME_WIDTH = 3.0f;
		final float DEFAULT_FRAME_HEIGHT = 1.5f;
		test_createPhysicsPlayer(x_pos, y_pos, DEFAULT_RADIUS,
				DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
	}

	private void test_createPhysicsPlayer(float x_pos, float y_pos,
			float radius, float frame_width, float frame_height) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x_pos, y_pos);

		// CHASSIS
		PolygonShape chassisShape = new PolygonShape();
		chassisShape.set(new float[] { 2.0f, 2.0f, 4.2f, -2.2f, -1.0f, -2.0f,
				-4.2f, -2.2f, -2.0f, 0.9f });

		chassisFixtureDef = new FixtureDef();
		chassisFixtureDef.density = 20.0f;
		chassisFixtureDef.friction = 0.2f;
		chassisFixtureDef.restitution = 0.1f;
		chassisFixtureDef.shape = chassisShape;

		chassis = physicsWorld.createBody(bodyDef);
		chassis.createFixture(chassisFixtureDef);

		// LEFT WHEEL
		CircleShape wheelShape = new CircleShape();
		wheelShape.setRadius(1.7f);

		wheelFixtureDef = new FixtureDef();
		wheelFixtureDef.density = chassisFixtureDef.density - 0.5f;
		wheelFixtureDef.friction = 80.0f;
		wheelFixtureDef.restitution = 0.1f;
		wheelFixtureDef.shape = wheelShape;

		leftBodyWheel = physicsWorld.createBody(bodyDef);
		leftBodyWheel.createFixture(wheelFixtureDef);

		rightBodyWheel = physicsWorld.createBody(bodyDef);
		wheelFixtureDef.density = chassisFixtureDef.density + 30.0f;
		rightBodyWheel.createFixture(wheelFixtureDef);

		WheelJointDef wheelJoinDef = new WheelJointDef();
		wheelJoinDef.bodyA = chassis;
		wheelJoinDef.bodyB = leftBodyWheel;
		wheelJoinDef.localAnchorA.set(-4.2f, -2.2f);
		wheelJoinDef.frequencyHz = chassisFixtureDef.density;
		wheelJoinDef.localAxisA.set(Vector2.Y);
		wheelJoinDef.maxMotorTorque = chassisFixtureDef.density * 2300;
		leftWheel = (WheelJoint) physicsWorld.createJoint(wheelJoinDef);

		// right axis
		wheelJoinDef.bodyB = rightBodyWheel;
		wheelJoinDef.localAnchorA.x *= -1.05;

		rightWheel = (WheelJoint) physicsWorld.createJoint(wheelJoinDef);

		chassisShape.dispose();
		wheelShape.dispose();
	}

}

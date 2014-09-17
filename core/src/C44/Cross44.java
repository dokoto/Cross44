package C44;

import java.util.ArrayList;

import C44.utils.primitives.Curve;

import com.apple.eawt.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

public class Cross44 extends ApplicationAdapter
{
	private OrthographicCamera camera;
	private World physicsWorld;
	private Box2DDebugRenderer physicsDebugRenderer;
	
	private Body chassis, leftBodyWheel, rightBodyWheel;
	private FixtureDef chassisFixtureDef;
	private FixtureDef wheelFixtureDef;
	private WheelJoint leftWheel, rightWheel;
	private Body motorcycle;
	private final static float SPEED = 75.0f;

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 780;
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;
	public static final int CAMERA_DISTANCE = 2;

	@Override
	public void create()
	{
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / CAMERA_DISTANCE, Gdx.graphics.getHeight() / CAMERA_DISTANCE);				
		physicsWorld = new World(new Vector2(0, -10), true);
		physicsDebugRenderer = new Box2DDebugRenderer();
		test_createPhysicsObjects();
	}

	@Override
	public void resize(int width, int height)
	{
		camera.viewportWidth = SCREEN_WIDTH / CAMERA_DISTANCE;
		camera.viewportHeight = SCREEN_HEIGHT / CAMERA_DISTANCE;
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		test_handleKeyBoardInput();
		physicsDebugRenderer.render(physicsWorld, camera.combined);
		camera.position.x = chassis.getPosition().x;
		camera.position.y = chassis.getPosition().y;
		camera.update();
		physicsWorld.step(1 / 45f, 6, 2);
	}

	private void test_createPhysicsObjects()
	{
		test_createPhysicsWorldBundaries();
		test_createPhysicsGround();
		test_createPhysicsPlayer(5.0f, 55.0f);
	}

	private void test_handleKeyBoardInput()
	{
		if (Gdx.input.isKeyPressed(Keys.S) == true) // BREAK
		{
			leftWheel.enableMotor(true);
			leftWheel.setMotorSpeed(0.0f);
			leftBodyWheel.setLinearVelocity(0.0f, 0.0f);
			rightBodyWheel.setLinearVelocity(0.0f, 0.0f);
		} 
		else if (Gdx.input.isKeyPressed(Keys.D) == true) // ACCELERATOR
		{
			leftWheel.enableMotor(true);
			leftWheel.setMotorSpeed(-SPEED);	
		} 
		else if (Gdx.input.isKeyPressed(Keys.UP) == true)
		{
			rightBodyWheel.setLinearVelocity(0.0f, 10.0f);
		}
		else if (Gdx.input.isKeyPressed(Keys.DOWN) == true)
		{
			rightBodyWheel.setLinearVelocity(0.0f, -10.0f);
		}
		else if (Gdx.input.isKeyPressed(Keys.SPACE) == true)
		{
			chassis.setLinearVelocity(100.0f, 0.0f);
			leftBodyWheel.setLinearVelocity(100.0f, 0.0f);
			rightBodyWheel.setLinearVelocity(100.0f, 0.0f);
		} 
		else if (Gdx.input.isKeyPressed(Keys.ESCAPE) == true)
		{
			Gdx.app.exit();
		}
		else
		{
			leftWheel.enableMotor(true);
			leftWheel.setMotorSpeed(-leftWheel.getMotorSpeed());
		}
	}

	private void test_createPhysicsGround()
	{
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
	}
	
	private void test_createPhysicsGroundLoop()
	{
		BodyDef bodyDef = new BodyDef();
		Body edge = physicsWorld.createBody(bodyDef);

		/*
		ArrayList<Vector2> sides = new ArrayList<Vector2>();

		sides.add(new Vector2(200.0f, 400.0f));
		sides.add(new Vector2(220.0f, 440.0f));
		sides.add(new Vector2(220.0f, 460.0f));
		sides.add(new Vector2(200.0f, 480.0f));
		sides.add(new Vector2(180.0f, 480.0f));
		sides.add(new Vector2(160.0f, 460.0f));
		sides.add(new Vector2(160.0f, 440.0f));
		sides.add(new Vector2(180.0f, 420.0f));
*/
		ArrayList<Vector2> sides = Curve.generateCurve(new Vector2(200.0f, 100.0f), 30.0f, 20.0f, 5.0f);
		//sides.addAll( Curve.generateCurve(new Vector2(150.0f, 55.0f), new Vector2(150.0f, 75.0f), 20.0f, 1.0f) );
		ChainShape chain = new ChainShape();
		chain.createChain(sides.toArray(new Vector2[sides.size()]));

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = chain;
		fixtureDef.density = 10000.5f;
		fixtureDef.friction = 100.0f;

		edge.createFixture(fixtureDef);
				
		chain.dispose();
	}
	
	

	private void test_createPhysicsWorldBundaries()
	{
		BodyDef bodyDef = new BodyDef();
		Body edge = physicsWorld.createBody(bodyDef);

		Vector2 sides[] = new Vector2[5];
		int index = 0;
		sides[index++] = new Vector2(0.0f, 0.0f);
		sides[index++] = new Vector2(1280.0f, 0.0f);
		sides[index++] = new Vector2(1280.0f, 720.0f);
		sides[index++] = new Vector2(0.0f, 720.0f);
		sides[index++] = new Vector2(0.0f, 0.0f);

		ChainShape chain = new ChainShape();
		chain.createChain(sides);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = chain;
		fixtureDef.density = 0.5f;

		edge.createFixture(fixtureDef);
		chain.dispose();
	}

	private void test_createPhysicsPlayer()
	{
		final float DEFAULT_X_POS = 100.0f;
		final float DEFAULT_Y_POS = 12.0f;
		test_createPhysicsPlayer(DEFAULT_X_POS, DEFAULT_Y_POS);
	}

	private void test_createPhysicsPlayer(float x_pos, float y_pos)
	{
		final float DEFAULT_RADIUS = 10.4f;
		final float DEFAULT_FRAME_WIDTH = 3.0f;
		final float DEFAULT_FRAME_HEIGHT = 1.5f;
		test_createPhysicsPlayer(x_pos, y_pos, DEFAULT_RADIUS, DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
	}

	private void test_createPhysicsPlayer2(float x_pos, float y_pos, float radius, float frame_width, float frame_height)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x_pos, y_pos);

		// CHASSIS
		PolygonShape chassisShape = new PolygonShape();
		chassisShape.set(new float[] { -frame_width / 2, -frame_height / 2, frame_width / 2, -frame_height / 2, frame_width / 2 * .4f,
				frame_height / 2, -frame_width / 2 * .8f, frame_height / 2 * .8f });

		chassisFixtureDef = new FixtureDef();
		chassisFixtureDef.density = 5.0f;
		chassisFixtureDef.friction = 0.4f;
		chassisFixtureDef.restitution = 0.3f;
		chassisFixtureDef.shape = chassisShape;

		chassis = physicsWorld.createBody(bodyDef);
		chassis.createFixture(chassisFixtureDef);

		// LEFT WHEEL
		CircleShape wheelShape = new CircleShape();
		wheelShape.setRadius(frame_height / 3.5f);

		wheelFixtureDef = new FixtureDef();
		wheelFixtureDef.density = chassisFixtureDef.density - 0.5f;
		wheelFixtureDef.friction = 2.0f;
		wheelFixtureDef.restitution = 0.4f;
		wheelFixtureDef.shape = wheelShape;

		leftBodyWheel = physicsWorld.createBody(bodyDef);
		leftBodyWheel.createFixture(wheelFixtureDef);

		rightBodyWheel = physicsWorld.createBody(bodyDef);
		rightBodyWheel.createFixture(wheelFixtureDef);

		WheelJointDef wheelJoinDef = new WheelJointDef();
		wheelJoinDef.bodyA = chassis;
		wheelJoinDef.bodyB = leftBodyWheel;
		wheelJoinDef.localAnchorA.set(-frame_width / 2 * 0.75f + wheelShape.getRadius(), -frame_height / 2.0f * 1.25f);
		wheelJoinDef.frequencyHz = chassisFixtureDef.density;
		wheelJoinDef.localAxisA.set(Vector2.Y);
		wheelJoinDef.maxMotorTorque = chassisFixtureDef.density * 10;
		leftWheel = (WheelJoint) physicsWorld.createJoint(wheelJoinDef);

		// right axis
		wheelJoinDef.bodyB = rightBodyWheel;
		wheelJoinDef.localAnchorA.x *= -1;

		rightWheel = (WheelJoint) physicsWorld.createJoint(wheelJoinDef);

	}

	private void test_createPhysicsPlayer(float x_pos, float y_pos, float radius, float frame_width, float frame_height)
	{
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(10.0f, 55.0f);

		// CHASSIS
		PolygonShape chassisShape = new PolygonShape();
		chassisShape.setAsBox(2.0f, 1.8f);
		
		chassisFixtureDef = new FixtureDef();
		chassisFixtureDef.density = 10.0f;
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
		rightBodyWheel.createFixture(wheelFixtureDef);

		WheelJointDef wheelJoinDef = new WheelJointDef();
		wheelJoinDef.bodyA = chassis;
		wheelJoinDef.bodyB = leftBodyWheel;
		wheelJoinDef.localAnchorA.set(-3.2f, -2.2f);
		wheelJoinDef.frequencyHz = chassisFixtureDef.density;
		wheelJoinDef.localAxisA.set(Vector2.Y);
		wheelJoinDef.maxMotorTorque = chassisFixtureDef.density * 700;
		leftWheel = (WheelJoint) physicsWorld.createJoint(wheelJoinDef);

		// right axis
		wheelJoinDef.bodyB = rightBodyWheel;
		wheelJoinDef.localAnchorA.x *= -1;

		rightWheel = (WheelJoint) physicsWorld.createJoint(wheelJoinDef);

		chassisShape.dispose();
		wheelShape.dispose();
	}

}

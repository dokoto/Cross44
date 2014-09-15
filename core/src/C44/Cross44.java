package C44;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.physics.box2d.World;

public class Cross44 extends ApplicationAdapter
{
	private OrthographicCamera camera;
	private World physicsWorld;
	Box2DDebugRenderer physicsDebugRenderer;
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 780;
	public static final int CAMERA_WIDTH = 800;
	public static final int CAMERA_HEIGHT = 480;

	@Override
	public void create()
	{
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
		physicsWorld = new World(new Vector2(0, -10), true);
		physicsDebugRenderer = new Box2DDebugRenderer();
		test_createPhysicsObjects();
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		physicsDebugRenderer.render(physicsWorld, camera.combined);
		physicsWorld.step(1/45f, 6, 2);
	}
	
	private void test_createPhysicsObjects()
	{
		test_createPhysicsWorldBundaries();
		test_createPhysicsGround();
		test_createPhysicsPlayer();
	}
	
	private void test_createPhysicsGround()
	{
		BodyDef bodyDef = new BodyDef();
		Body edge = physicsWorld.createBody(bodyDef);
		
		ArrayList<Vector2> sides = new ArrayList<Vector2>();

		sides.add( new Vector2(0.0f, 400.0f) );
		sides.add( new Vector2(200.0f, 350.0f) );
		sides.add( new Vector2(400.0f, 460.0f) );
		sides.add( new Vector2(600.0f, 250.0f) );
		sides.add( new Vector2(1200.0f, 50.0f) );
	
		ChainShape chain = new ChainShape();
		chain.createChain(sides.toArray(new Vector2[sides.size()]));
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = chain;
		fixtureDef.density = 0.5f;
		
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
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(10.0f, 460.0f);
		
		Body body = physicsWorld.createBody(bodyDef);
		
		CircleShape circle = new CircleShape();
		circle.setRadius(6.0f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f;
		
		body.createFixture(fixtureDef);
		
		circle.dispose();
	}
	
}

package sosu;

import java.util.HashSet;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.broadphase.Dispatcher;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;

/**
 * Our engine interface. Works with Bullet to provide the devs a nicer interface.
 * Toot toot!
 * 
 * @author cgraber
 *
 */
public class LilEngineThatCould {
	
	//Bullet Schtuff
	private Dispatcher dispatcher;
	private BroadphaseInterface broadphase;
	private ConstraintSolver constraintSolver;
	private CollisionConfiguration collisionConfig;
	private DiscreteDynamicsWorld world;
	
	//Keeps track of all the items currently within the world
	private HashSet<EngineThingy> thingies = new HashSet<EngineThingy>();
	public LilEngineThatCould(){
		
		//Initialized everything needed for Bullet to work
		broadphase = new DbvtBroadphase();
		constraintSolver = new SequentialImpulseConstraintSolver();
		collisionConfig = new DefaultCollisionConfiguration();
		dispatcher = new CollisionDispatcher(collisionConfig);
		world = new DiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver,
										  collisionConfig);
		
		//Sets the gravity. Currently, y is "up"
		world.setGravity(new Vector3f(0.0f,-10.0f,0.0f));
	}
	
	/**
	 * Adds an enginethingy to the physics world
	 * @param thingy The thing being added
	 */
	public void addThingy(EngineThingy thingy){
		thingies.add(thingy);
		world.addRigidBody(thingy);
	}
	
	public void removeThingy(EngineThingy thingy){
		thingies.remove(thingy);
		world.removeRigidBody(thingy);
	}
	
}

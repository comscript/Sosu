package sosu;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.MotionState;



/**
 * Something that has physics attached to it. Works with our engine interface
 * @author cgraber
 *
 */
public class EngineThingy extends RigidBody{

	public EngineThingy(float mass, MotionState motionState,
			CollisionShape collisionShape) {
		super(mass, motionState, collisionShape);
	}
	
	/**
	 * Adds object to our engine
	 */
	public void addToEngine(){
		
	}
	
	/**
	 * Removes object from our engine
	 */
	public void removeFromEngine(){
		
	}
}

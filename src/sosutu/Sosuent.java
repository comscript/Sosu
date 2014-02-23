package sosutu;

/**
 *This class should be the base class of all entities created in the game
 */

public abstract class Sosuent{
	
	private Sosutu sosutu = null;
	private World world = null;

	public Sosuent(){
		sosutu = Sosutu.get();
		world = sosutu.getWorld();
		//Automatically binds functions defined in entity to proper keys
		world.bindEntity(this);
	}
	
	public Sosuent(World world){
		sosutu = Sosutu.get();
		this.world = world;
		//Automatically binds functions defined in entity to proper keys
		world.bindEntity(this);
	}
}
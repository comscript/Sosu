package sosu;

/**
 *This class should be the base class of all entities created in the game
 */

public abstract class Bu{
	
	private Sosu sosu = null;
	private World world = null;

	public Bu(){
		sosu = Sosu.get();
		world = sosu.getWorld();
		//Automatically binds functions defined in entity to proper keys
		world.newBu(this);
	}
	
	public Bu(World world){
		sosu = Sosu.get();
		this.world = world;
		//Automatically binds functions defined in entity to proper keys
		world.newBu(this);
	}
}
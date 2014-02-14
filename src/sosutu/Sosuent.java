package sosutu;

/**
 *This class should be the base class of all entities created in the game
 */

public abstract class Sosuent{

	public Sosuent(){
		//Automatically binds functions defined in entity to proper keys
		SosuKey.getInstance().bindEntity(this);
	}
}
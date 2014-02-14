package sosutu;

import java.util.HashMap;
import java.util.HashSet;
import java.lang.reflect.Method;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 *SosuKey manages all of the key bindings within the game.
 */

public class SosuKey{

	//There should only be one instance of sosukey
	private static SosuKey sosukey;

	//Maps key to function name
	private HashMap<Integer, String>  bindings = new HashMap<Integer, String>();

	//Keeps track of functions that have bindings
	//TODO: if we decide functions can map to multiple entities, we'll
	//      need to keep track of how many entities are bound as well
	private HashSet<String> functions = new HashSet<String>();

	//Maps function name to game objects
	//TODO: be able to map a function to multiple entities (if desired; wasn't
	//      sure if we were planning on doing this or not
	private HashMap<String, Sosuent> functionEnts = new HashMap<String, Sosuent>();

	private SosuKey(String[][] newBindings){
		//Set up keyboard
		try{
			Keyboard.create();
		}catch (Exception e){
			e.printStackTrace();
		}

		//Initialize all bindings
		for (String[] binding : newBindings){
			System.out.println("Key: "+binding[0]+" Number: "+Keyboard.getKeyIndex(binding[0]));
			bindings.put(Keyboard.getKeyIndex(binding[0]), binding[1]);
			functions.add(binding[1]);
		}
	}

	public static SosuKey getInstance(){
		return sosukey;
	}

	//This is used in lieu of a constructor
	//The bindings should be passed in the form of an array of pairs:
	// [[key1, binding1], [key2, binding2], ..., [keyn, bindingn]]
	//TODO: set third parameter for binding that dictates whether function happens on key press/release
	public static void init(String[][] bindings){
		if (sosukey != null)
			return;
		sosukey = new SosuKey(bindings);
	}

	//Called whenever an entity is created; keeps track of functions that
	//are mapped and which entity they are mapped to.
	public void bindEntity(Sosuent entity){
		Method[] methods = entity.getClass().getMethods();
		for (Method m: methods){
			System.out.println("Function: "+m.getName());
			if (functions.contains(m.getName())){
				functionEnts.put(m.getName(), entity);
			}
		}
	}
	//TODO: allow bindings to be changed dynamically (e.g. in controls menu)

	public void checkKeys(){
		String function;
		while (Keyboard.next()){
			function = bindings.get(Keyboard.getEventKey());
			if (function != null){
				Sosuent entity = functionEnts.get(function);
				try{
					entity.getClass().getMethod(function, new Class[]{})
					.invoke(entity, new Object[]{});
				}catch (Exception e){
					//TODO: determine whether there are any possible exceptions we need to worry about
				}
			}
		}
	}
}

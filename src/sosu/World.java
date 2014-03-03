package sosu;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

/**
 * Under construction!
 *
 *
 */
public class World {
	
	private Sosu sosu = null;
	
	//Maps key to function name
	private HashMap<Integer, String>  inputBindings = new HashMap<Integer, String>();

	//Maps function name to game objects
	private HashMap<String, ArrayList<MethodHandle>> inputReceivers = new HashMap<String, ArrayList<MethodHandle>>();
	
	public World(){
			this.sosu = Sosu.get();
	}
		
	public void start(){
		
	}
	
	public void render(){
		
	}
	
	public void update(){
		
	}
	
	/**
	 * Gets called whenever the world is loaded into Sosu via loadWorld().
	 */
	public void load(){
		
	}
	
	/**
	 * Bind an input to a function name for this world. All entities in this world will 
	 * have the specified function called if the specified input is activated.
	 * @param inputName
	 * @param functionName
	 */
	public void bindInput(String inputName, String functionName){
		int index = Keyboard.getKeyIndex(inputName);
		if(index != Keyboard.KEY_NONE){
			inputBindings.put(index, functionName);
			inputReceivers.put(functionName, new ArrayList<MethodHandle>());
			return;
		}
		index = Mouse.getButtonIndex(inputName);
		if(index != -1){
			index = -index -1;
			inputBindings.put(index, functionName);
			inputReceivers.put(functionName, new ArrayList<MethodHandle>());
			return;
		}
	}
	
	/**
	 * Called whenever a Bu is created; this function "registers" the Bu with the world,
	 * adding it to all of the proper lists, based on the members/methods it has.
	 * @param entity
	 */
	public void newBu(Bu entity){
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		Method[] methods = entity.getClass().getMethods();
		for (Method m: methods){
			ArrayList<MethodHandle> methodList = inputReceivers.get(m.getName());
			if(null != methodList){
				try{
				MethodHandle handle = lookup.unreflect(m).bindTo(entity);
				methodList.add(handle);
				}
				catch(IllegalAccessException e){
					/* 
					 * Private methods can't be bound to input, but there's no reason
					 * to fail over it. We'll just treat private methods as if they're not
					 * bound to any input, even if their names match.
					 */
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
	}
	
	/**
	 * Broadcasts input events to all of the Bus which have the proper functions
	 * to receive each given input event.
	 */
	public void broadcastInput(){
		String function;
		//Handle the mouse first.
		while (Mouse.next()){
			//We map mouse to -1,-2 and -3, to keep it from overlapping with keys.
			int button = (-Mouse.getEventButton()) - 1;
			function = inputBindings.get(button);
			if (function != null){
				boolean pressed = Mouse.getEventButtonState();
				for (MethodHandle handle : inputReceivers.get(function)){
					try{
						handle.invoke(pressed);
					}catch (Throwable e){
						/*
						 * Entities shouldn't be throwing errors in their input handlers. 
						 * If this happens, we'll just throw the stack trace and fail sans grace
						 * in the hopes of encouraging better error handling game-side. 
						 */
					}
				}
			}
			
		}
		//Handle the keyboard next.
		while (Keyboard.next()){
			function = inputBindings.get(Keyboard.getEventKey());
			if (function != null){
				boolean pressed = Keyboard.getEventKeyState();
				for (MethodHandle handle : inputReceivers.get(function)){
					try{
						handle.invoke(pressed);
					}catch (Throwable e){
						/*
						 * Entities shouldn't be throwing errors in their input handlers. 
						 * If this happens, we'll just throw the stack trace and fail sans grace
						 * in the hopes of encouraging better error handling game-side. 
						 */
					}
				}
			}
		}
	}

}

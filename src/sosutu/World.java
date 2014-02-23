package sosutu;

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
	
	private Sosutu sosutu = null;
	
	//Maps key to function name
	private HashMap<Integer, String>  bindings = new HashMap<Integer, String>();

	//Maps function name to game objects
	private HashMap<String, ArrayList<MethodHandle>> functionEnts = new HashMap<String, ArrayList<MethodHandle>>();
	
	public World(){
			this.sosutu = Sosutu.get();
	}
		
	public void start(){
		
	}
	
	public void render(){
		
	}
	
	public void update(){
		
	}
	
	/**
	 * Bind an input to a function name for this world. All entities in this world will 
	 * have the specified function called if the specified input is activated.
	 * @param inputName
	 * @param function
	 */
	public void addBinding(String inputName, String function){
		int index = Keyboard.getKeyIndex(inputName);
		if(index != Keyboard.KEY_NONE){
			bindings.put(index, function);
			functionEnts.put(function, new ArrayList<MethodHandle>());
			return;
		}
		index = Mouse.getButtonIndex(inputName);
		if(index != -1){
			index = -index -1;
			bindings.put(index, function);
			functionEnts.put(function, new ArrayList<MethodHandle>());
			return;
		}
	}
	
	/**
	 * Called whenever an entity is created; keeps track of functions that
	 * are mapped and which entity they are mapped to.
	 * @param entity
	 */
	public void bindEntity(Sosuent entity){
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		Method[] methods = entity.getClass().getMethods();
		for (Method m: methods){
			ArrayList<MethodHandle> methodList = functionEnts.get(m.getName());
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
	
	public void broadcastInput(){
		String function;
		//Handle the mouse first.
		while (Mouse.next()){
			//We map mouse to -1,-2 and -3, to keep it from overlapping with keys.
			int button = (-Mouse.getEventButton()) - 1;
			function = bindings.get(button);
			if (function != null){
				boolean pressed = Mouse.getEventButtonState();
				for (MethodHandle handle : functionEnts.get(function)){
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
			function = bindings.get(Keyboard.getEventKey());
			if (function != null){
				boolean pressed = Keyboard.getEventKeyState();
				for (MethodHandle handle : functionEnts.get(function)){
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

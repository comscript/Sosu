package sosutu;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

/**
 * Sosutu is the core of the soon-to-be wildly popular 
 * SoulSurvivor video game.
 */
public class Sosutu {
	//Only once instance of sosutu should exist.
	private static Sosutu sosutu = new Sosutu();
	
	//Control variables:
	private boolean quitting = false;
	
	//Loaded objects:
	private World world = null;
	
	//Maps key to function name
	private HashMap<Integer, String>  bindings = new HashMap<Integer, String>();

	//Keeps track of functions that have bindings
	//TODO: if we decide functions can map to multiple entities, we'll
	//      need to keep track of how many entities are bound as well
	private HashSet<String> functions = new HashSet<String>();

	//Maps function name to game objects
	//TODO: be able to map a function to multiple entities (if desired; wasn't
	//      sure if we were planning on doing this or not
	private HashMap<String, MethodHandle> functionEnts = new HashMap<String, MethodHandle>();
	
	//Automatically create an 800x600 OpenGL 3.2 window.
	//A more robust system will be implemented later.
	private Sosutu(){
		try {
			/*
			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs context = new ContextAttribs(3,2)
				.withForwardCompatible(true)
				.withProfileCore(true);
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create(pixelFormat, context);
			*/
			Display.create();
			//Set up keyboard
			Keyboard.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the running instance of Sosutu.
	 * @return Sosutu instance
	 */
	public static Sosutu get(){
		return sosutu;
	}
	
	/**
	 * Change the current world.
	 * @param world
	 */
	public void world_change(World world){
		this.world = world;
	}
	
	/**
	 * Quit the program.
	 */
	public void quit(){
		this.quitting = true;
	}
	
	/**
	 * Whether a quit has been requested.
	 * @return whether a quit has been requested
	 */
	private boolean isQuitting(){
		return (Display.isCloseRequested()) ? true : this.quitting;
	}
	
	/**
	 * Start Sosutu. The currently loaded world will begin to render
	 * and update. If no world is loaded, this method does nothing.
	 */
	public void go(){
		if(null == this.world){
			return;
		}
		while(!isQuitting()){
			world.update();
			world.render();
		}
	}
	
	public void addBinding(String key, String function){
		bindings.put(Keyboard.getKeyIndex(key), function);
		functions.add(function);
	}
	/**
	 * Called whenever an entity is created; keeps track of functions that
	 * are mapped and which entity they are mapped to.
	 * @param entity
	 */
	public void bindEntity(Sosuent entity){
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		Method[] methods = entity.getClass().getMethods();
		//TODO: Determine if there's a faster way to get the method handle
		for (Method m: methods){
			if (functions.contains(m.getName())){
				try{
					functionEnts.put(m.getName(), lookup.unreflect(m).bindTo(entity));
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		}
	}
	//TODO: allow bindings to be changed dynamically (e.g. in controls menu)

	public void checkKeys(){
		String function;
		while (Keyboard.next()){
			function = bindings.get(Keyboard.getEventKey());
			if (function != null){
				MethodHandle handle = functionEnts.get(function);
				try{
					handle.invoke();
				}catch (Throwable e){
					e.printStackTrace();
					//TODO: determine whether there are any possible exceptions we need to worry about
				}
			}
		}
	}

}

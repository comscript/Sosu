package sosutu;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
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
	private boolean running = false;
	private boolean quitting = false;
	
	//Loaded objects:
	private World world = null;
	
	//Automatically create an 800x600 OpenGL 3.2 window.
	//A more robust system will be implemented later.
	private Sosutu(){
	}
	
	/**
	 * Get the running instance of Sosutu.
	 * @return Sosutu instance
	 */
	public static Sosutu get(){
		return sosutu;
	}
	
	public void go(){
		sosutu.init();
		sosutu.loop();
	}
	
	public void init(){
		try {
			//Set up the display for OpenGL 3.2
			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs context = new ContextAttribs(3,2)
				.withForwardCompatible(true)
				.withProfileCore(true);
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create(pixelFormat, context);
			//Set up input devices.
			Mouse.create();
			Keyboard.create();
			//Sosu should now be running.
			running = true;
		} catch (LWJGLException e) {
			/**
			 * If we can't initialize a display, crash and burn.
			 */
			e.printStackTrace();
		}
	}
	
	/**
	 * Change the current world.
	 * @param world
	 */
	public void setWorld(World world){
		this.world = world;
	}
	
	/**
	 * Get the current world.
	 * 
	 */
	public World getWorld(){
		return world;
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
	public void loop(){
		if(null == this.world){
			return;
		}
		running = true;
		while(!isQuitting()){
			world.broadcastInput();
			world.update();
			world.render();
			Display.update();
		}
		Keyboard.destroy();
		Mouse.destroy();
		Display.destroy();
		
	}
	


}

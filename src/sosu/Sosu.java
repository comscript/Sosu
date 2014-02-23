package sosu;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

/**
 * Sosu is the core of the soon-to-be wildly popular 
 * SoulSurvivor video game.
 */
public class Sosu {
	//Only once instance of sosu should exist.
	private static Sosu sosu = new Sosu();
	
	//Control variables:
	private boolean running = false;
	private boolean quitting = false;
	
	//Loaded objects:
	private World world = null;
	
	private Sosu(){
	}
	
	/**
	 * Get the instance of Sosu.
	 * @return Sosu instance
	 */
	public static Sosu get(){
		return sosu;
	}
	
	/**
	 * Initialize OpenGL, the display, and input devices. This will cause a window
	 * to pop up, so be sure to render a splash image if you plan to spend a while loading.
	 */
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
	public void loadWorld(World world){
		this.world = world;
		world.load();
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
	 * Start Sosu. The currently loaded world will begin to render
	 * and update. If no world is loaded, this method does nothing.
	 */
	public void start(){
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

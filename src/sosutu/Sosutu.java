package sosutu;

import org.lwjgl.LWJGLException;
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
	
	//Automatically create an 800x600 OpenGL 3.2 window.
	//A more robust system will be implemented later.
	private Sosutu(){
		try {
			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs context = new ContextAttribs(3,2)
				.withForwardCompatible(true)
				.withProfileCore(true);
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create(pixelFormat, context);
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

}

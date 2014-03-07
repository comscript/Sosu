package sosu;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
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
	
	//Test stuff:
	private Model model = new Model();
	
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
	 * @throws IOException 
	 */
	public void init() throws IOException{
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
			//Set up the viewport
			GL11.glClearColor(0.4f, 0.6f, 0.9f, 0f);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glViewport(0,0,800,600);
			model.loadFromObj("models/crate.obj");
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
		model.construct();
		while(!isQuitting()){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			world.broadcastInput();
			world.update();
			world.render();
			model.render();
			Display.update();
		}
		model.delete();
		Keyboard.destroy();
		Mouse.destroy();
		Display.destroy();
		
	}
	


}

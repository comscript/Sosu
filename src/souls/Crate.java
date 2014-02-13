package souls;

import org.lwjgl.opengl.GL11;

import sosutu.Renderable;
import sosutu.Updatable;

/**
 * Under construction!
 *
 *
 */
public class Crate implements Updatable, Renderable{

	@Override
	public void render() {
		GL11.glColor3f(0.5f, 0.5f, 1.0f);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(100, 100);
			GL11.glVertex2f(300, 100);
			GL11.glVertex2f(300, 300);
			GL11.glVertex2f(100, 300);
		GL11.glEnd();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}

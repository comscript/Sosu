package sosutu;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 * Under construction!
 */
public class Model implements Renderable{
	
	private int id_vao;
	private int id_vbo;
	
	public Model(String filename){
		this.id_vao = GL30.glGenVertexArrays();
		this.id_vbo = GL15.glGenBuffers();
	}

	@Override
	public void render() {
	}
	
}

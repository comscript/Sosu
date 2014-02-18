package sosutu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

/**
 * Under construction!
 */
public class Model implements Renderable{
	
	private int id_vao;
	private int id_vbo;
	private int id_vboi;
	private LinkedList<float[]> vertices = new LinkedList<>();
	private LinkedList<float[]> normals = new LinkedList<>();
	private LinkedList<float[]> textures = new LinkedList<>();
	private LinkedList<Face> faces = new LinkedList<>();
	
	public Model(String filename){

	}
	
	private void loadFromObj(String filename) throws IOException{
		BufferedReader r = new BufferedReader(new FileReader(filename));
		String line = null;
		while(null != (line = r.readLine())){
			line = line.trim();
			
			if(line.charAt(0) == '#'){
				continue;
			}
			else{
				String [] tokens = line.split(" ");
				if(tokens[0].equals("v")){
					float x = Float.valueOf(tokens[1]);
					float y = Float.valueOf(tokens[2]);
					float z = Float.valueOf(tokens[3]);
					float [] vertex = {x,y,z};
					vertices.add(vertex);
				}
				else if(tokens[0].equals("vn")){
					float u = Float.valueOf(tokens[1]);
					float v = Float.valueOf(tokens[2]);
					float [] texture = {u,v,0};
					textures.add(texture);
				}
				else if(tokens[0].equals("vt")){
					float x = Float.valueOf(tokens[1]);
					float y = Float.valueOf(tokens[2]);
					float z = Float.valueOf(tokens[3]);
					float [] normal = {x,y,z};
					normals.add(normal);
				}
				else if(tokens[0].equals("f")){
					Face face = new Face(tokens[1],tokens[2],tokens[3]);
					faces.add(face);
				}
			}
			
		}
	}

	public void construct(){
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.size()*3);
		for(float [] vertex : vertices){
			verticesBuffer.put(vertex);
		}
		verticesBuffer.flip();
		
		IntBuffer indicesBuffer = BufferUtils.createIntBuffer(faces.size()*3);
		for(Face face : faces){
			indicesBuffer.put(face.getVertices());
		}
		indicesBuffer.flip();

		id_vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(id_vao);
		
		id_vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id_vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
		
		id_vboi = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id_vboi);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	
	@Override
	public void render() {
		GL30.glBindVertexArray(id_vao);
		GL20.glEnableVertexAttribArray(0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id_vboi);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, faces.size()*3, GL11.GL_UNSIGNED_INT, 0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	public void delete(){
		GL20.glDisableVertexAttribArray(0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(id_vbo);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(id_vboi);
		
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(id_vao);
	}
	
	private class Face{
		
		private int [] v = new int[3];
		private int [] t = new int[3];
		private int [] n = new int[3];
		private boolean hast = false;
		private boolean hasn = false;
		
		public Face(String attribs1, String attribs2, String attribs3){
			String [] a1 = attribs1.split("/");
			String [] a2 = attribs2.split("/");
			String [] a3 = attribs3.split("/");
			v[0] = Integer.valueOf(a1[0]);
			v[1] = Integer.valueOf(a2[0]);
			v[2] = Integer.valueOf(a3[0]);
			if(a1.length == 2){
				t[0] = Integer.valueOf(a1[1]);
				t[1] = Integer.valueOf(a2[1]);
				t[2] = Integer.valueOf(a3[1]);
				hast = true;
			}
			if(a1.length == 3){
				n[0] = Integer.valueOf(a1[2]);
				n[1] = Integer.valueOf(a2[2]);
				n[2] = Integer.valueOf(a3[2]);
				hasn = true;
				if(!"".equals(a1[1])){
					t[0] = Integer.valueOf(a1[1]);
					t[1] = Integer.valueOf(a2[1]);
					t[2] = Integer.valueOf(a3[1]);
					hast = true;
				}
			}
		}
		
		public boolean hasTexture(){
			return hast;
		}
		
		public boolean hasNormals(){
			return hasn;
		}
		
		public int [] getVertices(){
			return v;
		}
		
		public int [] getTexture(){
			return t;
		}
		
		public int [] getNormals(){
			return n;
		}
		
	}
	
}

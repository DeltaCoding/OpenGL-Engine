package graphics.models;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ModelLoader {

	private List<Integer> vaos;
	private List<Integer> vbos;
	private List<Integer> ebos;
	
	public ModelLoader() {
		this.vaos = new ArrayList<Integer>();
		this.vbos = new ArrayList<Integer>();
		this.ebos = new ArrayList<Integer>();
	}
	
	public RawModel loadToVAO(float[] vertexData, int[] indices) {
		int vao = GL30.glGenVertexArrays();	
		int vbo = GL15.glGenBuffers();
		int ebo = GL15.glGenBuffers();
		
		// Bind VAO
		GL30.glBindVertexArray(vao);
		
		// Copy position data to VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexData, GL15.GL_STATIC_DRAW);
		
		// Copy indices data to VBO
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
		
		// Set vertex attribute pointers
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 32, 0);
		GL20.glEnableVertexAttribArray(0);
		
		GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 32, 12);
		GL20.glEnableVertexAttribArray(1);
		
		GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, 32, 24);
		GL20.glEnableVertexAttribArray(2);
		
		// Unbind buffers and vao
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
		this.vaos.add(vao);
		this.vbos.add(vbo);
		
		return new RawModel(vao, vertexData.length / 3, indices.length);
	}
	
	public void cleanup() {
		for(int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		
		for(int ebo : ebos) {
			GL15.glDeleteBuffers(ebo);
		}
	}
	
}

package graphics.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

public abstract class ShaderProgram {

	private int vertexShaderID;
	private int fragmentShaderID;
	protected int programID;
	
	private String vertexShaderPath;
	private String fragmentShaderPath;
	
	public ShaderProgram(String vertexShader, String fragmentShader) {
		this.vertexShaderPath = vertexShader;
		this.fragmentShaderPath = fragmentShader;
		
		this.init();
		this.getUniformLocations();
	}
	
	public void start() {
		GL20.glUseProgram(this.programID);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public void cleanup() {
		GL20.glDeleteProgram(this.programID);
	}
	
	protected abstract void getUniformLocations();
	
	protected void setBool(int uniformLocation, boolean value) {
		GL20.glUniform1i(uniformLocation, this.booleanToInt(value));
	}
	
	protected void setInt(int uniformLocation, int value) {
		GL20.glUniform1i(uniformLocation, value);
	}
	
	protected void setFloat(int uniformLocation, float value) {
		GL20.glUniform1f(uniformLocation, value);
	}
	
	private int booleanToInt(boolean value) {
		return value ? 1 : 0;
	}
	
	private void init() {
		String vertexSource = this.readShaderFile(this.vertexShaderPath);
		this.vertexShaderID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(this.vertexShaderID, vertexSource);
		GL20.glCompileShader(this.vertexShaderID);
		
		String fragmentSource = this.readShaderFile(this.fragmentShaderPath);
		this.fragmentShaderID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(this.fragmentShaderID, fragmentSource);
		GL20.glCompileShader(this.fragmentShaderID);
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer vertexShaderResult = stack.mallocInt(1);
			IntBuffer fragmentShaderResult = stack.mallocInt(1);
			
			GL20.glGetShaderiv(this.vertexShaderID, GL20.GL_COMPILE_STATUS, vertexShaderResult);
			GL20.glGetShaderiv(this.fragmentShaderID, GL20.GL_COMPILE_STATUS, fragmentShaderResult);
			
			if(vertexShaderResult.get(0) == 0) {
				System.err.println("Could not compile vertex shader:");
				System.err.println(GL20.glGetShaderInfoLog(this.vertexShaderID, 512));
			} else if(fragmentShaderResult.get(0) == 0) {
				System.err.println("Could not compile fragment shader:");
				System.err.println(GL20.glGetShaderInfoLog(this.fragmentShaderID, 512));
			}			
		}	
		
		this.programID = GL20.glCreateProgram();
		GL20.glAttachShader(this.programID, this.vertexShaderID);
		GL20.glAttachShader(this.programID, this.fragmentShaderID);
		GL20.glLinkProgram(this.programID);
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer success = stack.mallocInt(1);
			
			GL20.glGetProgramiv(this.programID, GL20.GL_LINK_STATUS, success);
			
			if(success.get(0) == 0) {
				System.err.println("Could not link shader program:");
				System.err.println(GL20.glGetProgramInfoLog(this.programID));
			}
		}
		
		GL20.glDeleteShader(this.vertexShaderID);
		GL20.glDeleteShader(this.fragmentShaderID);
	}
	
	private String readShaderFile(String fileLocation) {
		StringBuilder shaderSource = new StringBuilder();
		
		try(BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
			String line;
			while((line = br.readLine()) != null) {
				shaderSource.append(line + "\n");
			}
		} catch(IOException e) {
			System.err.println("Could not read shader file '" + fileLocation + "'");
			e.printStackTrace();
		}
		
		return shaderSource.toString().trim();
	}
	
}

package graphics.shaders;

import org.lwjgl.opengl.GL20;

public class TexturedShader extends ShaderProgram {

	private int tex1Location;
	private int tex2Location;
	
	public TexturedShader(String vertexShader, String fragmentShader) {
		super(vertexShader, fragmentShader);
	}

	@Override
	protected void getUniformLocations() {
		this.tex1Location = GL20.glGetUniformLocation(super.programID, "tex1");
		this.tex2Location = GL20.glGetUniformLocation(super.programID, "tex2");
	}
	
	public void setSamplers() {
		super.setInt(this.tex1Location, 0);
		super.setInt(this.tex2Location, 1);
	}

}

package graphics.shaders;

import org.lwjgl.opengl.GL20;

public class PrimitiveShader extends ShaderProgram {
	
	private int horizontalOffsetLocation;
	
	public PrimitiveShader(String vertexShader, String fragmentShader) {
		super(vertexShader, fragmentShader);
	}

	@Override
	protected void getUniformLocations() {
		this.horizontalOffsetLocation = GL20.glGetUniformLocation(super.programID, "horizontalOffset");
	}
	
	public void setHorizontalOffset(float offset) {
		super.setFloat(this.horizontalOffsetLocation, offset);
	}

}

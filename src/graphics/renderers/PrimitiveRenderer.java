package graphics.renderers;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import graphics.models.RawModel;

public class PrimitiveRenderer {

	public void prepare() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public void render(List<RawModel> models) {
		for(RawModel m : models) {
			GL30.glBindVertexArray(m.getVAOId());
			GL11.glDrawElements(GL11.GL_TRIANGLES, m.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
	}
}

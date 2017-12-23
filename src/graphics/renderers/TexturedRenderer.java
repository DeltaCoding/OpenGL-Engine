package graphics.renderers;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import graphics.models.TexturedModel;
import graphics.textures.Texture;

public class TexturedRenderer {

	public void prepare() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public void render(List<TexturedModel> models) {
		for(TexturedModel tm : models) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tm.getTexture().getTextureID());			
			GL30.glBindVertexArray(tm.getModel().getVAOId());
			GL11.glDrawElements(GL11.GL_TRIANGLES, tm.getModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
	}
	
	public void renderMultipleTextures(TexturedModel tm, Texture t1, Texture t2) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, t1.getTextureID());
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, t2.getTextureID());

		GL30.glBindVertexArray(tm.getModel().getVAOId());
		GL11.glDrawElements(GL11.GL_TRIANGLES, tm.getModel().getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
	
}

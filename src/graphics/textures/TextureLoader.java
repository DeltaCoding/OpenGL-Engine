package graphics.textures;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class TextureLoader {
	
	private List<Integer> textures;
	
	public TextureLoader() {
		this.textures = new ArrayList<Integer>();
	}

	public Texture generateTexture(String path, boolean flip) {
		int width, height;
		ByteBuffer data;
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer widthBuffer = stack.mallocInt(1);
			IntBuffer heightBuffer = stack.mallocInt(1);
			IntBuffer nrChannelsBuffer = stack.mallocInt(1);
			
			STBImage.stbi_set_flip_vertically_on_load(flip);
			data = STBImage.stbi_load(path, widthBuffer, heightBuffer, nrChannelsBuffer, 0);
			if(data == null) {
				throw new RuntimeException("Failed to load image:\n" + STBImage.stbi_failure_reason());
			}
			
			width = widthBuffer.get(0);
			height = heightBuffer.get(0);
		}
		
		int textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		
		// Set texture wrapping/filtering options
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB,
				GL11.GL_UNSIGNED_BYTE, data);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		STBImage.stbi_image_free(data);
		
		this.textures.add(textureID);
		
		return new Texture(textureID);
	}
	
	public Texture generateTransparentTexture(String path, boolean flip) {
		int width, height;
		ByteBuffer data;
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer widthBuffer = stack.mallocInt(1);
			IntBuffer heightBuffer = stack.mallocInt(1);
			IntBuffer nrChannelsBuffer = stack.mallocInt(1);
			
			STBImage.stbi_set_flip_vertically_on_load(flip);
			data = STBImage.stbi_load(path, widthBuffer, heightBuffer, nrChannelsBuffer, 0);
			if(data == null) {
				throw new RuntimeException("Failed to load image:\n" + STBImage.stbi_failure_reason());
			}
			
			width = widthBuffer.get(0);
			height = heightBuffer.get(0);
		}
		
		int textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		
		// Set texture wrapping/filtering options
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, data);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		STBImage.stbi_image_free(data);
		
		this.textures.add(textureID);
		
		return new Texture(textureID);
	}
	
	public void cleanup() {
		for(int texture : this.textures) {
			GL11.glDeleteTextures(texture);
		}
	}
	
}

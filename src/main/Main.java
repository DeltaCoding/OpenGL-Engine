package main;

import display.DisplayManager;
import graphics.models.ModelLoader;
import graphics.models.TexturedModel;
import graphics.renderers.TexturedRenderer;
import graphics.shaders.TexturedShader;
import graphics.textures.Texture;
import graphics.textures.TextureLoader;

public class Main {
	
	public static void main(String[] args) {
		// -------------------- DisplayManager --------------------
		DisplayManager displayManager = new DisplayManager(800, 600, "What a wonderful world");
		displayManager.initGLFW();
		//displayManager.initKeyCallbacks();
		displayManager.initOpenGL(0.2f, 0.3f, 0.3f, 0.0f);

		// -------------------- Loaders --------------------
		ModelLoader modelLoader = new ModelLoader();
		TextureLoader textureLoader = new TextureLoader();
		
		// -------------------- RawModel --------------------
		float[] vertexData = new float[] {
			// positions		colors				tex coords
			0.5f, 0.5f, 0.0f,	1.0f, 0.0f, 0.0f,	1.0f, 1.0f, // top right
			0.5f, -0.5f, 0.0f,	0.0f, 1.0f, 0.0f, 	1.0f, 0.0f, // right
			-0.5f, -0.5f, 0.0f,	0.0f, 0.0f, 1.0f,	0.0f, 0.0f, // left
			-0.5f, 0.5f, 0.0f,	1.0f, 1.0f, 1.0f, 	0.0f, 1.0f	// top left	
		};
		
		int[] indices = new int[] {
				0, 1, 2,
				2, 0, 3
				
		};
		
		TexturedModel tm = new TexturedModel(modelLoader.loadToVAO(vertexData, indices),
								null);
		
		Texture t1 = textureLoader.generateTexture(".\\res\\textures\\container.jpg", false);
		Texture t2 = textureLoader.generateTransparentTexture(".\\res\\textures\\awesomeface.png", true);
		
		// -------------------- Renderer & Shader --------------------		
		TexturedRenderer texturedRenderer = new TexturedRenderer();
		TexturedShader texturedShader = new TexturedShader(
			".\\res\\shaders\\textureVertex.glsl",
			".\\res\\shaders\\textureFragment.glsl"
		);
		
		while(!displayManager.isCloseRequested()) {
			displayManager.fetchInput();
			
			texturedRenderer.prepare();
			texturedShader.start();
			texturedShader.setSamplers();
			texturedRenderer.renderMultipleTextures(tm, t1, t2);
			texturedShader.stop();
			
			displayManager.next();
		}
		
		textureLoader.cleanup();
		modelLoader.cleanup();
		displayManager.cleanup();
	}
	
}

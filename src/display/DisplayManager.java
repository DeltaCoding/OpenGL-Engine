package display;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class DisplayManager {

	private int width;
	private int height;
	private String title;
	
	private long window;
	
	public DisplayManager(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public void initGLFW() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if(!GLFW.glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
		GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_TRUE);
		
		this.window = GLFW.glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);
		if(this.window == MemoryUtil.NULL) {
			throw new RuntimeException("Unable to create GLFW window");
		}
		
		GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(
				this.window,
				(vidMode.width() - this.width) / 2,
				(vidMode.height() - this.height) / 2
		);
		
		GLFW.glfwMakeContextCurrent(this.window);
		GLFW.glfwSwapInterval(1);
		GLFW.glfwShowWindow(this.window);
	}
	
	public void initOpenGL(float r, float g, float b, float a) {
		GL.createCapabilities();
		GL11.glViewport(0, 0, this.width, this.height);
		GL11.glClearColor(r, g, b, a);
	}
	
	/*public void initKeyCallbacks() {
		GLFW.glfwSetKeyCallback(this.window, (window, key, scancode, action, mods) -> {
			if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
				GLFW.glfwSetWindowShouldClose(this.window, true);
			} else if(key == GLFW.GLFW_KEY_Y && action == GLFW.GLFW_RELEASE) {
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			}
		});
	}*/
	
	public void fetchInput() {
		if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
			GLFW.glfwSetWindowShouldClose(this.window, true);
		} else if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_Z) == GLFW.GLFW_PRESS) {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		} else if(GLFW.glfwGetKey(this.window, GLFW.GLFW_KEY_X) == GLFW.GLFW_PRESS) {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
	}
	
	public void cleanup() {
		GLFW.glfwDestroyWindow(this.window);
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}
	
	public void next() {
		GLFW.glfwSwapBuffers(this.window);
		GLFW.glfwPollEvents();
	}
	
	public boolean isCloseRequested() {
		return GLFW.glfwWindowShouldClose(this.window);
	}
	
}

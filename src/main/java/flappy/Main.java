package flappy;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import flappy.graphics.Shader;
import flappy.input.Input;
import flappy.level.Level;
import flappy.math.Matrix4f;
public class Main implements Runnable{
	
	private int width = 1280;
	private int height = 720;
	private static Thread thread;
	private boolean running = false;
	
	private long window;
	
	private Level level;
	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
		
	}
	
	private void init() {
		
		if(GLFW.glfwInit()) {
			// to do handle it
		}
		
		glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE);
		window = glfwCreateWindow(width, height, "Flappy", NULL, NULL);
		
		if(window == NULL) {
			// to do
		}
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidmode.width() - width) / 2, (vidmode.height() - height)/ 2);
		
		glfwSetKeyCallback(window, new Input());
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GL.createCapabilities();
		
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		System.out.println("OpenGL: " + glGetString(GL_VERSION));
		Shader.loadAll();
		
		Matrix4f pr_matrix = Matrix4f.orthographic(-10.0f, 10.0f, -10.0f * 9.0f / 16.0f, 10.0f * 9.0f / 16.0f, -1.0f, 1.0f);
		Shader.BG.setUniformMat4f("pr_matrix", pr_matrix);
		
		level = new Level();
	}
	
	public void run() {
		init();
		while(running) {
			update();
			render();
			
			if(glfwWindowShouldClose(window)) {
				running = false;
			}
		}
	}


	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		level.render();
		glfwSwapBuffers(window);
		
	}

	private void update() {
		glfwPollEvents();
		
		
	}

	public static void main(String[] args) {
		new Main().start();
	}

}

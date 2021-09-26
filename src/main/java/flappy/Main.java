package flappy;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import flappy.input.Input;
public class Main implements Runnable{
	
	private int width = 1280;
	private int height = 720;
	private static Thread thread;
	private boolean running = false;
	
	private long window;
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
		glfwSwapBuffers(window);
		
	}

	private void update() {
		glfwPollEvents();
		
		
	}

	public static void main(String[] args) {
		new Main().start();
	}

}

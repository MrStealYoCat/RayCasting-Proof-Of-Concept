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

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Main {
	public static void main(String[] args) {
		int wallSize = 100;
		Window window = new Window("My Window", 2000, 1000);


		int[][] mapArray = {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,1,1,0,0,0,1},
			{1,0,0,0,1,1,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1}
		};

		Player player = new Player(50,3*wallSize,6*wallSize,mapArray);

//		KeyListener listener = new KeyListener() {
//			@Override
//			public void keyTyped(KeyEvent e) {
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				//player.keyReleased(e);
//			}
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				//player.keyPressed(e);
//			}
//		};



		loop(window, mapArray, player);

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window.getWindowHandle());
		glfwDestroyWindow(window.getWindowHandle());

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	public static void loop(Window window, int[][] map, Player player) {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window.getWindowHandle()) ) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			player.drawWalls(window);
			glFlush(); // render now

			glfwSwapBuffers(window.getWindowHandle()); // swap the color buffers
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}
}
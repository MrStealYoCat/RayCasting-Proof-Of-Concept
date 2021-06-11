import org.lwjgl.opengl.*;

import static controlListeners.MouseListener.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Main {
	public static void main(String[] args) {
		Window window = new Window("My Window", 1000, 1000);

		Map map = new Map(1000,1000,100);

		Double[] vertices = {
						200.0,200.0,
						200.0,300.0,
						300.0,200.0
		};

		Double[] vertices2 = {
						400.0,400.0,
						400.0,600.0,
						600.0,600.0,
						600.0,400.0
		};
		map.addObstacle(new Obstacle(100,vertices2, "Obstacle1"));

		Player player = new Player("Player", 50,200,200, 0.0, map);

		loop(window, player);

		// Free up cursor after exiting
		glfwSetInputMode(window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window.getWindowHandle());
		glfwDestroyWindow(window.getWindowHandle());

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	/* Game Loop Function */
	public static void loop(Window window, Player player) {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Keep mouse inside window and hidden
		glfwSetInputMode(window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
		glfwSetInputMode(window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window.getWindowHandle()) ) {

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();

			//TODO change to an object that only re-calculates opengl walls when you move
			Frame.drawWalls(player);

			player.keyPressed();
			player.mouseMoved();
			endMouseFrame();

			glFlush(); // render now

			glfwSwapBuffers(window.getWindowHandle()); // swap the color buffers
		}
	}
}

import controlListeners.KeyListener;
import controlListeners.MouseListener;
import display.Frame;
import display.Window;
import map_utils.Map;
import map_utils.Obstacle;
import org.lwjgl.opengl.*;
import sprites.Player;

import java.util.Objects;

import static controlListeners.MouseListener.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Main {
	public static void main(String[] args) {
		Window window = new Window("My display.Window", 1000, 1000);

		Map map = new Map(3000,3000);

		Double[] vertices = {
						400.0,400.0,
						400.0,800.0,
						800.0,800.0,
						800.0,400.0
		};

		Double[] vertices2 = {
						1000.0,1000.0,
						1000.0,1400.0,
						1400.0,1400.0,
						1400.0,1000.0
		};
		map.addObstacle(new Obstacle(100,vertices, "Obstacle1"));
		map.addObstacle(new Obstacle(100,vertices2,"Obstacle 2"));

		Player player = new Player("sprites.Player", 50,200,200, map);
		KeyListener.setPlayerInstance(player);
		MouseListener.setPlayerInstance(player);

		loop(window, player);

		// Free up cursor after exiting
		glfwSetInputMode(window.getWindowHandle(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window.getWindowHandle());
		glfwDestroyWindow(window.getWindowHandle());

		// Terminate GLFW and free the error callback
		glfwTerminate();
		Objects.requireNonNull(glfwSetErrorCallback(null)).free();
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
			//endMouseFrame();

			glFlush(); // render now

			glfwSwapBuffers(window.getWindowHandle()); // swap the color buffers
		}
	}
}

package display;

import controlListeners.KeyListener;
import controlListeners.MouseListener;
import org.lwjgl.glfw.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

	private final String title;
	private final int width;
	private final int height;

	// What LWJGL uses to keep track of the window
	private long windowHandle;

	public Window(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;

		init();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		// Create the window
		windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
		if ( windowHandle == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup callbacks
		glfwSetCursorPosCallback(windowHandle, MouseListener::mousePosCallback);
		glfwSetMouseButtonCallback(windowHandle, MouseListener::mouseButtonCallback);
		glfwSetScrollCallback(windowHandle, MouseListener::mouseScrollCallback);
		glfwSetKeyCallback(windowHandle, KeyListener::keyCallback);

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(windowHandle, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			if (vidMode != null) {
				glfwSetWindowPos(
								windowHandle,
								(vidMode.width() - pWidth.get(0)) / 10,
								(vidMode.height() - pHeight.get(0)) / 2
				);
			} else {
				throw new IllegalStateException("vidMode width cannot be null!");
			}
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(windowHandle);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(windowHandle);
	}

	public long getWindowHandle() {
		return windowHandle;
	}
}

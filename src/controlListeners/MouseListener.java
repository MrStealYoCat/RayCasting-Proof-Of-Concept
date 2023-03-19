package controlListeners;

import sprites.Player;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {
	private static MouseListener instance;
	private double scrollX;
	private double scrollY;
	private double xPos;
	private double yPos;
	private double lastX;
	private double lastY;
	private final boolean[] mouseButtonPressed = new boolean[GLFW_MOUSE_BUTTON_LAST+1];
	private boolean isDragging;
	private Player player;

	private MouseListener() {

	}

	public static MouseListener get() {
		if (instance == null) {
			instance = new MouseListener();
		}
		return instance;
	}

	public static void mousePosCallback(long window, double xPos, double yPos) {
		get().lastX = get().xPos;
		get().lastY = get().yPos;
		get().xPos = xPos;
		get().yPos = yPos;
		get().isDragging = isPressed();
		//get().player.mouseMoved();
	}

	private static boolean isPressed() {
		for (Boolean button : get().mouseButtonPressed) {
			if (button) {
				return true;
			}
		}
		return false;
	}

	public static void mouseButtonCallback(long window, int button, int action, int mods) {
		if (action == GLFW_PRESS) {
			get().mouseButtonPressed[button] = true;
		} else if (action == GLFW_RELEASE) {
			get().mouseButtonPressed[button] = false;
			get().isDragging = false;
		}
	}

	public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
		get().scrollX = xOffset;
		get().scrollY = yOffset;
	}

	public static void endMouseFrame() {
		get().scrollX = 0;
		get().scrollY = 0;
		get().lastX = get().xPos;
		get().lastY = get().yPos;
	}

	public static float getXPos() {
		return (float)get().xPos;
	}
	public static float getYPos() {
		return (float)get().yPos;
	}
	public static float getDX() {
		return (float)(get().lastX - get().xPos);
	}
	public static float getDY() {
		return (float)(get().lastY - get().yPos);
	}
	public static float getScrollX() {
		return (float)get().scrollX;
	}
	public static float getScrollY() {
		return (float)get().scrollY;
	}
	public static boolean getIsDragging() {
		return get().isDragging;
	}
	public static boolean mouseButtonDown(int button) {
		if (button < get().mouseButtonPressed.length) {
			return get().mouseButtonPressed[button];
		} else {
			return false;
		}
	}

	public static void setPlayerInstance(Player player) {
		get().player = player;
	}
}

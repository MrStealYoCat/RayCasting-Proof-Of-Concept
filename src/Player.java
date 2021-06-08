import static org.lwjgl.glfw.GLFW.*;

public class Player extends Sprite {

	private int rotation = 90;
	private static final int RAY_COUNT = 180;
	private Ray[] rays = new Ray[RAY_COUNT];
	private final Map map;

	public Player(int size, int posX, int posY, Map map) {
		super(size, posX, posY);
		this.map = map;
	}

	/* Checks normal game keys for movement or rotation */
	public void keyPressed() {
		if (map.didCollide(posX, posY)) {
			return;
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_LEFT) || KeyListener.isKeyPressed(GLFW_KEY_A)) {
			posX += 5 * Math.cos((rotation - 90) * 3.14159 / 180);
			posY += 5 * Math.sin((rotation - 90) * 3.14159 / 180);
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT) || KeyListener.isKeyPressed(GLFW_KEY_D)) {
			posX += 5 * Math.cos((rotation + 90) * 3.14159 / 180);
			posY += 5 * Math.sin((rotation + 90) * 3.14159 / 180);
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_DOWN) || KeyListener.isKeyPressed(GLFW_KEY_S)) {
			posX += 5 * Math.cos((rotation + 180) * 3.14159 / 180);
			posY += 5 * Math.sin((rotation + 180) * 3.14159 / 180);
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_UP) || KeyListener.isKeyPressed(GLFW_KEY_W)) {
			posX += 5 * Math.cos(rotation * 3.14159 / 180);
			posY += 5 * Math.sin(rotation * 3.14159 / 180);
		}
		// Just calculate anyways if a key is pressed.
		Ray.processRays(rotation, posX, posY, RAY_COUNT, map);
	}

	/* Check is mouse has been moved on the x plane for player rotation */
	public void mouseMoved() {
		if (MouseListener.getXPos() != 0) {
			rotation = (rotation - (int)(MouseListener.getDX()/2)) % 360;
			Ray.processRays(rotation, posX, posY, RAY_COUNT, map);
		}
	}
}

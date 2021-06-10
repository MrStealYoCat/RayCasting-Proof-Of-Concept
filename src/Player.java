import static org.lwjgl.glfw.GLFW.*;

public class Player extends Sprite {

	private int rotation = -90;
	private final Map map;

	public Player(int size, double posX, double posY, double posZ, Map map) {
		super(size, posX, posY, posZ);
		this.map = map;
	}

	/* Checks normal game keys for movement or rotation */
	public void keyPressed() {
		if (map.didCollideWalls(posX, posY, posZ)
		 || map.didCollideAnyObstacle(posX,posY)
			 ) {
			posX = lastX;
			posY = lastY;
			return;
		}
		lastX = posX;
		lastY = posY;
		if (controlListeners.KeyListener.isKeyPressed(GLFW_KEY_LEFT) || controlListeners.KeyListener.isKeyPressed(GLFW_KEY_A)) {
			posX += Ray.calculateRunD(5.0,rotation + 90.0);
			posY += Ray.calculateRiseD(5.0,rotation + 90.0);
			Frame.updateAndDrawWalls(this);
		}
		if (controlListeners.KeyListener.isKeyPressed(GLFW_KEY_RIGHT) || controlListeners.KeyListener.isKeyPressed(GLFW_KEY_D)) {
			posX += Ray.calculateRunD(5.0,rotation - 90.0);
			posY += Ray.calculateRiseD(5.0,rotation - 90.0);
			Frame.updateAndDrawWalls(this);
		}
		if (controlListeners.KeyListener.isKeyPressed(GLFW_KEY_DOWN) || controlListeners.KeyListener.isKeyPressed(GLFW_KEY_S)) {
			posX += Ray.calculateRunD(5.0,rotation + 180.0);
			posY += Ray.calculateRiseD(5.0,rotation + 180.0);
			Frame.updateAndDrawWalls(this);
		}
		if (controlListeners.KeyListener.isKeyPressed(GLFW_KEY_UP) || controlListeners.KeyListener.isKeyPressed(GLFW_KEY_W)) {
			posX += Ray.calculateRunD(5.0,1.0*rotation);
			posY += Ray.calculateRiseD(5.0,1.0*rotation);
			Frame.updateAndDrawWalls(this);
			//System.out.println(posX + " " + posY);
		}
	}

	/* Check is mouse has been moved on the x plane for player rotation */
	public void mouseMoved() {
		if (controlListeners.MouseListener.getDX() != 0) {
			rotation = (rotation + (int)(controlListeners.MouseListener.getDX()/2)) % 360;
			//System.out.println(rotation);
			Frame.updateAndDrawWalls(this);
		}
	}

	public int getRotation() {
		return rotation;
	}

	public Map getPlayerMap() {
		return map;
	}
}

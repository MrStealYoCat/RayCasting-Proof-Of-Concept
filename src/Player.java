import static org.lwjgl.glfw.GLFW.*;

public class Player extends Sprite {

	private final Map map;

	public Player(String name, int size, double posX, double posY, double posZ, Map map) {
		super(name, size, posX, posY, posZ);
		this.map = map;
	}

	/* Checks normal game keys for movement or rotation */
	public void keyPressed() {

		if (controlListeners.KeyListener.isKeyPressed(GLFW_KEY_LEFT) || controlListeners.KeyListener.isKeyPressed(GLFW_KEY_A)) {
			if (!didPlayerCollide()) {
				posX += Ray.calculateRunD(5.0,rotation + 90.0);
				posY += Ray.calculateRiseD(5.0,rotation + 90.0);
				collisionBoxToPos(posX,posY);
				Frame.updateAndDrawWalls(this);
			}
		}
		if (controlListeners.KeyListener.isKeyPressed(GLFW_KEY_RIGHT) || controlListeners.KeyListener.isKeyPressed(GLFW_KEY_D)) {
			if (!didPlayerCollide()) {
				posX += Ray.calculateRunD(5.0, rotation - 90.0);
				posY += Ray.calculateRiseD(5.0, rotation - 90.0);
				collisionBoxToPos(posX,posY);
				Frame.updateAndDrawWalls(this);
			}
		}
		if (controlListeners.KeyListener.isKeyPressed(GLFW_KEY_DOWN) || controlListeners.KeyListener.isKeyPressed(GLFW_KEY_S)) {
			if (!didPlayerCollide()) {
				posX += Ray.calculateRunD(5.0, rotation + 180.0);
				posY += Ray.calculateRiseD(5.0, rotation + 180.0);
				collisionBoxToPos(posX,posY);
				Frame.updateAndDrawWalls(this);
			}
		}
		if (controlListeners.KeyListener.isKeyPressed(GLFW_KEY_UP) || controlListeners.KeyListener.isKeyPressed(GLFW_KEY_W)) {
			if (!didPlayerCollide()) {
				posX += Ray.calculateRunD(5.0, 1.0 * rotation);
				posY += Ray.calculateRiseD(5.0, 1.0 * rotation);
				collisionBoxToPos(posX,posY);
				Frame.updateAndDrawWalls(this);
				//System.out.println(posX + " " + posY);
			}
		}
	}

	//TODO fix player collision with new coordinate system
	private boolean didPlayerCollide() {
		System.out.printf("%f, %f, %f, %f\n",collisionBox.getVertices()[0],collisionBox.getVertices()[2],collisionBox.getVertices()[4],collisionBox.getVertices()[6]);
		if (
				map.didCollideAnyObstacle(collisionBox.getVertices()[0],collisionBox.getVertices()[1]) ||
				map.didCollideAnyObstacle(collisionBox.getVertices()[2],collisionBox.getVertices()[3]) ||
				map.didCollideAnyObstacle(collisionBox.getVertices()[4],collisionBox.getVertices()[5]) ||
				map.didCollideAnyObstacle(collisionBox.getVertices()[6],collisionBox.getVertices()[7])
		) {
			System.out.println("Collided!");
			posX = lastX;
			posY = lastY;
			collisionBoxToPos(posX,posY);
			return true;
		}
		lastX = posX;
		lastY = posY;
		return false;
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

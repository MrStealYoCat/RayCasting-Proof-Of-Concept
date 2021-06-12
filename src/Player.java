import map_utils.Map;
import sprites.Ray;
import sprites.Sprite;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Sprite {

	private final Map map;

	public Player(String name, int size, double posX, double posY, Map map) {
		super(name, size, posX, posY);
		this.map = map;
	}

	/* Checks normal game keys for movement */
	public void keyPressed() {

		if (controlListeners.KeyListener.isKeyPressed(GLFW_KEY_LEFT) || controlListeners.KeyListener.isKeyPressed(GLFW_KEY_A)) {
			move(rotation+90);
		}
		if (controlListeners.KeyListener.isKeyPressed(GLFW_KEY_RIGHT) || controlListeners.KeyListener.isKeyPressed(GLFW_KEY_D)) {
			move(rotation-90);
		}
		if (controlListeners.KeyListener.isKeyPressed(GLFW_KEY_DOWN) || controlListeners.KeyListener.isKeyPressed(GLFW_KEY_S)) {
			move(rotation+180);
		}
		if (controlListeners.KeyListener.isKeyPressed(GLFW_KEY_UP) || controlListeners.KeyListener.isKeyPressed(GLFW_KEY_W)) {
			move(rotation);
		}
	}

	private void move(double rotation) {
		//Check X collision
		collisionBoxToPos(posX + Ray.calculateRunD(5.0, rotation), posY);
		if (playerDidNotCollide()) {
			posX += Ray.calculateRunD(5.0, rotation);
			lastX = posX;
		}
		collisionBoxToPos(posX,posY + Ray.calculateRiseD(5.0, rotation));
		if (playerDidNotCollide()) {
			posY += Ray.calculateRiseD(5.0, rotation);
			lastY = posY;
		}
		collisionBoxToPos(posX,posY);
		Frame.updateAndDrawWalls(this);
	}

	private boolean playerDidNotCollide() {
		return !(
				map.didCollideAnyObstacle(collisionBox.getVertices()[0],collisionBox.getVertices()[1]) ||
				map.didCollideAnyObstacle(collisionBox.getVertices()[2],collisionBox.getVertices()[3]) ||
				map.didCollideAnyObstacle(collisionBox.getVertices()[4],collisionBox.getVertices()[5]) ||
				map.didCollideAnyObstacle(collisionBox.getVertices()[6],collisionBox.getVertices()[7])
		);
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

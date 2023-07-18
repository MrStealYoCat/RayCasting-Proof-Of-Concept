package sprites;

import map_utils.CollisionBox;

public class Sprite {

	private final String name;
	protected int size;
	protected double posX;
	protected double posY;
	protected double lastX;
	protected double lastY;
	protected CollisionBox collisionBox;
	protected int rotation = -90;

	//TODO add other sprite objects to the game besides sprites.Player
	public Sprite(String name, int size, double posX, double posY) {
		this.name = name;
		this.size = size;
		this.posX = posX;
		this.posY = posY;
		collisionBox = new CollisionBox(
						posX - (size / 2.0),
						posY - (size / 2.0),
						size,
						size,
						90.0
		);
	}

	public double getPosX() {
		return posX;
	}
	public double getPosY() {
		return posY;
	}
	public void collisionBoxToPos(double posX, double posY) {
		collisionBox.setPosX(posX-size/2.0);
		collisionBox.setPosY(posY-size/2.0);
		collisionBox.calculateVertices();
	}
}

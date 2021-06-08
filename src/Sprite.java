public class Sprite {

	protected int size;
	protected double posX;
	protected double posY;
	protected double lastX;
	protected double lastY;

	//TODO add other sprite objects to the game besides Player
	public Sprite(int size, double posX, double posY) {
		this.size = size;
		this.posX = posX;
		this.posY = posY;
	}

	public int getSize() {
		return size;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public double getLastX() {
		return lastX;
	}

	public double getLastY() {
		return lastY;
	}
}

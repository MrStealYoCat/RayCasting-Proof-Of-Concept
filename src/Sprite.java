public class Sprite {

	protected int size;
	protected double posX;
	protected double posY;
	protected double posZ;
	protected double lastX;
	protected double lastY;
	protected double lastZ;

	//TODO add other sprite objects to the game besides Player
	public Sprite(int size, double posX, double posY, double posZ) {
		this.size = size;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
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
	public double getPosZ() {
		return posZ;
	}

	public double getLastX() {
		return lastX;
	}
	public double getLastY() {
		return lastY;
	}
	public double getLastZ() {
		return lastZ;
	}
}

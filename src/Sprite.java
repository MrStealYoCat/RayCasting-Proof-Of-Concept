public class Sprite {

	protected int size;
	protected double posX;
	protected double posY;

	public Sprite(int size, double posX, double posY) {
		this.size = size;
		this.posX = posX;
		this.posY = posY;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
}

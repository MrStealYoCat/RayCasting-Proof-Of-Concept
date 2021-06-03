public class Sprite {

	protected int size;
	protected int posX;
	protected int posY;

	public Sprite(int size, int posX, int posY) {
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

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
}

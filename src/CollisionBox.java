public class CollisionBox {

	private double posX;
	private double posY;
	private double posZ;
	private double width; // X-axis
	private double length; // Y-axis
	private double height; // Z-axis

	public CollisionBox(double posX, double posY, double posZ, double width, double length, double height) {
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.width = width;
		this.length = length;
		this.height = height;
	}

	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	public double getPosZ() {
		return posZ;
	}
	public void setPosZ(double posZ) {
		this.posZ = posZ;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length = length;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public void show() {
		System.out.printf("%f, %f, %f, %f\n",posX, posY, width, length);
	}
}

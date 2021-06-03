public class Ray {

	private double startX;
	private double startY;
	private double lastX;
	private double lastY;
	private double angle; // relative to player in degrees
	private double rise;
	private double run;

	public Ray(double startX, double startY, double angle) {
		this.startX = startX;
		this.startY = startY;
		this.lastX = startX;
		this.lastY = startY;
		this.angle = angle;

		rise = Math.sin(angle*3.14159/180.0);
		run = Math.cos(angle*3.14159/180.0);
	}

	public double getStartX() {
		return startX;
	}
	public double getStartY() {
		return startY;
	}
	public double getLastX() {
		return lastX;
	}
	public double getLastY() {
		return lastY;
	}
	public double getAngle() {
		return angle;
	}
	public double getRise() {
		return rise;
	}
	public double getRun() {
		return run;
	}
	public double getDistance() {
		return Math.sqrt( (startX-lastX)*(startX-lastX) + (startY-lastY)*(startY-lastY) );
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}
	public void setStartY(double startY) {
		this.startY = startY;
	}
	public void setLastX(double lastX) {
		this.lastX = lastX;
	}
	public void setLastY(double lastY) {
		this.lastY = lastY;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
}

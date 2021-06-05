public class Ray {

	private double startX;
	private double startY;
	private double lastX;
	private double lastY;
	private double rise;
	private double run;

	public Ray(double startX, double startY, double startAngle) {
		this.startX = startX;
		this.startY = startY;
		this.lastX = startX;
		this.lastY = startY;

		rise = Math.sin(startAngle*3.14159/180.0);
		run = Math.cos(startAngle*3.14159/180.0);
	}

	public double getLastX() {
		return lastX;
	}
	public double getLastY() {
		return lastY;
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

	public void setLastX(double lastX) {
		this.lastX = lastX;
	}
	public void setLastY(double lastY) {
		this.lastY = lastY;
	}
}

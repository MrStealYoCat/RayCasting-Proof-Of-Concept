public class Ray {

	/* Generates the ray array used for just about everything else */
	public static Ray[] rayGen(int rotation, double posX, double posY, int rayCount) {
		Ray[] rays = new Ray[rayCount];
		int startRays = 60 + rotation;
		int endRays = -60 + rotation;
		double rayDistanceBetween = 1.0*(startRays-endRays)/(rayCount -1);
		for (int i=0;i<rays.length;i++) {
			rays[i] = new Ray(posX,posY,startRays-i*rayDistanceBetween);
		}
		return rays;
	}
	private double startX;
	private double startY;
	private double lastX;
	private double lastY;
	private double rise;
	private double run;
	private boolean collideX;
	private double angle;

	public Ray(double startX, double startY, double angle) {
		this.startX = startX;
		this.startY = startY;
		this.lastX = startX;
		this.lastY = startY;
		this.angle = angle;

		rise = Math.sin(angle*3.14159/180.0);
		run = Math.cos(angle*3.14159/180.0);
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
	public boolean getCollideX() {
		return collideX;
	}
	public double getAngle() {
		return angle;
	}

	public void setLastX(double lastX) {
		this.lastX = lastX;
	}
	public void setLastY(double lastY) {
		this.lastY = lastY;
	}
	public void setCollideX(boolean collideX) {
		this.collideX = collideX;
	}
}

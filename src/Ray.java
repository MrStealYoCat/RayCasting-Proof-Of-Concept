public class Ray {

	/* Generates the ray array used for just about everything else
	*/
	/* Only should be called when moving or basically when location
	 * or rotation of the rays change. Goes through each ray and calculates
	 * everything needed to draw them. Found that this calculation has
	 * heavy CPU usage.
	*/
	public static void processRays(int rotation, double posX, double posY, int rayCount, Map map) {
		// Generate rays
		Ray[] rays = new Ray[rayCount];
		int startRays = 60 + rotation;
		int endRays = -60 + rotation;
		double rayDistanceBetween = 1.0*(startRays-endRays)/(rayCount -1);
		for (int i=0;i<rays.length;i++) {
			rays[i] = new Ray(posX,posY,startRays-i*rayDistanceBetween);
		}
		// Calculate collision
		for (Ray r : rays) {
			while (true) {
				r.setLastX(r.getLastX() + r.getRun());

				if (map.didCollide(r.getLastX(), r.getLastY())) {
					r.setLastY(r.getLastY() + r.getRise());
					r.setCollideX(true);
					break;
				}
				r.setLastY(r.getLastY() + r.getRise());
				if (map.didCollide(r.getLastX(),r.getLastY())) {
					r.setCollideX(false);
					break;
				}
			}
		}
		map.drawWalls(rays);
	}

	private double startX;
	private double startY;
	private double lastX;
	private double lastY;
	private double rise;
	private double run;
	private boolean collideX;
	private double rotation;

	public Ray(double startX, double startY, double rotation) {
		this.startX = startX;
		this.startY = startY;
		this.lastX = startX;
		this.lastY = startY;
		this.rotation = rotation;

		rise = Math.sin(rotation *3.14159/180.0);
		run = Math.cos(rotation *3.14159/180.0);
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

public class Ray {

	/* Generates the ray array used for just about everything else
	*/
	/* Only should be called when moving or basically when location
	 * or rotation of the rays change. Goes through each ray and calculates
	 * everything needed to draw them. Found that this calculation has
	 * heavy CPU usage.
	*/
	public static Ray[] processRays(int rotation, double posX, double posY, double posZ, Map map) {
		// Generate rays
		Ray[] rays = new Ray[RAY_COUNT];
		int startRays = 60 + rotation;
		int endRays = -60 + rotation;
		double rayDistanceBetween = 1.0*(startRays-endRays)/(RAY_COUNT -1);
		for (int i=0;i<rays.length;i++) {
			rays[i] = new Ray(posX,posY,posZ,startRays-i*rayDistanceBetween);
		}
		// Calculate collision
		for (Ray r : rays) {
			while (true) {
				r.setLastX(r.getLastX() + r.getRun());

				if (map.didCollide(r.getLastX(), r.getLastY(), r.getLastZ())) {
					r.setLastY(r.getLastY() + r.getRise());
					r.setCollideX(true);
					break;
				}
				r.setLastY(r.getLastY() + r.getRise());
				if (map.didCollide(r.getLastX(),r.getLastY(), r.getLastZ())) {
					r.setCollideX(false);
					break;
				}
			}
		}
		return rays;
	}

	public static final int RAY_COUNT = 180;
	private final double startX;
	private final double startY;
	private final double startZ;
	private double lastX;
	private double lastY;
	private double lastZ;
	private final double rise;
	private final double run;
	private boolean collideX;

	public Ray(double startX, double startY, double startZ, double rotation) {
		this.startX = startX;
		this.startY = startY;
		this.startZ = startZ;
		this.lastX = startX;
		this.lastY = startY;
		this.lastZ = startZ;

		rise = Math.sin(rotation *3.14159/180.0);
		run = Math.cos(rotation *3.14159/180.0);
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
	public void setLastZ(double lastZ) {
		this.lastZ = lastZ;
	}
	public void setCollideX(boolean collideX) {
		this.collideX = collideX;
	}
}

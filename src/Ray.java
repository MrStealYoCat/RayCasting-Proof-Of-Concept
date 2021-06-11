import java.awt.*;

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
		for (int i=0; i<rays.length; i++) {
			while (true) {
				//System.out.printf("X: %f, Y: %f\n", rays[i].getEndX(), rays[i].getEndY());
				// X Collision
				rays[i].setEndX((rays[i].getEndX() + rays[i].getRun()));
				if ( map.didCollideAnyObstacle(rays[i].getEndX(),rays[i].getEndY()) ) {
					//rays[i].setEndY(rays[i].getEndY() + rays[i].getRise());
					//System.out.printf("Collided X on Obstacle @ (%f,%f)!\n", rays[i].getEndX(), rays[i].getEndY());
					rays[i].setColor(Color.BLUE);
					break;
				}
//				if ( map.didCollideWalls(rays[i].getEndX(), rays[i].getEndY(), rays[i].getEndZ()) ) {
//					//rays[i].setEndY(rays[i].getEndY() + rays[i].getRise());
//					//System.out.printf("Collided X @ (%f,%f)!\n", rays[i].getEndX(), rays[i].getEndY());
//					rays[i].setColor(Color.BLUE);
//					break;
//				}


				// Y Collision
				rays[i].setEndY((rays[i].getEndY() + rays[i].getRise()));
				if ( map.didCollideAnyObstacle(rays[i].getEndX(),rays[i].getEndY()) ) {
					//System.out.printf("Collided Y on Obstacle @ (%f,%f)!\n", rays[i].getEndX(), rays[i].getEndY());
					rays[i].setColor(Color.GREEN);
					break;
				}
//				if ( map.didCollideWalls(rays[i].getEndX(),rays[i].getEndY(), rays[i].getEndZ()) ) {
//					//System.out.printf("Collided Y @ (%f,%f)!\n", rays[i].getEndX(), rays[i].getEndY());
//					rays[i].setColor(Color.GREEN);
//					break;
//				}
			}
//			System.out.println("----------- Collided with something ----------");
//			System.out.printf("          ( %f , %f )\n\n\n", rays[i].getEndX(), rays[i].getEndY());
			rays[i].setDrawWidth((float)(2.0/rays.length));
			rays[i].setDrawHeight((float)(Map.WALL_HEIGHT /rays[i].getDistance()*4));
			rays[i].setDrawX((float)(-1+(2.0/(rays.length))*i));
			rays[i].setDrawY((float)((rays[i].getDrawHeight()/2.0)));
		}
		return rays;
	}

	public static Double calculateRiseR(Double riseDistance, Double rotationInRadians) {
		return riseDistance * Math.sin(rotationInRadians);
	}
	public static Double calculateRiseD(Double riseDistance, Double rotationInDegrees) {
		return calculateRiseR(riseDistance, rotationInDegrees * 3.14159/180);
	}
	public static Double calculateRunR(Double runDistance, Double rotationInRadians) {
		return runDistance * Math.cos(rotationInRadians);
	}
	public static Double calculateRunD(Double runDistance, Double rotationInDegrees) {
		return calculateRunR(runDistance, rotationInDegrees * 3.14159/180);
	}
	public static Double calculateRotation(Double x1, Double x2, Double y1, Double y2) {
		double rise = y2-y1;
		double run = x2-x1;
		double rotation = 0;
		if (run != 0) {
			rotation = (Math.atan(rise/run)*180/3.14159);
		}
		// Account for run being negative else arcTangent will always rotate run positive
		if (rise == 0 && run < 0) {
			rotation = 180;
		}
		return rotation;
	}

	public static final int RAY_COUNT = 180;
	public static final Double MOVE_DISTANCE = 10.0;
	private final double startX;
	private final double startY;
	private final double startZ;
	private double endX;
	private double endY;
	private double endZ;
	private double rise;
	private double run;
	private double rotation;
	private Color color;
	private float drawWidth;
	private float drawHeight;
	private float drawX;
	private float drawY;

	public Ray(double startX, double startY, double startZ, double rotation) {
		this.startX = startX;
		this.startY = startY;
		this.startZ = startZ;
		this.endX = startX;
		this.endY = startY;
		this.endZ = startZ;

		rise = calculateRiseD(MOVE_DISTANCE,rotation);
		run = calculateRunD(MOVE_DISTANCE,rotation);
		this.rotation = rotation;
	}

	public void reset() {
		endX = startX;
		endY = startY;
		endZ = startZ;
	}

	public double getEndX() {
		return endX;
	}
	public double getEndY() {
		return endY;
	}
	public double getEndZ() {
		return endZ;
	}
	public double getRise() {
		return rise;
	}
	public double getRun() {
		return run;
	}
	public double getDistance() {
		return Math.sqrt( (startX- endX)*(startX- endX) + (startY- endY)*(startY- endY) );
	}
	public Color getColor() {
		return color;
	}
	public float getDrawWidth() {
		return drawWidth;
	}
	public float getDrawX() {
		return drawX;
	}
	public float getDrawY() {
		return drawY;
	}
	public float getDrawHeight() {
		return drawHeight;
	}

	public void setEndX(double endX) {
		this.endX = endX;
	}
	public void setEndY(double endY) {
		this.endY = endY;
	}
	public void setEndZ(double endZ) {
		this.endZ = endZ;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setDrawWidth(float drawWidth) {
		this.drawWidth = drawWidth;
	}
	public void setDrawHeight(float drawHeight) {
		this.drawHeight = drawHeight;
	}
	public void setDrawX(float drawX) {
		this.drawX = drawX;
	}
	public void setDrawY(float drawY) {
		this.drawY = drawY;
	}
}

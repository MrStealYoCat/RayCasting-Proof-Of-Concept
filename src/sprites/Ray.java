package sprites;

import map_utils.Map;

import java.awt.*;

public class Ray {

	/* Generates the ray array used for just about everything else
	*/
	/* Only should be called when moving or basically when location
	 * or rotation of the rays change. Goes through each ray and calculates
	 * everything needed to draw them. Found that this calculation has
	 * heavy CPU usage.
	*/
	public static Ray[] processRays(int rotation, double posX, double posY, Map map) {
		// Generate rays
		Ray[] rays = new Ray[RAY_COUNT];
		int startRays = 60 + rotation;
		int endRays = -60 + rotation;
		double rayDistanceBetween = 1.0*(startRays-endRays)/(RAY_COUNT -1);
		for (int i=0;i<rays.length;i++) {
			rays[i] = new Ray(posX,posY,startRays-i*rayDistanceBetween);
		}
		// Calculate collision
		for (int i=0; i<rays.length; i++) {
			while (true) {
				// X Collision
				rays[i].setEndX((rays[i].getEndX() + rays[i].getRun()));
				if ( map.didCollideAnyObstacle(rays[i].getEndX(),rays[i].getEndY()) ) {
					rays[i].setColor(Color.RED);
					break;
				}
				if ( map.didCollideMapBoundary(rays[i].getEndX(),rays[i].getEndY()) ) {
					rays[i].setColor(Color.BLUE);
					break;
				}


				// Y Collision
				rays[i].setEndY((rays[i].getEndY() + rays[i].getRise()));
				if ( map.didCollideAnyObstacle(rays[i].getEndX(),rays[i].getEndY()) ) {
					rays[i].setColor(Color.ORANGE);
					break;
				}
				if ( map.didCollideMapBoundary(rays[i].getEndX(),rays[i].getEndY()) ) {
					rays[i].setColor(Color.GREEN);
					break;
				}
			}
			rays[i].setDrawWidth((float)(2.0/rays.length));
			rays[i].setDrawHeight( (float)
							(Map.WALL_HEIGHT /rays[i].getDistance())
			);
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
	private double endX;
	private double endY;
	private final double rise;
	private final double run;
	private final double rotation;
	private Color color;
	private float drawWidth;
	private float drawHeight;
	private float drawX;
	private float drawY;

	public Ray(double startX, double startY, double rotation) {
		this.startX = startX;
		this.startY = startY;
		this.endX = startX;
		this.endY = startY;
		this.rotation = rotation;

		rise = calculateRiseD(MOVE_DISTANCE,rotation);
		run = calculateRunD(MOVE_DISTANCE,rotation);
	}

	public double getEndX() {
		return endX;
	}
	public double getEndY() {
		return endY;
	}
	public double getRise() {
		return rise;
	}
	public double getRun() {
		return run;
	}
	public double getRotation() {
		return rotation;
	}
	public double getDistance() {
		return Math.sqrt( Math.pow(startX- endX,2) + Math.pow(startY- endY,2) );
		//return Math.cos(rotation*(3.14159/180))*dp;
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

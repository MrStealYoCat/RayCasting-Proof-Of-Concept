import java.util.*;

public class Obstacle {

	private final Double[] vertices;
	private List<CollisionBox> collisionBoxes;

	public Obstacle(int collisionSize, Double[] vertices) {
		this.vertices = vertices;
		addCollision(collisionSize);
	}

	private void addCollision(int collisionSize) {
		for (int i=0;i<vertices.length-2;i+=2) {
			//make 1x1 collision squares from one vertex to another
			double rise = vertices[i+3]-vertices[i+1];
			double run = vertices[i+2]-vertices[i];
			double wholeDistance = Math.sqrt( Math.pow(run, 2) + Math.pow(rise, 2) );
			// Set rotation to be +-90 and if run == 0 then don't change it.
			// Have to multiply by -1 since java y axis is flipped.
			// Doing these extra steps so I can specify the distance between collision.
			double rotation = -1.0 * (rise/Math.abs(rise))*90;
			if (run != 0) {
				rotation = (Math.atan(rise/run)*180/3.14159);
			}
			double distance = 1;
			for (int j=0; j<(int)wholeDistance; j++) {
				collisionBoxes.add(new CollisionBox(
								vertices[i] + Ray.calculateRunD(distance,rotation)*j,
								vertices[i+1] + Ray.calculateRiseD(distance,rotation)*j,
								0,
								1,
								1,
								Map.WALL_HEIGHT //Obstacle height
								)
				);
			}
			// Add a collision box in the corner if the for loop didn't make it.
			if (wholeDistance%1 != 0) {
				collisionBoxes.add(new CollisionBox(
												vertices[i+2],
												vertices[i+3],
												0,
												1,
												1,
												Map.WALL_HEIGHT //Obstacle height
								)
				);
			}
		}
	}

	public Double[] getVertices() {
		return vertices;
	}
	public List<CollisionBox> getCollisionBoxes() {
		return collisionBoxes;
	}
}

import java.util.*;

public class Obstacle {

	private final Double[] vertices;
	private List<CollisionBox> collisionBoxes = new ArrayList<CollisionBox>();
	private int collisionSize;

	public Obstacle(int collisionSize, Double[] vertices) {
		if (vertices.length % 2 != 0) {
			throw new IllegalArgumentException(
							"Vertices array does not have a matching pair of (x,y) for every vertex"
			);
		}
		if (!(vertices.length >= 6 )) {
			throw new IllegalArgumentException(
							"You need at least three (x,y) vertices to make a shape"
			);
		}
		this.vertices = vertices;
		this.collisionSize = collisionSize;
		addCollisionBoxes(collisionSize);
		showCollision();
	}

	private void addCollisionBoxes(int collisionSize) {
		for (int i=0; i<vertices.length-2; i+=2) {
			addCollisionLine(
							collisionSize,
							vertices[i],
							vertices[i+1],
							vertices[i+2],
							vertices[i+3]
			);
		}
		// Connect last vertex back to the beginning
		addCollisionLine(
						collisionSize,
						vertices[vertices.length-2],
						vertices[0],
						vertices[vertices.length-1],
						vertices[1]);
	}

	private void addCollisionLine(int collisionSize, Double x1, Double x2, Double y1, Double y2) {
		//make 1x1 collision squares from one vertex to another
		double rise = y2-y1;
		double run = x2-x1;
		double wholeDistance = Math.sqrt( Math.pow(run, 2) + Math.pow(rise, 2) );
		// Set rotation to be +-90 and if run == 0 then don't change it.
		// Doing these extra steps so I can specify the distance between collision.
		double rotation = 1.0 * (rise/Math.abs(rise))*90;
		if (run != 0) {
			rotation = (Math.atan(rise/run)*180/3.14159);
		}
		// Account for run being negative else arctan will always rotate run positive
		if (rise == 0 && run < 0) {
			rotation = 180;
		}
		double distance = 1;
		for (int j=0; j<(int)wholeDistance; j++) {
			collisionBoxes.add(new CollisionBox(
							x1 + Ray.calculateRunD(distance,rotation)*j,
							y1 + Ray.calculateRiseD(distance,rotation)*j,
							0,
							collisionSize, // Length
							collisionSize, // Width
							Map.WALL_HEIGHT //Obstacle height
							)
			);
		}
//		// Add a collision box in the corner if the for loop didn't make it.
//		if (wholeDistance%1 != 0) {
//			collisionBoxes.add(new CollisionBox(
//							x2,
//							y2,
//							0,
//							collisionSize,
//							collisionSize,
//							Map.WALL_HEIGHT //Obstacle height
//							)
//			);
//		}
	}

	public Double[] getVertices() {
		return vertices;
	}
	public List<CollisionBox> getCollisionBoxes() {
		if (collisionBoxes.size() != 0) {
			return collisionBoxes;
		} else {
			throw new ArrayIndexOutOfBoundsException(
							"Collision boxes haven't been set yet!!!"
			);
		}
	}
	public int getCollisionSize() {
		return collisionSize;
	}
	public void showCollision() {
		for (CollisionBox b : collisionBoxes) {
			b.show();
		}
	}
}

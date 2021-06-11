import java.util.ArrayList;
import java.util.List;

public class Map {
	private static final int WALL_SIZE = 100;
	public static final int WALL_HEIGHT = 90;
	private final int[][] mapArray;
	private List<Obstacle> obstacles = new ArrayList<Obstacle>();

	public Map(int[][] mapArray) {
		this.mapArray = mapArray;
	}

	//TODO calculate collision in terms of hitting an
	// obstacle rather than just parallel cubes.
	public boolean didCollideAnyObstacle(double colliderX, double colliderY) {
		for (int i=0; i<obstacles.size();i++) {
			if ( didCollideObstacle(obstacles.get(i),colliderX,colliderY) ) {
				return true;
			}
		}
		return false;
	}

	public boolean didCollideObstacle(Obstacle obstacle, double colliderX, double colliderY) {
		List<CollisionBox> collisionBoxes = obstacle.getCollisionBoxes();
		int collisionSize = obstacle.getCollisionSize();
		for (int i=0; i<collisionBoxes.size(); i++) {
			Double x1 = collisionBoxes.get(i).getVertices()[0];
			Double y1 = collisionBoxes.get(i).getVertices()[1];
			Double x2 = collisionBoxes.get(i).getVertices()[4];
			Double y2 = collisionBoxes.get(i).getVertices()[5];
			Double rotation = collisionBoxes.get(i).getRotation();
			//System.out.printf("Rotation: %f\n", rotation);
			if (
					Obstacle.calculateRotation(x1, colliderX, y1, colliderY) <= rotation
			 && Obstacle.calculateRotation(x1, colliderX, y1, colliderY) >= rotation - 90
			 && Obstacle.calculateRotation(x2, colliderX, y2, colliderY) <= rotation - 180
			 && Obstacle.calculateRotation(x2, colliderX, y2, colliderY) >= rotation - 270
			) {
				return true;
			}
		}

		return false;
	}

	/* Used for checking collision with the map with a pair of coordinates.
	* Only used for basic collision with square cubes, cannot detect
	*  anything diagonally!!! */
	public boolean didCollideWalls(double colliderX, double colliderY, double colliderZ) {
		return
			// Left and top of 2D box
			mapArray[(int)(colliderY/ WALL_SIZE)][(int)(colliderX/ WALL_SIZE)] != 0
			// Right of 2D box
	 || mapArray[(int)(colliderY/ WALL_SIZE)][(int)((colliderX+ WALL_SIZE)/ WALL_SIZE)-1] != 0
			// Bottom of 2D box
	 || mapArray[(int)((colliderY+ WALL_SIZE)/ WALL_SIZE)-1][(int)(colliderX/ WALL_SIZE)] != 0;
	}

	public void addObstacle(Obstacle obstacle) {
		obstacles.add(obstacle);
	}
	public Obstacle getObstacle(int index) {
		return obstacles.get(index);
	}
}

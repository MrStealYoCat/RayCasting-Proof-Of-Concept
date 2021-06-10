public class Map {
	private static final int WALL_SIZE = 100;
	public static final int WALL_HEIGHT = 90;
	private final int[][] mapArray;

	public Map(int[][] mapArray) {
		this.mapArray = mapArray;
	}

	//TODO calculate collision in terms of hitting an
	// obstacle rather than just parallel cubes.

	public boolean didCollideObstacle(CollisionBox collisionBox) {


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
}

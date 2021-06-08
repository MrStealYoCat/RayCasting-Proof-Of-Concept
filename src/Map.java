import static org.lwjgl.opengl.GL11.*;

public class Map {
	private static final int WALL_SIZE = 100;
	public static final int WALL_HEIGHT = 75;
	private final int[][] mapArray;

	public Map(int[][] mapArray) {
		this.mapArray = mapArray;
	}

	/* Used for checking collision with the map with a pair of coordinates */
	public boolean didCollide(double colliderX, double colliderY) {
		return
			// Left and top of 2D box
			mapArray[(int)(colliderY/ WALL_SIZE)][(int)(colliderX/ WALL_SIZE)] == 1
			// Right of 2D box
	 || mapArray[(int)(colliderY/ WALL_SIZE)][(int)((colliderX+ WALL_SIZE)/ WALL_SIZE)-1] == 1
			// Bottom of 2D box
	 || mapArray[(int)((colliderY+ WALL_SIZE)/ WALL_SIZE)-1][(int)(colliderX/ WALL_SIZE)] == 1;
	}
}

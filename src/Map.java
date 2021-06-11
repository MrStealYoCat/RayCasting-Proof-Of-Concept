import java.util.ArrayList;
import java.util.List;

public class Map {
	public static final int WALL_HEIGHT = 90;
	private final int length;
	private final int width;
	private final int height;
	private List<Obstacle> obstacles = new ArrayList<Obstacle>();

	public Map(int length, int width, int height) {
		this.length = length;
		this.width = width;
		this.height = height;
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
		return (
						obstacle.getVertices()[0] <= colliderX && colliderX <= obstacle.getVertices()[4]
						&& obstacle.getVertices()[1] <= colliderY && colliderY <= obstacle.getVertices()[5]
						)
						|| (colliderX > width)
						|| (colliderX < 0)
						|| (colliderY > length)
						|| (colliderY < 0);
	}

	public void addObstacle(Obstacle obstacle) {
		obstacles.add(obstacle);
	}
	public Obstacle getObstacle(int index) {
		return obstacles.get(index);
	}
}

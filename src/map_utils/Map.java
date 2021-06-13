package map_utils;

import java.util.ArrayList;
import java.util.List;

public class Map {
	public static final int WALL_HEIGHT = 90;
	private final int length;
	private final int width;
	private final List<Obstacle> obstacles = new ArrayList<>();

	public Map(int length, int width) {
		this.length = length;
		this.width = width;
	}

	//TODO calculate collision in terms of hitting any
	// shaped object rather than just parallel cubes.
	public boolean didCollideAnyObstacle(double colliderX, double colliderY) {
		for (Obstacle obstacle : obstacles) {
			if ( didCollideObstacle(obstacle,colliderX,colliderY) ) {
				return true;
			}
		}
		return false;
	}

	public boolean didCollideMapBoundary(double colliderX, double colliderY) {
		return (
						(colliderX > width)
						|| (colliderX < 0)
						|| (colliderY > length)
						|| (colliderY < 0)
						);
	}
	public boolean didCollideObstacle(Obstacle obstacle, double colliderX, double colliderY) {
		return (
						obstacle.getVertices()[0] <= colliderX && colliderX <= obstacle.getVertices()[4]
						&& obstacle.getVertices()[1] <= colliderY && colliderY <= obstacle.getVertices()[5]
						);
	}
	public void addObstacle(Obstacle obstacle) {
		obstacles.add(obstacle);
	}
}

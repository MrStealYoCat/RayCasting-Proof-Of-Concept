import java.util.*;

public class Obstacle {

	private final Double[] vertices;
	private List<CollisionBox> collisionBoxes;

	public Obstacle(int collisionSize, Double[] vertices) {
		this.vertices = vertices;
		addCollision(collisionSize);
	}

	private void addCollision(int collisionSize) {
		for (int i=0;i<vertices.length;i+=2) {
			//make 1x1 collision squares from one vertice to another
		}
	}

	public Double[] getVertices() {
		return vertices;
	}
	public List<CollisionBox> getCollisionBoxes() {
		return collisionBoxes;
	}
}

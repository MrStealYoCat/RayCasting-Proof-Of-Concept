package map_utils;

import sprites.Ray;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Obstacle {

	private final Double[] vertices;
	private final List<CollisionBox> collisionBoxes = new ArrayList<>();
	private final String name;
	private Color color;

	public Obstacle(int collisionSize, Double[] vertices, String name, Color color) {
		this.name = name;
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
		this.color = color;
		addCollisionBoxes(collisionSize);
		//showCollision();
	}
	public Obstacle(int collisionSize, Double[] vertices, String name) {
		this(collisionSize,vertices,name,null);

	}

	private void addCollisionBoxes(int collisionSize) {
		for (int i=0; i<vertices.length-3; i+=2) {
			addCollisionBox(
							collisionSize,
							vertices[i],
							vertices[i+2],
							vertices[i+1],
							vertices[i+3]
			);

		}
		// Connect last vertex back to the beginning
		addCollisionBox(
						collisionSize,
						vertices[vertices.length-2],
						vertices[0],
						vertices[vertices.length-1],
						vertices[1]
		);
		System.out.println(name);
		for (CollisionBox b : collisionBoxes) {

			System.out.println(b.getRotation());
		}
		System.out.println();
	}

	private void addCollisionBox(int width, Double x1, Double x2, Double y1, Double y2) {
		double rise = y2-y1;
		double run = x2-x1;
		double wholeDistance = Math.sqrt( Math.pow(run, 2) + Math.pow(rise, 2) );
		// Set rotation to be +-90 and if run == 0 then don't change it.
		// Doing these extra steps so I can specify the distance between collision.
		double rotation = Ray.calculateRotation(x1, x2, y1, y2);
		System.out.println(name);
		collisionBoxes.add(new CollisionBox(
			x1,
			y1,
			wholeDistance,
			width,
			rotation
		));
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
	public void showCollision() {
		for (CollisionBox b : collisionBoxes) {
			b.show();
		}
	}
}

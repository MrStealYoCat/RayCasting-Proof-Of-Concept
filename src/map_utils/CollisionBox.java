package map_utils;

import sprites.Ray;

public class CollisionBox {

	private double posX;
	private double posY;
	private final double width; // X-axis
	private final double length; // Y-axis
	private final Double rotation; // Right side of first point facing inward
	private final Double[] vertices = new Double[8];

	public CollisionBox(double posX, double posY, double length, double width, Double rotation) {
		this.posX = posX;
		this.posY = posY;
		this.length = length;
		this.width = width;
		this.rotation = rotation;
		calculateVertices();
	}

	public void calculateVertices() {
		vertices[0] = posX;
		vertices[1] = posY;
		vertices[2] = vertices[0] + Ray.calculateRunD(length,rotation);
		vertices[3] = vertices[1] + Ray.calculateRiseD(length,rotation);
		vertices[4] = vertices[2] + Ray.calculateRunD(width,rotation-90);
		vertices[5] = vertices[3] + Ray.calculateRiseD(width,rotation-90);
		vertices[6] = vertices[4] + Ray.calculateRunD(length,rotation-180);
		vertices[7] = vertices[5] + Ray.calculateRiseD(length,rotation-180);
		//showVertices();
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	public Double[] getVertices() {
		return vertices;
	}
	public Double getRotation() {
		return rotation;
	}

	public void show() {
		System.out.printf("%f, %f, %f, %f, %f\n",posX, posY, width, length, rotation);
	}
	public void showVertices() {
		for (Double d : vertices) {
			System.out.printf("%f\n", d);
		}
		System.out.println();
	}
}

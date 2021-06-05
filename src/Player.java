import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Player extends Sprite {

	private int rotation = 90;
	private final int rayCount = 180;
	private double startRays; // = 45 + rotation;
	private double endRays; // = -45 + rotation;
	private double rayDistanceBetween; // = (startRays-endRays)/(rayCount-1);
	private Ray[] rays = new Ray[rayCount];
	private int[][] map;
	private final int wallSize = 100;

	public Player(int size, int posX, int posY, int[][] map) {
		super(size, posX, posY);
		this.map = map;
		calculateRays();
	}

	/* Generates the ray array used for just about everything else */
	public void rayGen() {
		for (int i=0;i<rays.length;i++) {
			rays[i] = new Ray(posX,posY,startRays-i*rayDistanceBetween);
		}
	}

	/* Only should be called when moving or basically when location
	 * or rotation of the rays change. Goes through each ray and calculates
	 * everything needed to draw them. Found that this calculation has
	 * heavy CPU usage.
	 */
	public void calculateRays() {
		renewRotation();
		rayGen();
		for (Ray r : rays) {
			while (r.getLastX() > 0.0
							&& r.getLastX() < map[0].length*wallSize
							&& r.getLastY() > 0.0
							&& r.getLastY() < map.length*wallSize) {
				r.setLastX(r.getLastX() + r.getRun());
				r.setLastY(r.getLastY() + r.getRise());
				if (didCollideX((int) r.getLastX(), (int) r.getLastY())) {
					r.setCollideX(true);
					break;
				}
				if (didCollideY((int)r.getLastX(),(int)r.getLastY())) {
					r.setCollideX(false);
					break;
				}
			}
		}
	}

	/* Used for checking collision with the map with a pair of coordinates */
	public boolean didCollideX(double colliderX, double colliderY) {
		for (int i=0; i<map.length;i++) {
			for (int j=0; j<map[i].length; j++) {
				int y = i*wallSize;
				int x = j*wallSize;
				// Left of box collision
				if (map[i][j] == 1 && colliderX == x && colliderY >= y && colliderY <= y+wallSize) {
					return true;
				// Right of box collision
				} else if (map[i][j] == 1 && colliderX == x+wallSize && colliderY >= y && colliderY <= y+wallSize) {
					return true;
				}
			}
		}
		return false;
	}
	/* Used for checking collision with the map with a pair of coordinates */
	public boolean didCollideY(double colliderX, double colliderY) {
		for (int i=0; i<map.length;i++) {
			for (int j=0; j<map[i].length; j++) {
				int y = i*wallSize;
				int x = j*wallSize;
				// Top of box collision
				if (map[i][j] == 1 && colliderY == y && colliderX >= x && colliderX <= x+wallSize) {
					return true;
					// Bottom of box collision
				} else if (map[i][j] == 1 && colliderY == y+wallSize && colliderX >= x && colliderX <= x+wallSize) {
					return true;
				}
			}
		}
		return false;
	}

	/* Function used to draw "3D" walls around the player object. Uses openGL. */
	public void drawWalls() {

		// Have to use floats as other functions are deprecated
		//glBegin(GL_QUADS); // Start to get a set of 4 vertices for a rectangle
		//glColor3f(1.0f, 0.0f, 0.0f); // red
			/*
			 In openGL coordinates min,max are (-1.0,1.0) for both axis
			 regardless of window size. This means that the width can be stretched
			 if not calculated properly.
			*/


		// 3D walls
		for (int i=rays.length-1;i>=0;i--) {
			float width =(float)(2.0/rays.length);
			float height = (float)(50.0/rays[i].getDistance()*4);
			float x = (float)(-1+(2.0/(rays.length))*(rays.length-1-i));
			float y = (float)((height/2.0));
			glBegin(GL_QUADS);
			if (rays[i].getCollideX()) {
				glColor3f(0.0f, 0.0f, 1.0f);
			} else {
				glColor3f(0.0f, 1.0f, 0.0f);
			}
			// Remember to go counter clockwise when rendering so the "face" is towards me
			glVertex2f(x,y);
			glVertex2f(x,y-height);
			glVertex2f(x+width,y-height);
			glVertex2f(x+width,y);
			glEnd();
		}
	}

	/* Checks normal game keys for movement or rotation */
	public void keyPressed() {
		if (KeyListener.isKeyPressed(GLFW_KEY_LEFT) || KeyListener.isKeyPressed(GLFW_KEY_A)) {
			posX += 5 * Math.cos((rotation - 90) * 3.14159 / 180);
			posY += 5 * Math.sin((rotation - 90) * 3.14159 / 180);
			calculateRays();
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT) || KeyListener.isKeyPressed(GLFW_KEY_D)) {
			posX += 5 * Math.cos((rotation + 90) * 3.14159 / 180);
			posY += 5 * Math.sin((rotation + 90) * 3.14159 / 180);
			calculateRays();
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_DOWN) || KeyListener.isKeyPressed(GLFW_KEY_S)) {
			posX += 5 * Math.cos((rotation + 180) * 3.14159 / 180);
			posY += 5 * Math.sin((rotation + 180) * 3.14159 / 180);
			calculateRays();
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_UP) || KeyListener.isKeyPressed(GLFW_KEY_W)) {
			posX += 5 * Math.cos(rotation * 3.14159 / 180);
			posY += 5 * Math.sin(rotation * 3.14159 / 180);
			calculateRays();
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_Q)) {
			rotation -= 5;
			calculateRays();
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_E)) {
			rotation += 5;
			calculateRays();
		}
	}

	/* Check is mouse has been moved on the x plane for player rotation */
	public void mouseMoved() {
		if (MouseListener.getXPos() != 0) {
			rotation -= MouseListener.getDX()/2;
			calculateRays();
		}
	}

	/* Helper method for making sure variables regarding rotation are up to date
	 * and correct */
	public void renewRotation() {
		rotation = rotation % 360;
		startRays = 60 + rotation;
		endRays = -60 + rotation;
		rayDistanceBetween = (startRays-endRays)/(rayCount-1);
	}
}

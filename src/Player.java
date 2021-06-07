import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Player extends Sprite {

	private int rotation = 90;
	private static final int RAY_COUNT = 180;
	private Ray[] rays = new Ray[RAY_COUNT];
	private int[][] map;
	private final int wallSize = 100;
	private final int wallHeight = 75;

	public Player(int size, int posX, int posY, int[][] map) {
		super(size, posX, posY);
		this.map = map;
		calculateRays();
	}

	/* Only should be called when moving or basically when location
	 * or rotation of the rays change. Goes through each ray and calculates
	 * everything needed to draw them. Found that this calculation has
	 * heavy CPU usage.
	 */
	public void calculateRays() {
		rays = Ray.rayGen(rotation, posX, posY, RAY_COUNT);
		for (Ray r : rays) {
			while (true) {
				r.setLastX(r.getLastX() + r.getRun());

				if (didCollide(r.getLastX(), r.getLastY())) {
					r.setLastY(r.getLastY() + r.getRise());
					r.setCollideX(true);
					break;
				}
				r.setLastY(r.getLastY() + r.getRise());
				if (didCollide(r.getLastX(),r.getLastY())) {
					r.setCollideX(false);
					break;
				}
			}
		}
	}

	/* Used for checking collision with the map with a pair of coordinates */
	public boolean didCollide(double colliderX, double colliderY) {
		return
			// Left and top of 2D box
			map[(int)(colliderY/wallSize)][(int)(colliderX/wallSize)] == 1
			// Right of 2D box
	 || map[(int)(colliderY/wallSize)][(int)((colliderX+wallSize)/wallSize)-1] == 1
			// Bottom of 2D box
	 || map[(int)((colliderY+wallSize)/wallSize)-1][(int)(colliderX/wallSize)] == 1;
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
			float height = (float)(wallHeight/rays[i].getDistance()*4);
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
		double x;
		double y;
		if (KeyListener.isKeyPressed(GLFW_KEY_LEFT) || KeyListener.isKeyPressed(GLFW_KEY_A)) {
			x = posX + 5 * Math.cos((rotation - 90) * 3.14159 / 180);
			y = posY + 5 * Math.sin((rotation - 90) * 3.14159 / 180);
			if (!didCollide(x,y)) {
				posX = x;
				posY = y;
				calculateRays();
			}
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT) || KeyListener.isKeyPressed(GLFW_KEY_D)) {
			x = posX + 5 * Math.cos((rotation + 90) * 3.14159 / 180);
			y = posY + 5 * Math.sin((rotation + 90) * 3.14159 / 180);
			if (!didCollide(x,y)) {
				posX = x;
				posY = y;
				calculateRays();
			}
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_DOWN) || KeyListener.isKeyPressed(GLFW_KEY_S)) {
			x = posX + 5 * Math.cos((rotation + 180) * 3.14159 / 180);
			y = posY + 5 * Math.sin((rotation + 180) * 3.14159 / 180);
			if (!didCollide(x,y)) {
				posX = x;
				posY = y;
				calculateRays();
			}
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_UP) || KeyListener.isKeyPressed(GLFW_KEY_W)) {
			x = posX + 5 * Math.cos(rotation * 3.14159 / 180);
			y = posY + 5 * Math.sin(rotation * 3.14159 / 180);
			if (!didCollide(x,y)) {
				posX = x;
				posY = y;
				calculateRays();
			}
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
			rotation = (rotation - (int)(MouseListener.getDX()/2)) % 360;
			System.out.println(rotation);
			calculateRays();
		}
	}
}

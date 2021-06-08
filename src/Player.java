import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Player extends Sprite {

	private int rotation = -90;
	public static final int RAY_COUNT = 180;
	private final Map map;

	public Player(int size, int posX, int posY, Map map) {
		super(size, posX, posY);
		this.map = map;
	}

	/* Checks normal game keys for movement or rotation */
	public void keyPressed() {
		if (map.didCollide(posX, posY)) {
			posX = lastX;
			posY = lastY;
			return;
		}
		lastX = posX;
		lastY = posY;
		if (KeyListener.isKeyPressed(GLFW_KEY_LEFT) || KeyListener.isKeyPressed(GLFW_KEY_A)) {
			posX += 5 * Math.cos((rotation - 90) * 3.14159 / 180);
			posY += 5 * Math.sin((rotation - 90) * 3.14159 / 180);
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT) || KeyListener.isKeyPressed(GLFW_KEY_D)) {
			posX += 5 * Math.cos((rotation + 90) * 3.14159 / 180);
			posY += 5 * Math.sin((rotation + 90) * 3.14159 / 180);
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_DOWN) || KeyListener.isKeyPressed(GLFW_KEY_S)) {
			posX += 5 * Math.cos((rotation + 180) * 3.14159 / 180);
			posY += 5 * Math.sin((rotation + 180) * 3.14159 / 180);
		}
		if (KeyListener.isKeyPressed(GLFW_KEY_UP) || KeyListener.isKeyPressed(GLFW_KEY_W)) {
			posX += 5 * Math.cos(rotation * 3.14159 / 180);
			posY += 5 * Math.sin(rotation * 3.14159 / 180);
		}
	}

	/* Check is mouse has been moved on the x plane for player rotation */
	public void mouseMoved() {
		if (MouseListener.getDX() != 0) {
			rotation = (rotation - (int)(MouseListener.getDX()/2)) % 360;
		}
	}

	/* Function used to draw "3D" walls around the player object. Uses openGL. */
	public void drawWalls() {

		// Make sure rays are up to date before drawing
		Ray[] rays = Ray.processRays(rotation, posX, posY, RAY_COUNT, map);

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
			float height = (float)(Map.WALL_HEIGHT /rays[i].getDistance()*4);
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
}

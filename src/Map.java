import static org.lwjgl.opengl.GL11.*;

public class Map {
	private static final int WALL_SIZE = 100;
	private static final int WALL_HEIGHT = 75;
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

	/* Function used to draw "3D" walls around the player object. Uses openGL. */
	public void drawWalls(Ray[] rays) {

		// Have to use floats as other functions are deprecated
		//glBegin(GL_QUADS); // Start to get a set of 4 vertices for a rectangle
		//glColor3f(1.0f, 0.0f, 0.0f); // red
			/*
			 In openGL coordinates min,max are (-1.0,1.0) for both axis
			 regardless of window size. This means that the width can be stretched
			 if not calculated properly.
			*/

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

		// 3D walls
		for (int i=rays.length-1;i>=0;i--) {
			float width =(float)(2.0/rays.length);
			float height = (float)(WALL_HEIGHT /rays[i].getDistance()*4);
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
		glFlush(); // render now
	}

	public static int getWallSize() {
		return WALL_SIZE;
	}

	public static int getWallHeight() {
		return WALL_HEIGHT;
	}

	public int[][] getMapArray() {
		return mapArray;
	}
}

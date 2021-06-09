import static org.lwjgl.opengl.GL11.*;

public class Frame {

	/* Function used to draw "3D" walls around the player object. Uses openGL. */
	public static void drawWalls(Player player) {

		// Make sure rays are up to date before drawing
		Ray[] rays = Ray.processRays(
						player.getRotation(),
						player.getPosX(),
						player.getPosY(),
						player.getPlayerMap()
		);

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

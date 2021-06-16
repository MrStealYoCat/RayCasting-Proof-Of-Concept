package display;

import sprites.Player;
import sprites.Ray;

import static org.lwjgl.opengl.GL11.*;

public class Frame {

	private static Frame instance;
	private Ray[] rays;

	public static Frame get(Player player) {
		if (instance == null) {
			instance = new Frame();
			Frame.updateAndDrawWalls(player);
		}
		return instance;
	}

	public static void updateAndDrawWalls(Player player) {
		get(player).rays = Ray.processRays(
						player.getRotation(),
						player.getPosX(),
						player.getPosY(),
						player.getPlayerMap()
		);
		drawWalls(player);
	}

	/* Function used to draw "3D" walls around the player object. Uses openGL. */
	public static void drawWalls(Player player) {
		Ray[] rays = get(player).rays;
		// 3D walls
		for (int i=rays.length-1;i>=0;i--) {
			glBegin(GL_QUADS);
			glColor3f(
							rays[i].getColor().getRed(),
							rays[i].getColor().getGreen(),
							rays[i].getColor().getBlue()
			);
			// Remember to go counter clockwise when rendering so the "face" is towards me
			glVertex2f(
							rays[i].getDrawX(),
							rays[i].getDrawY()
			);
			glVertex2f(
							rays[i].getDrawX(),
							rays[i].getDrawY()-rays[i].getDrawHeight()
			);
			glVertex2f(
							rays[i].getDrawX()+rays[i].getDrawWidth(),
							rays[i].getDrawY()-rays[i].getDrawHeight()
			);
			glVertex2f(
							rays[i].getDrawX()+rays[i].getDrawWidth(),
							rays[i].getDrawY()
			);
			glEnd();

			drawFloor(rays[i].getDrawX(),
							rays[i].getDrawWidth(),
							rays[i].getDrawY()-rays[i].getDrawHeight()
			);
		}
	}

	public static void drawFloor(float drawX,float drawWidth,float drawYOffset) {
		// Draw floor as well
		glBegin(GL_QUADS);
		glColor3f((float)0.5,(float)0.5,(float)0.5);
		glVertex2f(
						drawX,
						drawYOffset
		);
		glVertex2f(
						drawX,
						-1
		);
		glVertex2f(
						drawX+drawWidth,
						-1
		);
		glVertex2f(
						drawX+drawWidth,
						drawYOffset
		);
		glEnd();
	}
}

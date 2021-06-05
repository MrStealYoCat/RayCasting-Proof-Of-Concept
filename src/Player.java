import java.awt.*;
import java.awt.event.KeyEvent;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
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
			while (r.getLastX() > 0.0 && r.getLastX() < map[0].length*wallSize && r.getLastY() > 0.0 && r.getLastY() < map.length*wallSize) {
				//System.out.println(r.getLastX() + " : " + r.getLastY());
				r.setLastX(r.getLastX() + r.getRun());
				r.setLastY(r.getLastY() + r.getRise());
				if (didCollide((int)r.getLastX(),(int)r.getLastY()))
					break;
			}
		}
	}
	// unused for now
	public void drawRays(Graphics graphics) {
		for (Ray r : rays) {
			//graphics.drawLine((int)r.getStartX(),(int)r.getStartY(),(int)r.getLastX(),(int)r.getLastY());
		}
	}

	public boolean didCollide(double colliderX, double colliderY) {
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
				// Top of box collision
				} else if (map[i][j] == 1 && colliderY == y && colliderX >= x && colliderX <= x+wallSize) {
					return true;
				// Bottom of box collision
				} else if (map[i][j] == 1 && colliderY == y+wallSize && colliderX >= x && colliderX <= x+wallSize) {
					return true;
				}
			}
		}
		return false;
	}
	// Unused for now
	public void drawPlayer(Graphics graphics) {
		//graphics.setColor(Color.GREEN);
		//graphics.fillOval(posX-size/2,posY-size/2,size,size);
	}

	public void drawWalls(Window window) {

//		// 2D Walls to the left
//		graphics.setColor(Color.WHITE);
//		for (int i=0; i<map.length; i++) {
//			for (int j=0; j<map[i].length; j++) {
//				if (map[i][j] == 1)
//					graphics.fillRect(wallSize*j,wallSize*i,wallSize,wallSize);
//			}
//		}

		// Have to use floats as other functions are deprecated
		//glBegin(GL_QUADS); // Start to get a set of 4 vertices for a rectangle
		//glColor3f(1.0f, 0.0f, 0.0f); // red
			/*
			 In openGL coordinates min,max are (-1.0,1.0) for both axis
			 regardless of window size. This means that the width can be stretched
			 if not calculated properly.
			*/


		// 3D walls
		//int wallHeight = 50;
		//graphics.setColor(Color.GREEN);
		for (int i=rays.length-1;i>=0;i--) {
			//double viewedWallHeight = 0.002* window.getHeight()*wallHeight/rays[i].getDistance()*4.0;
			float width =(float)(2.0/rays.length);
			float height = (float)(50.0/rays[i].getDistance()*4);
			float x = (float)(-1+(2.0/(rays.length))*(rays.length-1-i));
			float y = (float)((height/2.0));
			//System.out.printf("x: %f, y: %f, width: %f, height: %f\n", x, y, width, height);
			glBegin(GL_QUADS);
			glColor3f(0.0f, 0.0f, 1.0f);
			// Remember to go counter clockwise when rendering so the "face" is towards me
			glVertex2f(x,y);
			glVertex2f(x,y-height);
			glVertex2f(x+width,y-height);
			glVertex2f(x+width,y);
			glEnd();
			/*graphics.fillRect(
							(rays.length-1-i)*((window.getWidth()/2)/rays.length)+map[0].length*wallSize,
							(window.getHeight()/2)-(int)(viewedWallHeight/2),
							((window.getWidth()/2)/rays.length),
							(int)viewedWallHeight
			);*/
		}
	}

	public void keyPressed() {
		if (KeyListener.isKeyPressed(GLFW_KEY_LEFT) || KeyListener.isKeyPressed(GLFW_KEY_A))
			if (!didCollide(posX-size/2,posY)) {
				posX += 5*Math.cos((rotation-90)*3.14159/180);
				posY += 5*Math.sin((rotation-90)*3.14159/180);
				calculateRays();
			}
		if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT) || KeyListener.isKeyPressed(GLFW_KEY_D))
			if (!didCollide(posX+size/2,posY)) {
				posX += 5*Math.cos((rotation+90)*3.14159/180);
				posY += 5*Math.sin((rotation+90)*3.14159/180);
				calculateRays();
			}
		if (KeyListener.isKeyPressed(GLFW_KEY_DOWN) || KeyListener.isKeyPressed(GLFW_KEY_S))
			if (!didCollide(posX,posY+size/2)) {
				posX += 5*Math.cos((rotation+180)*3.14159/180);
				posY += 5*Math.sin((rotation+180)*3.14159/180);
				calculateRays();
			}
		if (KeyListener.isKeyPressed(GLFW_KEY_UP) || KeyListener.isKeyPressed(GLFW_KEY_W))
			if (!didCollide(posX,posY-size/2)) {
				posX += 5*Math.cos(rotation*3.14159/180);
				posY += 5*Math.sin(rotation*3.14159/180);
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

	public void mouseMoved() {
		if (MouseListener.getXPos() != 0) {
			rotation -= MouseListener.getDX()/2;
			calculateRays();
		}
	}

	public void renewRotation() {
		rotation = rotation % 360;
		startRays = 60 + rotation;
		endRays = -60 + rotation;
		rayDistanceBetween = (startRays-endRays)/(rayCount-1);
	}

	public Ray[] getRays() {
		return rays;
	}
}

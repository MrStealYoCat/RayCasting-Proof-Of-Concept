import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Sprite {

	private int rotation = 0;
	private int wallSize = 100;
	int rayCount = 180;
	double startRays; // = 45 + rotation;
	double endRays; // = -45 + rotation;
	double rayDistanceBetween; // = (startRays-endRays)/(rayCount-1);
	Ray[] rays = new Ray[rayCount];
	int[][] map;

	public Player(int size, int posX, int posY, int[][] map) {
		super(size, posX, posY);
		this.map = map;
		calculateRays(map);
	}

	public void rayGen() {
		for (int i=0;i<rays.length;i++) {
			rays[i] = new Ray(posX,posY,startRays-i*rayDistanceBetween);
		}
	}

	/* Only should be called when moving or basically when location
	 * or rotation of the rays change. Goes through each ray and calculates
	 * everything needed to draw them.
	 */
	public void calculateRays(int[][] map) {
		renewRotation();
		rayGen();
		for (Ray r : rays) {
			while (r.getLastX() > 0.0 && r.getLastX() < map[0].length*wallSize && r.getLastY() > 0.0 && r.getLastY() < map.length*wallSize) {
				System.out.println(r.getLastX() + " : " + r.getLastY());
				r.setLastX(r.getLastX() + r.getRun());
				r.setLastY(r.getLastY() + r.getRise());
				if (didCollide((int)r.getLastX(),(int)r.getLastY(),map))
					break;
			}
		}
	}

	public void drawRays(Graphics graphics) {
		graphics.setColor(Color.RED);
		for (Ray r : rays) {
			graphics.drawLine((int)r.getStartX(),(int)r.getStartY(),(int)r.getLastX(),(int)r.getLastY());
		}
	}

	public void drawWalls(Graphics graphics, Window window) {
		int wallHeight = 50;
		graphics.setColor(Color.GREEN);
		for (int i=rays.length-1;i>=0;i--) {
			double viewedWallHeight = 1.0* window.getHeight()*wallHeight/rays[i].getDistance()*4.0;
			graphics.fillRect(
							(rays.length-1-i)*((window.getWidth()/2)/rays.length)+map[0].length*wallSize,
							(window.getHeight()/2)-(int)(viewedWallHeight/2),
							((window.getWidth()/2)/rays.length),
							(int)viewedWallHeight
			);
		}
	}

	public boolean didCollide(int colliderX, int colliderY, int[][] map) {
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

	public void drawPlayer(Graphics graphics) {
		graphics.setColor(Color.RED);
		graphics.fillOval(posX-size/2,posY-size/2,size,size);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
			if (posX >= size/2) {
				posX -= 10;
				calculateRays(map);
			}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
			if (posX <= map[0].length*wallSize-size/2) {
				posX += 10;
				calculateRays(map);
			}
		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_W)
			if (posY >= size/2) {
				posY -= 10;
				calculateRays(map);
			}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_S)
			if (posY <= map.length*100-size) {
				posY += 10;
				calculateRays(map);
			}
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			rotation -= 5;
			calculateRays(map);
		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			rotation += 5;
			calculateRays(map);
		}
	}

	public void renewRotation() {
		startRays = 60 + rotation;
		endRays = -60 + rotation;
		rayDistanceBetween = (startRays-endRays)/(rayCount-1);
	}

	public Ray[] getRays() {
		return rays;
	}

	public double getRayDistanceBetween() {
		return rayDistanceBetween;
	}
}

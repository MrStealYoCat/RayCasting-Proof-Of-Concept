import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Main {
	public static void main(String[] args) {
		int wallSize = 100;
		Window window = new Window("My Window", 2000, 1000);

		int[][] mapArray = {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,1,1,0,0,0,1},
			{1,0,0,0,1,1,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,0,0,0,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1}
		};

		Player player = new Player(50,3*wallSize,6*wallSize,mapArray);

		window.getFrame().addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				//player.keyReleased(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				player.keyPressed(e);
			}
		});
		window.getFrame().setFocusable(true);





		BufferStrategy bufferStrategy;
		//TODO change this to java2D
		Graphics graphics;


		while (true) {

			bufferStrategy = window.getCanvas().getBufferStrategy();
			graphics = bufferStrategy.getDrawGraphics();

			graphics.setColor(Color.BLACK);
			graphics.fillRect(0,0, window.getWidth(), window.getHeight());

			player.drawWalls(graphics,window);
			player.drawPlayer(graphics);
			player.drawRays(graphics);
			//map.drawWalls(graphics,window, player.getRays());


			graphics.dispose();
			bufferStrategy.show();

		}
	}
}

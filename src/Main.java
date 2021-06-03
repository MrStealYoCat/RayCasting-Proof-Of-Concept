import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Main {
	public static void main(String[] args) {
		int wallSize = 100;
		int offset = 0;
		Window window = new Window("My Window", 2000, 1000);


		int[][] map = {
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

		Player player = new Player(50,3*wallSize,6*wallSize,map);

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
		Graphics graphics;


		while (true) {

			bufferStrategy = window.getCanvas().getBufferStrategy();
			graphics = bufferStrategy.getDrawGraphics();

			graphics.setColor(Color.BLACK);
			graphics.fillRect(0,0, window.getWidth(), window.getHeight());


			graphics.setColor(Color.WHITE);

			for (int i=0; i<map.length; i++) {
				for (int j=0; j<map[i].length; j++) {
					if (map[i][j] == 1)
						graphics.fillRect(wallSize*j+offset,wallSize*i+offset,wallSize,wallSize);
				}
			}


			player.drawPlayer(graphics);
			player.drawRays(graphics);
			player.drawWalls(graphics,window);

			graphics.dispose();
			bufferStrategy.show();

			// Sleep for 1/60 of a second (60 fps)
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.getMessage();
			}
		}
	}
}

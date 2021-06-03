import javax.swing.*;
import java.awt.*;

public class Window {

	private String title;
	private int width;
	private int height;
	private JFrame frame;
	private Canvas canvas;

	public Window(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		setup();
	}

	private void setup() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		canvas = new Canvas();
		canvas.setSize(width, height);
		canvas.setBackground(Color.BLACK);
		canvas.setVisible(true);

		frame.add(canvas);
		canvas.createBufferStrategy(3);
	}

	public Canvas getCanvas() {
		return canvas;
	}
	public JFrame getFrame() {
		return frame;
	}
	public String getTitle() {
		return title;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}

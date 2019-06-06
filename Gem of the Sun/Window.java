

import rnd.ImageLoader;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Window extends Canvas {

	private static final long serialVersionUID = -6781049889390764719L;
	private JFrame frame;

	// Create the window for the application to run;
	public Window(int width, int height, String title, Game game) {
		frame = new JFrame(title);

		// Make maximum and minimum size the same so that the window size is
		// consistent.
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		BufferedImage icon = new ImageLoader("icon").getImage();
		frame.setIconImage(new ImageIcon(icon).getImage());
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}

	// returns the location of window in the x axis
	public int JFgetX() {
		return frame.getLocation().getLocation().x;
	}

	// returns the location of the part at the bottom of the title bar in the y
	// axis.
	public int JFgetY() {
		return frame.getLocation().getLocation().y + frame.getInsets().top;
	}
}

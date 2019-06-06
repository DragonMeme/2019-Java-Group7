package rnd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class GUI {
	protected ImageLoader load = new ImageLoader("buttonIcons");
	protected ImageLoader menu_base = new ImageLoader("menu_base");
	protected BufferedImage Title = new ImageLoader("title").getImage();
	protected int jfx, jfy;
	protected Color highlight = new Color(255, 127, 39);
	protected Color non_highlight = new Color(232, 92, 0);

	public abstract void tick();

	public abstract void render(Graphics g);

	protected BufferedImage getIcon(int index) {
		return load.getPartImage(index * 145, 0, 145, 145);
	}

	protected boolean mouseOver(int mouse_x, int mouse_y, int x, int y, int width, int height) {
		boolean left_most = mouse_x > x;
		boolean right_most = mouse_x < x + width;
		boolean up_most = mouse_y > y;
		boolean down_most = mouse_y < y + height;
		return left_most && right_most && up_most && down_most;
	}

	// setter function that gets the position of the JFrame relative to screen
	public void setJFX(int x) {
		jfx = x;
	}

	public void setJFY(int y) {
		jfy = y;
	}
}

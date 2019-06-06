/*
    This code implements the introduction of the application.
 */

package rnd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Intro extends GUI {
	private int time_tick;
	private BufferedImage img = new ImageLoader("team_logo").getImage();

	public Intro() {
		time_tick = 200;
	}

	public boolean done() {
		return time_tick == 0;
	}

	public void tick() {
		if (time_tick > 0)
			time_tick--;
	}

	public void render(Graphics g) {
		g.setColor(new Color(21, 31, 54));
		g.fillRect(0, 0, 1440, 900);
		g.drawImage(img, 439, 151, null);
	}
}

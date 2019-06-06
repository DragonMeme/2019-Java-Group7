/*
    This code implements the functionality and render of the main menu that allows navigation between settings,
    highscore display and gameplay selection.
 */

package rnd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;

public class MainMenu extends GUI {
	private Color color_play, color_setting, color_score, color_exit;
	private int[] triX = { 650, 650, 820 };
	private int[] triY = { 250, 450, 350 };

	private BufferedImage Base = menu_base.getImage();
	private BufferedImage Cross = getIcon(2);
	private BufferedImage Cog = getIcon(1);
	private BufferedImage Stand = getIcon(5);

	public void render(Graphics g) {
		g.drawImage(Base, 0, 0, null);
		g.drawImage(Title, 370, 50, null);
		g.setColor(color_play);
		g.fillOval(570, 200, 300, 300);
		g.setColor(color_setting);
		g.fillOval(620, 600, 200, 200);
		g.setColor(color_score);
		g.fillOval(320, 600, 200, 200);
		g.setColor(color_exit);
		g.fillOval(920, 600, 200, 200);
		g.setColor(new Color(255, 204, 128));
		g.fillPolygon(triX, triY, 3); // triangle for play button
		g.drawImage(Cross, 947, 627, null);
		g.drawImage(Cog, 647, 627, null);
		g.drawImage(Stand, 347, 617, null);
	}

	public void tick() {
		int mx = MouseInfo.getPointerInfo().getLocation().x - jfx;
		int my = MouseInfo.getPointerInfo().getLocation().y - jfy;
		color_play = mouseOver(mx, my, 570, 200, 300, 300) ? highlight : non_highlight;
		color_setting = mouseOver(mx, my, 620, 600, 200, 200) ? highlight : non_highlight;
		color_score = mouseOver(mx, my, 320, 600, 200, 200) ? highlight : non_highlight;
		color_exit = mouseOver(mx, my, 920, 600, 200, 200) ? highlight : non_highlight;
	}
}

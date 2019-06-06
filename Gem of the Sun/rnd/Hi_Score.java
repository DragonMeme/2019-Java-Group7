/*
    Aim is to be able to display the highscores. The highscores are saved in a txt file.
 */

package rnd;

import ctrl.Memory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.MouseInfo;

import java.util.ArrayList;

public class Hi_Score extends GUI {
	private BufferedImage Base = menu_base.getImage();
	private BufferedImage Stand = getIcon(5);

	private int[] backArrowX = { 347, 430, 430, 492, 492, 430, 430 };
	private int[] backArrowY = { 700, 640, 670, 670, 730, 730, 760 };

	private Color color_back;
	private Memory m = new Memory();
	private ArrayList<String> scores = m.readScores();

	public Hi_Score() {
		Memory m = new Memory();
		scores = m.readScores();
	}

	public void tick() {
		int mx = MouseInfo.getPointerInfo().getLocation().x - jfx;
		int my = MouseInfo.getPointerInfo().getLocation().y - jfy;
		color_back = mouseOver(mx, my, 320, 600, 200, 200) ? highlight : non_highlight;
	}

	public void render(Graphics g) {
		g.drawImage(Base, 0, 0, null);

		// Make setting title
		g.setColor(Color.red);
		g.fillOval(370, 50, 200, 200);
		g.fillOval(870, 50, 200, 200);
		g.fillRect(470, 50, 500, 200);
		g.drawImage(Stand, 647, 77, null);

		// Setting for button
		g.setColor(color_back);
		g.fillOval(320, 600, 200, 200);

		// back logo
		g.setColor(new Color(255, 204, 128));
		g.fillPolygon(backArrowX, backArrowY, 7);

		g.setColor(non_highlight);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("Adventure Mode High Scores:", 550, 300);
		g.drawString("Survival Mode High Scores:", 550, 550);

		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("Easy: ", 550, 340);
		g.drawString("Medium: ", 550, 380);
		g.drawString("Hard: ", 550, 420);
		g.drawString("General Survival: ", 550, 590);
		g.drawString("Boss Mode Survival: ", 550, 630);

		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString(scores.get(0), 650, 340);
		g.drawString(scores.get(1), 700, 380);
		g.drawString(scores.get(2), 650, 420);
		g.drawString(scores.get(3), 845, 590);
		g.drawString(scores.get(4), 900, 630);
	}

	public void update() {
		scores = m.readScores();
	}
}

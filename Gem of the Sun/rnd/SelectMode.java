package rnd;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.MouseInfo;

public class SelectMode extends GUI {
	private BufferedImage Base = menu_base.getImage();
	private BufferedImage Tutorial = getIcon(8);
	private BufferedImage Survival = getIcon(7);
	private BufferedImage Adventure = getIcon(0);

	// coordinates for arrow
	private int[] backArrowX = { 347, 430, 430, 492, 492, 430, 430 };
	private int[] backArrowY = { 700, 640, 670, 670, 730, 730, 760 };

	// coordinaes for up down toogle arrows
	private int[] upDownX = { 690, 750, 720 };
	private int[] upY = { 595, 595, 540 };
	private int[] downY = { 730, 730, 785 };

	private int[] triY = { 77, 222, 149 };
	private int[] triX = { 647, 647, 792 };

	private int difficulty;
	private boolean bossMode; // false for monsters survival, true for boss
								// survival

	private Color color_back, color_tutorial, color_adventure, color_survival, color_difficultyUp, color_difficultyDown,
			color_bossModeOn;

	public SelectMode() {
		// default settings
		difficulty = 1;
		bossMode = false;
	}

	public void render(Graphics g) {
		g.drawImage(Base, 0, 0, null);

		// Make setting title
		g.setColor(Color.red);
		g.fillOval(370, 50, 200, 200);
		g.fillOval(870, 50, 200, 200);
		g.fillRect(470, 50, 500, 200);

		// Color for buttons
		g.setColor(color_back);
		g.fillOval(320, 600, 200, 200);
		g.setColor(color_tutorial);
		g.fillOval(320, 300, 200, 200);
		g.setColor(color_adventure);
		g.fillOval(620, 300, 200, 200);
		g.setColor(color_survival);
		g.fillOval(920, 300, 200, 200);
		g.setColor(color_difficultyUp);
		g.fillOval(670, 525, 100, 100);
		g.setColor(color_difficultyDown);
		g.fillOval(670, 700, 100, 100);
		g.setColor(color_bossModeOn);
		g.fillOval(970, 625, 100, 100);

		// back logo
		g.setColor(new Color(255, 204, 128));
		g.fillPolygon(backArrowX, backArrowY, 7);

		// play title logo
		g.fillPolygon(triX, triY, 3); // triangle for play logo

		// arrow up and down logos
		g.fillPolygon(upDownX, upY, 3);
		g.fillPolygon(upDownX, downY, 3);

		g.setFont(new Font("Arial", Font.BOLD, 50));
		if (difficulty == 0) {
			g.setColor(new Color(0, 200, 0));
			g.drawString("EASY", 650, 680);
		} else if (difficulty == 2) {
			g.setColor(Color.red);
			g.drawString("HARD", 645, 680);
		} else {
			g.setColor(highlight);
			g.drawString("MEDIUM", 605, 680);
		}
		
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		g.setColor(non_highlight);
		g.drawString("Boss", 978, 685);

		g.drawImage(Tutorial, 347, 327, null);
		g.drawImage(Adventure, 647, 327, null);
		g.drawImage(Survival, 947, 340, null);
	}

	public void tick() {
		int mx = MouseInfo.getPointerInfo().getLocation().x - jfx;
		int my = MouseInfo.getPointerInfo().getLocation().y - jfy;
		color_back = mouseOver(mx, my, 320, 600, 200, 200) ? highlight : non_highlight;
		color_adventure = mouseOver(mx, my, 620, 300, 200, 200) ? highlight : non_highlight;
		color_tutorial = mouseOver(mx, my, 320, 300, 200, 200) ? highlight : non_highlight;
		color_survival = mouseOver(mx, my, 920, 300, 200, 200) ? highlight : non_highlight;
		color_difficultyUp = mouseOver(mx, my, 670, 525, 100, 100) ? highlight : non_highlight;
		color_difficultyDown = mouseOver(mx, my, 670, 700, 100, 100) ? highlight : non_highlight;
		color_bossModeOn = bossMode ? Color.green : Color.red;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public void setBossMode(boolean bossMode) {
		this.bossMode = bossMode;
	}

	public boolean getBossMode() {
		return bossMode;
	}

	public int getDifficulty() {
		return difficulty;
	}
}

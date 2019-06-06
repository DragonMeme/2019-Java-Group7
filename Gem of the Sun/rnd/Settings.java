package rnd;

import ctrl.Memory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.MouseInfo;

public class Settings extends GUI {

	private BufferedImage Base = menu_base.getImage();
	private BufferedImage Cog = getIcon(1);
	private BufferedImage not = getIcon(4);
	private BufferedImage music = getIcon(3);
	private BufferedImage sfx = getIcon(6);

	private int[] backArrowX = { 347, 430, 430, 492, 492, 430, 430 };
	private int[] backArrowY = { 700, 640, 670, 670, 730, 730, 760 };

	private Color color_music, color_back, color_sfx;
	private boolean sound_on, sfx_on;

	private Memory m;
	private String setting_mod;

	public Settings() {
		m = new Memory();
		setting_mod = m.readSettings();
		sound_on = setting_mod.charAt(0) == 'T';
		sfx_on = setting_mod.charAt(1) == 'T';
	}

	public void tick() {
		int mx = MouseInfo.getPointerInfo().getLocation().x - jfx;
		int my = MouseInfo.getPointerInfo().getLocation().y - jfy;
		color_back = mouseOver(mx, my, 320, 600, 200, 200) ? highlight : non_highlight;
		color_music = mouseOver(mx, my, 320, 300, 200, 200) ? highlight : non_highlight;
		color_sfx = mouseOver(mx, my, 620, 300, 200, 200) ? highlight : non_highlight;
	}

	public void render(Graphics g) {
		g.drawImage(Base, 0, 0, null);

		// Make setting title
		g.setColor(Color.red);
		g.fillOval(370, 50, 200, 200);
		g.fillOval(870, 50, 200, 200);
		g.fillRect(470, 50, 500, 200);
		g.drawImage(Cog, 647, 77, null);

		// Setting for buttons
		g.setColor(color_back);
		g.fillOval(320, 600, 200, 200);
		g.setColor(color_music);
		g.fillOval(320, 300, 200, 200);
		g.setColor(color_sfx);
		g.fillOval(620, 300, 200, 200);
		g.drawImage(music, 335, 327, null);
		g.drawImage(sfx, 645, 327, null);

		if (!sound_on) {
			g.drawImage(not, 347, 327, null);
		}
		if (!sfx_on) {
			g.drawImage(not, 647, 327, null);
		}

		// back logo
		g.setColor(new Color(255, 204, 128));
		g.fillPolygon(backArrowX, backArrowY, 7);
	}

	public void setSound_on(boolean sound_on) {
		this.sound_on = sound_on;
		String s = sound_on ? "T" : "F";
		setting_mod = s + setting_mod.charAt(1);
		m.writeSettings(setting_mod);
	}

	public void setSfx_on(boolean sfx_on) {
		this.sfx_on = sfx_on;
		String s = this.sfx_on ? "T" : "F";
		setting_mod = setting_mod.charAt(0) + s;
		m.writeSettings(setting_mod);
	}

	public boolean getSound_on() {
		return sound_on;
	}

	public boolean getSfx_on() {
		return sfx_on;
	}
}

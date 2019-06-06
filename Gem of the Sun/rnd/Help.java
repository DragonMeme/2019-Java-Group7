package rnd;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.image.BufferedImage;

import go.Bat;
import go.Boss;
import go.Crystal;
import go.Slime;

public class Help extends GUI {
	private enum helpState {
		MOVEMENT, MISCELLANEOUS, NORMAL_ATTACK, POWER_UPS, ENEMIES, GAMEPLAY
	}

	private Color color_back, color_choice1, color_choice2, color_choice3, color_choice4, color_choice5, color_choice6;
	private ImageLoader p_up = new ImageLoader("power-ups");
	private BufferedImage Base = menu_base.getImage();
	private helpState h;

	private int[] backArrowX = { 347, 430, 430, 492, 492, 430, 430 };
	private int[] backArrowY = { 700, 640, 670, 670, 730, 730, 760 };

	Bat b = new Bat(425, 320, false);
	Slime s = new Slime(425, 400, false, 1);
	Crystal c = new Crystal(425, 485);
	Boss B = new Boss(600, 670, 50);

	public Help() {
		h = helpState.MOVEMENT;
	}

	public void setHelpPick(int i) {
		if (i == 0)
			h = helpState.MOVEMENT;
		if (i == 1)
			h = helpState.NORMAL_ATTACK;
		if (i == 2)
			h = helpState.MISCELLANEOUS;
		if (i == 3)
			h = helpState.POWER_UPS;
		if (i == 4)
			h = helpState.ENEMIES;
		if (i == 5)
			h = helpState.GAMEPLAY;
	}

	public void tick() {
		int mx = MouseInfo.getPointerInfo().getLocation().x - jfx;
		int my = MouseInfo.getPointerInfo().getLocation().y - jfy;

		color_back = mouseOver(mx, my, 320, 600, 200, 200) ? highlight : non_highlight;
		color_choice1 = mouseOver(mx, my, 320, 250, 50, 50) ? highlight : non_highlight;
		color_choice2 = mouseOver(mx, my, 320, 305, 50, 50) ? highlight : non_highlight;
		color_choice3 = mouseOver(mx, my, 320, 360, 50, 50) ? highlight : non_highlight;
		color_choice4 = mouseOver(mx, my, 320, 415, 50, 50) ? highlight : non_highlight;
		color_choice5 = mouseOver(mx, my, 320, 470, 50, 50) ? highlight : non_highlight;
		color_choice6 = mouseOver(mx, my, 320, 525, 50, 50) ? highlight : non_highlight;
		b.tick();
		s.tick();
		B.tick();
	}

	public void render(Graphics g) {
		g.drawImage(Base, 0, 0, null);

		// Make setting title
		g.setColor(Color.red);
		g.fillOval(370, 50, 200, 200);
		g.fillOval(870, 50, 200, 200);
		g.fillRect(470, 50, 500, 200);
		g.drawImage(getIcon(8), 647, 77, null);

		// make the selection choice look highlighted by drawing the highlighted
		// part first
		int highlightX = 310, highlightY;
		if (h == helpState.NORMAL_ATTACK)
			highlightY = 295;
		else if (h == helpState.MISCELLANEOUS)
			highlightY = 350;
		else if (h == helpState.POWER_UPS)
			highlightY = 405;
		else if (h == helpState.ENEMIES)
			highlightY = 460;
		else if (h == helpState.GAMEPLAY)
			highlightY = 515;
		else
			highlightY = 240;
		g.setColor(Color.black);
		g.fillOval(highlightX, highlightY, 70, 70);

		// Setting for each button
		g.setColor(color_back);
		g.fillOval(320, 600, 200, 200);
		g.setColor(color_choice1);
		g.fillOval(320, 250, 50, 50);
		g.setColor(color_choice2);
		g.fillOval(320, 305, 50, 50);
		g.setColor(color_choice3);
		g.fillOval(320, 360, 50, 50);
		g.setColor(color_choice4);
		g.fillOval(320, 415, 50, 50);
		g.setColor(color_choice5);
		g.fillOval(320, 470, 50, 50);
		g.setColor(color_choice6);
		g.fillOval(320, 525, 50, 50);

		// back logo
		g.setColor(new Color(255, 204, 128));
		g.fillPolygon(backArrowX, backArrowY, 7);

		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.setColor(highlight);
		switch (h) {
		case MISCELLANEOUS:
			g.drawString("Other player controls!", 450, 300);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.drawString("P - Pauses/resumes the game.", 450, 340);
			g.drawString("ESC - allows you to go back to game mode", 450, 370);
			g.drawString("         selection", 450, 400);
			break;

		case NORMAL_ATTACK:
			g.drawString("Attacking!", 450, 300);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.drawString("SPACE BAR - Shoot an arrow to the direction that", 450, 340);
			g.drawString("                    the player is facing.", 450, 370);
			g.drawString("B - Change selection between each normal attack", 450, 400);
			g.drawString("     and power-up attack.", 450, 430);
			g.drawString("Note that there is a cooldown on each attack and", 450, 500);
			g.drawString("player may not be able to temporarily move!", 450, 530);
			break;

		case POWER_UPS:
			g.drawString("Power-Up Details!", 450, 300);
			g.drawString("Other Power-Ups include:", 550, 620);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("If an arrow power-up runs out, used arrows will be switched to normal", 450, 340);
			g.drawString("arrows.", 450, 360);
			g.drawString("Arrow types include:", 450, 380);
			g.drawString("Normal arrow: Deal damage in a limited range.", 525, 420);
			g.drawString("Explosive arrow: Area of effect damage. ", 525, 480);
			g.drawString("Piercing arrow: A normal arrow that goes through every enemy. ", 525, 540);
			g.drawString(" Health: Heals the player by one health if not full.", 650, 680);
			g.drawString(" Potion: Grants temporary immunity to all damage.", 650, 740);

			g.drawImage(p_up.getPartImage(250, 0, 50, 50), 450, 380, null); // normal
			g.drawImage(p_up.getPartImage(50, 0, 50, 50), 450, 440, null); // explosive
			g.drawImage(p_up.getPartImage(100, 0, 50, 50), 450, 500, null); // piercing
			g.drawImage(p_up.getPartImage(200, 0, 50, 50), 570, 640, null); // health
			g.drawImage(p_up.getPartImage(150, 0, 50, 50), 570, 700, null); // potion
			break;

		case ENEMIES:
			g.drawString("Enemies!", 450, 300);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Bat - They fly about aimlessly when they see an adventurer.", 520, 340);
			g.drawString("Hopefully one does not hit you.", 520, 360);

			g.drawString("Slime - Keen for a hug, too bad this monster is too dangerous even after", 520, 420);
			g.drawString("death.", 520, 440);
			g.drawString("Crystal - Its power comes from the Gem of the Sun itself.", 520, 500);
			g.drawString("A sufficient amount spawned (3+) can render the Boss", 520, 520);
			g.drawString("invulnerable to any damage.", 520, 540);
			g.drawString("Boss - A king monkey corrupted by the power ", 520, 580);
			g.drawString("of the gem. He must be brought down before ", 520, 600);
			g.drawString("he can harness its full power.", 600, 620);
			b.render(g);
			s.render(g);
			c.render(g);
			B.render(g);
			break;

		case GAMEPLAY:
			g.drawString("GamePlay!", 450, 300);
			g.drawString("Story!", 450, 470);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Adventure - Aim is to defeat the boss which is on the other side of the map.", 450, 340);
			g.drawString("Upon defeating the boss, you will be able to take the Gem of the Sun from him", 450, 360);
			g.drawString("and reinforce the security vault at which the gem rests at.", 450, 380);
			g.drawString("Survival - The goal is simple. Live as long as you can with only one hitpoints.", 450, 420);

			g.drawString("The Gem of the Sun was a gem that was guarded by Karui's family in a castle of ", 450, 510);
			g.drawString("Katon. One day the Monkey King, known as Zorro had a ninja monkey stole the  ", 450, 530);
			g.drawString("gem from the castle vault which had Karui's mother sending his soldiers to ", 450, 550);
			g.drawString("get the gem back but with failure. After a couple of attempts, Karui has ", 450, 570);

			g.drawString("decided to sneak outside to get the gem back...[ADVENTURE]... After ", 450, 590);
			g.drawString("      successfully getting the gem back, she returned it to the castle vault ", 450, 610);
			g.drawString("and have managed to reinforce the security of the vault. After a long ", 520, 630);
			g.drawString("day, Karui decided to take a rest and unintentionally dreamt about", 520, 650);
			g.drawString("her encounters on the way to King Zorro.....[SURVIVAL]", 520, 670);
			break;
		default:
			g.drawString("Character Movements!", 400, 300);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			g.drawString("W - Moves the character up.", 450, 340);
			g.drawString("A - Moves the character left.", 450, 370);
			g.drawString("S - Moves the character right.", 450, 400);
			g.drawString("D - Moves the character down.", 450, 430);
			g.drawString("M - Change player direction in anti-clockwise", 450, 490);
			g.drawString("     direction.", 450, 520);
			g.drawString("N - Change player direction in clockwise direction.", 450, 550);
			break;
		}
	}
}

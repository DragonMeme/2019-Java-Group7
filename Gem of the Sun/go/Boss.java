/*
    This class implements boss functionality. CoolDownThresh determines boss attack speed and is
    adjustable. Smaller the coolDownThresh, the faster the boss attacks and its highest attack
    rate is 10frames/try.
 */

package go;

import ctrl.Sound;
import rnd.ImageLoader;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Boss extends Enemy {
	private boolean attack;
	private int coolDownAttack, coolDownThresh;
	private Sound s = new Sound();
	private ImageLoader spritesheet = new ImageLoader("boss");
	private BufferedImage shield = new ImageLoader("bossShield").getImage();

	public Boss(int x, int y, int coolDownThresh) {
		super(x, y);
		this.id = ID.BOSS;
		HP = 10;
		attack = false;
		this.coolDownThresh = coolDownThresh;
		coolDownAttack = 0;
		immune = true;
	}

	public void heal() {
		if (HP < 10)
			HP++;
	}

	public boolean canAttack() {
		if (coolDownAttack == 0) {
			s.playSound(6);
			return true;
		}
		return false;
	}

	public void tick() {
		if (m_state != monsterState.DEAD) {
			x += dx;
			y += dy;
			if (coolDownAttack > 0)
				coolDownAttack--;
			else
				coolDownAttack = coolDownThresh;
			if (coolDownThresh < 10)
				coolDownThresh = 10;
			attack = coolDownAttack > (coolDownThresh - 10);
		}
		if (HP == 0)
			m_state = monsterState.DEAD;
	}

	public void render(Graphics g) {
		int posY = attack ? 180 : 0;
		g.drawImage(spritesheet.getPartImage(0, posY, 230, 180), x, y, null);
		if (immune)
			g.drawImage(shield, x + 15, y - 35, null);
		g.setColor(Color.red);
		g.fillOval(getCenter()[0] - 15, getCenter()[1] - 25, 50, 50);

		g.setFont(new Font("Arial", Font.PLAIN, 25));
		g.setColor(Color.yellow);
		g.drawString(String.valueOf(HP), getCenter()[0], getCenter()[1] + 10);
	}

	public Rectangle getBounds() {
		return new Rectangle(x + 50, y, 180, 180);
	}

	public void setCoolDownThresh(int coolDownThresh) {
		this.coolDownThresh = coolDownThresh;
	}

	public void setImmune(boolean immune) {
		this.immune = immune;
	}

	public int[] getCenter() {
		int[] center = new int[2];
		center[0] = x + 140;
		center[1] = y + 90;
		return center;
	}

	public boolean getImmune() {
		return immune;
	}

	public int getCoolDownThresh() {
		return coolDownThresh;
	}

	public Rectangle getDYTickAheadBounds() {
		return new Rectangle(x, y + dy, 180, 180);
	}

	public Rectangle getDXTickAheadBounds() {
		return new Rectangle(x + dx, y, 180, 180);
	}

}

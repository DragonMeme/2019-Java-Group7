/*
    Bat is a monster that can fly through obstacles and will evantually charge towards the player.
    They are fast creatures.
 */

package go;

import rnd.ImageLoader;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Bat extends Enemy {
	private boolean mutated, swapLook;
	private int swapTime = 0;
	private ImageLoader spritesheet = new ImageLoader("bat");

	public Bat(int x, int y, boolean mutated) {
		super(x, y);
		this.mutated = mutated;
		this.id = ID.BAT;
		HP = mutated ? 2 : 1;
		swapLook = false;
	}

	public void tick() {
		if (m_state != monsterState.DEAD) {
			this.x += dx;
			this.y += dy;
		}
		if (swapTime < 5)
			swapTime++;
		else {
			swapTime = 0;
			swapLook = !swapLook;
		}
		if (HP == 0) {
			m_state = monsterState.DEAD;
			s.playSound(7);
		}
		mutated = HP > 1;
	}

	public Rectangle getBounds() {
		return new Rectangle(x + 1, y + 11, 69, 47);
	}

	public Rectangle getDYTickAheadBounds() {
		return new Rectangle(x + 1, y + 11 + dy, 69, 47);
	}

	public Rectangle getDXTickAheadBounds() {
		return new Rectangle(x + 1 + dx, y + 11, 69, 47);
	}

	public void render(Graphics g) {
		int posX = swapLook ? 70 : 0;
		int posY = mutated ? 70 : 0;
		BufferedImage bat = spritesheet.getPartImage(posX, posY, 70, 70);
		if (m_state != monsterState.DEAD)
			g.drawImage(bat, x, y, null);
	}

	public int[] getCenter() {
		int[] center = new int[2];
		center[0] = x + 35;
		center[1] = y + 34;
		return center;
	}
}

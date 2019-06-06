package go;

import rnd.ImageLoader;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Crystal extends Enemy {
	private BufferedImage crystal = new ImageLoader("enemy_crystal").getImage();

	public Crystal(int x, int y) {
		super(x, y);
		this.id = ID.ENEMY_CRYSTAL;
		HP = 1;
	}

	public void tick() {
		if (HP == 0) {
			m_state = monsterState.DEAD;
			s.playSound(9);
		}
	}

	public void render(Graphics g) {
		g.drawImage(crystal, x, y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 60, 60);
	}

	public int[] getCenter() {
		int[] center = new int[2];
		center[0] = x + 30;
		center[1] = y + 30;
		return center;
	}

	public Rectangle getDYTickAheadBounds() {
		return new Rectangle(x, y + dy, 60, 60);
	}

	public Rectangle getDXTickAheadBounds() {
		return new Rectangle(x + dx, y, 60, 60);
	}
}

/*
    Player ammunition model.
 */

package go;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Arrow extends Projectile {
	private int initX, initY;

	public Arrow(int x, int y, int dx, int dy, int power_up) {
		super(x, y, dx, dy);
		if (power_up == 0)
			id = ID.NORMAL_ARROW;
		else if (power_up == 2)
			id = ID.PIERCING_ARROW;
		else
			id = ID.EXPLOSIVE_ARROW;
		initX = x;
		initY = y;
	}

	public void tick() {
		x += dx;
		y += dy;
	}

	// normal arrow shot
	public Rectangle getBounds() {
		if (id == ID.PIERCING_ARROW) {
			return new Rectangle(x, y, 30, 30);
		} else {
			if (dx > 0) {
				if (dy > 0) { // 5
					return new Rectangle(x + 20, y + 20, 5, 5);
				} else if (dy < 0) { // 3
					return new Rectangle(x + 20, y + 3, 5, 5);
				} else { // 4
					return new Rectangle(x + 24, y + 11, 6, 9);
				}
			} else if (dx < 0) {
				if (dy > 0) { // 7
					return new Rectangle(x + 3, y + 20, 5, 5);
				} else if (dy < 0) { // 1
					return new Rectangle(x + 3, y + 3, 5, 5);
				} else { // 8
					return new Rectangle(x, y + 10, 6, 9);
				}
			} else {
				if (dy < 0) { // 2
					return new Rectangle(x + 11, y, 6, 9);
				} else if (dy > 0) {// 6
					return new Rectangle(x + 10, y + 24, 6, 9);
				} else
					return null;
			}
		}
	}

	// Used for explosive arrow shots.
	public Rectangle getExplosionBounds() {
		return new Rectangle(x - 75, y - 75, 180, 180);
	}

	public void render(Graphics g) {
		if (id == ID.PIERCING_ARROW) {
			g.setColor(Color.orange);
			g.fillOval(x, y, 30, 30);
		} else if (id == ID.EXPLOSIVE_ARROW) {
			g.setColor(Color.black);
			g.fillOval(x, y, 30, 30);
		}
		int posX = 0, posY = 0;
		if (dx > 0)
			posX = 2;
		else if (dx == 0)
			posX = 1;
		if (dy > 0)
			posY = 2;
		else if (dy == 0)
			posY = 1;
		BufferedImage arrow = projectiles.getPartImage(30 * posX, 30 * posY, 30, 30);
		if (!(posX == posY && posX == 1))
			g.drawImage(arrow, x, y, null);
	}

	public int[] distanceTravelled() {
		int[] distance = new int[2];
		distance[0] = initX - x > 0 ? initX - x : x - initX;
		distance[1] = initY - y > 0 ? initY - y : y - initY;
		return distance;
	}

}

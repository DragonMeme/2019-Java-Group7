/*
    This class implements the obstacle that prevents player, most monsters and projectiles from going through
    them.
*/

package go;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Obstacle extends GameObject {
	private int height, width;

	public Obstacle(int x, int y, int height, int width) {
		super(x, y);
		this.height = height;
		this.width = width;
		dy = 0;
		dx = 0;
		id = ID.OBSTACLE;
	}

	public void tick() {
	}

	public void render(Graphics g) {
	}

	public Rectangle getBounds() {
		return new Rectangle(this.x, this.y, width, height);
	}

	public Rectangle getOneTickAheadBounds() {
		return getBounds();
	} // obstacles don't move

	public boolean isCollide(Player player) {
		return player.getOneTickAheadBounds().intersects(getBounds());
	}

	public boolean isCollideX(Enemy enemy) {
		return enemy.getDXTickAheadBounds().intersects(getBounds());
	}

	public boolean isCollideY(Enemy enemy) {
		return enemy.getDYTickAheadBounds().intersects(getBounds());
	}
}

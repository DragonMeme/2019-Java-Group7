package go;

import java.awt.*;

public class FireBall extends Projectile {
	public FireBall(int x, int y, int dx, int dy) {
		super(x, y, dx, dy);
		id = ID.FIRE_BALL;
	}

	public void tick() {
		x += dx;
		y += dy;
	}

	public void render(Graphics g) {
		g.drawImage(boss_projectile, x, y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 60, 60);
	}
}

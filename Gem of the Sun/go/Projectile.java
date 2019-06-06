package go;

import rnd.ImageLoader;

import java.awt.image.BufferedImage;

public abstract class Projectile extends GameObject {
	protected ImageLoader projectiles = new ImageLoader("projectile");
	protected BufferedImage boss_projectile = new ImageLoader("boss_fire").getImage();

	public Projectile(int x, int y, int dx, int dy) {
		super(x, y);
		this.dx = dx;
		this.dy = dy;
	}

	// for player fired shots
	public boolean isCollide(Enemy obj) {
		return this.getBounds().intersects(obj.getBounds());
	}

	// any shots that hit the obstacle
	public boolean isCollode(Obstacle obj) {
		return this.getBounds().intersects(obj.getBounds());
	}

	// for enemy fired shots
	public boolean isCollide(Player obj) {
		return this.getBounds().intersects(obj.getBounds());
	}
}

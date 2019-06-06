/*
    This abstract class aims to give each game object their common properties including movement and positions.
    Most of the sub classes will use render and tick functions.
 */

package go;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	// Position of object
	protected int x, y;
	// Change in object position
	protected int dx = 0, dy = 0;

	protected ID id;

	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract Rectangle getBounds(); // current boundary

	// setter functions
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	// getter functions
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	public ID getID() {
		return id;
	}
}

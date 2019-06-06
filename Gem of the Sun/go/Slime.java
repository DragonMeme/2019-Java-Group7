/*
    Slime is a monster that will aim to stand on the player so that the player can take damage.
    When the HP reaches 0, it becomes a slime gas that is toxic to players. This slime gas will
    dissipate over time.
    Increased difficulty will increase the slime's gas collision square.
 */

package go;

import rnd.ImageLoader;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Slime extends Enemy {
	private boolean mutated, swapLook;
	private final int deathExplodeTime = 600;
	private int swapTime = 100, deathExplodeCountDown, difficulty;
	private ImageLoader spritesheet = new ImageLoader("slime");
	private BufferedImage slime_gas = new ImageLoader("slime_gas").getImage();

	public Slime(int x, int y, boolean mutated, int difficulty) {
		super(x, y);
		this.mutated = mutated;
		this.difficulty = difficulty;
		this.id = ID.SLIME;
		HP = mutated ? 2 : 1;
		swapLook = false;
		deathExplodeCountDown = deathExplodeTime;
	}

	public void tick() {
		// monster able to move when HP at more than 0
		if (deathExplodeCountDown == deathExplodeTime) {
			this.x += dx;
			this.y += dy;
		}
		if (swapTime < 10)
			swapTime++;
		else {
			swapTime = 0;
			swapLook = !swapLook;
		}

		// slime is officially dead 2000 ticks after death.
		if (deathExplodeCountDown == 0)
			m_state = monsterState.DEAD;

		if (deathExplodeCountDown == deathExplodeTime - 1)
			s.playSound(8);
		mutated = HP > 1;

		// if slime has 0 hp, death explode countdown starts counting down
		if (HP == 0 && deathExplodeCountDown > 0)
			deathExplodeCountDown--;
	}

	public void render(Graphics g) {
		if (deathExplodeCountDown == deathExplodeTime) {
			// render slime in alive state
			int posX = swapLook ? 60 : 0;
			int posY = mutated ? 60 : 0;
			BufferedImage slime = spritesheet.getPartImage(posX, posY, 60, 60);
			g.drawImage(slime, x, y, null);
		} else if (deathExplodeCountDown > 0) {
			// render slime when at 0 hp and duration of slime gas state
			for (int i = 0; i < 3 + difficulty; i++) {
				for (int j = 0; j < 3 + difficulty; j++) {
					int x_Pos = x + 60 * (i - 1) - (30 * difficulty);
					int y_Pos = y + 60 * (j - 1) - (30 * difficulty);
					g.drawImage(slime_gas, x_Pos, y_Pos, null);
				}
			}
		}
	}

	public Rectangle getBounds() {
		int x_Pos = x - 60 - (30 * difficulty);
		int y_Pos = y - 60 - (30 * difficulty);
		if (deathExplodeCountDown == deathExplodeTime)
			return new Rectangle(x + 1, y + 4, 52, 55);
		else
			return new Rectangle(x_Pos, y_Pos, 180 + 60 * difficulty, 180 + 60 * difficulty);
	}

	public Rectangle getDYTickAheadBounds() {
		return new Rectangle(x + 1, y + 4 + dy, 52, 55);
	}

	public Rectangle getDXTickAheadBounds() {
		return new Rectangle(x + 1 + dx, y + 4, 52, 55);
	}

	public int[] getCenter() {
		int[] center = new int[2];
		center[0] = x + 27;
		center[1] = y + 32;
		return center;
	}
}

/*
    Aim is to act as a power up. Allows the player beneficial features.
    Power up declaration are as follows:

    [Both Adventure and Survival mode only]
    0 - Gem that gives extra score to the player
    1 - Gives player an explosive arrow
    2 - Gives player a piercing arrow
    3 - Allows player to gain temporary invincibility

    [Adventure mode only]
    8 - Allows the player to gain an extra HP if less than 3
 */

package go;

import rnd.ImageLoader;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PowerUp extends GameObject {
	private ImageLoader powerUpSheet = new ImageLoader("power-ups");
	private int powerUpDeclare;
	private int appear_time;

	public PowerUp(int x, int y, boolean Adventure) {
		super(x, y);
		Random r = new Random();
		powerUpDeclare = Adventure ? r.nextInt(9) : r.nextInt(6);
		appear_time = 500;
		switch (powerUpDeclare) {
		case 1:
			id = ID.EXPLOSIVE_ARROW_P;
			break;
		case 2:
			id = ID.PIERCING_ARROW_P;
			break;
		case 3:
			id = ID.INVINCIBLE_POTION;
			break;
		case 8:
			id = ID.HEALTH;
			break;
		default:
			id = ID.GEM;
			break;
		}
	}

	public void tick() {
		if (obtainable())
			appear_time--;
	}

	public void render(Graphics g) {
		int posX = 0;
		switch (powerUpDeclare) {
		case 1:
			posX = 1;
			break;
		case 2:
			posX = 2;
			break;
		case 3:
			posX = 3;
			break;
		case 8:
			posX = 4;
			break;
		default:
			break;
		}
		BufferedImage power = powerUpSheet.getPartImage(posX * 50, 0, 50, 50);
		g.drawImage(power, x, y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 50, 50);
	}

	public Rectangle getOneTickAheadBounds() {
		return getBounds();
	}

	public boolean obtainable() {
		return appear_time > 0;
	}

	public void coolidewithPlayer(Player player) {
		if (player.getBounds().intersects(getBounds()) && obtainable()) {
			switch (powerUpDeclare) {
			case 1:
				player.addExplosiveArrowCount();
				break;
			case 2:
				player.addPiercingArrowCount();
				break;
			case 3:
				player.addInvincibility(1000);
				break;
			case 8:
				player.addHP();
				break;
			default:
				player.addScore(100);
				break;
			}
		}
	}
}

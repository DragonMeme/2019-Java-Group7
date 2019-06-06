/*
    Model of a player class. Player will be able to attack, move, have power ups
    Using image "karui.png" and mapping of form:
    [1] [2] [3]
    [8] [C] [4]
    [7] [6] [5]
    Where [C] represents the 'center' and other numbers represent the respective direction relative to
    [C].

    Each 'player image' is sized 180 x 180.

    x-y plane is of form

    |----------> x
    |
    |
    |
    |
    V

    y
*/

package go;

import ctrl.Sound;
import rnd.ImageLoader;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject {
	private int HP, direction, attackCoolDown, immortalCoolDown, piercingArrowCount, explosiveArrowCount, score;
	private boolean attack, moveAllowed = true;
	private Sound s = new Sound();
	private ImageLoader spritesheet = new ImageLoader("karui");

	public Player(int x, int y, int HP) {
		super(x, y);
		id = ID.PLAYER;
		this.HP = HP;
		score = 0;
		direction = 4;
		attackCoolDown = 0;
		immortalCoolDown = 0;
		piercingArrowCount = 0;
		explosiveArrowCount = 0;
	}

	// mainly to indicate attack and cooldowns
	public void attack() {
		attackCoolDown = 50;
	} // initiate attack cooldown

	// command to make player take damage and gain temporary immunity to all
	// damage
	public void takeDamage() {
		if (immortalCoolDown == 0) {
			immortalCoolDown = 200; // 1.5 seconds temporary immunity to all
									// damage.
			if (HP > 0) {
				HP--;
				if (HP == 0)
					s.playSound(3);
				else
					s.playSound(4);
			}
		}
	}

	private BufferedImage getSprite(int col, int row) {
		return spritesheet.getPartImage(col * 180, row * 180, 180, 180);
	}

	// Ability to make player face 8 different directions without moving the
	// character.
	public void switchDirection(int i) {
		direction += i;
		if (direction > 8) {
			direction = 1;
		} else if (direction < 1) {
			direction = 8;
		}
	}

	// Get the boundary of the player.
	public Rectangle getBounds() {
		return new Rectangle(x + 60, y + 60, 60, 60);
	}

	// Get the boundary of the player in the future if moved.
	public Rectangle getOneTickAheadBounds() {
		return new Rectangle(x + 60 + dx, y + 60 + dy, 60, 60);
	}

	// Function used by other class to affect permission of movement
	public void allowMovement(boolean move) {
		moveAllowed = move;
	}

	// Restrict movement when player is attacking. Implement invulnerability and
	// cooldown ticks.
	public void tick() {
		// movement restricted when initiating attack and restricted movement
		if (!attack && moveAllowed && HP > 0) {
			this.x += dx;
			this.y += dy;
		}
		moveAllowed = true;
		attack = (attackCoolDown > 30);

		// initiate cooldown of immortal or attack
		if (getAttackCoolDown())
			attackCoolDown--;
		if (getImmortal())
			immortalCoolDown--;
	}

	// Draw the character idle and attack animation
	public void render(Graphics g) {
		int posX = 0, posY = 0;
		if (attack)
			posX = 3;
		switch (direction) {
		case 1:
			break;
		case 2:
			posX += 1;
			break;
		case 3:
			posX += 2;
			break;
		case 4:
			posX += 2;
			posY = 1;
			break;
		case 5:
			posX += 2;
			posY = 2;
			break;
		case 6:
			posX += 1;
			posY = 2;
			break;
		case 7:
			posY = 2;
			break;
		case 8:
			posY = 1;
			break;
		}
		BufferedImage karui = getSprite(posX, posY);
		if (immortalCoolDown % 20 < 10 && HP > 0)
			g.drawImage(karui, x, y, null);
	}

	public void setDirection(int i) {
		this.direction = i;
	}

	public void addHP() {
		HP++;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public void addPiercingArrowCount() {
		piercingArrowCount++;
	}

	public void addExplosiveArrowCount() {
		explosiveArrowCount++;
	}

	public void addInvincibility(int tick) {
		immortalCoolDown = tick;
	}

	public void reducePiercingArrowCount() {
		if (piercingArrowCount > 0)
			piercingArrowCount--;
	}

	public void reduceExplosiveArrowCount() {
		if (explosiveArrowCount > 0)
			explosiveArrowCount--;
	}

	public int getScore() {
		return score;
	}

	public int getPiercingArrowCount() {
		return piercingArrowCount;
	}

	public int getExplosiveArrowCount() {
		return explosiveArrowCount;
	}

	public int getDirection() {
		return direction;
	}

	public int getHP() {
		return HP;
	}

	public boolean getAttackCoolDown() {
		return attackCoolDown > 0;
	}

	private boolean getImmortal() {
		return immortalCoolDown > 0;
	}

	public int[] getCenter() {
		int[] center_coordinates = new int[2];
		center_coordinates[0] = x + 90;
		center_coordinates[1] = y + 90;
		return center_coordinates;
	}
}

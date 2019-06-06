package ctrl;

import go.*;

import java.util.Random;

public class AI {
	private Random r = new Random();
	private int difficulty;

	public AI(int difficulty) {
		this.difficulty = difficulty;
	}

	public void attemptToStandOnPlayer(Enemy subject, Player target, int targetSpeed) {
		if (subject.getCenter()[0] - target.getCenter()[0] > targetSpeed)
			subject.setDx(-targetSpeed);
		else if (subject.getCenter()[0] - target.getCenter()[0] < -targetSpeed)
			subject.setDx(targetSpeed);
		else
			subject.setDx(0);

		if (subject.getCenter()[1] - target.getCenter()[1] > targetSpeed)
			subject.setDy(-targetSpeed);
		else if (subject.getCenter()[1] - target.getCenter()[1] < -targetSpeed)
			subject.setDy(targetSpeed);
		else
			subject.setDy(0);
	}

	// settings:
	// 1 for x axis, 2 for y axis
	// Mainly designed for boss and rock-thrower.
	public void allignToPlayer(Player player, Enemy enemy, int setting, int speed) {
		switch (setting) {
		case 1:
			if (enemy.getCenter()[0] - player.getCenter()[0] > speed)
				enemy.setDx(-speed);
			else if (enemy.getCenter()[0] - player.getCenter()[0] < -speed)
				enemy.setDx(speed);
			else
				enemy.setDx(0);
			break;
		case 2:
			if (enemy.getCenter()[1] - player.getCenter()[1] > speed)
				enemy.setDy(-speed);
			else if (enemy.getCenter()[1] - player.getCenter()[1] < -speed)
				enemy.setDy(speed);
			else
				enemy.setDy(0);
			break;
		}
	}

	// bat goes in zig-zags
	public void backOffObstacle(Enemy enemy, Obstacle obstacle) {
		if (enemy.getDXTickAheadBounds().intersects(obstacle.getBounds()))
			enemy.setDx(-enemy.getDx());
		if (enemy.getDYTickAheadBounds().intersects(obstacle.getBounds()))
			enemy.setDy(-enemy.getDy());
	}

	public void randomAI(Enemy enemy) {
		int XMod = r.nextBoolean() ? 1 : -1;
		int YMod = r.nextBoolean() ? 1 : -1;
		int randX = r.nextInt(3) + 2 * (difficulty + 1);
		int randY = r.nextInt(3) + 2 * (difficulty + 1);
		if (enemy.getDx() == 0)
			enemy.setDx(randX * XMod);
		if (enemy.getDy() == 0)
			enemy.setDy(randY * YMod);
	}

	// this command makes monsters patrol around the arena
	public void IdleMovement(Enemy enemy) {
		if (enemy.getDx() == 0 && enemy.getDy() == 0) {
			int direction = r.nextBoolean() ? 1 : -1;
			if (r.nextBoolean())
				enemy.setDx(direction);
			else
				enemy.setDy(direction);
		}
		int distX = enemy.getInitX() - enemy.getX() > 0 ? enemy.getInitX() - enemy.getX()
				: enemy.getX() - enemy.getInitX();
		int distY = enemy.getInitY() - enemy.getY() > 0 ? enemy.getInitX() - enemy.getY()
				: enemy.getY() - enemy.getInitX();

		if (distX > 100)
			enemy.setDx(-enemy.getDx());
		if (distY > 100)
			enemy.setDy(-enemy.getDy());
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}

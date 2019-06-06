/*
    Class that renders/ticks all game objects and stores obstacles for one map.
*/

package ctrl;

import go.*;
import rnd.ImageLoader;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Handler {
	// Generalised arrays
	private LinkedList<GameObject> objects = new LinkedList<>();
	private LinkedList<Enemy> enemies = new LinkedList<>(); // used for
															// detecting player

	// Unique item arrays
	private LinkedList<Arrow> arrows = new LinkedList<>();
	private LinkedList<FireBall> fireballs = new LinkedList<>();
	private ArrayList<Obstacle> obstacles = new ArrayList<>();
	private LinkedList<Enemy> crystals = new LinkedList<>();
	private LinkedList<PowerUp> powerUps = new LinkedList<>();

	private Boss boss;
	private Image map;
	private AI ai;
	private Player player;
	private int difficulty, boss_delay, prevBossHP = 10;
	private Sound s = new Sound();
	private Random r = new Random();
	private boolean adventure;

	public Handler(String map_src, Player player, int difficulty, boolean adventure) {
		map = new ImageLoader(map_src).getImage();
		boss_delay = 250;
		this.player = player;
		this.adventure = adventure;
		objects.add(this.player);
		this.difficulty = difficulty;
		ai = new AI(difficulty);
	}

	public Player getPlayer() {
		return this.player;
	}

	public void tick() {
		LinkedList<Arrow> arrowsRemove = new LinkedList<>();
		LinkedList<Enemy> enemiesRemove = new LinkedList<>();
		LinkedList<FireBall> fireballsRemove = new LinkedList<>();
		LinkedList<Enemy> enemiesCollided = new LinkedList<>();
		LinkedList<PowerUp> powerUpRemove = new LinkedList<>();

		if (ai.getDifficulty() != this.difficulty)
			ai.setDifficulty(this.difficulty);

		// iterate through each player fired arrow
		for (Arrow arrow : arrows) {
			// check if arrow has collided with an enemy
			if (!arrowsRemove.contains(arrow)) {
				for (Enemy enemy : enemies) {
					if (arrow.isCollide(enemy)) {
						// behaviour of arrow as stated
						if (arrow.getID() == ID.EXPLOSIVE_ARROW) {
							s.playSound(5);
							for (Enemy enemy2 : enemies) {
								if (arrow.getExplosionBounds().intersects(enemy2.getBounds()))
									enemy2.takeDamage();
							}
							arrowsRemove.add(arrow);
						} else {
							// makes sure that the piercing arrow damages the
							// enemies it goes through once.
							if (!enemiesCollided.contains(enemy)) {
								enemy.takeDamage();
								enemiesCollided.add(enemy);
								if (arrow.getID() == ID.PIERCING_ARROW)
									s.playSound(10);
							}
							if (arrow.getID() == ID.NORMAL_ARROW) {
								arrowsRemove.add(arrow);
							}
						}
					}
				}
			}

			// check if arrow has collided with boss fireball. Boss fireballs
			// generally are able to 'swallow' arrows
			for (FireBall fireball : fireballs) {
				if (arrow.getBounds().intersects(fireball.getBounds()) && arrow.getID() != ID.PIERCING_ARROW)
					arrowsRemove.add(arrow);
			}

			// check if arrow has collided with obstacle
			for (Obstacle obstacle : obstacles) {
				if (arrow.isCollode(obstacle)) {
					arrowsRemove.add(arrow);
					if (arrow.getID() == ID.EXPLOSIVE_ARROW) {
						s.playSound(5);
						for (Enemy enemy : enemies) {
							if (arrow.getExplosionBounds().intersects(enemy.getBounds()))
								enemy.takeDamage();
						}
					}
				}
			}
			if (arrow.getID() != ID.PIERCING_ARROW) {
				int distX = arrow.distanceTravelled()[0];
				int distY = arrow.distanceTravelled()[1];
				if (distX * distX + distY * distY > 150000)
					arrowsRemove.add(arrow);
			}
		}

		// boss projectile
		for (FireBall fireball : fireballs) {
			if (fireball.isCollide(player)) {
				player.takeDamage();
			} else {
				for (Obstacle obstacle : obstacles) {
					if (fireball.isCollode(obstacle))
						fireballsRemove.add(fireball);
				}
			}
		}

		// if a boss exists then do this command
		if (boss != null) {
			boss.setImmune(crystals.size() > 2);
			boolean t_unchanged = true;
			for (Obstacle o : obstacles) {
				if ((r.nextInt(600) > 598) && !o.isCollideY(boss) && t_unchanged) {
					boss.setDy(-boss.getDy());
					t_unchanged = false;
				}
			}
			// cooldown between boss attack
			if (boss_delay == 0) {
				if (boss.canAttack()) {
					int dy;
					int player_boss_XDistance = boss.getCenter()[0] - player.getCenter()[0];
					if (player.getCenter()[1] - boss.getCenter()[1] >= (player_boss_XDistance / 3))
						dy = 8;
					else if (player.getCenter()[1] - boss.getCenter()[1] <= (-player_boss_XDistance / 3))
						dy = -8;
					else
						dy = 0;
					addFireBall(new FireBall(boss.getCenter()[0] - 150, boss.getCenter()[1] - 30, -8, dy));
				}
			} else {
				boss_delay--;
			}

			// boss gains immortality if survival mode
			if (boss.getHP() < prevBossHP) {
				player.addScore(20);
				if (!adventure)
					boss.heal();
			}
			prevBossHP = boss.getHP();
		}

		// AI of enemies by setting up dy and dx of each enemy
		for (Enemy enemy : enemies) {
			if (enemy.getMonsterState() == Enemy.monsterState.IDLE) {
				if (enemy.getID() != ID.BOSS) {
					if (enemy.setAggressiveDistanceFromPlayerSquared(player) < 100000 * (difficulty + 1)) {
						enemy.setMonsterState(Enemy.monsterState.AGGRESSIVE);
					} else {
						ai.IdleMovement(enemy);
					}
				} else {
					if (enemy.setAggressiveDistanceFromPlayerSquared(player) < 360000)
						enemy.setMonsterState(Enemy.monsterState.AGGRESSIVE);
				}
			} else if (enemy.getMonsterState() == Enemy.monsterState.AGGRESSIVE) {
				int slime_speed = difficulty / 2;
				if (enemy.getID() == ID.SLIME)
					ai.attemptToStandOnPlayer(enemy, player, 2 + slime_speed);
				if (enemy.getID() == ID.BAT) {
					ai.randomAI(enemy);
				}
				if (enemy.getBounds().intersects(player.getBounds()))
					player.takeDamage();
			} else if (enemy.getMonsterState() == Enemy.monsterState.DEAD)
				enemiesRemove.add(enemy);
			for (Obstacle obstacle : obstacles)
				ai.backOffObstacle(enemy, obstacle);
		}

		// powerups iteration
		for (PowerUp p : powerUps) {
			if (p.obtainable()) {
				// the powerup wants to coolide with player.
				p.coolidewithPlayer(player);
				if (player.getBounds().intersects(p.getBounds()))
					powerUpRemove.add(p);
			} else {
				powerUpRemove.add(p);
			}
		}

		// remove all objects that should be removed
		for (Arrow arrow : arrowsRemove)
			removeArrow(arrow);
		for (Enemy enemy : enemiesRemove) {
			if (r.nextInt(10) >= 5)
				addPowerUps(new PowerUp(enemy.getX(), enemy.getY(), adventure));
			removeEnemy(enemy);
			player.addScore(10);
		}
		for (FireBall fireball : fireballsRemove)
			removeFireBall(fireball);
		for (PowerUp p : powerUpRemove)
			removePowerUp(p);

		// prevent player from going through obstacle
		for (Obstacle obstacle : obstacles) {
			if (player.getOneTickAheadBounds().intersects(obstacle.getBounds()))
				player.allowMovement(false);
		}

		for (GameObject tempObj : objects)
			tempObj.tick();
	}

	public void render(Graphics g) {
		g.drawImage(map, 0, 0, null);
		for (GameObject tempObj : objects)
			tempObj.render(g);
	}

	public void addEnemy(Enemy enemy) {
		this.objects.add(enemy);
		this.enemies.add(enemy);
		if (enemy.getID() == ID.ENEMY_CRYSTAL)
			this.crystals.add(enemy);
	}

	public void addArrow(Arrow arrow) {
		this.objects.add(arrow);
		this.arrows.add(arrow);
	}

	public void addFireBall(FireBall fireball) {
		this.objects.add(fireball);
		this.fireballs.add(fireball);
	}

	public void addPowerUps(PowerUp power) {
		this.powerUps.add(power);
		this.objects.add(power);
	}

	public void addObstacle(Obstacle obstacle) {
		this.obstacles.add(obstacle);
	}

	public void removeEnemy(Enemy enemy) {
		this.objects.remove(enemy);
		this.enemies.remove(enemy);
		if (enemy.getID() == ID.ENEMY_CRYSTAL)
			crystals.remove(enemy);
	}

	public void removeArrow(Arrow arrow) {
		this.objects.remove(arrow);
		this.arrows.remove(arrow);
	}

	public void removeFireBall(FireBall fireball) {
		this.objects.remove(fireball);
		this.fireballs.remove(fireball);
	}

	public void removePowerUp(PowerUp power) {
		this.powerUps.remove(power);
		this.objects.remove(power);
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public void setBoss(Boss boss) {
		this.boss = boss;
		if (!this.enemies.contains(this.boss)) {
			this.enemies.add(this.boss);
			this.objects.add(this.boss);
		}
	}

	public int getDifficulty() {
		return difficulty;
	}

	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}
}

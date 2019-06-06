package ctrl;

import go.*;
import go.Enemy.monsterState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

public class Survival extends GamePlay {
	private int difficulty, time_fs, crystalSpawnTime, timeCatch, monsterSpawnRate, mutatedChance;
	private Boss boss;
	private boolean bossMode, updateScore;
	private Random r = new Random();

	private int HighScoreBoss = Integer.parseInt(HighScores.get(4));
	private int HighScoreGen = Integer.parseInt(HighScores.get(3));

	public Survival(boolean bossMode) {
		player = new Player(510, 335, 1);
		id = ID.SURVIVAL;
		time_fs = 0;
		timeCatch = 0; // used as incrementer on when to spawn.
		difficulty = 0;
		mutatedChance = 1;
		updateScore = false;
		crystalSpawnTime = 400;
		monsterSpawnRate = 300;
		stage = GameStage.PLAY;
		this.bossMode = bossMode;
		currentHandler = bossMode ? new Handler("bossMap", player, difficulty, false)
				: new Handler("map3", player, difficulty, false);
		if (bossMode) { // add obstacles for bossMap
			// top left portion of obstacle
			currentHandler.addObstacle(new Obstacle(0, 0, 850, 50));
			currentHandler.addObstacle(new Obstacle(290, 0, 850, 50));
			currentHandler.addObstacle(new Obstacle(50, 50, 265, 190));

			// bottom left portion of obstacle
			currentHandler.addObstacle(new Obstacle(0, 510, 340, 50));
			currentHandler.addObstacle(new Obstacle(50, 540, 310, 250));

			// obstacle for center and right.
			currentHandler.addObstacle(new Obstacle(340, 0, 50, 860));
			currentHandler.addObstacle(new Obstacle(340, 800, 50, 860));
			currentHandler.addObstacle(new Obstacle(1150, 0, 850, 50));
			currentHandler.addObstacle(new Obstacle(1120, 425, 50, 50));
			boss = new Boss(800, 420, 100);
			boss.setDy(2);
			currentHandler.setBoss(boss);
			currentHandler.addEnemy(new Crystal(600, 300));
			currentHandler.addEnemy(new Crystal(700, 600));
			currentHandler.addEnemy(new Crystal(500, 750));
		} else { // add obstacle for map3 as survival mode map
					// exterior of the map
			currentHandler.addObstacle(new Obstacle(0, 0, 850, 30));
			currentHandler.addObstacle(new Obstacle(0, 820, 30, 1200));
			currentHandler.addObstacle(new Obstacle(0, 0, 30, 1200));
			currentHandler.addObstacle(new Obstacle(1170, 0, 1200, 30));

			// side of entrance/exit
			currentHandler.addObstacle(new Obstacle(160, 30, 650, 30));
			currentHandler.addObstacle(new Obstacle(1006, 572, 248, 30));

			// other obstacles
			currentHandler.addObstacle(new Obstacle(495, 611, 51, 259));
			currentHandler.addObstacle(new Obstacle(739, 148, 59, 259));
			currentHandler.addObstacle(new Obstacle(393, 279, 178, 60));
		}
		timer.start();
	}

	public void tick() {
		// score added for every time passed
		if (stage == GameStage.PLAY) {
			// initiate timer if started on pause/new game and increment
			// time_fs.
			timer.start();
			time_fs++;
			timeCatch++;

			if (time_fs % 100 == 0)
				player.addScore(1);
			if (time_fs % 1000 == 0 && time_fs > 0)
				difficulty++;
			if (currentHandler.getDifficulty() != difficulty)
				currentHandler.setDifficulty(difficulty);
			if (player.getHP() == 0)
				stage = GameStage.END;

			// instantly switch to normal arrows when out of power_ups
			if (getPower_up() == 1 && player.getExplosiveArrowCount() == 0) {
				toogleWeapons();
				if (getPower_up() == 2)
					toogleWeapons();
			} else if (getPower_up() == 2 && player.getPiercingArrowCount() == 0)
				toogleWeapons();

			if (time_fs > 0) {
				if (bossMode) {
					// increase fire rate of boss over time and casually spawn
					// other monsters. Crystals may spawn faster.
					if (time_fs % 500 == 0)
						boss.setCoolDownThresh(boss.getCoolDownThresh() - 10);
					if (time_fs % 150 == 0) {
						Enemy enemy;
						int genMonster = r.nextInt(1);
						if (genMonster == 0)
							enemy = new Bat(r.nextInt(650) + 360, r.nextInt(670) + 60, false);
						else
							enemy = new Slime(r.nextInt(650) + 360, r.nextInt(670) + 60, false, difficulty);
						enemy.setMonsterState(monsterState.AGGRESSIVE);
						if ((Math.abs(enemy.getCenter()[0] - player.getCenter()[0]) > 200)
								&& (Math.abs(enemy.getCenter()[1] - player.getCenter()[1]) > 200))
							currentHandler.addEnemy(enemy);
					}
					if (time_fs % 750 == 0) {
						if (crystalSpawnTime > 50)
							crystalSpawnTime -= 25;
					}
					if (timeCatch % crystalSpawnTime == 0) {
						Enemy crystal = new Crystal(r.nextInt(650) + 360, r.nextInt(670) + 60);
						if ((Math.abs(crystal.getCenter()[0] - player.getCenter()[0]) > 200)
								&& (Math.abs(crystal.getCenter()[1] - player.getCenter()[1]) > 200))
							currentHandler.addEnemy(crystal);
						timeCatch = 0;
					}

				} else {
					// initiate spawning of monster
					if (timeCatch >= monsterSpawnRate) {
						Enemy enemy;
						int[] spawnCoordinateX = { 50, 210, 495, 1070 };
						int[] spawnCoordinateY = { 50, 145, 490, 700 };
						int otherAxis = 0;
						boolean isX = r.nextBoolean(); // whether
														// spawnCoordinateX is
														// chosen or not
						int pos = 0;

						if (isX) { // order is (2nd, 3rd, last, 1st) where
									// position in bracket represents closest to
									// the top left.
							switch (r.nextInt(3)) {
							case 0:
								otherAxis = 50 + r.nextInt(370);
								pos++;
								if (getDistance(player, 210, otherAxis) > 200)
									break;
							case 1:
								otherAxis = 50 + r.nextInt(370);
								pos++;
								if (getDistance(player, 4950, otherAxis) > 200)
									break;
							case 2:
								otherAxis = 50 + r.nextInt(700);
								pos++;
								if (getDistance(player, 1070, otherAxis) > 200)
									break;
							case 3:
								pos = 0;
								otherAxis = 50 + r.nextInt(700);
								break;
							}
						} else {
							switch (r.nextInt(3)) {
							case 0:
								otherAxis = 210 + r.nextInt(390);
								pos++;
								if (getDistance(player, otherAxis, 145) > 200)
									break;
							case 1:
								otherAxis = 210 + r.nextInt(390);
								pos++;
								if (getDistance(player, otherAxis, 490) > 200)
									break;
							case 2:
								otherAxis = 50 + r.nextInt(820);
								pos++;
								if (getDistance(player, otherAxis, 700) > 200)
									break;
							case 3:
								pos = 0;
								otherAxis = 210 + r.nextInt(390);
								break;
							}
						}
						int enemyX = isX ? spawnCoordinateX[pos] : otherAxis;
						int enemyY = isX ? otherAxis : spawnCoordinateY[pos];

						int enemyDeclare = r.nextInt(2);
						if (enemyDeclare == 1)
							enemy = new Bat(enemyX, enemyY, r.nextInt(mutatedChance) > 3);
						else
							enemy = new Slime(enemyX, enemyY, r.nextInt(mutatedChance) > 2, difficulty);

						if (time_fs > 250)
							enemy.setMonsterState(monsterState.AGGRESSIVE);
						currentHandler.addEnemy(enemy);

						timeCatch = 0;

						if (time_fs % 500 == 0 && monsterSpawnRate > 70)
							monsterSpawnRate -= 20;
						if (time_fs % 750 == 0 && mutatedChance < 7)
							mutatedChance++;
					}
				}
			}
			currentHandler.tick();
		} else if (stage == GameStage.PAUSE) {
			timer.pause();
		} else if (stage == GameStage.END) {
			timer.stop();
			if (bossMode) {
				if (player.getScore() > HighScoreBoss) {
					HighScoreBoss = player.getScore();
					HighScores.set(4, String.valueOf(HighScoreBoss));
				}
			} else {
				if (player.getScore() > HighScoreGen) {
					HighScoreGen = player.getScore();
					HighScores.set(3, String.valueOf(HighScoreGen));
				}
			}
			if (HighScores != m.readScores() && !updateScore) {
				m.writeScores(HighScores);
				updateScore = true;
			}
		}
	}

	public void render(Graphics g) {
		currentHandler.render(g);
		displayStatus(g);
		if (stage == GameStage.PAUSE)
			pauseRender(g);
		else if (stage == GameStage.END)
			gameOverRender(g);
	}

	private int getDistance(Player player, int Xaxis, int Yaxis) {
		int[] player_center = player.getCenter();
		int Xdist = player_center[0] - Xaxis;
		int Ydist = player_center[1] - Yaxis;
		return (int) Math.sqrt(Xdist * Xdist + Ydist * Ydist);
	}

	protected void gameOverRender(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(300, 50, 840, 700);

		g.setFont(new Font("Arial", Font.BOLD, 100));
		g.setColor(Color.white);
		g.drawString("GAME OVER", 375, 200);
		g.setFont(new Font("Arial", Font.BOLD, 80));
		g.drawString("Score :", 350, 400);
		g.drawString(String.valueOf(player.getScore()), 675, 400);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("Press ESC to go to game selection!", 350, 500);
	}
}

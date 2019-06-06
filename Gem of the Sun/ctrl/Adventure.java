package ctrl;

import go.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Adventure extends GamePlay {
	private int difficulty;
	private Handler handler1, handler2, handler3, handler4, handlerBoss;
	private Boss boss;
	private int HighScoreEasy = Integer.parseInt(HighScores.get(0));
	private int HighScoreMedium = Integer.parseInt(HighScores.get(1));
	private int HighScoreHard = Integer.parseInt(HighScores.get(2));
	private boolean win, updated;

	private Rectangle gate1 = new Rectangle(900, 400, 18, 100);
	private Rectangle gate1b = new Rectangle(40, 0, 120, 30);
	private Rectangle gate2 = new Rectangle(870, 750, 100, 20);
	private Rectangle gate2b = new Rectangle(30, 0, 129, 30);
	private Rectangle gate3 = new Rectangle(1035, 805, 133, 30);
	private Rectangle shopEnter = new Rectangle(680, 120, 160, 130);
	private Rectangle shopExit = new Rectangle(845, 798, 75, 50);
	private int a = 0, b = 0, c = 1;

	public Adventure(int difficulty) {
		win = false;
		updated = false;
		timer.start();
		id = ID.ADVENTURE;
		player = new Player(200, 200, 3);
		this.difficulty = difficulty;

		stage = GameStage.PLAY;

		// ************************ handler 1
		// ************************************//

		handler1 = new Handler("map1", this.player, this.difficulty, true);

		handler1.addObstacle(new Obstacle(0, 0, 825, 10));
		handler1.addObstacle(new Obstacle(0, 30, 30, 900));
		handler1.addObstacle(new Obstacle(0, 810, 30, 900));
		handler1.addObstacle(new Obstacle(905, 0, 395, 33));
		handler1.addObstacle(new Obstacle(925, 0, 900, 1));
		handler1.addObstacle(new Obstacle(905, 500, 400, 33));

		// *************************************************************************//

		// ************************* handler 2
		// ************************************//

		handler2 = new Handler("map2", this.player, this.difficulty, true);

		handler2.addObstacle(new Obstacle(0, 0, 900, 20));
		handler2.addObstacle(new Obstacle(0, 0, 1, 1200));
		handler2.addObstacle(new Obstacle(0, 820, 20, 900));
		handler2.addObstacle(new Obstacle(200, 550, 30, 150));
		handler2.addObstacle(new Obstacle(680, 520, 150, 30));
		handler2.addObstacle(new Obstacle(230, 0, 40, 1200));
		handler2.addObstacle(new Obstacle(1150, 40, 900, 30));
		handler2.addObstacle(new Obstacle(600, 730, 80, 250));
		handler2.addObstacle(new Obstacle(980, 820, 40, 80));
		handler2.addObstacle(new Obstacle(1080, 800, 80, 50));

		handler2.addEnemy(new Bat(1050, 260, false));
		handler2.addEnemy(new Slime(600, 400, false, difficulty));

		// *************************************************************************//

		// ************************ handler 3
		// ************************************//
		handler3 = new Handler("map3", this.player, this.difficulty, true);

		// exterior of the map
		handler3.addObstacle(new Obstacle(0, 0, 850, 30));
		handler3.addObstacle(new Obstacle(0, 820, 30, 1035));
		handler3.addObstacle(new Obstacle(0, 820, 1, 1200));
		handler3.addObstacle(new Obstacle(160, 0, 30, 1040));
		handler3.addObstacle(new Obstacle(0, 0, 1, 1200));
		handler3.addObstacle(new Obstacle(1170, 0, 1200, 30));

		// side of entrance/exit
		handler3.addObstacle(new Obstacle(160, 30, 650, 30));
		handler3.addObstacle(new Obstacle(1006, 572, 248, 30));

		// other obstacles
		handler3.addObstacle(new Obstacle(495, 611, 51, 259));
		handler3.addObstacle(new Obstacle(739, 148, 59, 259));
		handler3.addObstacle(new Obstacle(393, 279, 178, 55));

		handler3.addEnemy(new Bat(300, 250, false));
		handler3.addEnemy(new Slime(1000, 200, false, difficulty));

		// *************************************************************************//

		handler4 = new Handler("shopInside", this.player, this.difficulty, true);

		// ************************ handler Boss
		// ************************************//
		handlerBoss = new Handler("bossMap", this.player, this.difficulty, true);

		// top left portion of obstacle
		handlerBoss.addObstacle(new Obstacle(0, 0, 850, 50));
		handlerBoss.addObstacle(new Obstacle(290, 0, 340, 50));
		handlerBoss.addObstacle(new Obstacle(50, 50, 265, 190));

		// bottom left portion of obstacle
		handlerBoss.addObstacle(new Obstacle(0, 510, 340, 50));
		handlerBoss.addObstacle(new Obstacle(290, 510, 350, 50));
		handlerBoss.addObstacle(new Obstacle(50, 540, 310, 250));

		// obstacle for center and right.
		handlerBoss.addObstacle(new Obstacle(340, 0, 50, 860));
		handlerBoss.addObstacle(new Obstacle(340, 800, 50, 860));
		handlerBoss.addObstacle(new Obstacle(1150, 0, 850, 50));
		handlerBoss.addObstacle(new Obstacle(1120, 425, 50, 50));

		boss = new Boss(800, 420, 100);
		boss.setDy(2);
		handlerBoss.setBoss(boss);
		handlerBoss.addEnemy(new Crystal(600, 300));
		handlerBoss.addEnemy(new Crystal(700, 600));
		handlerBoss.addEnemy(new Crystal(500, 750));

		// *************************************************************************//

		currentHandler = handler1;

	}

	public void tick() {

		if ((stage == GameStage.PLAY)) {
			// initiate timer if started on pause/new game.
			timer.start();
			if (currentHandler.getDifficulty() != difficulty)
				currentHandler.setDifficulty(difficulty);
			if (player.getHP() == 0)stage = GameStage.END;
			if (timer.secondsPassed() >= 300) stage = GameStage.END;
			if (boss.getHP() == 0){
				stage = GameStage.END;
				win = true;
			}
			currentHandler.tick();

		} else if (stage == GameStage.PAUSE) {
			timer.pause();
		} else if (stage == GameStage.END) {
			timer.stop();
			if(win){
				if(!updated){
					int extra_score = 300 - timer.secondsPassed();
					player.addScore(extra_score);
					if(difficulty == 0){
						if(player.getScore() > HighScoreEasy) {
							HighScoreEasy = player.getScore();
							HighScores.set(0, String.valueOf(HighScoreEasy));
						}
					}
					if(difficulty == 1){
						if(player.getScore() > HighScoreMedium) HighScoreMedium = player.getScore();
						HighScores.set(1, String.valueOf(HighScoreMedium));
					}
					if(difficulty == 2){
						if(player.getScore() > HighScoreHard) HighScoreHard = player.getScore();
						HighScores.set(2, String.valueOf(HighScoreHard));
					}
					if (HighScores != m.readScores()) {m.writeScores(HighScores);}
					updated = true;
				}
			}
		}

		// instantly switch to normal arrows when out of power_ups
		if (getPower_up() == 1 && player.getExplosiveArrowCount() == 0) {
			toogleWeapons();
			if (getPower_up() == 2)toogleWeapons();
		} else if (getPower_up() == 2 && player.getPiercingArrowCount() == 0)toogleWeapons();
		
		if (currentHandler == handler1) {
			if (player.getBounds().intersects(gate1)) {
				currentHandler = handler2;
				try {
					Thread.sleep(350);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.setX(30);
				player.setY(10);
				player.setDirection(6);

			} else if (player.getBounds().intersects(shopEnter)) {
				currentHandler = handler4;
				try {
					Thread.sleep(350);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.setX(790);
				player.setY(640);
				player.setDirection(2);
			}
		} else if (currentHandler == handler2) {

			if (b > 150 && c == 1) {
				handler2.addEnemy(new Bat(450, 650, false));
				c++;
			} else if (b > 350 && c == 2) {
				handler2.addEnemy(new Slime(1050, 700, false, difficulty));
				c++;
			}

			else if (b > 600 && c == 3) {
				handler2.addEnemy(new Bat(940, 715, false));
				c++;
			} else if (b > 750 && c == 4) {
				handler2.addEnemy(new Bat(940, 715, false));
				c++;
			} else if (b > 900 && c == 5) {
				handler2.addEnemy(new Slime(1050, 600, true, difficulty));
				c++;
			} else if (b > 1500 && c == 6) {
				handler2.addEnemy(new Bat(940, 715, true));
				c++;
			}
			b++;

			if (player.getBounds().intersects(gate1b)) {
				b = 0;
				c = 1;
				currentHandler = handler1;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.setX(750);
				player.setY(400);
			} else if (player.getBounds().intersects(gate2)) {
				b = 0;
				c = 1;
				currentHandler = handler3;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.setX(20);
				player.setY(10);
			}
		} else if (currentHandler == handler3) {

			if (b > 300 && c == 1) {
				handler3.addEnemy(new Bat(450, 740, false));
				c++;
			} else if (b > 900 && c == 2) {
				handler3.addEnemy(new Bat(1075, 790, false));
				c++;
			} else if (b > 1100 && c == 3) {
				handler3.addEnemy(new Slime(1050, 650, true, difficulty));
				c++;
			} else if (b > 1500 && c == 4) {
				handler3.addEnemy(new Bat(940, 715, true));
				c++;
			}
			b++;

			if (player.getBounds().intersects(gate2b)) {
				currentHandler = handler2;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.setX(860);
				player.setY(600);

			}

			else if (player.getBounds().intersects(gate3)) {
				b = 0;
				c = 1;
				currentHandler = handlerBoss;
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				player.setX(0);
				player.setY(340);
				player.setDirection(4);
			}
		} else if (currentHandler == handler4) {
			if (player.getBounds().intersects(shopExit)) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				currentHandler = handler1;
				player.setX(670);
				player.setY(250);
				player.setDirection(6);
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

		g.setColor(Color.black);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		if (currentHandler == handler1) {

			g.drawString("STAGE 2", 900, 450);
			g.drawRect(900, 400, 25, 100);
		} else if (currentHandler == handler2) {
			g.drawString("STAGE 1", 60, 20);
			g.drawRect(30, 0, 140, 30);

			g.drawString("STAGE 3", 870, 760);
			g.drawRect(870, 740, 100, 30);
		} else if (currentHandler == handler3) {

			g.drawString("STAGE 2", 50, 20);
			g.drawRect(30, 0, 129, 30);

			g.drawString("BOSS!", 1070, 818);
			g.setFont(new Font("TimesRoman", Font.BOLD, 15));
			g.drawString("No Coming Back!", 1030, 840);
			g.drawRect(1035, 798, 133, 50);
		} else if (currentHandler == handlerBoss) {
			g.fillRect(3, 347, 20, 155);
			if (a < 300) {
				g.setFont(new Font("Castellar", Font.BOLD, 80));
				g.drawString("GOOD LUCK", 460, 370);
				g.drawString("Karui", 620, 510);
				a++;

			}

		} else if (currentHandler == handler4) {
			g.setFont(new Font("Impact", Font.BOLD, 35));
			g.drawRect(845, 798, 75, 50);
			Color WHITE = new Color(255, 255, 255);
			g.setColor(WHITE);
			g.drawString("EXIT", 850, 835);

		}
	}
	
	protected void gameOverRender(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(300, 50, 840, 700);

		g.setFont(new Font("Arial", Font.BOLD, 100));
		if(win) {
			g.setColor(Color.green);
			g.drawString("CONGRATZ!", 350, 300);
			g.drawString("YOU WON!", 350, 400);
		}
		g.setColor(Color.white);
		g.drawString("GAME OVER", 375, 200);
		g.setFont(new Font("Arial", Font.BOLD, 80));
		
		g.drawString("Score :", 350, 500);
		g.drawString(String.valueOf(player.getScore()), 675, 500);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("Press ESC to go to game selection!", 350, 600);
	}
}
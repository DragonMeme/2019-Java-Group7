package ctrl;

import go.*;
import rnd.ImageLoader;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class GamePlay {
	public enum GameStage {
		PLAY, PAUSE, END
	}

	protected Memory m = new Memory();
	protected ArrayList<String> HighScores = m.readScores();
	protected Player player;
	private Arrow arrow;
	protected Handler currentHandler;
	protected GameStage stage = GameStage.PLAY;
	protected Stopwatch timer = new Stopwatch();
	protected ID id;

	private int power_up = 0;
	protected Sound s = new Sound();
	private ImageLoader someLoadOut = new ImageLoader("power-ups");
	private BufferedImage heartP = someLoadOut.getPartImage(200, 0, 50, 50);
	private BufferedImage normalArrowP = someLoadOut.getPartImage(250, 0, 50, 50);
	private BufferedImage explosionArrowP = someLoadOut.getPartImage(50, 0, 50, 50);
	private BufferedImage piercingArrowP = someLoadOut.getPartImage(100, 0, 50, 50);

	public abstract void render(Graphics g);

	public abstract void tick();

	public void playerAttack(int power_up) {
		if (!player.getAttackCoolDown())
			makeArrow(power_up);
	}

	private void makeArrow(int power_up) {
		switch (player.getDirection()) {
		case 1: // nw
			arrow = new Arrow(player.getX() + 60, player.getY() + 60, -8, -8, power_up);
			break;
		case 2: // n
			arrow = new Arrow(player.getX() + 75, player.getY() + 50, 0, -8, power_up);
			break;
		case 3: // ne
			arrow = new Arrow(player.getX() + 90, player.getY() + 60, 8, -8, power_up);
			break;
		case 4: // e
			arrow = new Arrow(player.getX() + 120, player.getY() + 75, 8, 0, power_up);
			break;
		case 5: // se
			arrow = new Arrow(player.getX() + 95, player.getY() + 95, 8, 8, power_up);
			break;
		case 6: // s
			arrow = new Arrow(player.getX() + 75, player.getY() + 100, 0, 8, power_up);
			break;
		case 7: // sw
			arrow = new Arrow(player.getX() + 60, player.getY() + 90, -8, 8, power_up);
			break;
		case 8: // w
			arrow = new Arrow(player.getX() + 30, player.getY() + 75, -8, 0, power_up);
			break;
		}
		if (power_up == 1)
			player.reduceExplosiveArrowCount();
		if (power_up == 2)
			player.reducePiercingArrowCount();
		currentHandler.addArrow(arrow);
		player.attack();
		s.playSound(1);
	}

	protected void displayStatus(Graphics g) {
		String numExplosive = String.valueOf(player.getExplosiveArrowCount());
		String numPiercing = String.valueOf(player.getPiercingArrowCount());
		String time = getTime();
		String score = String.valueOf(player.getScore());
		// highlight chosen bolt
		g.setColor(Color.red);
		g.fillRect(1215, 290 + 100 * power_up, 70, 70);

		// highlight power ups
		g.setColor(Color.orange);
		g.fillRect(1220, 295, 60, 60);
		g.fillRect(1220, 395, 60, 60);
		g.fillRect(1220, 495, 60, 60);

		// set fonts
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 25));
		g.drawString("Time: ", 1225, 25);
		g.drawString(time, 1225, 50);
		g.drawString("Score: ", 1225, 100);
		g.drawString(score, 1225, 125);
		g.drawString(" :" + numExplosive, 1280, 430);
		g.drawString(" :" + numPiercing, 1280, 530);

		if (player.getHP() > 0)
			g.drawImage(heartP, 1225, 200, null);
		if (player.getHP() > 1)
			g.drawImage(heartP, 1295, 200, null);
		if (player.getHP() > 2)
			g.drawImage(heartP, 1365, 200, null);
		g.drawImage(normalArrowP, 1225, 300, null);
		g.drawImage(explosionArrowP, 1225, 400, null);
		g.drawImage(piercingArrowP, 1225, 500, null);
	}

	protected String getTime() {
		int minutes = 0, seconds = 0, time_left;
		String m, s;
		if (id == ID.SURVIVAL) {
			minutes = timer.secondsPassed() / 60;
			seconds = timer.secondsPassed() % 60;
		} else if (id == ID.ADVENTURE) {
			time_left = 300 - timer.secondsPassed();
			minutes = time_left / 60;
			seconds = time_left % 60;
		}
		m = Integer.toString(minutes);
		s = Integer.toString(seconds);
		if (seconds < 10)
			s = "0" + s;
		return m + ":" + s;
	}

	protected void pauseRender(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(300, 50, 840, 700);

		g.setColor(Color.red);
		g.fillOval(370, 100, 200, 200);
		g.fillOval(870, 100, 200, 200);
		g.fillRect(470, 100, 500, 200);

		g.setColor(new Color(255, 204, 128));
		g.fillRect(645, 125, 50, 150);
		g.fillRect(745, 125, 50, 150);

		g.setColor(new Color(232, 92, 0));
		g.setFont(new Font("Arial", Font.BOLD, 25));

		g.drawString("[W] [A] [S] [D] Move character!", 350, 330);
		g.drawString("[N] [M] Change character facing direction!", 350, 360);
		g.drawString("[B] Select power-up/normal attack!", 350, 390);
		g.drawString("[SPACE] Shoot!", 350, 420);
		g.drawString("[P] Resume/Pause!", 350, 450);
		g.drawString("[ESC] Return to Game Selection!", 350, 480);

	}

	

	public void setPaused(boolean pause) {
		stage = pause ? GameStage.PAUSE : GameStage.PLAY;
	}

	public GameStage getStage() {
		return stage;
	}

	public void playerMoveLeft() {
		player.setDx(-4);
	}

	public void playerMoveRight() {
		player.setDx(4);
	}

	public void playerMoveUp() {
		player.setDy(-4);
	}

	public void playerMoveDown() {
		player.setDy(4);
	}

	public void playerTurnCW() {
		player.switchDirection(1);
	}

	public void playerTurnCCW() {
		player.switchDirection(-1);
	}

	public void playerXIdle() {
		player.setDx(0);
	}

	public void playerYIdle() {
		player.setDy(0);
	}

	public void toogleWeapons() {
		if (power_up == 0) {
			if (player.getExplosiveArrowCount() > 0)
				power_up = 1;
			else if (player.getPiercingArrowCount() > 0)
				power_up = 2;
		} else if (power_up == 1) {
			if (player.getPiercingArrowCount() > 0)
				power_up = 2;
			else
				power_up = 0;
		} else if (power_up == 2)
			power_up = 0;
	}

	public int getPower_up() {
		return power_up;
	}
}

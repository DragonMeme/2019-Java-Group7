/*
    This is the code where the main function exists.
 */


import ctrl.KeyBoard;
import ctrl.Mouse;
import rnd.*;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -5775159194456889953L;
	private Thread thread;
	private MainController controller;
	private boolean running = false;

	// create a game window along with the components needed to run the
	// application
	private Game() {
		Intro intro = new Intro();
		Hi_Score score = new Hi_Score();
		MainMenu menu = new MainMenu();
		SelectMode select_mode = new SelectMode();
		Settings settings = new Settings();
		Mouse mouse = new Mouse();
		KeyBoard keys = new KeyBoard();
		Help help = new Help();
		Window window = new Window(1440, 900, "Gem of the Sun", this);
		this.addKeyListener(keys);
		this.addMouseListener(mouse);
		controller = new MainController(intro, mouse, window, keys, menu, settings, select_mode, score, help);
	}

	public synchronized void start() {
		if (running)
			return;
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	private synchronized void stop() {
		try {
			thread.join(); // stop the thread
			running = false;
		} catch (Exception e) {
			e.printStackTrace();// print error statement
		}
	}

	// function that does the main render and tick of the game
	// aim is to get approximately 50 frames per second.
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 50.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		// int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			// frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println("FPS: "+ frames);
				// frames = 0;
			}
		}
		stop();
	}

	// main function that makes up the functionality of the application
	private void tick() {
		controller.tick();
	}

	// main function that gives the view of the application
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, 1440, 900);
		controller.render(g);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Game();
	}
}

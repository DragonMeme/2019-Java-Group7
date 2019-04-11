
//The entry point of the game.
//This class loads up a JFrame window and
//puts a GamePanel into it.

package Main;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args) {
		
		JFrame window = new JFrame("Princess of Zelda");
		
		window.add(new GamePanel());
		
		window.setResizable(true);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
}

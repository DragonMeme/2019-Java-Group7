/*
    The main controller of the application.
 */


import ctrl.*;
import rnd.*;

import java.awt.Graphics;

public class MainController {
	private GameState state;
	private GamePlay play;
	private Mouse mouse;
	private Intro intro;
	private MainMenu menu;
	private Settings settings;
	private SelectMode mode;
	private Window window;
	private KeyBoard keys;
	private Hi_Score score;
	private Help help;

	private int debounce_toogle = 0;
	private int new_mode;

	// Initialise of the controller
	public MainController(Intro intro, Mouse mouse, Window window, KeyBoard keys, MainMenu menu, Settings settings,
			SelectMode mode, Hi_Score score, Help help) {
		this.state = GameState.INTRO;
		this.mouse = mouse;
		this.intro = intro;
		this.menu = menu;
		this.settings = settings;
		this.window = window;
		this.keys = keys;
		this.mode = mode;
		this.score = score;
		this.help = help;
	}

	public void render(Graphics g) {
		switch (state) {
		case INTRO:
			intro.render(g);
			break;
		case MAIN_MENU:
			menu.render(g);
			break;
		case SETTINGS:
			settings.render(g);
			break;
		case SELECT_M:
			mode.render(g);
			break;
		case HI_SCORES:
			score.render(g);
			break;
		case HELP:
			help.render(g);
			break;
		case GAME:
			play.render(g);
			break;
		}
	}

	// Tick every part of the application
	public void tick() {
		mouse.setGameState(state);
		switch (state) {
		case INTRO:
			IntroTick();
			break;
		case MAIN_MENU:
			MenuTick();
			break;
		case SETTINGS:
			SettingsTick();
			break;
		case SELECT_M:
			ModeTick();
			break;
		case HI_SCORES:
			ScoreTick();
			break;
		case HELP:
			HelpTick();
			break;
		case GAME:
			GamePlayTick();
			break;
		}
		// Software method of debouncing button toogles if necessary
		if (debounce_toogle > 0)
			debounce_toogle--;
	}

	public GameState getState() {
		return state;
	}

	// Tick the introduction state
	private void IntroTick() {
		if (intro.done())
			state = GameState.MAIN_MENU;
		intro.tick();

	}

	// Check for buttons pressed in main menu
	private void MenuTick() {
		if (mouse.isStartPressed())
			state = GameState.SELECT_M;
		else if (mouse.isSettingsPressed())
			state = GameState.SETTINGS;
		else if (mouse.isHighScoresPressed()) {
			state = GameState.HI_SCORES;
			score.update();
		} else if (mouse.isExitPressed())
			System.exit(0);
		else {
			menu.setJFX(window.JFgetX());
			menu.setJFY(window.JFgetY());
		}
		menu.tick();
	}

	// Check for button toogles
	private void SettingsTick() {
		if (mouse.isBackPressed())
			state = GameState.MAIN_MENU;
		else if (mouse.isMusicPressed()) {
			if (debounce_toogle == 0) {
				settings.setSound_on(!settings.getSound_on());
				debounce_toogle = 20;
			}
		} else if (mouse.isSfxPressed()) {
			if (debounce_toogle == 0) {
				settings.setSfx_on(!settings.getSfx_on());
				debounce_toogle = 20;
			}
		} else {
			settings.setJFY(window.JFgetY());
			settings.setJFX(window.JFgetX());
		}
		settings.tick();
	}

	private void ModeTick() {
		if (mouse.isBackPressed()) {
			if (debounce_toogle == 0) {
				state = GameState.MAIN_MENU;
				debounce_toogle = 20;
			}
		} else if (mouse.isAdventurePressed()) {
			play = new Adventure(mode.getDifficulty());
			state = GameState.GAME;
		} else if (mouse.isSurvivalPressed()) {
			play = new Survival(mode.getBossMode());
			state = GameState.GAME;
		} else if (mouse.isHelpPressed()) {
			state = GameState.HELP;
		} else if (mouse.isDifficultyUpPressed()) {
			if (debounce_toogle == 0) {
				if (new_mode < 2)
					new_mode++;
				mode.setDifficulty(new_mode);
				debounce_toogle = 20;
			}
		} else if (mouse.isDifficultyDownPressed()) {
			if (debounce_toogle == 0) {
				if (new_mode > 0)
					new_mode--;
				mode.setDifficulty(new_mode);
				debounce_toogle = 20;
			}
		} else if (mouse.isBossSurvivalModePressed()) {
			if (debounce_toogle == 0) {
				mode.setBossMode(!mode.getBossMode());
				debounce_toogle = 20;
			}
		} else {
			mode.setJFY(window.JFgetY());
			mode.setJFX(window.JFgetX());
		}
		mode.tick();
	}

	private void ScoreTick() {
		if (mouse.isBackPressed())
			state = GameState.MAIN_MENU;
		else {
			score.setJFY(window.JFgetY());
			score.setJFX(window.JFgetX());
		}
		score.tick();
	}

	private void GamePlayTick() {
		// game controls when game is being played
		if (play.getStage() == GamePlay.GameStage.PLAY) {
			if (keys.isW_Pressed() && !keys.isS_Pressed())
				play.playerMoveUp(); // Make player go up
			else if (keys.isS_Pressed() && !keys.isW_Pressed())
				play.playerMoveDown(); // Make player go down
			else
				play.playerYIdle();
			if (keys.isA_Pressed() && !keys.isD_Pressed())
				play.playerMoveLeft(); // Make player go left
			else if (keys.isD_Pressed() && !keys.isA_Pressed())
				play.playerMoveRight(); // make player go right
			else
				play.playerXIdle();
			if (keys.isN_Pressed()) { // make player change facing direction
										// counter clockwise
				if (debounce_toogle == 0) {
					play.playerTurnCCW();
					debounce_toogle = 10;
				}
			} else if (keys.isM_Pressed()) { // make player change facing
												// direction clockwise
				if (debounce_toogle == 0) {
					play.playerTurnCW();
					debounce_toogle = 10;
				}
			}
			if (keys.isSPACE_Pressed())
				play.playerAttack(play.getPower_up()); // attack command
			else if (keys.isB_Pressed()) { // supposed to be switching between
											// power-ups and normal attacks
				if (debounce_toogle == 0) {
					play.toogleWeapons();
					debounce_toogle = 10;
				}
			}
			if (keys.isP_Pressed()) {
				if (debounce_toogle == 0) {
					play.setPaused(true);
					debounce_toogle = 20;
				}
			}
		} else if (play.getStage() == GamePlay.GameStage.PAUSE) {
			if (keys.isP_Pressed()) {
				if (debounce_toogle == 0) {
					play.setPaused(false);
					debounce_toogle = 20;
				}
			}
		}
		if (keys.isESC_Pressed())
			state = GameState.SELECT_M;

		play.tick();
	}

	private void HelpTick() {
		if (mouse.isBackPressed()) {
			if (debounce_toogle == 0) {
				state = GameState.SELECT_M;
				debounce_toogle = 20;
			}
		} else if (mouse.is1stButtonPressed())
			help.setHelpPick(0);
		else if (mouse.is2ndButtonPressed())
			help.setHelpPick(1);
		else if (mouse.is3rdButtonPressed())
			help.setHelpPick(2);
		else if (mouse.is4thButtonPressed())
			help.setHelpPick(3);
		else if (mouse.is5thButtonPressed())
			help.setHelpPick(4);
		else if (mouse.is6thButtonPressed())
			help.setHelpPick(5);
		else {
			help.setJFY(window.JFgetY());
			help.setJFX(window.JFgetX());
		}
		help.tick();
	}

	public GamePlay getGame() {
		return play;
	}

}

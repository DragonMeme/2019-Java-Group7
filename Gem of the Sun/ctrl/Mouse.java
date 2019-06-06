package ctrl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {
	private GameState state;
	private boolean startPressed, settingsPressed, highScoresPressed, exitPressed, backPressed, musicPressed,
			adventurePressed, survivalPressed, helpPressed, difficultyUpPressed, difficultyDownPressed,
			bossSurvivalModePressed, pausePressed, sfxPressed, firstButtonPressed, secondButtonPressed,
			thirdButtonPressed, fourthButtonPressed, fifthButtonPressed, sixthButtonPressed;

	public void mousePressed(MouseEvent e) {
		int mouse_x = e.getX();
		int mouse_y = e.getY();
		switch (state) {
		case MAIN_MENU:
			if (mouseOver(mouse_x, mouse_y, 570, 200, 300, 300))
				startPressed = true;
			if (mouseOver(mouse_x, mouse_y, 620, 600, 200, 200))
				settingsPressed = true;
			if (mouseOver(mouse_x, mouse_y, 320, 600, 200, 200))
				highScoresPressed = true;
			if (mouseOver(mouse_x, mouse_y, 920, 600, 200, 200))
				exitPressed = true;
			break;
		case SETTINGS:
			if (mouseOver(mouse_x, mouse_y, 320, 600, 200, 200))
				backPressed = true;
			if (mouseOver(mouse_x, mouse_y, 320, 300, 200, 200))
				musicPressed = true;
			if (mouseOver(mouse_x, mouse_y, 620, 300, 200, 200))
				sfxPressed = true;
			break;
		case HI_SCORES:
			if (mouseOver(mouse_x, mouse_y, 320, 600, 200, 200))
				backPressed = true;
			break;
		case SELECT_M:
			if (mouseOver(mouse_x, mouse_y, 320, 600, 200, 200))
				backPressed = true;
			if (mouseOver(mouse_x, mouse_y, 620, 300, 200, 200))
				adventurePressed = true;
			if (mouseOver(mouse_x, mouse_y, 320, 300, 200, 200))
				helpPressed = true;
			if (mouseOver(mouse_x, mouse_y, 920, 300, 200, 200))
				survivalPressed = true;
			if (mouseOver(mouse_x, mouse_y, 670, 525, 100, 100))
				difficultyUpPressed = true;
			if (mouseOver(mouse_x, mouse_y, 670, 700, 100, 100))
				difficultyDownPressed = true;
			if (mouseOver(mouse_x, mouse_y, 970, 625, 100, 100))
				bossSurvivalModePressed = true;
			break;
		case HELP:
			if (mouseOver(mouse_x, mouse_y, 320, 600, 200, 200))
				backPressed = true;
			if (mouseOver(mouse_x, mouse_y, 320, 250, 50, 50))
				firstButtonPressed = true;
			if (mouseOver(mouse_x, mouse_y, 320, 305, 50, 50))
				secondButtonPressed = true;
			if (mouseOver(mouse_x, mouse_y, 320, 360, 50, 50))
				thirdButtonPressed = true;
			if (mouseOver(mouse_x, mouse_y, 320, 415, 50, 50))
				fourthButtonPressed = true;
			if (mouseOver(mouse_x, mouse_y, 320, 470, 50, 50))
				fifthButtonPressed = true;
			if (mouseOver(mouse_x, mouse_y, 320, 525, 50, 50))
				sixthButtonPressed = true;
			break;
		case GAME:
			break;
		default:
			break;
		}
	}

	public void mouseReleased(MouseEvent e) {
		startPressed = false;
		settingsPressed = false;
		highScoresPressed = false;
		exitPressed = false;
		backPressed = false;
		musicPressed = false;
		adventurePressed = false;
		survivalPressed = false;
		helpPressed = false;
		difficultyUpPressed = false;
		difficultyDownPressed = false;
		bossSurvivalModePressed = false;
		pausePressed = false;
		sfxPressed = false;
		firstButtonPressed = false;
		secondButtonPressed = false;
		thirdButtonPressed = false;
		fourthButtonPressed = false;
		fifthButtonPressed = false;
		sixthButtonPressed = false;
	}

	private boolean mouseOver(int mouse_x, int mouse_y, int x, int y, int width, int height) {
		boolean left_most = mouse_x > x;
		boolean right_most = mouse_x < x + width;
		boolean up_most = mouse_y > y;
		boolean down_most = mouse_y < y + height;
		return left_most && right_most && up_most && down_most;
	}

	public boolean isStartPressed() {
		return startPressed;
	}

	public boolean isHighScoresPressed() {
		return highScoresPressed;
	}

	public boolean isSettingsPressed() {
		return settingsPressed;
	}

	public boolean isExitPressed() {
		return exitPressed;
	}

	public boolean isBackPressed() {
		return backPressed;
	}

	public boolean isMusicPressed() {
		return musicPressed;
	}

	public boolean isAdventurePressed() {
		return adventurePressed;
	}

	public boolean isSurvivalPressed() {
		return survivalPressed;
	}

	public boolean isHelpPressed() {
		return helpPressed;
	}

	public boolean isDifficultyUpPressed() {
		return difficultyUpPressed;
	}

	public boolean isDifficultyDownPressed() {
		return difficultyDownPressed;
	}

	public boolean isBossSurvivalModePressed() {
		return bossSurvivalModePressed;
	}

	public boolean isPausePressed() {
		return pausePressed;
	}

	public boolean isSfxPressed() {
		return sfxPressed;
	}

	public boolean is1stButtonPressed() {
		return firstButtonPressed;
	}

	public boolean is2ndButtonPressed() {
		return secondButtonPressed;
	}

	public boolean is3rdButtonPressed() {
		return thirdButtonPressed;
	}

	public boolean is4thButtonPressed() {
		return fourthButtonPressed;
	}

	public boolean is5thButtonPressed() {
		return fifthButtonPressed;
	}

	public boolean is6thButtonPressed() {
		return sixthButtonPressed;
	}

	public void setGameState(GameState state) {
		this.state = state;
	}

}

/*
    INTRO - makes the introduction screen (team logo)
    MAIN_MENU - makes the window display the main menu.
                * has a play button, settings button, highscores and credits.
    SELECT_M - selects between ADVENTURE, SURVIVAL_1 or SURVIVAL_2.
    SETTINGS - allows choice of resetting highscore, toggle music, sfx, skip scenes etc.
    HI_SCORES - displays highscore.
    ADVENTURE - player adventure mode.
    SURVIVAL_1 - player surviving against monster spawns.
    SURVIVAL_2 - player surviving against a boss with infinite points.
    PAUSE - show game paused state.
    G-OVER - make game over screen and update highscore if score is higher.
*/

package ctrl;

public enum GameState {
	INTRO, MAIN_MENU, SELECT_M, SETTINGS, HI_SCORES, HELP, GAME,
}

package ctrl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyBoard extends KeyAdapter {
	private boolean W_Pressed, A_Pressed, S_Pressed, D_Pressed, P_Pressed, M_Pressed, N_Pressed, B_Pressed,
			SPACE_Pressed, ESC_Pressed;

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// move character keybinds
		if (key == KeyEvent.VK_W)
			W_Pressed = true;
		if (key == KeyEvent.VK_A)
			A_Pressed = true;
		if (key == KeyEvent.VK_S)
			S_Pressed = true;
		if (key == KeyEvent.VK_D)
			D_Pressed = true;

		// pause
		if (key == KeyEvent.VK_P)
			P_Pressed = true;
		if (key == KeyEvent.VK_ESCAPE)
			ESC_Pressed = true;

		// change character facing direction
		if (key == KeyEvent.VK_M)
			M_Pressed = true;
		if (key == KeyEvent.VK_N)
			N_Pressed = true;

		// character attacks
		if (key == KeyEvent.VK_B)
			B_Pressed = true; // switch power-ups
		if (key == KeyEvent.VK_SPACE)
			SPACE_Pressed = true; // attack
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W)
			W_Pressed = false;
		if (key == KeyEvent.VK_A)
			A_Pressed = false;
		if (key == KeyEvent.VK_S)
			S_Pressed = false;
		if (key == KeyEvent.VK_D)
			D_Pressed = false;
		if (key == KeyEvent.VK_P)
			P_Pressed = false;
		if (key == KeyEvent.VK_M)
			M_Pressed = false;
		if (key == KeyEvent.VK_N)
			N_Pressed = false;
		if (key == KeyEvent.VK_B)
			B_Pressed = false;
		if (key == KeyEvent.VK_SPACE)
			SPACE_Pressed = false;
		if (key == KeyEvent.VK_ESCAPE)
			ESC_Pressed = false;
	}

	public boolean isA_Pressed() {
		return A_Pressed;
	}

	public boolean isW_Pressed() {
		return W_Pressed;
	}

	public boolean isS_Pressed() {
		return S_Pressed;
	}

	public boolean isD_Pressed() {
		return D_Pressed;
	}

	public boolean isP_Pressed() {
		return P_Pressed;
	}

	public boolean isSPACE_Pressed() {
		return SPACE_Pressed;
	}

	public boolean isB_Pressed() {
		return B_Pressed;
	}

	public boolean isN_Pressed() {
		return N_Pressed;
	}

	public boolean isM_Pressed() {
		return M_Pressed;
	}

	public boolean isESC_Pressed() {
		return ESC_Pressed;
	}
}

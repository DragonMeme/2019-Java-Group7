package go;

import ctrl.Sound;

import java.awt.Rectangle;

public abstract class Enemy extends GameObject {
	public enum monsterState {
		IDLE, AGGRESSIVE, DEAD
	}

	protected monsterState m_state;
	protected int HP, initX, initY;
	protected boolean immune;
	protected Sound s = new Sound();

	public Enemy(int x, int y) {
		super(x, y);
		m_state = monsterState.IDLE;
		immune = false;
		// capture initial positions
		initX = x;
		initY = y;
	}

	public abstract int[] getCenter();

	public abstract Rectangle getDYTickAheadBounds();

	public abstract Rectangle getDXTickAheadBounds();

	public int getHP() {
		return HP;
	}

	public monsterState getMonsterState() {
		return m_state;
	}

	public void setMonsterState(monsterState m_state) {
		this.m_state = m_state;
	}

	public void takeDamage() {
		if (HP > 0 && !immune)
			HP--;
	}

	public int setAggressiveDistanceFromPlayerSquared(Player player) {
		int diffX = player.getCenter()[0] - getCenter()[0];
		int diffY = player.getCenter()[1] - getCenter()[1];
		return (diffX * diffX) + (diffY * diffY);
	}

	public int getInitX() {
		return initX;
	}

	public int getInitY() {
		return initY;
	}
}

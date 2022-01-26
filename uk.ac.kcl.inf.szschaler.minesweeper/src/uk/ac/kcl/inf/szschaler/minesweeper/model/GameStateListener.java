package uk.ac.kcl.inf.szschaler.minesweeper.model;

/**
 * Interface to be implemented by classes interested in changes to the overall game state.
 * 
 * @author Steffen Zschaler
 */
public interface GameStateListener {

	public void mineHit(MineField mineField, int x, int y);

	public void foundAllMines(MineField mineField);
	
}
package uk.ac.kcl.inf.szschaler.minesweeper.model.cells;


/**
 * A factory producing cells as required. Using a factory here enables me to
 * exchange the specific cell implementation easily.
 * 
 * @author Steffen Zschaler
 */
public interface CellFactory {

	/**
	 * Factory method producing a mine cell. The cell is initially undiscovered
	 * and unflagged.
	 * 
	 * @return
	 */
	public Cell createMine();

	/**
	 * Factory method producing an empty cell. The cell is initially undiscovered
	 * and unflagged.
	 * 
	 * @param numMines
	 *            number of mines surrounding this cell.
	 * @return
	 */
	public Cell createEmptyField (int numMines);
}
package uk.ac.kcl.inf.szschaler.minesweeper.model.cells;

/**
 * CellFactory implementation producing inheritance-based cells.
 * 
 * @author Steffen Zschaler
 */
public class InheritanceCellFactory implements CellFactory {

	@Override
	public Cell createMine() {
		return new Mine();
	}

	@Override
	public Cell createEmptyField(int numMines) {
		return new EmptyCell (numMines);
	}
}

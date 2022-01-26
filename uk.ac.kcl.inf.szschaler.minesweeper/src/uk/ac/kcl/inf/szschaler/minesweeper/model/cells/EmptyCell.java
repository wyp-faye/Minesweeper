package uk.ac.kcl.inf.szschaler.minesweeper.model.cells;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * An empty cell in the mine field.
 * 
 * @author Steffen Zschaler
 */
public class EmptyCell extends InheritanceCell {

	/**
	 * Keep track of the number of mines surrounding this cell.
	 */
	private int numMines;

	/**
	 * Create a new empty cell with the given number of surrounding mines.
	 * 
	 * @param numMines
	 */
	public EmptyCell(int numMines) {
		this.numMines = numMines;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.model.Cell#formatUIRepresentation(javax.swing.JButton,
	 * javax.swing.JLabel)
	 */
	@Override
	public Component formatUIRepresentation(JButton jbUnclicked,
			JLabel jlClicked) {
		if (isDiscovered()) {
			jlClicked = (JLabel) super.formatUIRepresentation(jbUnclicked,
					jlClicked);

			if (numMines > 0) {
				jlClicked.setText("" + numMines);
			} else {
				jlClicked.setText("");
			}

			return jlClicked;
		} else {
			return super.formatUIRepresentation(jbUnclicked, jlClicked);
		}
	}

	@Override
	public boolean isEmpty() {
		return (numMines == 0);
	}
}
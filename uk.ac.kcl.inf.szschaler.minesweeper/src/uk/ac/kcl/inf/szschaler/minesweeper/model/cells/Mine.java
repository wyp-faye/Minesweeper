package uk.ac.kcl.inf.szschaler.minesweeper.model.cells;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Mine extends InheritanceCell {

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

			jlClicked.setText("M");

			return jlClicked;
		} else {
			return super.formatUIRepresentation(jbUnclicked, jlClicked);
		}
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.model.Cell#isMine()
	 */
	@Override
	public boolean isMine() {
		return true;
	}

}
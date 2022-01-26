package uk.ac.kcl.inf.szschaler.minesweeper.model.cells;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Inheritance-based implementation of the Cell interface.
 * 
 * @author Steffen Zschaler
 */
public abstract class InheritanceCell implements Cell {
	private boolean fDiscovered = false;
	private boolean fFlagged = false;

	/**
	 * Select either the clicked or unclicked component and format it
	 * appropriately.
	 * 
	 * @param jbUnclicked
	 * @param jlClicked
	 * @return
	 */
	public Component formatUIRepresentation(JButton jbUnclicked,
			JLabel jlClicked) {
		if (isDiscovered()) {
			return jlClicked;
		} else {
			if (isFlagged()) {
				jbUnclicked.setText("F");
			} else {
				jbUnclicked.setText("");
			}

			return jbUnclicked;
		}
	}

	public boolean isDiscovered() {
		return fDiscovered;
	}

	public boolean isFlagged() {
		return fFlagged;
	}

	public void setFlagged(boolean b) {
		fFlagged = b;
	}

	public void setDiscovered() {
		fDiscovered = true;
	}

	public abstract boolean isEmpty();

	/**
	 * Is this a mine?
	 * 
	 * @return
	 */
	public boolean isMine() {
		return false;
	}

	@Override
	public Cell getDiscovered() {
		setDiscovered();
		return this;
	}

	@Override
	public Cell getFlagToggled() {
		setFlagged(!isFlagged());
		return this;
	}
}
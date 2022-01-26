package uk.ac.kcl.inf.szschaler.minesweeper.model.cells;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * An individual cell in the mine field.
 * 
 * @author Steffen Zschaler
 */
public interface Cell {

	/**
	 * Select either the clicked or unclicked component and format it
	 * appropriately.
	 * 
	 * @param jbUnclicked
	 * @param jlClicked
	 * @return
	 */
	public Component formatUIRepresentation(JButton jbUnclicked,
			JLabel jlClicked);

	public boolean isDiscovered();

	public boolean isFlagged();

	public abstract boolean isEmpty();

	public boolean isMine();

	/**
	 * If this cell is not yet discovered, return a discovered version. The
	 * object returned may or may not be the same object as <code>this</code>,
	 * but it must return <code>true</code> in response to an
	 * {@link #isDiscovered()} call.
	 * 
	 * @return
	 */
	public Cell getDiscovered();

	/**
	 * If this cell is not yet discovered, return a version of the cell with a
	 * toggled flag. Otherwise return <code>this</this>. The
	 * object returned may or may not be the same object as <code>this</code>.
	 * 
	 * @return
	 */
	public Cell getFlagToggled();
}
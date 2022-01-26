package uk.ac.kcl.inf.szschaler.minesweeper;

import uk.ac.kcl.inf.szschaler.minesweeper.model.cells.InheritanceCellFactory;
import uk.ac.kcl.inf.szschaler.minesweeper.view.MineFieldFrame;

/**
 * Main class to run mine sweeper.
 * 
 * @author Steffen Zschaler
 */
public class Main {

	public static void main(String[] args) {
		new MineFieldFrame(new InheritanceCellFactory()).setVisible(true);
	}
}
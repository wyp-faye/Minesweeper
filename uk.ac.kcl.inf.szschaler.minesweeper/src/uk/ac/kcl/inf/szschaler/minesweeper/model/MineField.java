package uk.ac.kcl.inf.szschaler.minesweeper.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.table.AbstractTableModel;

import uk.ac.kcl.inf.szschaler.minesweeper.model.cells.Cell;
import uk.ac.kcl.inf.szschaler.minesweeper.model.cells.CellFactory;

/**
 * A rectangular field of mines.
 * 
 * @author Steffen Zschaler
 */
public class MineField extends AbstractTableModel {

	private int width, height, numMines;
	private Cell[][] field;
	private CellFactory cellFactory;
	/**
	 * List of objects interested in learning about the end of game (and,
	 * possibly in later versions, other important changes in game state.
	 */
	private List<GameStateListener> gameStateListeners = new LinkedList<>();

	/**
	 * Create a new mine field of the dimensions indicated.
	 * 
	 * @param width
	 * @param height
	 * @param numMines
	 */
	public MineField(int width, int height, int numMines,
			CellFactory cellFactory) {
		super();
		this.width = width;
		this.height = height;
		this.numMines = numMines;
		this.cellFactory = cellFactory;
		field = new Cell[width][height];
		initField();
	}

	private void initField() {
		// 1. Randomly allocate mines
		Random r = new Random();

		for (int i = 0; i < numMines; i++) {
			boolean fSet = false;
			do {
				int x = r.nextInt(width);
				int y = r.nextInt(height);

				if (field[x][y] == null) {
					field[x][y] = cellFactory.createMine();

					fSet = true;
				}
			} while (!fSet);
		}

		// 2. Fill the rest of the field with empty cells
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (field[x][y] == null) {
					field[x][y] = cellFactory
							.createEmptyField(countMinesAround(x, y));
				}
			}
		}
	}

	private int countMinesAround(int x, int y) {
		int numMines = 0;
		for (int xd = -1; xd <= +1; xd++) {
			for (int yd = -1; yd <= +1; yd++) {
				// don't check the cell itself (although this should not cause a
				// problem)
				if ((xd != 0) || (yd != 0)) {
					int xact = x + xd;
					int yact = y + yd;
					// ensure boundary checks
					if ((xact >= 0) && (yact >= 0) && (xact < width)
							&& (yact < height)) {
						if ((field[xact][yact] != null)
								&& (field[xact][yact].isMine())) {
							numMines++;
						}
					}
				}
			}
		}

		return numMines;
	}

	@Override
	public int getColumnCount() {
		return width;
	}

	@Override
	public int getRowCount() {
		return height;
	}

	@Override
	public Object getValueAt(int row, int col) {
		if ((row >= 0) && (col >= 0) && (row < height) && (col < width)) {
			return field[col][row];
		} else {
			return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Cell.class;
	}

	/**
	 * Toggle the flag status of the indicated cell.
	 * 
	 * @param col
	 * @param row
	 */
	public void toggleFlag(int row, int col) {
		Cell c = (Cell) getValueAt(row, col);
		setValueAt(c.getFlagToggled(), row, col);
	}

	/**
	 * Switches the indicated cell to discovered. If the cell does not contain a
	 * mine and there are no mines around it, recursively discovers the
	 * surrounding cells.
	 * 
	 * @param row
	 * @param col
	 */
	public void discoverCell(int row, int col) {
		doDiscoverCell(row, col);

		if (foundAllMines()) {
			fireFoundAllMines();
		}
	}

	/**
	 * Internal recursive implementation so that {@link #discoverCell(int, int)}
	 * can check for success once and send appropriate events.
	 * 
	 * @param row
	 * @param col
	 */
	private void doDiscoverCell(int row, int col) {
		Cell c = (Cell) getValueAt(row, col);
		if ((c != null) && (!c.isDiscovered())) {
			setValueAt(c.getDiscovered(), row, col);

			if (c.isMine()) {
				// WARNING BILL ROBERTSON WARNING! --- KABOOM!
				fireHitMine(col, row);
				return;
			}

			if (c.isEmpty()) {
				for (int xd = -1; xd <= +1; xd++) {
					for (int yd = -1; yd <= +1; yd++) {
						doDiscoverCell(row + xd, col + yd);
					}
				}
			}
		}
	}

	/**
	 * Check if only mines remain undiscovered.
	 * 
	 * @return true if only mines remain undiscovered.
	 */
	private boolean foundAllMines() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Cell c = (Cell) getValueAt(y, x);

				if (!c.isDiscovered() && !c.isMine()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Let all game state listeners know we've hit a mine.
	 */
	protected void fireHitMine(int x, int y) {
		for (GameStateListener l : gameStateListeners) {
			l.mineHit(this, x, y);
		}
	}

	/**
	 * Let all game state listeners know we've found all mines.
	 */
	protected void fireFoundAllMines() {
		for (GameStateListener l : gameStateListeners) {
			l.foundAllMines(this);
		}
	}

	public void addGameStateChangeListener(GameStateListener l) {
		gameStateListeners.add(l);
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		field[col][row] = (Cell) value;
		fireTableCellUpdated(row, col);
	}

	/**
	 * Reveal all undiscovered mine cells. Do not trigger updates to
	 * GameStateListeners.
	 */
	public void revealAllMines() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (field[x][y].isMine()) {
					setValueAt(field[x][y].getDiscovered(), y, x);
				}
			}
		}
	}
}
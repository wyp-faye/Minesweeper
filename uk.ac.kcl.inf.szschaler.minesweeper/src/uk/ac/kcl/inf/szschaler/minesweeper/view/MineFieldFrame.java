package uk.ac.kcl.inf.szschaler.minesweeper.view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import uk.ac.kcl.inf.szschaler.minesweeper.model.GameStateListener;
import uk.ac.kcl.inf.szschaler.minesweeper.model.MineField;
import uk.ac.kcl.inf.szschaler.minesweeper.model.cells.Cell;
import uk.ac.kcl.inf.szschaler.minesweeper.model.cells.CellFactory;

/**
 * Main frame of the mine sweeper game.
 * 
 * @author Steffen Zschaler
 */
@SuppressWarnings("serial")
public class MineFieldFrame extends JFrame {

	private static class MineCellRenderer implements TableCellRenderer {

		private final JButton jbUnclicked = new JButton();
		private final JLabel jlClicked = new JLabel();

		public MineCellRenderer() {
			// Ensure labels are shown even for relatively small-sized buttons
			jbUnclicked.setMargin(new Insets(0, 0, 0, 0));
		}

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value != null) {
				jbUnclicked.getModel().setPressed(isSelected);
				return ((Cell) value).formatUIRepresentation(jbUnclicked,
						jlClicked);
			} else {
				jlClicked.setText(" ");
				return jlClicked;
			}
		}
	}

	private CellFactory cellFactory;
	private MineField field;
	private JTable jtDisplay;
	private static final int cellSize = 20;
	private MouseAdapter mouseHandler = new MouseAdapter() {

		@Override
		public void mouseReleased (MouseEvent e) {
			Point p = e.getPoint();
			int row = jtDisplay.rowAtPoint(p);
			int col = jtDisplay.columnAtPoint(p);

			if (SwingUtilities.isRightMouseButton(e)) {
				field.toggleFlag(row, col);
			} else if (SwingUtilities.isLeftMouseButton(e)) {
				field.discoverCell(row, col);
			}
		}

	};

	public MineFieldFrame(CellFactory cellFactory) {
		super("Mine Sweeper");

		this.cellFactory = cellFactory;

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		initWidgets();
		initEasy();
	}

	private void initWidgets() {
		setLayout(new FlowLayout());
		setResizable(false);

		jtDisplay = new JTable();
		jtDisplay.setDefaultRenderer(Cell.class, new MineCellRenderer());

		jtDisplay.setRowHeight(cellSize);
		jtDisplay.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jtDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtDisplay.setCellSelectionEnabled(true);

		add(jtDisplay);

		JMenuBar jmb = new JMenuBar();
		setJMenuBar(jmb);

		JMenu jmFile = new JMenu("File");
		jmb.add(jmFile);

		JMenu jmNew = new JMenu("New");
		jmFile.add(jmNew);
		jmNew.add(new JMenuItem(new AbstractAction("Easy") {

			@Override
			public void actionPerformed(ActionEvent e) {
				initEasy();
			}
		}));
		jmNew.add(new JMenuItem(new AbstractAction("Medium") {

			@Override
			public void actionPerformed(ActionEvent e) {
				initMedium();
			}
		}));
		jmNew.add(new JMenuItem(new AbstractAction("Hard") {

			@Override
			public void actionPerformed(ActionEvent e) {
				initHard();
			}
		}));

		jmFile.add(new JMenuItem(new AbstractAction("Exit") {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}));
	}

	/**
	 * Initialise the mine field and set up appropriate listeners.
	 * 
	 * @param width
	 * @param height
	 * @param numMines
	 */
	private void initField(int width, int height, int numMines) {
		field = new MineField(width, height, numMines, cellFactory);
		jtDisplay.setModel(field);

		Enumeration<TableColumn> eCols = jtDisplay.getColumnModel()
				.getColumns();
		while (eCols.hasMoreElements()) {
			TableColumn tc = eCols.nextElement();
			tc.setWidth(cellSize);
			tc.setPreferredWidth(cellSize);
			tc.setMaxWidth(cellSize);
			tc.setMinWidth(cellSize);
		}

		jtDisplay.removeMouseListener(mouseHandler); // just in case...
		jtDisplay.addMouseListener(mouseHandler);

		field.addGameStateChangeListener(new GameStateListener() {

			@Override
			public void mineHit(MineField mineField, int x, int y) {
				removeListeners();
				field.revealAllMines();
				JOptionPane.showMessageDialog(MineFieldFrame.this, "KABOOM!!");
			}

			@Override
			public void foundAllMines(MineField mineField) {
				removeListeners();
				JOptionPane.showMessageDialog(MineFieldFrame.this,
						"Well Done! You have successfully found all mines!");
			}

			private void removeListeners() {
				jtDisplay.removeMouseListener(mouseHandler);
			}
		});

		pack();
	}

	/**
	 * Initialise the game for an easy round.
	 */
	protected void initEasy() {
		initField(9, 9, 10);
	}

	/**
	 * Initialise the game for a medium round.
	 */
	protected void initMedium() {
		initField(16, 16, 40);
	}

	/**
	 * Initialise the game for a hard round.
	 */
	protected void initHard() {
		initField(30, 16, 99);
	}
}
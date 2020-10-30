package shingshang.abstraction;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.lang.Math;

/** Represents the board.
 * @author Tanguy Mossion
 */

public class Board {
	
	// CONSTANTS
	private final static int NB_LIN = 10;
	private final static int NB_COL = 10;
	private final static int X = 0;
	private final static int Y = 1;
	
	// INSTANCE VARIABLES (ATTRIBUTES)

	/**
	 * The board of the game, it's a two dimension tab of Cell
	 */
	static public Cell board[][];
	private Player player1;
	private Player player2;
	public Cell cellSelected;
	
	// METHODS
	
	/**
	 * Constructor initializing the Board with the beginning placement of the bushis
	 */
	public Board() {
		reset();
	}

	/**
	 * Reset the board placing the bushis to their beginning placement
	 */
	public void reset() {
		resetBoard();
		resetBushis();
	}
	
	/**
	 * Part of the constructor initializing the board with empty cells 
	 */
	public void resetBoard() {
		board = new Cell[NB_COL][NB_LIN];
		for (int col = 0 ; col < NB_LIN ; col++ ) {
			for (int lin = 0 ; lin < NB_COL ; lin++) {
				// Initialize all cells which are not portals or invalid cells
				if(((col != 4 || col != 5) && (lin != 1 || lin != 8))
						&& ((col != 0 && col != 9) || (lin ==  4 || lin == 5)))
					board[col][lin] = new Cell(false, true);
				// Initialize portals
				if((col == 4 || col == 5) && (lin == 1 || lin == 8))
					board[col][lin] = new Cell(true, true);
				// Initialize invalid cells
				if((col == 0 || col == 9) && (lin != 4 && lin != 5))
					board[col][lin] = new Cell(false, false);
			}
		}		
	}
	
	/**
	 * Part of the constructor initializing the bushis on the board
	 */
	public void resetBushis() {
		this.player1 = new Player("Player 1");
		this.player2 = new Player("Player 2");
		
		/// Dragons
		// P1
		Bushi bushiP1Dragon1 = new Bushi(BushiType.DRAGON, player1, board[1][0]);
		Bushi bushiP1Dragon2 = new Bushi(BushiType.DRAGON, player1, board[8][0]);
		// P2
		Bushi bushiP2Dragon1 = new Bushi(BushiType.DRAGON, player2, board[1][9]);
		Bushi bushiP2Dragon2 = new Bushi(BushiType.DRAGON, player2, board[8][9]);
		
		/// Lions
		// P1
		Bushi bushiP1Lion1 = new Bushi(BushiType.LION, player1, board[2][0]);
		Bushi bushiP1Lion2 = new Bushi(BushiType.LION, player1, board[7][0]);
		Bushi bushiP1Lion3 = new Bushi(BushiType.LION, player1, board[1][1]);
		Bushi bushiP1Lion4 = new Bushi(BushiType.LION, player1, board[8][1]);
		// P2
		Bushi bushiP2Lion1 = new Bushi(BushiType.LION, player2, board[2][9]);
		Bushi bushiP2Lion2 = new Bushi(BushiType.LION, player2, board[7][9]);
		Bushi bushiP2Lion3 = new Bushi(BushiType.LION, player2, board[1][8]);
		Bushi bushiP2Lion4 = new Bushi(BushiType.LION, player2, board[8][8]);
		
		/// Monkeys
		// P1
		Bushi bushiP1Monkey1 = new Bushi(BushiType.MONKEY, player1, board[3][0]);
		Bushi bushiP1Monkey2 = new Bushi(BushiType.MONKEY, player1, board[6][0]);
		Bushi bushiP1Monkey3 = new Bushi(BushiType.MONKEY, player1, board[2][1]);
		Bushi bushiP1Monkey4 = new Bushi(BushiType.MONKEY, player1, board[7][1]);
		Bushi bushiP1Monkey5 = new Bushi(BushiType.MONKEY, player1, board[1][2]);
		Bushi bushiP1Monkey6 = new Bushi(BushiType.MONKEY, player1, board[8][2]);
		// P2
		Bushi bushiP2Monkey1 = new Bushi(BushiType.MONKEY, player2, board[3][9]);
		Bushi bushiP2Monkey2 = new Bushi(BushiType.MONKEY, player2, board[6][9]);
		Bushi bushiP2Monkey3 = new Bushi(BushiType.MONKEY, player2, board[2][8]);
		Bushi bushiP2Monkey4 = new Bushi(BushiType.MONKEY, player2, board[7][8]);
		Bushi bushiP2Monkey5 = new Bushi(BushiType.MONKEY, player2, board[1][7]);
		Bushi bushiP2Monkey6 = new Bushi(BushiType.MONKEY, player2, board[8][7]);
	}
	
	/**
	 * Returns a Cell object
	 * @param  col	the column of the cell
	 * @param  lin	the line of the cell
	 * @return		the cell at the position [col][lin]
	 * @see    Cell
	 */
	public Cell getCell(int col, int lin) {
		return board[col][lin];
	}
	
	public int[] getCoordinatesOfCell(Cell cell) {
		int[] coordinates = new int[2];
		for (int col = 0 ; col < NB_LIN ; col++ ) {
			for (int lin = 0 ; lin < NB_COL ; lin++) {
				if(getCell(col,lin) == cell) {
					coordinates[X] = col;
					coordinates[Y] = lin;
				}
			}
		}
		return coordinates;
	}
	
	/**
	 * Display the board in the console
	 */
	public void displayBoard() {
		for (int lin = 0 ; lin < NB_LIN ; lin++ ) {
			for (int col = 0 ; col < NB_COL ; col++) {
				displayCell(board[col][lin]);
				System.out.print("  ");
			}
			System.out.print("\n\n");
		}
	}
	
	/**
	 * Display a cell in the console. There are different representations depending on the cell state.
	 * @param  cell	the cell to display on console
	 */
	public void displayCell(Cell cell) {
		if(cell.isPortal())
		{
			System.out.print("P");
		}
		else if(!cell.isValid())
		{
			System.out.print("⬛");
		}
		else if(cell.isEmpty())
		{
			System.out.print("□");
		}
		else if(cell.getBushi().getBushiType() == BushiType.DRAGON)
		{
			System.out.print("D");
		}
		else if(cell.getBushi().getBushiType() == BushiType.LION)
		{
			System.out.print("L");
		}
		else if(cell.getBushi().getBushiType() == BushiType.MONKEY)
		{
			System.out.print("M");
		}
		else // Unrecognized
		{
			System.out.print("?");
		}
	}
	
	/**
	 * Return a list of cells where the bushi selected can move
	 * @return	the cells where the bushi selected can move
	 */
	public List<Cell> getCellsWhereBushiSelectedCanMove()
	{
		List<Cell> cellsWhereBushiSelectedCanMove = new ArrayList<Cell>();
		cellsWhereBushiSelectedCanMove.addAll(getCellsWhichBushiSelectedCanReach());
		this.removeCellsInvalidInList(cellsWhereBushiSelectedCanMove);
		this.removeCellsOccupiedInList(cellsWhereBushiSelectedCanMove);
		this.removeCellsPortalInList(cellsWhereBushiSelectedCanMove);
		return cellsWhereBushiSelectedCanMove;
	}
	
	/**
	 * Return a list of cells which the bushi selected can reach
	 * @return	the cells which the bushi selected can reach
	 */
	public List<Cell> getCellsWhichBushiSelectedCanReach()
	{
		List<Cell> cellsWhichBushiSelectedCanReach = new ArrayList<Cell>();
		int[] coordinatesOfCellSelected = getCoordinatesOfCell(this.cellSelected);
		Bushi bushiSelected = this.cellSelected.getBushi();
		int movementPoint = bushiSelected.getMovementPoint();
		for(int i = coordinatesOfCellSelected[X] - movementPoint ; 
				i <= coordinatesOfCellSelected[X] + movementPoint ; i++)
		{

			for(int j = coordinatesOfCellSelected[Y] - movementPoint ; 
					j <= coordinatesOfCellSelected[Y] + movementPoint ; j++)
			{
				if(i >= 0  && i < NB_LIN && j >= 0 && j < NB_LIN) {
					// Exclude the cellSelected and invalid cells
					if(!(coordinatesOfCellSelected[X] == i
							&& coordinatesOfCellSelected[Y] == j)) 
					{
						// Add cells which the Bushi can reach horizontally and vertically
						if(coordinatesOfCellSelected[X] == i
								|| coordinatesOfCellSelected[Y] == j)
						{
							cellsWhichBushiSelectedCanReach.add(getCell(i,j));
						}
						// Add cells which the Bushi can reach diagonally
						else if((Math.abs(coordinatesOfCellSelected[X] - i) == 1
								&& (Math.abs(coordinatesOfCellSelected[Y] - j) == 1 || (Math.abs(coordinatesOfCellSelected[Y] + j) == 1))
								|| (Math.abs(coordinatesOfCellSelected[X] + i) == 1
										&& (Math.abs(coordinatesOfCellSelected[Y] - j) == 1 || (Math.abs(coordinatesOfCellSelected[Y] + j) == 1)))))
						{
							cellsWhichBushiSelectedCanReach.add(getCell(i,j));
						}
						else if ((Math.abs(coordinatesOfCellSelected[X] - Math.abs(i)) == 2)
								&& (Math.abs(coordinatesOfCellSelected[Y] - Math.abs(j)) == 2)) 
						{
							cellsWhichBushiSelectedCanReach.add(getCell(i,j));
						}
					}
				}
			}
		}
		return cellsWhichBushiSelectedCanReach;
	}
	
	/**
	 * Remove the cells occupied from a list of cells
	 * @param 	a list of cell
	 * @return	a list of cell where none is occupied
	 */
	public List<Cell> removeCellsOccupiedInList(List<Cell> cells)
	{
		ListIterator<Cell> itCell = cells.listIterator();
		while(itCell.hasNext())
		{
			if(!itCell.next().isEmpty())
				itCell.remove();
		}
		return cells;
	}
	
	/**
	 * Remove the cells which are portals from a list of cells
	 * @param 	a list of cell
	 * @return	a list of cell where none is a portal
	 */
	public List<Cell> removeCellsPortalInList(List<Cell> cells)
	{
		ListIterator<Cell> itCell = cells.listIterator();
		while(itCell.hasNext())
		{
			if(itCell.next().isPortal())
				itCell.remove();
		}
		return cells;
	}
	
	/**
	 * Remove the invalid cells from a list of cells
	 * @param 	a list of cell
	 * @return	a list of cell where none is invalid
	 */
	public List<Cell> removeCellsInvalidInList(List<Cell> cells)
	{
		ListIterator<Cell> itCell = cells.listIterator();
		while(itCell.hasNext())
		{
			if(!itCell.next().isValid())
				itCell.remove();
		}
		return cells;
	}
}
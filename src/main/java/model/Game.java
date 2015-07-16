package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Game state class.
 * 
 * @author Jockay
 *
 */
public class Game {
	/** State of Player 1. */
	public Player p1;
	/** State of Player 2. */
	public Player p2;
	/** Game board. */
	public int[][] board;
	/** Decides if the game is over. */
	private boolean over;
	/** Decides if the game goes on player against player. */
	private boolean againstPlayer;
	
	/**
	 * Constructor for Game class. 
	 */
	public Game() {
		super();
		this.p1 = new Player("Player-1", true);
		this.p2 = new Player("Player-2", false);
		this.board = new int[][] { 
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
			   // 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12
		};
		this.over = false;
	}
		
	/**
	 * Returns the actual player in the game.
	 * 
	 * @return Actual player in the game.
	 */
	public Player actualPlayer() {
		return this.p1.isTurn() ? this.p1 : this.p2;
	}
	
	/**
	 * Returns the other player beside the actual player.
	 * 
	 * @return Other player beside the actual player.
	 */
	public Player notActualPlayer() {
		return this.p1.isTurn() ? this.p2 : this.p1;
	}
	
	/**
	 * Returns the first player.
	 * 
	 * @return First player.
	 */
	public Player getP1() {
		return this.p1;
	}
	
	/**
	 * Returns the second player.
	 * 
	 * @return Second player.
	 */
	public Player getP2() {
		return this.p2;
	}
	
	/**
	 * Switches the actual player.
	 */
	public void switchPlayer() {
		if(p1.isTurn()) {
			p1.setTurn(false);
			p2.setTurn(true);
		} else {
			p1.setTurn(true);
			p2.setTurn(false);
		}
	}
	
	/**
	 * Decides if the game is draw. The board is full of signs but there is no 
	 * winner combination, then the game is draw.
	 * 
	 * @return True if the game is draw.
	 */
	public boolean isDraw() {
		for (int i = 0; i < 13; ++i)
			for (int j = 0; j < 13; ++j)
				if (this.board[i][j] == 0)
					return false;
		return true;
	}
	
	/**
	 * Checks the board for five signs next to each other in a row or 
	 * column or diagonal direction.
	 * 
	 * @return List of winner sign coordinates or one sized list (contains
	 * 		   a zero coordinate) if the game is drawn or empty list if no 
	 * 		   winner sign combination found.
	 */
	public List<Coordinate> checkBoard() {
		List<Coordinate> winnerCoords = new ArrayList<Coordinate>(); 

		if (isDraw()) { 
			winnerCoords.add(new Coordinate(0, 0));
			return winnerCoords;
		}

		// sorok
		for (int i = 0; i <= 12; ++i)
			for (int j = 0; j <= 8; ++j)
				if (board[i][j] != 0 
						&& board[i][j] == board[i][j + 1]
						&& board[i][j] == board[i][j + 2]
						&& board[i][j] == board[i][j + 3]
						&& board[i][j] == board[i][j + 4]) {
					winnerCoords.add(new Coordinate(i, j));
					winnerCoords.add(new Coordinate(i, j + 1));
					winnerCoords.add(new Coordinate(i, j + 2));
					winnerCoords.add(new Coordinate(i, j + 3));
					winnerCoords.add(new Coordinate(i, j + 4));
					return winnerCoords;
				}
		// oszlopok
		for (int i = 0; i <= 8; ++i)
			for (int j = 0; j <= 12; ++j)
				if (board[i][j] != 0 
						&& board[i][j] == board[i + 1][j]
						&& board[i][j] == board[i + 2][j]
						&& board[i][j] == board[i + 3][j]
						&& board[i][j] == board[i + 4][j]) {
					winnerCoords.add(new Coordinate(i, j));
					winnerCoords.add(new Coordinate(i + 1, j));
					winnerCoords.add(new Coordinate(i + 2, j));
					winnerCoords.add(new Coordinate(i + 3, j));
					winnerCoords.add(new Coordinate(i + 4, j));
					return winnerCoords;
				}

		// bal-jobb le
		for (int i = 0; i <= 8; ++i)
			for (int j = 0; j <= 8; ++j)
				if (board[i][j] != 0 
				        && board[i][j] == board[i + 1][j + 1]
						&& board[i][j] == board[i + 2][j + 2]
						&& board[i][j] == board[i + 3][j + 3]
						&& board[i][j] == board[i + 4][j + 4]) {
					winnerCoords.add(new Coordinate(i, j));
					winnerCoords.add(new Coordinate(i + 1, j + 1));
					winnerCoords.add(new Coordinate(i + 2, j + 2));
					winnerCoords.add(new Coordinate(i + 3, j + 3));
					winnerCoords.add(new Coordinate(i + 4, j + 4));
					return winnerCoords;
				}

		// bal-jobb fel
		for (int i = 0; i <= 8; ++i)
			for (int j = 4; j <= 12; ++j)
				if (board[i][j] != 0 
				        && board[i][j] == board[i + 1][j - 1]
						&& board[i][j] == board[i + 2][j - 2]
						&& board[i][j] == board[i + 3][j - 3]
						&& board[i][j] == board[i + 4][j - 4]) {
					winnerCoords.add(new Coordinate(i, j));
					winnerCoords.add(new Coordinate(i + 1, j - 1));
					winnerCoords.add(new Coordinate(i + 2, j - 2));
					winnerCoords.add(new Coordinate(i + 3, j - 3));
					winnerCoords.add(new Coordinate(i + 4, j - 4));
					return winnerCoords;
				}
	
		return winnerCoords;
	}
	
	/**
	 * Sets a board value on a specified coordinate for the actual player's sign,
	 * then adds coordinate to the actual player's sign list.  
	 * 
	 * @param coordinate Coordinate to set value on.
	 * @return True if a sign can be placed on the board at the specified 
	 * 		   coordinate, else returns false.
	 */
	public boolean putSign(Coordinate coordinate) {
		if(getBoardValue(coordinate) == 0) {
			setBoardValue(coordinate, actualPlayer().getSign());
			actualPlayer().addCoordinate(coordinate);
			return true;
		}
		return false;
	}
	
	/**
	 * Sets a board value at a specified coordinate. 
	 * 
	 * @param coordinate 
	 * @param value Value to set.
	 */
	public void setBoardValue(Coordinate c, int value) {
		this.board[c.getX()][c.getY()] = value;
	}
	
	/**
	 * Returns the state if the game play.
	 * 
	 * @return True if the game is over, else returns false.
	 */
	public boolean isGameOver() {
		return over;
	}
	
	/**
	 * Sets the game play state over.
	 */
	public void gameIsOver() {
		this.over = true;
	}
	
	/**
	 * Returns a value of the game board.
	 * 
	 * @param i X coordinate of the value.
	 * @param j Y coordinate of the value.
	 * @return Value of the game board.
	 */
	public int getBoardValue(Coordinate coordinate) {
		return board[coordinate.getX()][coordinate.getY()];
	}
	
	/**
	 * Decides if the board is empty (contains only zero elements).
	 * 
	 * @return True if the board contains only zero elements, else returns false.
	 */
	public boolean isEmptyBoard() {
		for (int i = 0; i < 13; i++)
			for (int j = 0; j < 13; j++)
				if(board[i][j] != 0) 
					return false;
		return true;
	}
	
	/**
	 * Sets the state for new game.
	 */
	public void newGame() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				this.board[i][j] = 0;
			}
		}
		this.over = false;
		p1.setTurn(true);
		p2.setTurn(false);
	}
	
}

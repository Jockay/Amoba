package model;

import java.util.LinkedList;

/**
 * Player state class.
 * 
 * @author Jockay
 *
 */
public class Player {
	
	/** Name of the player. */
	private String name;
	/** Determines the player actuality status. */
	private boolean isTurn;
	/** Determines the player game starter status. */
	private boolean isStarter;
	/** Contains the player sign coordinates on the game board. */
	private LinkedList<Coordinate> coords;
	/** Determines if the player won the game. */
	private boolean won;
	
	/**
	 * Class constructor.
	 * 
	 * @param name Name of the player.
	 * @param isStarter Game starter status of the player.
	 */
	public Player(String name, boolean isStarter) {
		this.name = name;
		this.won = false;
		this.isTurn = isStarter;
		this.isStarter = isStarter;
		this.coords = new LinkedList<Coordinate>();
	}
	
	/**
	 * Adds a new coordinate to the sign coordinate list of the player.
	 *  
	 * @param x X parameter of the coordinate.
	 * @param y Y parameter of the coordinate.
	 */
	public void addCoordinate(Coordinate coordinate) {
		this.coords.add(coordinate);
	}

	/**
	 * Returns the name of the player.
	 * 
	 * @return Name of the player.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the player.
	 * 
	 * @param name Name of the player.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Determines if the player is actual player.
	 * 
	 * @return True if the player is actual, else returns false.
	 */
	public boolean isTurn() {
		return isTurn;
	}

	/**
	 * Modify the players actuality state.
	 * 
	 * @param isTurn The players actuality state.
	 */
	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}
	
	/**
	 * Determines if the player is game starter.
	 * 
	 * @return True if the player is game starter, else returns false.
	 */
	public boolean isStarter() {
		return isStarter;
	}

	/**
	 * Returns a list of coordinates which contains the coordinates of the player's
	 * signs on the board.
	 * 
	 * @return List of player's signs on the game board.
	 */
	public LinkedList<Coordinate> getCoords() {
		return coords;
	}

	/**
	 * Sets the sign coordinate list of the player.
	 * 
	 * @param coords List of coordinates to set as sign coordinate list 
	 * 		  		for the player.
	 */
	public void setCoords(LinkedList<Coordinate> coords) {
		this.coords = coords;
	}
	
	/**
	 * Returns true if the player has won the game.
	 * 
	 * @return True if the player has won the game, else returns false.
	 */
	public boolean isWon() {
		return won;
	}

	/**
	 * Sets the winning state of the player.
	 * 
	 * @param won Boolean variable to set the winning state of the player.
	 */
	public void setWon(boolean won) {
		this.won = won;
	}
	
	/**
	 * Returns the sign of the player on the game board.
	 * 
	 * @return Integer that represents the sign of the player 
	 * 		   on the game board.
	 */
	public int getSign() {
		if(isStarter())
			return 1;
		return 2;
	}
	
	
}

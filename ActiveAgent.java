/**
 * CS 141: Introduction to Programming and Problem Solving
 * Professor: Edwin Rodr&iacute;guez
 *
 * Final Group Project
 * 
 * For this project we were assigned to develop a game where the player starts 
 * in the bottom left corner of a 9x9 grid. This grid is filled with items, rooms, 
 * and ninjas. There are three items that can be picked up. These items are radar, 
 * invincibility, and an additional bullet. One of these rooms contains an item called 
 * a briefcase and the player’s goal is to find it.The ninjas and items are placed 
 * randomly throughout the grid. The rooms are evenly spread out across the grid, and
 * the briefcase is randomly placed into one of these rooms. The environment the 
 * player is in is completely dark and the player can only see around him. Each turn the
 * player has the option to look further in any direction, as well as move or shoot in any
 * direction. If the player obtains the briefcase the game ends and the player wins. Each
 * ninja will start their turn checking to see if the player is nearby, and then they will
 * move in a random direction. If one of the ninjas catches the player he loses a life and is 
 * placed back at the start. Once the player is out of lives, the game is over and the player 
 * loses.
 * 
 * Group: The Cappucino
 * 
 * Clarissa Esparza
 * Christian Munoz
 * Jacky Hsieh
 * Jonathan Tjitrajadi
 * Tin Huynh
 */
package edu.csupomona.cs.cs141.FinalProject;

import java.io.Serializable;

/**
 * This class represents the active agents in this program. It have the
 * attributes/behaviors associate with the player and the NPC.
 * 
 * @author The Cappucino
 *
 */
public abstract class ActiveAgent implements GameElements, Serializable {

	/**
	 * This field represent this class serial ID.
	 */
	private static final long serialVersionUID = 3361107842599954810L;

	/**
	 * This field represent {@link ActiveAgent}'s status. if {@link #dead} is
	 * {@code false} it mean that the player is still alive, else the player is
	 * {@link #dead}
	 */
	private boolean dead = false;

	/**
	 * This field represent the {@link ActiveAgent}'s symbol, which will be
	 * represent on {@link Grid}.
	 */
	private String symbol;

	/**
	 * This field represent the row positions of the {@link ActiveAgent} on the
	 * grid, this field will keep changing through out the game as the
	 * {@link ActiveAgent} move.
	 */
	private int rowCoordinate;

	/**
	 * This field represent the column positions of the {@link ActiveAgent} on
	 * the grid, this field will keep changing through out the game as the
	 * {@link ActiveAgent} move.
	 */
	private int columnCoordinate;

	/**
	 * This is the constructor which will assign the symbol for each
	 * {@link ActiveAgent}
	 * 
	 * @param s
	 */
	public ActiveAgent(String s) {
		symbol = s;
	}

	/**
	 * This method is an implemented method from {@link GameElements}, this
	 * method will set {@link #dead} to {@code true}.
	 */
	public void getRemoved() {
		dead = true;
	}

	/**
	 * This method is an implemented method from {@link GameElements}, this
	 * method will return whether the {@link ActiveAgent} is {@link #dead} or
	 * not.
	 *
	 * @return Return the value of the boolean {@link #dead}.
	 */
	public boolean isRemoved() {
		return dead;
	}

	/**
	 * This method is an implemented method from {@link GameElements}, it will
	 * return the symbol that is assigned to the {@link ActiveAgent}.
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * This method is an implemented method from {@link GameElements}, it will
	 * update the {@link #rowCoordinate} and the {@link #columnCoordinate} of
	 * the {@link ActiveAgent} as they change throughout the game.
	 */
	public void setCurrentCoordinate(int row, int column) {
		rowCoordinate = row;
		columnCoordinate = column;
	}

	/**
	 * This method is an implemented method from {@link GameElements}, it will
	 * return the {@link #rowCoordinate} of the {@link ActiveAgent}.
	 */
	public int getRow() {
		return rowCoordinate;
	}

	/**
	 * This method is an implemented method from {@link GameElements}, it will
	 * return the {@link #columnCoordinate} of the {@link ActiveAgent}.
	 */
	public int getColumn() {
		return columnCoordinate;
	}

}

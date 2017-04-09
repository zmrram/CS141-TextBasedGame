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

/**
 * This class is a subclass of {@link ActiveAgent}, it inherited the attributes
 * and behavior of an {@link ActiveAgent} and it's own behavior as a
 * {@link Ninja}.This class is used to specify the type {@link Ninja} of
 * {@link ActiveAgent}.
 * 
 * @author The Cappucino
 *
 */
public class Ninja extends ActiveAgent {

	/**
	 * This field represent this class serial ID.
	 */
	private static final long serialVersionUID = 1903599051203095852L;

	/**
	 * The constructor which assign symbols to the {@link Ninja}. Initialize as
	 * {@code "[N]"}
	 */
	public Ninja() {
		super("[N]");
	}

}

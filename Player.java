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
 * {@link Player}.This class is used to specify the type {@link Player} of
 * {@link ActiveAgent}.
 * 
 * @author The Cappucino
 *
 */
public class Player extends ActiveAgent {

	/**
	 * This field represent this class serial ID.
	 */
	private static final long serialVersionUID = -8990122879019084694L;

	/**
	 * This field represent the number of {@link ammo} the {@link Player} hold.
	 * This field is only specify to the {@link Player}, and it is default as
	 * {@code 1}.
	 */
	private int ammo = 1;

	/**
	 * This field represent the number of {@link lives} the {@link Player} have.
	 * This field is only specify to the {@link Player}, and it is default as
	 * {@code 1}.
	 */
	private int lives = 3;

	/**
	 * The constructor which assign symbols to the {@link Player}. Initialize as
	 * {@code "[P]"}
	 */
	public Player() {
		super("[P]");
	}

	/**
	 * This method return the number of {@link #ammo} the {@link Player}
	 * currently hold.
	 * 
	 * @return {@link #ammo}
	 */
	public int getAmmo() {
		return ammo;
	}

	/**
	 * This method return the number of {@link #lives} the {@link Player}
	 * currently have.
	 * 
	 * @return {@link #lives}
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * This field represent the action of {@link #addAmmo()} as the
	 * {@link Player} picked up bullet during run-time.
	 */
	public void addAmmo() {
		ammo++;
	}

	/**
	 * This method represent the action {@link #shoot()} and it will take off
	 * {@code 1} {@link #ammo} from the {@link Player}
	 */
	public void shoot() {
		ammo--;
	}

	/**
	 * This method represent the action {@link #loseLife()} and it will take off
	 * {@code 1} {@link #lives} from the {@link Player}
	 */
	public void loseLife() {
		lives--;
	}

	/**
	 * This method will return a String that contain the {@link Player}'s
	 * informations such as {@link #ammo} and {@link #lives}.
	 */
	public String toString() {
		String info = "\nPlayer status: ";
		info += "\nlives: " + getLives();
		info += "\nammo: " + getAmmo();
		return info;
	}
}

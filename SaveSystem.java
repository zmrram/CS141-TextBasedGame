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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class represent the save/load function in the game.
 * 
 * @author The Cappucino
 *
 */
public class SaveSystem {

	/**
	 * This method will write the whole {@link GameEngine} into the file
	 * {@code save.dat}. Thus allow the user to save the current state of the
	 * game into the said file. This is possible because the {@link GameEngine}
	 * contain all the data/changes of the game.
	 * 
	 * @throws IOException
	 */
	public void saveGame(GameEngine GE) throws IOException {
		FileOutputStream fos = new FileOutputStream("save.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(GE);
		oos.close();
	}

	/**
	 * This method will open the file {@code save.dat} and read the saved
	 * {@link GameEngine}. and return that {@link GameEngine}. Thus allow the
	 * user to load the saved {@link GameEngine}.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public GameEngine loadGame(GameEngine GE) throws IOException,
			ClassNotFoundException {
		FileInputStream fis = new FileInputStream("save.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		GE = (GameEngine) ois.readObject();
		ois.close();
		return GE;
	}
}

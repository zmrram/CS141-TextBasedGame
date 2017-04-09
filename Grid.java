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
import java.util.Random;

/**
 * This class represent the {@link Grid} or "Map" of this game. It's purpose is
 * to create a grid/map, and place the {@link GameElements} onto their positions
 * on the said map.
 * 
 * @author The Cappucino
 *
 */
public class Grid implements Serializable {

	/**
	 * This field represent this class serial ID.
	 */
	private static final long serialVersionUID = -5882389683438274398L;

	/**
	 * This field is an object type {@link Random}.
	 */
	private Random random = new Random();

	/**
	 * This field represent the {@link Grid} of this game, it is a 9x9 array
	 * type {@link GameElements} as it will be filled with {@link ActiveAgent}
	 * and {@link Item}
	 */
	private GameElements[][] map = new GameElements[9][9];

	/**
	 * This field represent the vision of the {@link Player} on the {@link Grid}
	 * , and these value will change as an option of the game to increase
	 * vision.
	 */
	private int leftVision = 0, rightVision = 0, downVision = 0, upVision = 0;

	/**
	 * This field represent the {@link Player}'s row coordinate on the
	 * {@link Grid}. Initialize as {@code 8}. Because the {@link Player} start
	 * at the bottom left of the {@link Grid}.
	 */
	private int playerRow = 8;

	/**
	 * This field represent the {@link Player}'s row coordinate on the
	 * {@link Grid}. Initialize as {@code 8}. Because the {@link Player} start
	 * at the bottom right of the {@link Grid}.
	 */
	private int playerColumn = 0;

	/**
	 * This boolean field represent the status of {@link Radar}. If the
	 * {@link Player} obtained {@link Radar}, the {@link Radar} will turned on
	 * and show the location of the {@link Briefcase} on the {@link Grid}
	 */
	private boolean isRadarOn = false;

	/**
	 * This method populate the {@link Grid} will {@link Player}, {@link Ninja}
	 * and {@link Item}
	 * 
	 * @param agents
	 */
	public void populate(GameElements[] agents) {
		for (int i = 0; i < agents.length; i++) {
			if (agents[i] instanceof Briefcase) {
				spawnBriefcase(agents[i]);
			} else if (agents[i] instanceof Player) {
				spawnPlayer(agents[i]);
			} else if (agents[i] instanceof Ninja) {
				spawnNinjas(agents[i]);
			} else {
				spawnPowerUp(agents[i]);
			}
		}
	}

	/**
	 * This method will clear {@link #map} completely. As the positions of the
	 * {@link GameElements} will be updated.
	 */
	private void clearGrid() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = null;
			}
		}
	}

	/**
	 * This method will first call {@link #clearGrid()} then refill the
	 * {@link #map} with {@link GameElements} in their new positions.
	 * 
	 * @param agents
	 *            The array of agents that will go into the grid.
	 */
	public void repopulate(GameElements[] agents) {
		clearGrid();
		for (int i = 0; i < agents.length; i++) {
			if (!(agents[i].isRemoved())) {
				map[agents[i].getRow()][agents[i].getColumn()] = agents[i];
			}
			if (agents[i] instanceof Player) {
				getPlayerCoordinates((Player) agents[i]);
			}
		}
	}

	/**
	 * This method will be used to keep track of the {@link Player} positions on
	 * the {@link #map}.
	 * 
	 * @param p
	 *            the {@link Player}
	 */
	private void getPlayerCoordinates(Player p) {
		playerRow = p.getRow();
		playerColumn = p.getColumn();
	}

	/**
	 * This method will be used to print the {@link #map} and hiding anything
	 * that is not within vision of the {@link Player}.
	 * 
	 * @return A string that represents the {@link #map}.
	 */
	public String printGrid() {
		String displayMap = "";
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if (!(inVision(i, j)) && !(isRoom(i, j))) {
					displayMap += "[x]";
				} else if ((isRoom(i, j))) {
					if (map[i][j] != null && isRadarOn) {
						displayMap += map[i][j].getSymbol();
					} else {
						displayMap += "[R]";
					}
				} else if (map[i][j] == null) {
					displayMap += "[ ]";
				} else {
					displayMap += map[i][j].getSymbol();
				}
			}
			displayMap += "\n";
		}
		return displayMap;
	}

	/**
	 * This method is used to check whether the coordinate of the
	 * {@link GameElements} is overlapped with the positions of the set room in
	 * the {@link #map}.
	 * 
	 * @param row
	 *            the row coordinate of the {@link GameElements}
	 * @param column
	 *            the column coordinate of the {@link GameElements}
	 * @return {@code true} or {@code false}
	 */
	private boolean isRoom(int row, int column) {
		if ((row == 1 || row == 4 || row == 7)
				&& (column == 1 || column == 4 || column == 7)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method will be used to print the {@link #map} and show everything
	 * that is on the {@link #map} The {@link Radar} is always activate if the
	 * user choose to print this {@link #map}
	 * 
	 * @return A string that represents the {@link #map}.
	 */
	public String printGridDebug() {
		activateRadar();
		String displayMap = "";
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				if ((isRoom(i, j))) {
					if (map[i][j] != null && isRadarOn) {
						displayMap += map[i][j].getSymbol();
					} else {
						displayMap += "[R]";
					}
				} else if (map[i][j] == null) {
					displayMap += "[ ]";
				} else {
					displayMap += map[i][j].getSymbol();
				}
			}
			displayMap += "\n";
		}
		return displayMap;
	}

	/**
	 * This method will assign random location for the {@link Briefcase}, within
	 * one of the 9 rooms.
	 */
	private int getBriefcaseCoordinates() {
		int choice = random.nextInt(3) + 1;
		int coordinates = 0;
		switch (choice) {
		case 1:
			coordinates = 1;
			break;
		case 2:
			coordinates = 4;
			break;
		case 3:
			coordinates = 7;
			break;
		}
		return coordinates;
	}

	/**
	 * This method use the assigned the coordinate from the
	 * {@link #getBriefcaseCoordinates()} and spawn the {@link Briefcase} on the
	 * {@link #map} .
	 */
	private void spawnBriefcase(GameElements B) {
		int row = getBriefcaseCoordinates();
		int column = getBriefcaseCoordinates();
		map[row][column] = B;
		B.setCurrentCoordinate(row, column);
	}

	/**
	 * This method spawn the {@link Player} on the {@link #map} at the
	 * {@link Player} starting position .
	 */
	private void spawnPlayer(GameElements P) {
		int row = 8;
		int column = 0;
		map[row][column] = P;
		P.setCurrentCoordinate(row, column);
	}

	/**
	 * This method will spawn the {@link Ninja} at random positions on the
	 * {@link #map}. And the condition is set that {@link Ninja} cannot spawn in
	 * a room, on top of another {@link GameElements} or anywhere near the
	 * {@link Player}
	 * 
	 */
	private void spawnNinjas(GameElements N) {
		boolean hasSpawned = false;
		while (hasSpawned == false) {
			int row = random.nextInt(9);
			int column = random.nextInt(9);
			if (checkAvailibility(row, column)) {
				map[row][column] = N;
				N.setCurrentCoordinate(row, column);
				hasSpawned = true;
			}
		}
	}

	/**
	 * This method will spawn the {@link Radar}, {@link Bullet} and
	 * {@link Invincibility} power ups at random positions on the {@link #map}.
	 * And the condition is set that they cannot spawn in a room, on top of
	 * another {@link GameElements}.
	 * 
	 */
	private void spawnPowerUp(GameElements p) {
		boolean hasSpawned = false;
		while (hasSpawned == false) {
			int row = random.nextInt(9);
			int column = random.nextInt(9);
			if (checkAvailibility1(row, column)) {
				map[row][column] = p;
				p.setCurrentCoordinate(row, column);
				hasSpawned = true;
			}
		}
	}

	/**
	 * This method check the positions of the {@link Ninja} and make sure they
	 * won't spawn anywhere near the {@link Player}
	 * 
	 * @param row
	 *            row coordinate
	 * @param column
	 *            column coordinate
	 * @return {@code true} or {@code false}
	 */
	private boolean checkAvailibility(int row, int column) {
		if (((row == 1 || row == 4 || row == 7) && (column == 1 || column == 4 || column == 7))
				|| ((row == 8 || row == 7 || row == 6 || row == 5) && (column == 0
						|| column == 1 || column == 2 || column == 3)))
			return false;
		if (map[row][column] != null)
			return false;
		else
			return true;
	}

	/**
	 * This method check the positions of the {@link Ninja} and the {@link Item}
	 * and make sure they do not overlaps with another {@link GameElements} as
	 * they are spawning.
	 * 
	 * @param row
	 *            row coordinate
	 * @param column
	 *            column coordinate
	 * @return {@code true} or {@code false}
	 */
	private boolean checkAvailibility1(int row, int column) {
		if ((row == 1 || row == 4 || row == 7)
				&& (column == 1 || column == 4 || column == 7))
			return false;
		if (map[row][column] != null)
			return false;
		else
			return true;
	}

	/**
	 * This method represent the vision of the {@link Player} inside the
	 * pick-black {@link #map} as it's printed. The {@link Player}'s vision can
	 * change in any direction when the {@link Player} choose to peek.
	 * 
	 * @param row
	 *            row coordinate
	 * @param column
	 *            column coordinate
	 * @return {@code true} or {@code false}
	 */
	private boolean inVision(int row, int column) {

		if (((row == playerRow - 1 || row == playerRow + 1 || row == playerRow) 
				&& (column == playerColumn + 1 || column == playerColumn - 1 || column == playerColumn))
				&& !isRoom(playerRow, playerColumn)) {
			return true;
		}
		if (((row == playerRow - 1 - upVision|| row == playerRow + 1 + downVision || row == playerRow) 
				&& (column == playerColumn + 1 + rightVision|| column == playerColumn - 1 - leftVision || column == playerColumn))
				&& !isRoom(playerRow, playerColumn)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method change the vision of the {@link Player} on the {@link #map}
	 * in the up-direction
	 */
	public void changeUpVision() {
		upVision++;
	}

	/**
	 * This method change the vision of the {@link Player} on the {@link #map}
	 * in the down-direction
	 */
	public void changeDownVision() {
		downVision++;
	}

	/**
	 * This method change the vision of the {@link Player} on the {@link #map}
	 * in the left-direction
	 */
	public void changeLeftVision() {
		leftVision++;
	}

	/**
	 * This method change the vision of the {@link Player} on the {@link #map}
	 * in the right-direction
	 */
	public void changeRightVision() {
		rightVision++;
	}

	/**
	 * This method will reset the {@link Player}'s vision after the action is
	 * finished.
	 */
	public void resetVision() {
		leftVision = 0;
		rightVision = 0;
		downVision = 0;
		upVision = 0;
	}

	/**
	 * This method will activate the {@link Radar} and will show the
	 * {@link Player} the location of the {@link Briefcase}
	 */
	public void activateRadar() {
		isRadarOn = true;
	}

	/**
	 * This method will deactivate the {@link Radar}, because when the user
	 * choose to go on debug mode, radar is activated. So this method is used
	 * when player go back to normal mode from debug mode, and they don't have
	 * radar just yet.
	 */
	public void deactivateRadar() {
		isRadarOn = false;
	}
}

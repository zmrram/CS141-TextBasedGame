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
 * @author The Cappucino
 *
 */
public class GameEngine implements Serializable {

	/**
	 * This field represent this class serial ID.
	 */
	private static final long serialVersionUID = -7218167888207589807L;

	/**
	 * This field represent the level of difficulty of this game. {@code 1}
	 * being easy and {@code 2} is hard.
	 */
	private int gameDifficulty = 1;

	/**
	 * This field is an object type {@link Random}.
	 */
	private Random random = new Random();

	/**
	 * This field represent an array of object type {@link Ninja}, initialize
	 * with the size of {@code 6} because there are @ code 6} {@link Ninja} in
	 * this game.
	 */
	private Ninja[] ninja = new Ninja[6];

	/**
	 * This field represent the {@link Ninja}'s current status.
	 */
	private boolean ninjaIsDead = false;

	/**
	 * This field represent an object of type {@link Player}.
	 */
	private Player player = new Player();

	/**
	 * This field represent an {@link Item} object of type {@link Briefcase}.
	 */
	private Item briefcase = new Briefcase();

	/**
	 * This field represent an {@link Item} object of type {@link Radar}.
	 */
	private Item radar = new Radar();

	/**
	 * This field represent an {@link Item} object of type {@link Bullet}.
	 */
	private Item bullet = new Bullet();

	/**
	 * This field represent an {@link Item} object of type {@link Invincibility}
	 * .
	 */
	private Item invincibility = new Invincibility();

	/**
	 * This field represent the turns have {@link Invincibility} the
	 * {@link Player} have left.
	 */
	private int invincibilityTurns = 0;

	/**
	 * This field represent the condition of which the {@link Player} can win
	 * the game.
	 */
	private boolean winCondition = false;

	/**
	 * This field represent whether the {@link Player} had made an invalid move
	 * or not.
	 */
	private boolean invalidMove = false;

	/**
	 * This field represent an array of {@link GameElements} which contain the
	 * {@link ActiveAgent} and the {@link Item}
	 */
	private GameElements[] agentsArray = new GameElements[11];

	/**
	 * This field represent an object type {@link Grid}
	 */
	private Grid grid = new Grid();

	/**
	 * This constructor initialize the {@link Ninja} array with 6 ninjas and the
	 * {@link GameElements} array with {@link ActiveAgent} and {@link Item}.
	 * This constructor also spawn the {@link GameElements}.
	 */
	public GameEngine() {

		for (int i = 0; i < ninja.length; i++) {
			ninja[i] = new Ninja();
		}
		agentsArray[0] = briefcase;
		agentsArray[1] = radar;
		agentsArray[2] = bullet;
		agentsArray[3] = invincibility;
		agentsArray[4] = player;
		agentsArray[5] = ninja[0];
		agentsArray[6] = ninja[1];
		agentsArray[7] = ninja[2];
		agentsArray[8] = ninja[3];
		agentsArray[9] = ninja[4];
		agentsArray[10] = ninja[5];

		grid.populate(agentsArray);
	}

	/**
	 * This method will update the {@link GameElements} data and status as they
	 * changed over the course of this game.
	 */
	public void updateGrid() {
		grid.repopulate(agentsArray);
	}

	/**
	 * This method will increase the level of difficulty of the game as called
	 * upon.
	 */
	public void hardMode() {
		gameDifficulty++;
	}

	/**
	 * This method will return the level of difficulty
	 * 
	 * @return a integer value which represent {@link #gameDifficulty}
	 */
	public int getDifficulty() {
		return gameDifficulty;
	}

	/**
	 * This method will be used to moved the {@link Player}in any direction,
	 * however the condition is set that the {@link Player} cannot move out of
	 * the boundary and can only move into the room from the north side. If the
	 * condition is violated, it will be an invalid move.
	 */

	private void playerMove(int n) {
		switch (n) {
		case 1:
			playerMoveUp();
			break;
		case 2:
			playerMoveDown();
			break;
		case 3:
			playerMoveLeft();
			break;
		case 4:
			playerMoveRight();
			break;
		}
	}

	/**
	 * This method will shift the {@link Player} current row coordinate up, and
	 * if the player try to move up into one of the room/out of boundary, the
	 * coordinate will go back down.
	 */
	private void playerMoveUp() {
		int row = player.getRow();
		int column = player.getColumn();
		row = row - 1;
		if ((row < 0) || checkMove(row, column)) {
			row = row + 1;
			invalidMove = true;
		}
		player.setCurrentCoordinate(row, column);
	}

	/**
	 * This method will shift the player current row coordinate down, and if the
	 * player try to move down out of boundary, the coordinate will go back to
	 * the up.
	 */
	private void playerMoveDown() {
		int row = player.getRow();
		int column = player.getColumn();
		if (!(checkMove(row, column))) {
			row = row + 1;
		} else {
			invalidMove = true;
		}
		if (row > 8) {
			row = row - 1;
			invalidMove = true;
		}
		player.setCurrentCoordinate(row, column);
	}

	/**
	 * This method will shift the player current column coordinate to the left,
	 * and if the player try to move left into one of the room/out of boundary,
	 * the coordinate will go back to the right.
	 */
	private void playerMoveLeft() {
		int row = player.getRow();
		int column = player.getColumn();
		if (!(checkMove(row, column))) {
			column = column - 1;
		} else {
			invalidMove = true;
		}
		if (((column < 0) || (checkMove(row, column))) && !(inRoom())) {
			column = column + 1;
			invalidMove = true;
		}

		player.setCurrentCoordinate(row, column);
	}

	/**
	 * This method will shift the player current column coordinate to the right,
	 * and if the player try to move right into one of the room/out of boundary,
	 * the coordinate will go back to the left.
	 */
	private void playerMoveRight() {
		int row = player.getRow();
		int column = player.getColumn();
		if (!(checkMove(row, column))) {
			column = column + 1;
		} else {
			invalidMove = true;
		}
		if (((column > 8) || (checkMove(row, column))) && !(inRoom())) {
			column = column - 1;
			invalidMove = true;
		}
		player.setCurrentCoordinate(row, column);
	}

	/**
	 * This method will confirm whether the move the {@link Player} did is
	 * invalid or not
	 * 
	 * @return {@code true} or {@code false}
	 */
	public boolean invalidMove() {
		return invalidMove;
	}

	/**
	 * If the {@link Player} move is valid, this method will set the boolean
	 * {@link #invalidMove} to {@code false};
	 */
	public void validMove() {
		invalidMove = false;
	}

	/**
	 * This method will check whether the {@link Player} or {@link Ninja}'s
	 * moving into the room or not
	 * 
	 * @param row
	 *            {@link Player}/{@link Ninja}'s row coordinate
	 * @param column
	 *            {@link Player}/{@link Ninja}'s column coordinate
	 * @return {@code true} or {@code false}
	 */
	private boolean checkMove(int row, int column) {
		if ((row == 1 || row == 4 || row == 7)
				&& (column == 1 || column == 4 || column == 7)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method check whether the {@link Player} is currently inside one of
	 * the room or not
	 * 
	 * @return {@code true} or {@code false}
	 */
	public boolean inRoom() {
		int row = player.getRow();
		int column = player.getColumn();
		if ((row == 1 || row == 4 || row == 7)
				&& (column == 1 || column == 4 || column == 7)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method represent the turn of a {@link Ninja}. The {@link Ninja} will
	 * start by checking the adjacent spots for {@link Player}, then the
	 * {@link Ninja} will move in a random direction.
	 * 
	 * @param n
	 */
	private void ninjaTurn(Ninja n) {
		if (!(n.isRemoved())) {
			if (isPlayerNear(n)) {
				player.loseLife();
				player.setCurrentCoordinate(8, 0);
			}
			ninjaMove(n);
		}
	}

	/**
	 * This method will be used to moved the {@link Ninja}in a random direction,
	 * however the condition is set that the {@link Ninja} cannot move out of
	 * the boundary and cannot move into the rooms. If the condition is
	 * violated, the {@link Ninja} will move in another direction
	 */
	private void ninjaMove(Ninja N) {
		int direction = random.nextInt(4) + 1;
		switch (direction) {
		case 1:
			ninjaMoveUp(N);
			break;
		case 2:
			ninjaMoveDown(N);
			break;
		case 3:
			ninjaMoveRight(N);
			break;
		case 4:
			ninjaMoveLeft(N);
			break;
		}
	}

	/**
	 * This method will shift the {@link Ninja} current row coordinate up, and
	 * if the player try to move into one of the room/out of boundary, the
	 * coordinate will go back down and change to another direction.
	 */
	private void ninjaMoveUp(Ninja N) {
		int row = N.getRow();
		int column = N.getColumn();
		row = row - 1;
		if ((row < 0) || (checkMove(row, column))) {
			row = row + 1;
			ninjaMove(N);
		} else {
			N.setCurrentCoordinate(row, column);
		}
	}

	/**
	 * This method will shift the {@link Ninja} current row coordinate down, and
	 * if the player try to move into one of the room/out of boundary, the
	 * coordinate will go back up and change to another direction.
	 */
	private void ninjaMoveDown(Ninja N) {
		int row = N.getRow();
		int column = N.getColumn();
		row = row + 1;
		if ((row > 8) || (checkMove(row, column))) {
			row = row - 1;
			ninjaMove(N);
		} else {
			N.setCurrentCoordinate(row, column);
		}
	}

	/**
	 * This method will shift the {@link Ninja} current column coordinate left,
	 * and if the player try to move into one of the room/out of boundary, the
	 * coordinate will go back right and change to another direction.
	 */
	private void ninjaMoveLeft(Ninja N) {
		int row = N.getRow();
		int column = N.getColumn();
		column = column - 1;
		if ((column < 0) || (checkMove(row, column))) {
			column = column + 1;
			ninjaMove(N);
		} else {
			N.setCurrentCoordinate(row, column);
		}
	}

	/**
	 * This method will shift the {@link Ninja} current column coordinate right,
	 * and if the player try to move into one of the room/out of boundary, the
	 * coordinate will go back left and change to another direction.
	 */
	private void ninjaMoveRight(Ninja N) {
		int row = N.getRow();
		int column = N.getColumn();
		column = column + 1;
		if ((column > 8) || (checkMove(row, column))) {
			column = column - 1;
			ninjaMove(N);
		} else {
			N.setCurrentCoordinate(row, column);
		}
	}

	/**
	 * This method will use to shoot "{@link Player}'s "gun", the {@link Player}
	 * can shoot in any direction depend on the "{@link Player}'s choice. If the
	 * {@link Player} manage to hit a {@link Ninja}, the {@link Bullet} will
	 * stop. However, the {@link Player} cannot shoot through wall/rooms. And if
	 * the {@link Player} is out of ammo, this action will result as an invalid
	 * move.
	 */
	private void playerShoot(int n) {
		if (!playerOutOfAmmo()) {
			switch (n) {
			case 1:
				playerShootUp();
				break;
			case 2:
				playerShootDown();
				break;
			case 3:
				playerShootLeft();
				break;
			case 4:
				playerShootRight();
				break;
			}
		} else {
			invalidMove = true;
		}
	}

	/**
	 * This method will check the {@link Player}'s "bullet"'s coordinates as it
	 * move up and compare them to the {@link Ninja}'s coordinates.
	 */
	private void playerShootUp() {
		player.shoot();
		int ninjaNumber = 0;
		int row = player.getRow();
		int column = player.getColumn();
		while (row >= 0) {
			if (checkMove(row, column)) {
				break;
			}
			for (int i = 0; i < ninja.length; i++) {
				if (row == ninja[i].getRow() && column == ninja[i].getColumn()) {
					ninjaIsDead = true;
					ninja[i].getRemoved();
					ninjaNumber = i;
				}
			}
			if (ninja[ninjaNumber].isRemoved()) {
				break;
			}
			row--;
		}
	}

	/**
	 * This method will check the {@link Player}'s "bullet"'s coordinates as it
	 * move down and compare them to the {@link Ninja}'s coordinates.
	 */
	private void playerShootDown() {
		player.shoot();
		int ninjaNumber = 0;
		int row = player.getRow();
		int column = player.getColumn();
		while (row <= 8) {
			if (checkMove(row, column)) {
				break;
			}
			for (int i = 0; i < ninja.length; i++) {
				if (row == ninja[i].getRow() && column == ninja[i].getColumn()) {
					ninjaIsDead = true;
					ninja[i].getRemoved();
					ninjaNumber = i;
				}
			}
			if (ninja[ninjaNumber].isRemoved()) {
				break;
			}
			row++;
		}
	}

	/**
	 * This method will check the {@link Player}'s "bullet"'s coordinates as it
	 * move left and compare them to the {@link Ninja}'s coordinates.
	 */
	private void playerShootLeft() {
		player.shoot();
		int ninjaNumber = 0;
		int row = player.getRow();
		int column = player.getColumn();
		while (column >= 0) {
			if (checkMove(row, column)) {
				break;
			}
			for (int i = 0; i < ninja.length; i++) {
				if (row == ninja[i].getRow() && column == ninja[i].getColumn()) {
					ninjaIsDead = true;
					ninja[i].getRemoved();
					ninjaNumber = i;
				}
			}
			if (ninja[ninjaNumber].isRemoved()) {
				break;
			}
			column--;
		}
	}

	/**
	 * This method will check the {@link Player}'s "bullet"'s coordinates as it
	 * move right and compare them to the {@link Ninja}'s coordinates.
	 */
	private void playerShootRight() {
		player.shoot();
		int ninjaNumber = 0;
		int row = player.getRow();
		int column = player.getColumn();
		while (column <= 8) {
			if (checkMove(row, column)) {
				break;
			}
			for (int i = 0; i < ninja.length; i++) {
				if (row == ninja[i].getRow() && column == ninja[i].getColumn()) {
					ninjaIsDead = true;
					ninja[i].getRemoved();
					ninjaNumber = i;
				}
			}
			if (ninja[ninjaNumber].isRemoved()) {
				break;
			}
			column++;
		}
	}

	/**
	 * This method will be used on the {@link Ninja} to check whether the
	 * {@link Player} is near.
	 * 
	 * @param n
	 * @return {@code true} or {@code false}
	 */
	private boolean isPlayerNear(Ninja n) {
		int row = n.getRow();
		int column = n.getColumn();
		int playerRow = player.getRow();
		int playerColumn = player.getColumn();

		if (((row + 1 == playerRow && column == playerColumn)
				|| (row - 1 == playerRow && column == playerColumn)
				|| (row == playerRow && column + 1 == playerColumn)
				|| (row == playerRow && column - 1 == playerColumn) 
				|| (row == playerRow && column == playerColumn))
				&& (invincibilityTurns == 0) && (!inRoom())) {

			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method will check whether the {@link Player} is out of ammo or not.
	 * 
	 * @return {@code true} or {@code false}
	 */
	private boolean playerOutOfAmmo() {
		if (player.getAmmo() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method represent the turn of an {@link Item}. The {@link Item} will
	 * start by checking if it's coordinate is the same as the player's
	 * coordinate, if the condition is true, then the {@link Item} will be
	 * "picked up" and used by the {@link Player}. The effect of {@link Radar},
	 * {@link Invincibility}, {@link Bullet} and {@link Briefcase} is different
	 * from one another.
	 * 
	 * @param i
	 */
	private void itemTurn(Item i) {
		if ((i.getRow() == player.getRow())
				&& (i.getColumn() == player.getColumn()) && !(i.isRemoved())) {
			i.getRemoved();
			if (i instanceof Radar) {
				grid.activateRadar();
			}
			if (i instanceof Bullet) {
				player.addAmmo();
			}
			if (i instanceof Briefcase) {
				winCondition = true;
			}
			if (i instanceof Invincibility) {
				invincibilityTurns = 5;
			}
		}
	}

	/**
	 * This method represent the {@link Ninja}'s taking their turns and the
	 * {@link Item} taking their turns
	 */
	public void takeTurns() {
		for (int i = 0; i < agentsArray.length; i++) {
			if (agentsArray[i] instanceof Ninja) {
				ninjaTurn((Ninja) agentsArray[i]);
			}
			if (agentsArray[i] instanceof Item) {
				itemTurn((Item) agentsArray[i]);
			}
		}
	}

	/**
	 * This method represent the {@link Player}'s turn. The {@link Player} can
	 * move or shoot.
	 * 
	 * @param choice
	 * @param direction
	 */
	public void takePlayerTurn(int choice, int direction) {
		if (invincibilityTurns > 0) {
			invincibilityTurns--;
		}
		switch (choice) {
		case 1:
			playerMove(direction);
			break;
		case 2:
			playerShoot(direction);
			break;
		}
	}

	/**
	 * This method represent the action "Peek" which will allow the
	 * {@link Player} to look further in any direction.
	 * 
	 * @param direction
	 */
	public void playerPeek(int direction) {
		switch (direction) {
		case 1:
			grid.changeUpVision();
			break;
		case 2:
			grid.changeDownVision();
			break;
		case 3:
			grid.changeLeftVision();
			break;
		case 4:
			grid.changeRightVision();
			break;
		}
	}

	/**
	 * This method simply call the resetVision() method in {@link Grid} and will
	 * reset the {@link Player}'s vision after the peek action is finished.
	 */
	public void resetPeek() {
		grid.resetVision();
	}

	/**
	 * This method will check if the {@link Briefcase} has been picked up or
	 * not.
	 * 
	 * @return {@code true} or {@code false}
	 */
	public boolean briefcaseObtained() {
		if (winCondition == true) {
			return winCondition;
		} else {
			return false;
		}
	}

	/**
	 * This method will check if the {@link Player} has lost all lives or not.
	 * 
	 * @return {@code true} or {@code false}
	 */
	public boolean playerLostAllLives() {
		if (player.getLives() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method will return a string that display all of the information of
	 * the {@link Player}
	 * 
	 * @return {@code display}
	 */
	public String displayPlayerInfo() {
		String display;
		display = player.toString();
		display += "\n";
		if (invincibilityTurns > 0) {
			display += "Invincibility: ON\n";
		} else {
			display += "Invincibility: OFF\n";
		}
		return display;
	}

	/**
	 * This method will return a string that display the map.
	 * 
	 * @return {@code display}
	 */
	public String displayGame(int choice) {
		String display = "	   -MAP-\n";
		if (!radar.isRemoved()) {
			grid.deactivateRadar();
		}
		if (radar.isRemoved()) {
			display += "Radar activated!\n";
		}
		switch (choice) {
		case 1:
			display += grid.printGrid();
			break;

		case 2:
			display += grid.printGridDebug();
			break;
		}
		if (ninjaIsDead == true) {
			display += "\nYou hit a ninja!\n";
			ninjaIsDead = false;
		}
		return display;
	}
}

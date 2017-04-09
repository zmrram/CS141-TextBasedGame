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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represent a medium that act as a communicator between the user and
 * the {@link GameEngine}. All the user's input and game's output will happen in
 * this class.
 * 
 * @author The Cappucino
 *
 */
public class UserInterface {

	/**
	 * This field represent an object type {@link GameEngine} which will be used
	 * to interact with the {@link GameEngine}
	 */
	private GameEngine GE;

	/**
	 * This field represent an object type {@link SaveSystem} which will be used
	 * to save and load the game.
	 */
	private SaveSystem SS = new SaveSystem();

	/**
	 * This field represent a {@link Scanner} object. And is declared with the
	 * argument System.in, which will be used to take input from the keyboard.
	 */
	private Scanner sc = new Scanner(System.in);

	/**
	 * This field represent the direction which the user will choose for a
	 * certain actions.
	 */
	private int direction = 0;

	/**
	 * This field represent the option which the user will choose whether to
	 * play the game on normal mode or debug mode.
	 */
	private int mapChoice = 1;

	/**
	 * This field represent the in-game option which the user will choose which
	 * action to take.
	 */
	private int playerChoice = 0;

	/**
	 * This field represent the options which the user will choose whether
	 * he/she want to peek or not.
	 */
	private int peekChoice = 0;

	/**
	 * This field represent the options which the user will choose which
	 * difficulty the he/she want to play.
	 */
	private int difficulty = 0;

	/**
	 * This field represent the options which the user will choose whether
	 * he/she want to save or not.
	 */
	private int saveChoice = 0;

	/**
	 * This method will display the welcome message and give user options to
	 * either start a New Game, Load Game, read How To Play or Exit game.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void displayMenu() throws IOException, ClassNotFoundException {
		System.out.println("\nWelcome!\n");
		try {
			System.out
					.println("1.New Game\n2.Load Game\n3.How To Play\n4.Exit");
			menuSwitch();
		} catch (InputMismatchException e) {
			System.out.println("\nInvalid input, try again!\n");
			sc.nextLine();
			sc.nextLine();
			displayMenu();
		}
	}

	/**
	 * This method ask the user to choose the difficulty of the game. There are
	 * only 2 options so if the users input otherwise, it will show error
	 * message and ask for another input.
	 */
	private void chooseDifficulty() {
		try {
			System.out.println("\nSelect difficulty");
			System.out.println("1.ez \n2.Hardcore");
			difficulty = sc.nextInt();
			if (difficulty == 2) {
				GE.hardMode();
			}
		} catch (InputMismatchException e) {
			System.out.println("\nInvalid input, try again!");
			sc.nextLine();
			sc.nextLine();
			chooseDifficulty();
		}
		if (difficulty != 1 && difficulty != 2) {
			System.out.println("\nInvalid option, try again!");
			sc.nextLine();
			sc.nextLine();
			chooseDifficulty();
		}

	}

	/**
	 * This method ask the user to choose the if the user want to player in
	 * normal mode or debug mode. There are only 2 options so if the users input
	 * otherwise, it will show error message and ask for another input.
	 */
	private void chooseMode() {
		try {
			System.out.println("\nChange mode?\n1.Normal Mode\n2.Debug Mode");
			mapChoice = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("\nInvalid input, try again!");
			sc.nextLine();
			sc.nextLine();
			chooseMode();
		}

		if (mapChoice != 1 && mapChoice != 2) {
			System.out.println("\nInvalid option, try again!");
			sc.nextLine();
			sc.nextLine();
			chooseMode();
		}
	}

	/**
	 * This method represent the whole game process. This method utilize and put
	 * together all the methods that make up the game. Which explain why this
	 * method is a little bigger than others.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void playGame() throws IOException, ClassNotFoundException {
		mapChoice = 1;
		displayDifficulty();
		while (!GE.briefcaseObtained() && !GE.playerLostAllLives()) {
			System.out.println(GE.displayPlayerInfo());
			System.out.println(GE.displayGame(mapChoice));
			playerPeek();
			playerTurn();
			GE.updateGrid();
			System.out.println("\n\n" + GE.displayGame(mapChoice));
			System.out.println("You have taken your turn.");
			System.out.println("Ninjas are taking their turns.");
			sc.nextLine();
			GE.takeTurns();
			GE.updateGrid();
			System.out.println("\n" + GE.displayGame(mapChoice));
			if (GE.getDifficulty() == 2) {
				System.out
						.println("Ninjas took their first turns!\nThey are taking their second turns.");
				sc.nextLine();
				GE.takeTurns();
				GE.updateGrid();
			}
		}
		if (GE.briefcaseObtained()) {
			winMessage();
		}
		if (GE.playerLostAllLives()) {
			loseMessage();
		}
	}

	/**
	 * This method will display when the {@link Player} has completed the task
	 * of the game. And then take the user back to the main menu.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void winMessage() throws IOException, ClassNotFoundException {
		System.out.println("Briefcase obtained!");
		System.out.println("You win!\n");
		System.out.println("Returning to Main Menu");
		sc.nextLine();
		displayMenu();
	}

	/**
	 * This method will display when the {@link Player} fail. And then take the
	 * user back to the main menu.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void loseMessage() throws IOException, ClassNotFoundException {
		System.out.println("Lost all lives");
		System.out.println("Game Over\n");
		System.out.println("Returning to Main Menu");
		sc.nextLine();
		displayMenu();
	}

	/**
	 * This method will show the user the general information about this game.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void howToPlay() throws IOException, ClassNotFoundException {
		System.out.println("\nP = Player\nN = Ninja\nB = Briefcase\nR = Room");
		System.out.println("b = bullet\nr = radar\ni = invincibility\n");
		System.out
				.println("-You are a spy that is tasked with retrieving a briefcase containing ");
		System.out
				.println("classified enemy documents, which is located in one of the rooms");
		System.out
				.println("-You have 3 lives, the game is over once you lost all 3 lives.");
		System.out.println("-You are only given 1 bullet, use it wisely");
		System.out.println("-Good luck!\n");
		System.out.println("Credit: The Cappucino\n");
		System.out.println("Press ENTER");
		sc.nextLine();
		displayMenu();
	}

	/**
	 * This method is use in the {@link #displayMenu()}. There are only 4
	 * options so if the users input otherwise, it will show error message and
	 * ask for another input.
	 */
	private void menuSwitch() throws IOException, ClassNotFoundException {
		int choice = sc.nextInt();
		sc.nextLine();
		switch (choice) {
		case 1:
			GE = new GameEngine();
			chooseDifficulty();
			sc.hasNextLine();
			playGame();
			break;
		case 2:
			loadGame();
			playGame();
			break;
		case 3:
			howToPlay();
			break;
		case 4:
			System.exit(0);
			break;
		default:
			System.out.println("\nInvalid option, try again!");
			sc.nextLine();
			displayMenu();
		}

	}

	/**
	 * This method ask the user to choose the which direction to take action.
	 * There are only 4 direction so if the users input otherwise, it will show
	 * error message and ask for another input.
	 */
	private void directionSwitch() {
		try {
			System.out.println("\nWhich direction?");
			System.out.println("1.Up\n2.Down\n3.Left\n4.Right");
			direction = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("\nInvalid input, try again!");
			sc.nextLine();
			sc.nextLine();
			directionSwitch();
		}
		if (direction < 0 || direction > 4) {
			System.out.println("\nInvalid option, try again!");
			sc.nextLine();
			directionSwitch();
		}
	}

	/**
	 * This method ask the user to choose which action to take. The user can
	 * shoot, move, save, and return to menu. There are only 4 direction so if
	 * the users input otherwise, it will show error message and ask for another
	 * input.
	 */
	private void getPlayerChoice() {
		try {
			System.out
					.println("\nWhat would you like to do? \n1.Move\n2.Shoot\n3.Mode\n4.Return to menu");
			playerChoice = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("\nInvalid input, try again!");
			sc.nextLine();
			sc.nextLine();
			getPlayerChoice();
		}
	}

	/**
	 * 
	 * This method ask the user to choose whether he/she want to peek or not.
	 * There are only 2 option so if the users input otherwise, it will show
	 * error message and ask for another input.
	 */
	private void getPeekChoice() {
		try {
			System.out.println("Would you like to peek? \n1.Yes\n2.No");
			peekChoice = sc.nextInt();
			sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("\nInvalid input, try again!");
			sc.nextLine();
			sc.nextLine();
			getPeekChoice();
		}
		if (peekChoice < 0 || peekChoice > 2) {
			System.out.println("\nInvalid option, try again!");
			sc.nextLine();
			getPeekChoice();
		}
	}

	/**
	 * This method represent the action peek, which will allow the user to look
	 * further in any direction. This option will be disable in Hard mode.
	 */
	private void playerPeek() {
		if (GE.getDifficulty() != 2) {
			if (!GE.inRoom()) {
				getPeekChoice();
				if (peekChoice == 1) {
					directionSwitch();
					GE.playerPeek(direction);
					System.out.println("\n\n" + GE.displayGame(mapChoice));
					System.out.println("You have scanned the nearby area.");
					peekChoice = 0;
					GE.resetPeek();
				}
			}
		}
	}

	/**
	 * This method represent the {@link Player}'s turn in the game.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void playerTurn() throws IOException, ClassNotFoundException {
		getPlayerChoice();
		switch (playerChoice) {
		case 1:
		case 2:
			playerAction(playerChoice);
			break;
		case 3:
			chooseMode();
			System.out.println("\nMode changed!\n");
			System.out.println(GE.displayGame(mapChoice));
			playerTurn();
			break;
		case 4:
			saveGame();
			break;
		default:
			System.out.println("Invalid option, try again!");
			sc.nextLine();
			playerTurn();
			break;
		}
	}

	/**
	 * This method represent the two action that the {@link Player} can take.
	 * The {@link Player} and move, or shoot in any direction depend on the
	 * user's choice.
	 * 
	 * @param choice
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void playerAction(int choice) throws IOException,
			ClassNotFoundException {
		directionSwitch();
		GE.takePlayerTurn(choice, direction);
		if (GE.invalidMove() == true) {
			System.out.println("Invalid move, try again!");
			sc.nextLine();
			GE.validMove();
			playerTurn();
		}

	}

	/**
	 * Upon quitting the game, this method ask the user to choose whether of not
	 * to save the game. There are only 2 options so if the user's input is
	 * otherwise, it will show error message and ask for another input.
	 */

	private void saveOption() {
		try {
			System.out.println("\nSave game?\n1.Yes\n2.No");
			saveChoice = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("\nInvalid input, try again!");
			sc.nextLine();
			sc.nextLine();
			saveOption();
		}

		if (saveChoice != 1 && saveChoice != 2) {
			System.out.println("\nInvalid option, try again!");
			sc.nextLine();
			sc.nextLine();
			chooseMode();
		}
	}

	/**
	 * This method will call the {@link #SS} of {@link SaveSystem} and save the
	 * current data/statistic of the {@link GameEngine}. If the user choose not
	 * to save, they will go back to the menu without saving.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void saveGame() throws IOException, ClassNotFoundException {
		saveOption();
		switch (saveChoice) {
		case 1:
			System.out.println("\nSaving...");
			SS.saveGame(GE);
			sc.nextLine();
			sc.nextLine();
			displayMenu();
			break;
		case 2:
			displayMenu();
			break;
		}
	}

	/**
	 * This method will call the {@link #SS} of {@link SaveSystem} and load the
	 * current saved {@link GameEngine} in the file {@code save.dat}. If the
	 * game has never been saved previously, there will be an error message and
	 * user will be take back to the main menu.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void loadGame() throws ClassNotFoundException, IOException {
		System.out.println("Loading...");
		sc.nextLine();
		try {
			GE = SS.loadGame(GE);
		} catch (FileNotFoundException e) {
			System.out.println("No saved data!\n");
			System.out.println("Returning to Main Menu");
			sc.nextLine();
			displayMenu();
		}
	}

	/**
	 * This method will show the user which difficulty he/she play on. The
	 * reason why This method is necessary is that when the user load the game,
	 * it won't ask for difficulty again and go directly to the previous saved
	 * difficulty which will make some confusion.
	 */
	private void displayDifficulty() {
		if (GE.getDifficulty() == 1) {
			System.out.println("\nDifficulty set to ez");
		} else {
			System.out.println("\nDifficulty set to Hardcore");
		}
		sc.nextLine();
	}
}

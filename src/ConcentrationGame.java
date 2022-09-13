/**
 * Author: 	Ryan Rizzo
 * File: 	ConcentrationGame.java
 * Class: 	CSC335 Object-Oriented Programming
 * Project: Assignment 1
 * Date: 	9/13/22
 **/

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public interface ConcentrationGame {
	// This is the main function of our program. It will be the first thing to run when
	// we launch
	public static void main(String args[]) throws IOException, InterruptedException {
		// Create and select a set of images for use as pictures on each card. This is 
		// accomplished mainly through use of the ConcentrationGameImageSet class. More 
		// information can be found there
		Display display = new Display();
		settingsQuery(display);
	}
	
	// In this function, we are creating a new shell which is responsible for asking
	// the user what image set they would like to use, and how many players will
	// be participating. This function runs until this shell has been submitted.
	// @param display, the main display used for interface
	private static void settingsQuery(Display display) throws InterruptedException {
		// Create a new shell for the options
		Shell optionsShell = new Shell(display);
		optionsShell.setData(false);
		optionsShell.setSize(200, 250);
		optionsShell.setLayout(new GridLayout());

		// Add a submit button to the shell
		Button submitButton = new Button(optionsShell, SWT.PUSH);
		submitButton.setSize(100, 50);
		submitButton.setText("Submit");
		SubmitButtonSelectionListener submitSelector = new SubmitButtonSelectionListener(display, optionsShell);
		submitButton.addSelectionListener(submitSelector);
		
		// Create a String array containing all options for the image set
		String[] imageSetOptions = new String[6];
		imageSetOptions[0] = "Instructors";
		imageSetOptions[1] = "Foods";
		imageSetOptions[2] = "Fish";
		imageSetOptions[3] = "Animals";
		imageSetOptions[4] = "King of The Hill";
		imageSetOptions[5] = "Regular Show";
		
		// Create a String array containing all options for number of players
		String[] playerOptions = new String[5];
		playerOptions[0] = "2";
		playerOptions[1] = "3";
		playerOptions[2] = "4";
		playerOptions[3] = "5";
		playerOptions[4] = "6";
		
		// Create a String array containing all options for game modes
		String[] gameModes = new String[3];
		gameModes[0] = "Default";
		gameModes[1] = "One Flip";
		gameModes[2] = "Quick Switch";
		
		// Add a label to the shell which tells user to select image
		Label playerNumInput = new Label(optionsShell, SWT.BORDER);
		playerNumInput.setText("Select an Image Set:");
		
		// Add a combo to shell to give set options
		Combo imageSetCombo = new Combo(optionsShell, SWT.DROP_DOWN);
		imageSetCombo.setItems(imageSetOptions);
		
		// Add a label to the shell which tells user to select game mode
		Label gameModeLabel = new Label(optionsShell, SWT.BORDER);
		gameModeLabel.setText("Select Game Mode: ");
		
		// Add a combo to shell to give game mode options
		Combo gameModeCombo = new Combo(optionsShell, SWT.DROP_DOWN);
		gameModeCombo.setItems(gameModes);
		
		// Add a label to the shell which tell user to select player number
		Label setSelectionInput = new Label(optionsShell, SWT.BORDER);
		setSelectionInput.setText("Select Number of Players:");
		
		// Add a combo to shell to give player number options
		Combo playerOptionCombo = new Combo(optionsShell, SWT.DROP_DOWN);
		playerOptionCombo.setItems(playerOptions);
		optionsShell.open();
		
		// Wait for shell to be disposed and collect information
		while( !optionsShell.isDisposed()) {
			if(!display.readAndDispatch()) {
				Combo[] combos = new Combo[3];
				combos[0] = imageSetCombo;
				combos[1] = playerOptionCombo;
				combos[2] = gameModeCombo;
				submitSelector.addSettings(combos);
				display.sleep();
			}
		}
		optionsShell.dispose();
	}
	
	// This function waits to be called by the submitButtonListener. This function is responsible
	// for collecting the information from the optionsShell, and creating use-able variables for
	// our program
	// @param display, the main display for the game board
	// @param optionsShell, the shell which houses the options
	// @param settings, the array which we will append the values
	public static void collectSettings(Display display, Shell optionsShell, String[] settings) throws IOException {
		if (settings[0].equals("")) {
			System.out.println("Image set left empty");
			display.close();
			System.exit(0);
		}
		if (settings[1].equals("")) {
			System.out.println("Game mode left empty");
			display.close();
			System.exit(0);
		}
		if (settings[2].equals("")) {
			System.out.println("Player number left empty");
			display.close();
			System.exit(0);
		}
		
		// Get information from shell
		String imageSet = settings[0];
		String playerNum = settings[1];
		String gameMode = settings[2];
		
		
		/* ### CONSOLE OUTPUT ### */
		System.out.println("The image set selected was: " + imageSet);
		System.out.println("The number of players selected is: " + playerNum);
		System.out.println("The Game Mode selected was: " + gameMode + "\n");
		/* ###################### */
		
		// Move onto next function
		arrangeStringImages(imageSet, playerNum, gameMode, display);
	}
	
	// This function is responsible for taking in the settings, and creating the board itself, using
	// the images from the set provided
	// @param setName, String of name of set user chose
	// @param playerNum, String number of players which user chose
	// @param display, the main display
	private static void arrangeStringImages(String setName, String playerNum, String gameMode, Display display) throws IOException{
		// Create a new ConcentrationGameImageSet object
		ConcentrationGameImageSet setSelection = new ConcentrationGameImageSet(setName);
		// Use the getImageSet method to receive an array of the images
		String[] imageSet = setSelection.getImageSet();
		
		/* Using this set of images, randomly assign them to cells in the 2D array 
		 * 'images', while also maintaining that there exists exactly two of each image
		 * in the array. Each cell in this array corresponds to a cell in the game board,
		 * this array will later be used to construct the board.*/
		
		// Use a HashMap to determine how many times an image appears in the array
		Random rand = new Random();
		Map<String, Integer> map = new HashMap<String, Integer>();
		String[][] images = new String[4][4];
		// Initialize each image in the HashMap with the value 0
		for (int i=0; i<8; i++) {
			if (!map.containsKey(imageSet[i])) {
				map.put(imageSet[i], 0);
			}
		}
		// Assign images to cells
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				int index = rand.nextInt(0,8);
				// Make sure an image is not inserted more than twice
				while (map.get(imageSet[index]) >= 2){
					index = rand.nextInt(0, 8);
					
				}
				// Update the HashMap and the set the image
				map.put(imageSet[index], map.get(imageSet[index]) + 1);
				images[i][j] = imageSet[index];
			}
		}
		// Call the main loop for the program
		loop(images, playerNum, gameMode, display);
	}
	
	// This is the main loop of the program, this function will continue to run 
	// until the game has finished or the user has quit
	// @param stringImages, a 2D array of the images being used on the board
	// @param numPlayers, a string of number of players in game
	// @param display, the main display
	private static void loop(String[][] stringImages, String numPlayers, String gameMode, Display display) throws IOException {
		// Convert numPlayers to int
		int numberOfPlayers = Integer.parseInt(numPlayers);
		
		// Create the board object
		ConcentrationGameBoard board = new ConcentrationGameBoard(numberOfPlayers, stringImages, gameMode);
		
		// Create the new main shell
		Shell shell = new Shell(display);
		shell.setSize(550, 615 + (12 * numberOfPlayers));
		shell.setLayout( new GridLayout());

		// Create a main composite
	    Composite upperComp = new Composite(shell, SWT.NO_FOCUS);
	    
	    // Add a canvas to the composite
		Canvas canvas = new Canvas(upperComp, SWT.NONE);
		canvas.setSize(600, 615 + (12 * numberOfPlayers));			
		
		// Add our paint listener
		canvas.addPaintListener(new CanvasPaintListener(board, display));	
		// Add our mouse listener
		canvas.addMouseListener(new CanvasMouseListener(shell, display, board));
		
		// Add a quit button to the composite
		Button quitButton = new Button(canvas, SWT.PUSH);
		quitButton.setText("Quit");
		quitButton.setSize(100, 50);
		quitButton.setBounds(15, 525 ,100, 50);
		quitButton.addSelectionListener(new ButtonSelectionListener());
		
		// Open the shell
		shell.open();
		
		// Loop
		while( !shell.isDisposed())
		{
			if(!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();	
	}
	
	// This method is responsible for running when the user has completed the game
	// @param board, the main game board
	// @param display, the main game display
	// @param shell, the main game shell
	public static void gameEnd(ConcentrationGameBoard board, Display display, Shell shell) {
		// Close the main shell
		shell.close();
		
		// Create String arrays to house all players and all winners
		ConcentrationGamePlayer[] players = board.getPlayers();
		ConcentrationGamePlayer[] winners = new ConcentrationGamePlayer[6];
		
		// Determine who wins/ties
		int mostSets = 0;
		for (int i=0; i<players.length; i++) {
			if (players[i].getSets() > mostSets) {
				for (int j=0; j<players.length; j++) {
					winners[j] = null;
				}
				mostSets = players[i].getSets();
				winners[i] = players[i];
			}
			if (players[i].getSets() == mostSets) {
				winners[i] = players[i];
			}
		}
		
		// Create string output for winners message
		String winnersString = "";
		int counter = 0;
		for (int i=0; i<winners.length; i++) {
			if (winners[i] != null) {
				counter++;
				System.out.println("Player " + winners[i].getId() + " is a winner\n");
				winnersString = winnersString + "Player " + i + ", and ";
			}
		}
		winnersString = winnersString.substring(0, winnersString.length() - 6);
		
		// If there are multiple winners
		if (counter > 1) {
			winnersString = winnersString + " are winners!";
		// If there is only one winner
		} else {
			winnersString = winnersString + " is the winner!";
		}
		
		// Create end screen sheel
		Shell endScreen = new Shell(display);
		endScreen.setSize(250, 250);
		endScreen.setLayout(new GridLayout());
		
		// Create a canvas for the end screen
		Canvas endCanvas = new Canvas(endScreen, SWT.NONE);
		endCanvas.setSize(250, 250);
		
		// Add the winners string to a label
		Label winner = new Label(endScreen, SWT.NONE);
		winner.setText(winnersString);
		
		// Add the winners spec to a label
		Label winnerSpecs = new Label(endScreen, SWT.NONE);
		String specs = "This player(s) collected a total of " + mostSets + " sets";
		winnerSpecs.setText(specs);
		
		// Add label to ask user if they want to play again
		Label question = new Label(endScreen, SWT.NONE);
		question.setText("Would you like to play again?");
		
		// Add yes button
		Button yes = new Button(endScreen, SWT.PUSH);
		yes.setText("yes");
		yes.addSelectionListener(new NewGameButtonListener(endScreen));
		
		// Add no button
		Button no = new Button(endScreen, SWT.PUSH);
		no.setText("no");
		no.addSelectionListener(new ButtonSelectionListener());
		
		endScreen.open();
		
		// Loop
		while( !endScreen.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
		
	}
}
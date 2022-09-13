/**
 * Author: 	Ryan Rizzo
 * File: 	ConcentrationGameUI.java
 * Class: 	CSC335 Object-Oriented Programming
 * Project: Assignment 1
 * Date: 	9/13/22
 **/
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;
import java.io.IOException;

/**
 * This class is responsible for responding when an event occurs on our
 * canvas. An example of an event which will occur is mouseDown.
 * 
 * CanvasMouseListener(Shell, Display, ConcentrationGameBoard)
 * 		This is the constructor for the ConvasMouseListener 
 * 		class. This constructor just sets the values of the
 * 		class's private variables.
 * 
 * mouseDoubleClick(event)
 * 		This method is added to maintain the structure of the class MouseListener
 * 
 * mouseDown(event)
 * 		This method is responsible for acting whenever a cell is selected
 * 		on the board. Here we must determine which cell of our internal 
 * 		board was selected based off the coordinated given from the display.
 * 		Additionally, we determine if a card should be flipped, and update
 * 		the game accordingly.
 * 
 * mouseDownDefault(event)
 * 		This method handles click events when the game mode is default
 * 
 * mouseDownOneFlip(event)
 * 		This method handles click events when the game mode is one flip
 * 
 * mouseDownQuickSwitch(event)
 * 		This method handles click events when the game mode is quick switch
 * 
 * mouseUp(event)
 * 		This method is added to maintain the structure of the class MouseListener
 */
class CanvasMouseListener implements MouseListener {
    private Shell shell;					// Game board main shell
    private Display display;				// Game board main display
    private ConcentrationGameBoard board;	// Game board
    
    // Constructor
    public CanvasMouseListener(Shell sh, Display dis, ConcentrationGameBoard brd) {
    	shell = sh; display = dis; board = brd;
    } 
    
    // Part of the MouseListener class
	public void mouseDoubleClick(MouseEvent event){}
	
	// Responds to cells being clicked on the board
	// @param event, the mouse down event
	public void mouseDown(MouseEvent event) {	
		// Initialize values of x & y to -1 to detect errors occurring
		int x = -1;
		int y = -1;
		// If user clicked on most left square
		if (event.x >= 25 && event.x <= 125) {
			x = 0;
		// If user clicked on second most left square
		} else if (event.x >= 150 && event.x <= 250) {
			x = 1;
		// If user clicked on second most right square
		} else if (event.x >= 275 && event.x <= 375 ) {
			x = 2;
		// If user clicked on most right square
		} else if (event.x >= 400 && event.x <= 500) {
			x = 3;
		// If user clicked in a non-responsive section
		} else {
			return;
		}
		// If user clicked on most top square
		if (event.y >= 25 && event.y <= 125) {
			y = 0;
		// If user clicked on second most top square
		} else if (event.y >= 150 && event.y <= 250) {
			y = 1;
		// If user clicked on second most bottom square
		} else if (event.y >= 275 && event.y <= 375 ) {
			y = 2;
		// If user clicked on most bottom square
		} else if (event.y >= 400 && event.y <= 500) {
			y = 3;
		// If user clicked in a non-responsive section
		} else {
			return;
		}
		
		
		System.out.println("The game mode selected is " + board.getGameMode());
		// The user selected default
		if (board.getGameMode().equals("Default")) {
			mouseDownDefault(event, x, y);
		}
		// The user selected one flip
		if (board.getGameMode().equals("One Flip")) {
			mouseDownOneFlip(event, x, y);
		}
		// The user selected quick switch
		if (board.getGameMode().equals("Quick Switch")) {
			System.out.println("test");
			mouseDownQuickSwitch(event, x, y);
		}
	}
	
	// This method is responsible for handling mouse clicks, when
	// the game mode is set to default
	// @param event, the MouseEvent
	// @param x, int of users x coordinate click location
	// @param y, int of users y coordinate click location
	public void mouseDownDefault(MouseEvent event, int x, int y) {
		// Get the id of player who's turn it currently is
		int currPlayerId = board.getCurrPlayer();
		ConcentrationGamePlayer currPlayer = board.getPlayers()[currPlayerId];
		
		/* ### CONSOLE OUTPUT ### */
		System.out.println("Player " + currPlayer.getId() + " chose cell [" + x + "][" + y + "]");
		String msg = "";
		if (board.getCardsFlipped()[0] != null) {
			msg = "'s Second selection";
		} else {
			msg = "'s First selection";
		}
		System.out.println("This was Player " + currPlayer.getId() + msg);
		/* ###################### */
		
		// Audio output
		display.beep();
		
		// Reference the card which needs to be flipped
		ConcentrationGameCard cardToFlip = board.getCard(x, y);
		// Determine that the card being selected, is not one which is already selected
		
		if (board.getNumberOfCardsFlipped() == 2) {
			ConcentrationGameCard[] cardsFlipped = board.getCardsFlipped();
			if (cardsFlipped[0].compare(cardsFlipped[1])) {
				// Update the board
				board.unflipCard(cardsFlipped[0]);
				board.unflipCard(cardsFlipped[1]);
						
				board.removeCard(cardsFlipped[0]);
				board.removeCard(cardsFlipped[1]);
				currPlayer.addSet();
				currPlayer.addCard(cardsFlipped[0]);
			} else {
				// Update the board
				board.unflipCard(cardsFlipped[0]);
				board.unflipCard(cardsFlipped[1]);
			}
		}
		else if (board.getNumberOfCardsFlipped() == 0 || board.getCardsFlipped()[0].getId() != cardToFlip.getId()) {
			// Determine that the card being selected is not removed
			if (cardToFlip.getState() != "Removed") {
				// Flip the card
				board.flipCard(cardToFlip);
				
				/* ### CONSOLE OUTPUT */
				System.out.println("The image which was selected was " + cardToFlip.getImageMain());
				/* ################## */
				
				// If this is the second card this player has flipped this turn
				if (board.getNumberOfCardsFlipped() == 2) {			
					ConcentrationGameCard[] cardsFlipped = board.getCardsFlipped();
					// Determine if they are identical
					if (cardsFlipped[0].compare(cardsFlipped[1])) {
						
						/* ### CONSOLE OUTPUT */
						System.out.println("These two images are identical, player " + currPlayer.getId() + " gains a set");
						/* ################## */
					} else {
						
						/* ### CONSOLE OUTPUT */
						System.out.println("These two images are not identical, keep trying");
						/* ################## */
						
						// Update the board
						board.updateCurrPlayer();
						currPlayerId = board.getCurrPlayer();
						currPlayer = board.getPlayers()[currPlayerId];
					}
				}
			}
		}
		
		// If the game is over
		if (board.getCards() == 0) {
			System.out.println("Game over");
			ConcentrationGame.gameEnd(board, display, shell);
		}
		
		/* ### CONSOLE OUTPUT */
		board.updateInfo();
		String info = board.getInfo();
		System.out.println(info);
		System.out.println("\n");
		/* ################## */	
		
		if (!shell.isDisposed()) {
			shell.redraw();
			shell.update();	
		}
	}
	
	// This method is responsible for handling mouse clicks, when
	// the game mode is set to one flip
	// @param event, the MouseEvent
	// @param x, int of users x coordinate click location
	// @param y, int of users y coordinate click location
	public void mouseDownOneFlip(MouseEvent event, int x, int y) {
		// Get the id of player who's turn it currently is
		int currPlayerId = board.getCurrPlayer();
		ConcentrationGamePlayer currPlayer = board.getPlayers()[currPlayerId];
		
		/* ### CONSOLE OUTPUT ### */
		System.out.println("Player " + currPlayer.getId() + " chose cell [" + x + "][" + y + "]");
		/* ###################### */
		
		// Audio output
		display.beep();
		
		// Reference the card which needs to be flipped
		ConcentrationGameCard cardToFlip = board.getCard(x, y);
		// Determine that the card being selected, is not one which is already selected
		
		if (board.getNumberOfCardsFlipped() == 2) {
			ConcentrationGameCard[] cardsFlipped = board.getCardsFlipped();
			if (cardsFlipped[0].compare(cardsFlipped[1])) {
				// Update the board
				board.unflipCard(cardsFlipped[0]);
				board.unflipCard(cardsFlipped[1]);
						
				board.removeCard(cardsFlipped[0]);
				board.removeCard(cardsFlipped[1]);
				currPlayer.addSet();
				currPlayer.addCard(cardsFlipped[0]);
			} else {
				// Update the board
				board.unflipCard(cardsFlipped[0]);
				board.unflipCard(cardsFlipped[1]);
				
				// Update the board
				board.updateCurrPlayer();
				currPlayerId = board.getCurrPlayer();
				currPlayer = board.getPlayers()[currPlayerId];
			}
		}
		else if (board.getNumberOfCardsFlipped() == 0 || board.getCardsFlipped()[0].getId() != cardToFlip.getId()) {
			// Determine that the card being selected is not removed
			if (cardToFlip.getState() != "Removed") {
				// Flip the card
				board.flipCard(cardToFlip);
				
				/* ### CONSOLE OUTPUT */
				System.out.println("The image which was selected was " + cardToFlip.getImageMain());
				/* ################## */
				
				// If this is the second card this player has flipped this turn
				if (board.getNumberOfCardsFlipped() == 2) {			
					ConcentrationGameCard[] cardsFlipped = board.getCardsFlipped();
					// Determine if they are identical
					if (cardsFlipped[0].compare(cardsFlipped[1])) {
						
						/* ### CONSOLE OUTPUT */
						System.out.println("These two images are identical, player " + currPlayer.getId() + " gains a set");
						/* ################## */
					} else {
						
						/* ### CONSOLE OUTPUT */
						System.out.println("These two images are not identical, keep trying");
						/* ################## */
						
					}
				}
			}
			if (board.getNumberOfCardsFlipped() != 2) {
				// Update the board
				board.updateCurrPlayer();
				currPlayerId = board.getCurrPlayer();
				currPlayer = board.getPlayers()[currPlayerId];
			}
		}
		
		// If the game is over
		if (board.getCards() == 0) {
			System.out.println("Game over");
			ConcentrationGame.gameEnd(board, display, shell);
		}
		
		/* ### CONSOLE OUTPUT */
		board.updateInfo();
		String info = board.getInfo();
		System.out.println(info);
		System.out.println("\n");
		/* ################## */	
		
		if (!shell.isDisposed()) {
			shell.redraw();
			shell.update();	
		}
	}
	
	// This method is responsible for handling mouse clicks, when
	// the game mode is set to quick switch
	// @param event, the MouseEvent
	// @param x, int of users x coordinate click location
	// @param y, int of users y coordinate click location
	public void mouseDownQuickSwitch(MouseEvent event, int x, int y) {
		// Get the id of player who's turn it currently is
		int currPlayerId = board.getCurrPlayer();
		ConcentrationGamePlayer currPlayer = board.getPlayers()[currPlayerId];
		
		/* ### CONSOLE OUTPUT ### */
		System.out.println("Player " + currPlayer.getId() + " chose cell [" + x + "][" + y + "]");
		String msg = "";
		if (board.getCardsFlipped()[0] != null) {
			msg = "'s Second selection";
		} else {
			msg = "'s First selection";
		}
		System.out.println("This was Player " + currPlayer.getId() + msg);
		/* ###################### */
		
		// Audio output
		display.beep();
		
		// Reference the card which needs to be flipped
		ConcentrationGameCard cardToFlip = board.getCard(x, y);
		// Determine that the card being selected, is not one which is already selected
		
		if (board.getNumberOfCardsFlipped() == 2) {
			ConcentrationGameCard[] cardsFlipped = board.getCardsFlipped();
			if (cardsFlipped[0].compare(cardsFlipped[1])) {
				// Update the board
				board.unflipCard(cardsFlipped[0]);
				board.unflipCard(cardsFlipped[1]);
						
				board.removeCard(cardsFlipped[0]);
				board.removeCard(cardsFlipped[1]);
				currPlayer.addSet();
				currPlayer.addCard(cardsFlipped[0]);
			} else {
				// Update the board
				board.unflipCard(cardsFlipped[0]);
				board.unflipCard(cardsFlipped[1]);
			}
		}
		else if (board.getNumberOfCardsFlipped() == 0 || board.getCardsFlipped()[0].getId() != cardToFlip.getId()) {
			// Determine that the card being selected is not removed
			if (cardToFlip.getState() != "Removed") {
				// Flip the card
				board.flipCard(cardToFlip);
				
				/* ### CONSOLE OUTPUT */
				System.out.println("The image which was selected was " + cardToFlip.getImageMain());
				/* ################## */
				
				// If this is the second card this player has flipped this turn
				if (board.getNumberOfCardsFlipped() == 2) {			
					ConcentrationGameCard[] cardsFlipped = board.getCardsFlipped();
					// Determine if they are identical
					if (cardsFlipped[0].compare(cardsFlipped[1])) {
						
						/* ### CONSOLE OUTPUT */
						System.out.println("These two images are identical, player " + currPlayer.getId() + " gains a set");
						/* ################## */
					} else {
						
						/* ### CONSOLE OUTPUT */
						System.out.println("These two images are not identical, keep trying");
						/* ################## */
					}
					// Update the board
					board.updateCurrPlayer();
					currPlayerId = board.getCurrPlayer();
					currPlayer = board.getPlayers()[currPlayerId];
				}
			}
		}
		
		// The game is over
		if (board.getCards() == 0) {
			System.out.println("Game over");
			ConcentrationGame.gameEnd(board, display, shell);
		}
		
		/* ### CONSOLE OUTPUT */
		board.updateInfo();
		String info = board.getInfo();
		System.out.println(info);
		System.out.println("\n");
		/* ################## */	
		
		if (!shell.isDisposed()) {
			shell.redraw();
			shell.update();	
		}
	}
	// Part of the MouseListener class
	public void mouseUp(MouseEvent e){}
}	


/**
 * This class is responsible for sending the correct images, and setting the correct
 * format for the game board.
 * 
 * CanvasPainListener(ConcentrationGameBoard, Display)
 * 		This is the constructor of the CanvasPaintListener class. This constructor
 * 		is responsible for setting the private variables which will be referenced
 * 		inside the CanvasPaintListener class.
 * 
 * paintControl()
 * 		This method is responsible for maintaining the correct image for being displayed
 * 		in each cell of the game board's display
 * 
 */
class CanvasPaintListener implements PaintListener {
    private Display display;				// Game board main display
    private ConcentrationGameBoard board;	// Game board
    
    // Constructor
    public CanvasPaintListener(ConcentrationGameBoard board, Display display) {
    	this.board = board;
    	this.display = display;
    }
    
    // This method updates the pictures for display on the game board
    // @param event, a PaintEvent occurred
	public void paintControl(PaintEvent event) {
		// Create an image to use a border for each cell
		Image border = new Image(display, "src/images/white.jpeg");
		// Loop over each cell in board
		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++) {
				// Add the border
				event.gc.drawImage(border, (125*i) + 18, (125*j) + 18);
				// Add the image
				event.gc.drawImage(new Image(display, board.getCard(i, j).getImageState()), (125*i) + 25, (125*j) + 25);
			}
		}
		// Send updated info to the display
		event.gc.drawText(board.getInfo(), 125, 525);
	}
}  

/**
 * This class is responsible for responding when the new game button is selected
 * 
 * widgetSelected(event)
 * 		This method is responsible for restarting the program
 * 
 * windgetDefaultSelected(event)
 * 		This method is added to maintain the structure of the class SelectionListener
 * 
 */
class NewGameButtonListener implements SelectionListener {
	Shell shell;			// The end game shell
	
	// Constructor
	public NewGameButtonListener(Shell shell) {
		this.shell = shell;
	}
	
	// This method runs when the player decides to play another game
	// @param event, the SelectionEvent
	public void widgetSelected(SelectionEvent event) {
		// Get the display
		Display display = shell.getDisplay();
		// Close the display
		shell.close();
		display.close();
		
		// Start a new game
		String[] empty = new String[1];
		try {
			ConcentrationGame.main(empty);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void widgetDefaultSelected(SelectionEvent event) {}
	
	
}

/**
 * This class is responsible for responding when our quit button is selected
 * 
 * widgetSelected(event)
 * 		This method is responsible for terminating the program, once
 *		the quit button has been selected
 * 
 * windgetDefaultSelected(event)
 * 		This method is added to maintain the structure of the class SelectionListener
 * 
 */
class ButtonSelectionListener implements SelectionListener {
	// Responsible for quitting the program
	// @param event, SelectionEvent has occurred
	public void widgetSelected(SelectionEvent event) {System.exit(0);}
	
	// Part of the class SelectionListener
	public void widgetDefaultSelected(SelectionEvent event){}    
}	

/*
 * This class is responsible for waiting for the option shell's submit
 * button to be clicked. Once it has, it will read the information
 * out of this shell, to be used for the boards construction
 * 
 * SubmitButtonSelectionListener(Display, Shell)
 * 		This is the constructor for the class SubmitButtonSelectionListener. This
 * 		constructor is responsible for setting the values of the private variables
 * 
 * widgetSelected(SelectionEvent)
 * 		This method will be called when the submit button has been selected. This
 * 		method will then update the settings
 * 
 * widgetDefaultSelected(SelectionEvent)
 * 		This method has been included to complete the structure of the 
 * 		SelectionListener class
 * 
 * addSettings()
 * 		This method is responsible for reading the inputs out of the setting
 * 		shell's combo in order to get the settings preferences
 */
class SubmitButtonSelectionListener implements SelectionListener{
	Shell optionsShell;			// Game board options shell
	Display display;			// Game board main display
	String[] settings;			// Array containing settings for game board
	String set;					// Name of image set
	int players;				// Number of players
	
	// Constructor
	public SubmitButtonSelectionListener(Display dis, Shell shell) {
		this.optionsShell = shell; 
		this.display = dis;
		String[] settings = new String[3];
		this.settings = settings;
	}
	// Responsible for responding when the submit button has been selected. After
	// such, this method will close the settings shell, and collect the information
	// which was user inputed
	// @param event, a SelectionEvent occurred
	public void widgetSelected(SelectionEvent event) {
		// Close the settings shell
		this.optionsShell.close();
		try {
			// call the collect settings method from ConcentrationGame class
			ConcentrationGame.collectSettings(display, optionsShell, settings);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	// Part of the class SelectionListener
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}
	// This method is responsible for setting the values of settings
	// @param combos, a combo array containing the two combos in options shell
	public void addSettings(Combo[] combos) {
		this.settings[0] = combos[0].getText();
		this.settings[1] = combos[1].getText();
		this.settings[2] = combos[2].getText();
	}
}
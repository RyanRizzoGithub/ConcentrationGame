/**
 * Author: 	Ryan Rizzo
 * File: 	ConcentrationGameBoard.java
 * Class: 	CSC335 Object-Oriented Programming
 * Project: Assignment 1
 * Date: 	9/13/22
 **/
import java.io.IOException;

/* The purpose of this class is to represent the state of the game board. The board
 * itself is being represented by a 2D array of ConcentrationGameCard objects. The
 * following functions are used to maintain the board and its variables
 * 
 * ConcentrationGameBoard(int, String[])
 * 		This is the constructor for the ConcentrationGameBoard class. It is responsible
 * 		for initializing all of the private variables used by ConcentrationGameBoard
 * 
 * getInfo()
 * 		This method returns a string representation of the current state of the game board
 * 
 * updateInfo()
 * 		This method checks the board in order to update the string representation of the game
 * 		board.
 * 
 * getNumberOfCardsFlipped()
 * 		This method returns the number of cards on the board which are currently in a flipped 
 * 		state
 * 
 * getCardsFlipped()
 * 		This method returns an array of the ConcentrationGameCard objects which are
 * 		currently in a flipped state
 * 
 * flipCard(ConcentrationGameCard)
 * 		This method sets a ConcentrationGameCard to the flipped position
 * 
 * unflipCard(ConcentrationGameCard)
 * 		This method sets a ConcentrationGameCard to the unflipped position
 * 
 * removeCard(ConcentrationGameCard)
 * 		This method removes a ConcentrationGameCard from the game board
 * 
 * getPlayers()
 * 		This method returns a ConcentrationGamePlayer array containing all of the
 * 		players participating in the game
 * 
 * getNumberOfPlayers()
 * 		This method returns the number of players who are participating in the game
 * 
 * compareCards(ConcentrationGameCard, ConcentrationGameCard)
 * 		This method returns true if two ConcentrationGameCards are identical, and 
 * 		returns false otherwise
 * 
 * checkGameState()
 * 		This method returns true if there are no cards left and the game is over,
 * 		and returns false otherwise
 * 
 * getCard(int, int)
 * 		Given the x & y coordinates of a ConcentrationGameCard, this method returns
 * 		that card object
 * 
 * updateCurrPlayer()
 * 		This method updates the ConcentrationGamePlayer who's turn it currently is
 * 
 * getCurrPlayer()
 * 		This method returns the ConcentrationGamePlayer who's turn it currently is
 */
public class ConcentrationGameBoard {
	private ConcentrationGamePlayer[] players;		// Array of players in game
	private ConcentrationGameCard[][] board;		// 2D card array to represent board
	private ConcentrationGameCard cardA;			// first card selected
	private ConcentrationGameCard cardB;			// second card selected
	private String info;							// Information on game state
	private String gameMode;						// Name of game mode
	private int numberOfPlayers;					// The number of players
	private int cardsFlipped;						// How many cards are flipped
	private int currPlayer;							// Which player's turn it is	
	private int cards;								// Number of cards on board
	
	
	// Constructor
	public ConcentrationGameBoard(int playerNum, String[][] images, String gameMode) throws IOException {
		// Create new 2D array of cards
		ConcentrationGameCard[][] board = new ConcentrationGameCard[4][4];
		int counter = 0;
		// Construct each individual card, using the images stores in "images"
		for (int i=0 ; i<4; i++) {
			for (int j=0; j<4; j++) {
				board[i][j] = new ConcentrationGameCard(images[i][j], counter);
				counter++;
			}
		}
		// Read number of players from parameter and crate an array of ConcentrationGamePlayers
		this.numberOfPlayers = playerNum;
		ConcentrationGamePlayer[] players = new ConcentrationGamePlayer[playerNum];
		for (int i=0; i<playerNum; i++) {
			players[i] = new ConcentrationGamePlayer(i);
		}
		
		this.board = board;
		this.gameMode = gameMode;
		this.cardsFlipped = 0;
		this.cardA = null;
		this.cardB = null;
		this.cards = 16;
		
		
		// Initialize stating player
		this.currPlayer = 0;
		players[0].setTurnOn();
		this.players = players;
		
		// Initialize the information string
		this.info = "Player...			Turn...			Sets...\n";
		for (int i=0; i<numberOfPlayers; i++) {
			if (i == 0) {
				this.info += "0					✓					0\n";
			} else {
				this.info += i + "					✗					0\n";
			}
		}
	}
	
	// This method is responsible for returning the string representation
	// of the boards current state
	// @return info, the string representation of the boards current state
	public String getInfo() {
		return this.info;
	}
	
	// This method is responsible for updating the string representation
	// of the boards current state
	public void updateInfo() {
		// Set top layer of string
		String newInfo = "Player...			Turn...			Sets...\n";
		String symbol = "";
		// For each player, add their information
		for (int i=0; i<numberOfPlayers; i++) {
			if (players[i].getTurn()) {
				symbol = "✓";
			} else {
				symbol = "✗";
			}
			newInfo += "Player " + i + "			" + symbol + "				" + players[i].getSets() + "\n";	
		}
		// Update the variable
		this.info = newInfo;
	}
	
	// This method returns the number of cards which are in flipped state
	// @return cardsFlipped, int representation of number of cards flipped
	public int getNumberOfCardsFlipped() {
		return cardsFlipped;
	}
	
	// This method returns all card objects in a flipped state
	// @return cards, an ConcentrationGameCard[] of flipped cards
	public ConcentrationGameCard[] getCardsFlipped () {
		// Create new ConcentrationGameCard array
		ConcentrationGameCard[] cards = new ConcentrationGameCard[2];
		// Assign each cell to flipped cards
		cards[0] = cardA;
		cards[1] = cardB;
		return cards;
	}
	
	// This method will change the state of a single card object to be flipped
	// @param card, the ConcentrationGameCard which we want to flip
	public void flipCard(ConcentrationGameCard card) {
		// flip the card
		card.flipCard();
		// Increase number of cards flipped
		cardsFlipped++;
		// Update flipped cards
		if (cardA == null) {
			cardA = card;
		} else {
			cardB = card;
		}
		
	}
	
	// This method will change the state of a single card object to be face down
	// @param card, the ConcentrationGameCard which we want face down
	public void unflipCard(ConcentrationGameCard card) {
		// Unflip the card
		card.unflipCard();
		// Decrease the number of cards flipped
		cardsFlipped--;
		// Update flipped cards
		if (cardA == card) {
			cardA = null;
		}
		if (cardB == card) {
			cardB = null;
		}
		
	}
	
	// This method will remove a card object from the board
	// @param card, the ConcentrationGameCard which we want to remove
	public void removeCard(ConcentrationGameCard card) {
		// Remove the card
		card.removeCard();
		this.cards--;
		// Update flipped cards
		if (cardA == card) {
			cardA = null;
		}
		if (cardB == card) {
			cardB = null;
		}
	}
	
	// This method returns an array of player objects who are participating
	// @return player, the ConcentrationGamePlayer[] of players
	public ConcentrationGamePlayer[] getPlayers() {
		return this.players;
	}
	
	// This method returns the number of players who are participating
	// @return numberOfPlayers, int representing number of players
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	// This method will determine if two cards contain the same image
	// @param a, the card we are comparing to card b
	// @param b, the card we are comparing to card a
	// @return boolean, true if a & b are equivalent
	public boolean compareCards(ConcentrationGameCard a, ConcentrationGameCard b) {
		if (a.compare(b)) {
			return true;
		}
		return false;
	}
	
	// This method will determine if the game has finished
	// @return boolean, true if the game if over
	public boolean checkGameState() {
		if (cards == 0) {
			return true;
		} return false;
	}
	
	// This method returns a card object given its x & y coordinate
	// @return board[y][x], the ConcentrationGameCard being requested
	public ConcentrationGameCard getCard(int x, int y) {
		return this.board[y][x];
	}
	
	// This method updates the player who's turn it is
	public void updateCurrPlayer() {
		// Turn off current player
		players[currPlayer].setTurnOff();
		// Update Current player
		currPlayer++;
		if (currPlayer == numberOfPlayers) {
			currPlayer = 0;
		}
		// Turn on current player
		players[currPlayer].setTurnOn();
	}
	
	// This method returns the player objects who's turn it is
	// @return currPlayer, the ConcentrationGamePlayer who's turn it is
	public int getCurrPlayer() {
		return currPlayer;
	}
	
	public int getCards() {
		return this.cards;
	}
	
	public String getGameMode() {
		return this.gameMode;
	}
}


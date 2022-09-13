/**
 * Author: 	Ryan Rizzo
 * File: 	ConcentrationGamePlayer.java
 * Class: 	CSC335 Object-Oriented Programming
 * Project: Assignment 1
 * Date: 	9/13/22
 **/

/*
 *  The purpose of this class is the be a representation of a single player in the game of
 *  Concentration. This class will store information about each player as the game progresses
 *  
 *  ConcentrationGamePlayer(int)
 *  	This is the constructor for the ConcetrationGamePlayer class. This constructor 
 *  	initializes all of the private variables for the class.
 *  
 *  getId()
 *  	This method returns the unique integer representation of this player object
 *  
 *  addSet()
 *  	This method increases the number of sets that this player has collected
 *  
 *  getSets()
 *  	This method returns the number of sets this player has collected
 *  
 *  addCard()
 *  	This method adds a ConcentrationGameCard object to the set this player has collected
 *  
 *  getCards()
 *  	This method returns a ConcentrationGameCard array of all cards this player has collected
 *  
 *  setTurnOn()
 *  	This method sets the turn of this player to true
 *  
 *  setTurnOff()
 *  	This method sets the turn of this player to false
 *  
 *  getTurn()
 *  	This method returns the turn of this player
 */
public class ConcentrationGamePlayer {
	private int id;							// Unique identifier for player
	private int sets;						// Number of sets player has collected
	private ConcentrationGameCard[] cards;	// Cards which player has collected
	private boolean turn;					// True if it is players turn
	
	// Constructor
	public ConcentrationGamePlayer(int id) {
		this.id = id;
		this.turn = false;
		this.sets = 0;
		this.cards = new ConcentrationGameCard[8];
	}
	
	// Returns the unique id for this player
	// @return id, unique int
	public int getId() {
		return this.id;
	}
	
	// Increments the value of sets to represent the player finding a pair
	public void addSet() {
		this.sets++;
	}
	
	// Returns the number of pairs this player has found
	// @return sets, the number of pairs
	public int getSets() {
		return this.sets;
	}
	
	// Adds a card to this players list image pairs
	public void addCard(ConcentrationGameCard card) {
		// Iterate over all cards in cards
		for (int i=0; i<cards.length; i++) {
			// Once open cell has been found
			if (cards[i] == null) {
				// Add the card
				cards[i] = card;
				// Leave the loop
				i = cards.length;
			}
		}
	}
	
	// Returns an array of all images the player has discovered in a pair
	// @return cards, an array of all image pairs found
	public ConcentrationGameCard[] getCards() {
		return this.cards;
	}
	
	// Sets the status of this players turn to true
	public void setTurnOn() {
		this.turn = true;
	}
	// Sets the status of this players turn to false
	public void setTurnOff() {
		this.turn = false;
	}
	
	// Returns the status of this players turn
	// @return boolean, true if it is this players turn
	public boolean getTurn() {
		if (this.turn == true) {
			return true;
		} return false;
	}
}
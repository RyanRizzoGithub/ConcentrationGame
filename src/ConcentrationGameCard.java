/**
 * Author: 	Ryan Rizzo
 * File: 	ConcentrationGameCard.java
 * Class: 	CSC335 Object-Oriented Programming
 * Project: Assignment 1
 * Date: 	9/13/22
 **/
import java.io.IOException;

/*
 * This class is responsible for representing a single card on the game board. Each
 * card can be in a state of down, flipped, or removed. Each card has an image attached
 * 
 * ConcentrationGameCard(String, int)
 * 		This is the constructor for the ConcentrationGameCard class. This constructor 
 * 		is responsible for initializing all of our private variables.
 * 
 * getState()
 * 		This method returns the state of this card. Returns "Removed" if the card is removed,
 * 		"Flipped" if the card is flipped, and "Down" if the card is down;
 * 
 * getImageState()
 * 		This method returns the image which is currently being displayed on the board, which
 * 		represents this card
 * 
 * getImageMain()
 * 		This method returns the original image which is currently representing this card
 * 		on the board
 * 
 * compare(ConcentationGameCard)
 * 		This method returns true if two ConcentrationGameCards are identical, and returns 
 * 		false otherwise
 * 
 * flipCard()
 * 		This method changes this card to a flipped state
 * 
 * unflipCard()
 * 		This method changes this card to a unflipped state
 * 
 * removeCard()
 * 		This method changes this card to a removed state
 * 
 * getId()
 * 		This method returns the id of this card
 */
public class ConcentrationGameCard{
	private boolean flipped;		// True if card is flipped
	private boolean removed;		// True if card is removed
	private int id;					// Unique integer id
	private String image;			// Path to card's image
	
	// Constructor
	public ConcentrationGameCard(String image, int id) throws IOException {
		this.flipped = false;
		this.image = image;
		this.removed = false;
		this.id = id;
	}
	
	// This method returns the state of this card
	// @return boolean, true if card is flipped
	public String getState() {
		if (removed) {
			return "Removed";
		}
		if (flipped) {
			return "Flipped";
		} else {
			return "Down";
		}
		
	}
	
	// Returns the string path of the card's image
	// @return image, the image path
	public String getImageState() {
		if (removed) {
			return "src/images/removed.jpeg";
		}
		if (!flipped) {
			return "src/images/black.jpeg";
		}
		return this.image;
	}
	
	// Returns the string path of card's original image
	// @return image, the main image path
	public String getImageMain() {
		return this.image;
	}
	
	// Determines if two cards contain the same image
	// return boolean, true if the two cards are identical
	public boolean compare(ConcentrationGameCard b) {
		if (this.image == b.getImageMain()) {
			return true;
		} return false;
	}
	
	// This method sets this cards to a flipped state
	public void flipCard() {
		this.flipped = true;
	}
	
	// This method sets this card to an unflipped state
	public void unflipCard() {
		this.flipped = false;
	}
	
	// This method sets this cards to a removed state
	public void removeCard() {
		this.removed = true;
	}
	
	// This method returns the id of this card
	public int getId() {
		return this.id;
	}
	
}

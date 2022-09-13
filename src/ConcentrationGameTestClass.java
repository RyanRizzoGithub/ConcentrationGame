import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/*
 * This is the test class for the Concentration game. This class
 * aims to test all functions embedded in the different objects
 * which are a part of the Concentration game.
 * 
 * main(String[])
 * 		This is the main method. Here you can decide which
 * 		of the below methods you want to run
 * 
 * testConcentrationGameBoard()
 * 		This method tests the functions which are a part of the
 * 		ConcentrationGameBoard class
 * 
 * testConcentrationGameCard()
 * 		This method tests the functions which are a part of the
 * 		ConcentrationGameCard class
 * 
 * testConcentrationGameImageSet()
 * 		This method tests the functions which are a part of the 
 * 		ConcentrationGameImageSet class
 * 
 * testConcentrationGamePlayer()
 * 		This method tests the functions which are a part of the
 * 		ConcentrationGamePlayer class
 */
public class ConcentrationGameTestClass {
	public static void main(String args[]) throws IOException {
		boolean[] methodsToTest = new boolean[5];
		methodsToTest[0] = true;	
		methodsToTest[1] = true;
		methodsToTest[2] = true;
		methodsToTest[3] = true;
		methodsToTest[4] = true;
		
		if (methodsToTest[0] == true) {
			testConcentrationGameBoard();
		}
		if (methodsToTest[1] == true) {
			testConcentrationGameCard();
		}
		if (methodsToTest[2] == true) {
			testConcentrationGameImageSet();
		}
		if (methodsToTest[3] == true) {
			testConcentrationGamePlayer();
		}
	}
	
	// This method tests the functions which are a part of the
	// ConcentrationGameBoard class
	@Test
	public static void testConcentrationGameBoard() throws IOException {
		// Test Constructor
		ConcentrationGameImageSet set = new ConcentrationGameImageSet("Instructors");
		String[] imageSet = set.getImageSet();
		String[][] images = new String[4][4];
		images[0][0] = imageSet[0]; images[0][1] = imageSet[0];
		images[0][2] = imageSet[0]; images[0][3] = imageSet[0];
		images[1][0] = imageSet[1]; images[1][1] = imageSet[1];
		images[1][2] = imageSet[1]; images[1][3] = imageSet[1];
		images[2][0] = imageSet[2]; images[2][1] = imageSet[2];
		images[2][2] = imageSet[2]; images[2][3] = imageSet[2];
		images[3][0] = imageSet[3]; images[3][1] = imageSet[3];
		images[3][2] = imageSet[3]; images[3][3] = imageSet[3];
		
		ConcentrationGameBoard board1 = new ConcentrationGameBoard(2, images, "Default");
		
		boolean test = board1.compareCards(board1.getCard(0, 0), board1.getCard(1, 0));
		assertEquals(true, test);
		
		test = board1.getNumberOfPlayers() == 2;
		assertEquals(true, test);
		
		test = board1.getCurrPlayer() == 0;
		assertEquals(true, test);
		
		board1.updateCurrPlayer();
		test = board1.getCurrPlayer() == 1;
		assertEquals(true, test);
		
		board1.flipCard(board1.getCard(0, 0));
		test = board1.getNumberOfCardsFlipped() == 1;
		assertEquals(true, test);
		
		ConcentrationGameCard[] cards = board1.getCardsFlipped();
		test = cards[0] == board1.getCard(0, 0);
		assertEquals(true, test);
		
		test = board1.checkGameState() == false;
		assertEquals(true, test);
		
		test = board1.getGameMode() == "Default";
		assertEquals(true, test);
		
		test = board1.getInfo() != "";
		assertEquals(true, test);
		
		board1.removeCard(board1.getCard(0, 0));
		test = board1.getCard(0, 0).getState() == "Removed";
		assertEquals(true, test);
		
		board1.flipCard(board1.getCard(0, 1));
		board1.unflipCard(board1.getCard(0, 1));
		test = board1.getNumberOfCardsFlipped() == 1;
		assertEquals(true, test);
	}
	
	// This method tests the functions which are a part of the
	// ConcentrationGameCard class
	@Test
	public static void testConcentrationGameCard() throws IOException {
		ConcentrationGameCard card1 = new ConcentrationGameCard("src/images/instructors/claveau.jpeg", 0);
		ConcentrationGameCard card2 = new ConcentrationGameCard("src/images/instructors/claveau.jpeg", 1);
		ConcentrationGameCard card3 = new ConcentrationGameCard("src/images/KOTH/bobby.jpeg", 2);
		ConcentrationGameCard card4 = new ConcentrationGameCard("src/images/Fish/Angel.jpeg", 3);
		
		boolean test = card1.compare(card2);
		assertEquals(true, test);
		
		test = !card1.compare(card3);
		assertEquals(true, test);
		
		card1.flipCard();
		test = card1.getState() == "Flipped";
		assertEquals(true, test);
		
		card1.unflipCard();
		test = card1.getState() == "Down";
		assertEquals(true, test);
		
		card1.removeCard();
		test = card1.getState() == "Removed";
		assertEquals(true, test);
		
		test = card1.getImageMain() == "src/images/instructors/claveau.jpeg";
		assertEquals(true, test);
		
		test = card1.getImageState() == "src/images/removed.jpeg";
		assertEquals(true, test);
		
		test = card4.getId() == 3;
		assertEquals(true, test);
	}
	
	// This method tests the functions which are a part of the
	// ConcentrationGameBoard class
	@Test
	public static void testConcentrationGameImageSet() {
		ConcentrationGameImageSet instructors = new ConcentrationGameImageSet("Instructors");
		ConcentrationGameImageSet foods	= new ConcentrationGameImageSet("Foods");
		ConcentrationGameImageSet KOTH = new ConcentrationGameImageSet("King of The Hill");
		
		boolean test = instructors.getGenre() == "instructors";
		assertEquals(true, test);
		
		test = foods.getGenre() == "foods";
		assertEquals(true, test);
		
		test = KOTH.getGenre() == "KOTH";
		assertEquals(true, test);
		
		test = KOTH.getImageSet()[0] == "src/images/KOTH/hank.jpeg";
		assertEquals(true, test);	
	}
	
	//This method tests the functions which are a part of the
	// ConcentrationGameBoard class
	@Test
	public static void testConcentrationGamePlayer() throws IOException {
		ConcentrationGamePlayer player = new ConcentrationGamePlayer(0);
		
		boolean test = player.getTurn() == false;
		assertEquals(true, test);
		
		test = player.getSets() == 0;
		assertEquals(true, test);
		
		player.addSet();
		test = player.getSets() == 1;
		assertEquals(true, test);
		
		ConcentrationGameCard card = new ConcentrationGameCard("src/images/black.jpeg", 0);
		player.addCard(card);
		test = player.getCards()[0].compare(card);
		assertEquals(true, test);
		
		player.setTurnOn();
		test = player.getTurn() == true;
		assertEquals(true, test);
		
		player.setTurnOff();
		test = player.getTurn() == false;
		assertEquals(true, test);
	}
}

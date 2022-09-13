/**
 * Author: 	Ryan Rizzo
 * File: 	ConcentrationGameImageSet.java
 * Class: 	CSC335 Object-Oriented Programming
 * Project: Assignment 1
 * Date: 	9/13/22
 **/

/*
 * This class is responsible for selecting and organizing a set of images to
 * be used on the game board
 * 
 * ConcentrationGameImageSet(String)
 * 		This is the constructor for the ConcentraationGameImageSet class. This class is 
 * 		responsible for setting the values of all the private variables
 * 
 * animals()
 * 		This method will be called if the user selected "Animals". This image set contains
 * 		a series of different animals for the images.
 * 
 * foods()
 * 		This method will be called if the user selected "Foods". This image set contains
 * 		a series of different foods for the images.
 * 
 * instructors()
 * 		This method will be called if the user selected "Instructors". This image set 
 * 		contains a series of different Instructors for the images.
 * 
 *KOTH()
 * 		This method will be called if the user selected "King of The Hill". This image set 
 * 		contains a series of different characters from King of The Hill for the images.
 * 
 * fish()
 * 		This method will be called if the user selected "Fish". This image set contains
 * 		a series of different fish from my tanks for the images.
 * 
 * regularShow()
 * 		This method will be called if the user selected "Regular Show". This image set 
 * 		contains a series of different background characters from Regular Show
 * 		for the images.
 */
public class ConcentrationGameImageSet {
	private String[] imageSet;		// String array of image paths
	private String genre;			// String name of set genre
	
	// Constructor
	public ConcentrationGameImageSet(String setName) {
		String[] imageSet = new String[8];
		this.imageSet = imageSet;

		// Animals
		if (setName.equals("Animals")) {
			this.imageSet = animals();
			this.genre = "animals";
		}
		// Foods
		else if (setName.equals("Foods")) {
			this.imageSet = foods();
			this.genre = "foods";
		}
		// Instructors
		else if (setName.equals("Instructors")) {
			this.imageSet = instructors();
			this.genre = "instructors";
		}
		// King of The Hill
		else if (setName.equals("King of The Hill")) {
			this.imageSet = KOTH();
			this.genre = "KOTH";
		}
		// Fish
		else if (setName.equals("Fish")) {
			this.imageSet = fish();
			this.genre = "fish";
		}
		// Regular Show
		else if (setName.equals("Regular Show")) {
			this.imageSet = regularShow();
			this.genre = "Regular Show";
		} else {
			System.out.println("ComboReadException");
		}
	}
	
	// This method will be called if the user selected "Animals". This image set contains
	// a series of different animals for the images.
	// @return imageSet, a String[] of paths to animals pictures
	private String[] animals() {
		String[] imageSet = new String[8];
		imageSet[0] = "src/images/animals/chicken.jpeg";
		imageSet[1] = "src/images/animals/cow.jpeg";
		imageSet[2] = "src/images/animals/dog.jpeg";
		imageSet[3] = "src/images/animals/duck.jpeg";
		imageSet[4] = "src/images/animals/horse.jpeg";
		imageSet[5] = "src/images/animals/cat.jpeg";
		imageSet[6] = "src/images/animals/pig.jpeg";
		imageSet[7] = "src/images/animals/sheep.jpeg";
		return imageSet;
	}
	
	// This method will be called if the user selected "foods". This image set contains
	// a series of different foods for the images.
	// @return imageSet, a String[] of paths to food pictures
	private String[] foods() {
		String[] imageSet = new String[8];
		imageSet[0] = "src/images/foods/apple.jpeg";
		imageSet[1] = "src/images/foods/avocado.jpeg";
		imageSet[2] = "src/images/foods/greenapple.jpeg";
		imageSet[3] = "src/images/foods/peach.jpeg";
		imageSet[4] = "src/images/foods/pineapple.jpeg";
		imageSet[5] = "src/images/foods/pear.jpeg";
		imageSet[6] = "src/images/foods/kiwi.jpeg";
		imageSet[7] = "src/images/foods/bananna.jpeg";
		return imageSet;
	}
	
	// This method will be called if the user selected "Instructors". This image set contains
	// a series of different CS Instructors for the images.
	// @return imageSet, a String[] of paths to Instructor pictures
	private String[] instructors() {
		String[] imageSet = new String[8];
		imageSet[0] = "src/images/instructors/Claveau.jpeg";
		imageSet[1] = "src/images/instructors/Dicken.jpeg";
		imageSet[2] = "src/images/instructors/Efrat.jpeg";
		imageSet[3] = "src/images/instructors/Lotz.jpeg";
		imageSet[4] = "src/images/instructors/McCann.jpeg";
		imageSet[5] = "src/images/instructors/Mercer.jpeg";
		imageSet[6] = "src/images/instructors/Proebsting.jpeg";
		imageSet[7] = "src/images/instructors/Russ.jpeg";
		return imageSet;
	}
	
	// This method will be called if the user selected "King of The Hill". This image set contains
	// a series of different characters from King of The Hill for the images.
	// @return imageSet, a String[] of paths to King of The Hill pictures
	private String[] KOTH() {
		String[] imageSet = new String[8];
		imageSet[0] = "src/images/KOTH/hank.jpeg";
		imageSet[1] = "src/images/KOTH/peggy.jpeg";
		imageSet[2] = "src/images/KOTH/bobby.jpeg";
		imageSet[3] = "src/images/KOTH/luanne.jpeg";
		imageSet[4] = "src/images/KOTH/dale.jpeg";
		imageSet[5] = "src/images/KOTH/boomhauer.jpeg";
		imageSet[6] = "src/images/KOTH/bill.jpeg";
		imageSet[7] = "src/images/KOTH/kahn.jpeg";
		return imageSet;		
	}
	
	// This method will be called if the user selected "Fish". This image set contains
	// a series of different fish from my tanks for the images.
	// @return imageSet, a String[] of paths to fish pictures
	private String[] fish() {
		String[] imageSet = new String[8];
		imageSet[0] = "src/images/fish/Angel.jpeg";
		imageSet[1] = "src/images/fish/CoryCat.jpeg";
		imageSet[2] = "src/images/fish/fBetta.jpeg";
		imageSet[3] = "src/images/fish/Gourami.jpeg";
		imageSet[4] = "src/images/fish/loach.jpeg";
		imageSet[5] = "src/images/fish/mBetta.jpeg";
		imageSet[6] = "src/images/fish/platy.jpeg";
		imageSet[7] = "src/images/fish/rasboras.jpeg";
		return imageSet;
	}
	
	// This method will be called if the user selected "Regular Show". This image set contains
	// a series of different background characters from regular show for the images.
	// @return imageSet, a String[] of paths to regular show pictures pictures
	private String[] regularShow() {
		String[] imageSet = new String[8];
		imageSet[0] = "src/images/regularShow/gary.jpeg";
		imageSet[1] = "src/images/regularShow/jablonski.jpeg";
		imageSet[2] = "src/images/regularShow/garrette.jpeg";
		imageSet[3] = "src/images/regularshow/death.jpeg";
		imageSet[4] = "src/images/regularShow/richdick.jpeg";
		imageSet[5] = "src/images/regularShow/techmo.jpeg";
		imageSet[6] = "src/images/regularShow/musclebro.jpeg";
		imageSet[7] = "src/images/regularShow/sensei.jpeg";
		return imageSet;
	}
	// This method returns the set of images
	// @return imageSet, the String[] of image paths
	public String[] getImageSet() {
		return this.imageSet;
	}
	// This method returns the genre of the images
	// @return genre, the String genre of the images
	public String getGenre() {
		return this.genre;
	}
}

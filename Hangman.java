package edu.cuny.csi.csc330.extra;

import java.util.*;
import java.io.*;

public class Hangman {

	private final int MAX_GUESSES = 6;
	private int currentTry = 0;

	private static List<Character> previousGuesses = new ArrayList<>();
	private List<String> words = new ArrayList<>();
	private static FileReader fr;
	private static BufferedReader br;
	protected static String mystery;
	private StringBuilder builder;
	private static Scanner scanner = new Scanner(System.in);

	public Hangman() throws IOException {
		readIn();
		mystery = selectWord();
		builder = initializeCurrentGuess();
	}

	public void readIn() throws IOException {
		try {
			File infile = new File("C:\\Users\\brend\\Desktop\\words.txt");
			fr = new FileReader(infile);
			br = new BufferedReader(fr);
			String currentLine = br.readLine();
			for (int i = 0; i < 1000; i++) {
				words.add(currentLine);
				currentLine = br.readLine();
			}
			fr.close();
			br.close();
		} catch (IOException e) {
			System.err.println("File could not be opened.");
		}
	}

	public String selectWord() {
		Random random = new Random();
		int wordIndex = Math.abs(random.nextInt()) % words.size();
		return words.get(wordIndex);
	}

	public StringBuilder initializeCurrentGuess() {
		StringBuilder current = new StringBuilder();
		for (int i = 0; i < mystery.length() * 2; i++) {
			if (i % 2 == 0) {
				current.append("_");
			} else {
				current.append(" ");
			}
		}
		return current;
	}

	public String getCurrentGuess() {
		return "Current Guess: " + builder.toString();
	}

	public String displayGuessInfo() {
		int numOfGuessesLeft = MAX_GUESSES - currentTry;
		return "Guesses Left: " + numOfGuessesLeft;
	}

	public boolean isGuessedAlready(char guess) {
		return previousGuesses.contains(guess);
	}

	public boolean playGuess(char guess) {
		boolean goodGuess = false;
		for (int i = 0; i < mystery.length(); i++) {
			if (mystery.charAt(i) == guess) {
				builder.setCharAt(i * 2, guess);
				goodGuess = true;
				previousGuesses.add(guess);
			}
		}
		if (!goodGuess) {
			currentTry++;
			previousGuesses.add(guess);
		}
		return goodGuess;
	}

	public boolean gameOver() throws HangmanException {
		if (weWon()) {
			System.out.println();
			System.out.println("Congratulations! You won! The word is " + mystery + ".");
			return true;
		} else if (weLost()) {
			throw new HangmanException(mystery);
		}
		return false;
	}

	public boolean weLost() {
		return currentTry >= MAX_GUESSES;
	}

	public boolean weWon() {
		String guess = getCondensedWord();
		return guess.equals(mystery);
	}

	public String getCondensedWord() {
		String guess = builder.toString();
		return guess.replace(" ", "");
	}

	public void displayInstructions() {
		System.out.println("Welcome to Hangman! The object of the game is to guess characters "
				+ "that you think are in the mystery word.  If you guess correctly, the character you chose will appear."
				+ " However, if \nyou guess incorrectly, a body part will be added to the Hangman."
				+ " After six incorrect guesses, the body will be complete, and the game will end."
				+ " Let's get started!");
	}

	public String draw() {
		switch (currentTry) {
		case 0:
			return noPerson();
		case 1:
			return addHead();
		case 2:
			return addBody();
		case 3:
			return addOneArm();
		case 4:
			return addOtherArm();
		case 5:
			return addOneLeg();
		default:
			return fullBody();
		}
	}

	public String noPerson() {
		return "|----------\n"+
			   "|         |\n"+
			   "|          \n"+
			   "|          \n"+
			   "|          \n"+
			   "|          \n"+
			   "|          \n";
	}

	public String addHead() {
		return "|----------\n"+
			   "|         |\n"+
			   "|         O \n"+
			   "|          \n"+
			   "|          \n"+
			   "|          \n"+
			   "|          \n";
	}

	public String addBody() {
		return "|----------\n"+
			   "|         |\n"+
			   "|         O \n"+
			   "|         | \n"+
			   "|         | \n"+
			   "|          \n"+
			   "|          \n";
	}

	public String addOneArm() {
		return "|----------\n"+
			   "|         |\n"+
			   "|         O \n"+
			   "|        /|  \n"+
			   "|         | \n"+
			   "|          \n"+
			   "|          \n";
	}

	public String addOtherArm() {
		return "|----------\n"+
			   "|         |\n"+
			   "|         O \n"+
			   "|        /|\\  \n"+
			   "|         | \n"+
			   "|          \n"+
			   "|          \n";
	}

	public String addOneLeg() {
		return "|----------\n"+
			   "|         |\n"+
			   "|         O \n"+
			   "|        /|\\  \n"+
			   "|         | \n"+
			   "|        /  \n"+
			   "|          \n";
	}

	public String fullBody() {
		return "|----------\n"+
			   "|         |\n"+
			   "|         O \n"+
			   "|        /|\\  \n"+
			   "|         | \n"+
			   "|        / \\  \n"+
			   "|          \n";
	}

	public static void main(String[] args) throws IOException, HangmanException {
		Hangman hangman = new Hangman();

		hangman.displayInstructions();

		while (!hangman.gameOver()) {
			System.out.println();
			System.out.println(hangman.draw());
			System.out.println();
			System.out.println(hangman.getCurrentGuess());
			System.out.println(hangman.displayGuessInfo());
			System.out.println("Previous Guesses: " + previousGuesses);
			//System.out.println(mystery);

			System.out.print("Enter a character that you think is in the word: ");
			char guess = scanner.next().toUpperCase().charAt(0);

			while (hangman.isGuessedAlready(guess)) {
				System.out.println("Try again. You already guessed that character.");
				guess = scanner.next().toUpperCase().charAt(0);
			}

			if (hangman.playGuess(guess)) {
				System.out.println("That character is in the word.");
			} else {
				System.out.println("That character is not in the word.");
			}
		} // end while
	}//end main

}// end class

package edu.cuny.csi.csc330.extra;

public class HangmanException extends Exception {
	
	public HangmanException(String mysteryWord) {
	System.err.println("Sorry, you lost. The word was "+mysteryWord);
	System.out.println();
	}
}

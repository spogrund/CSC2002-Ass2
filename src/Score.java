//package skeletonCodeAssgnmt2;
/**
*CSC2002S Assignment 2
*PGRSAM001
*2021
**/


public class Score {
	private int missedWords;
	private int caughtWords;
	private int gameScore;
	
	Score() {
		missedWords=0;
		caughtWords=0;
		gameScore=0;
	}
		
	// all getters and setters must be synchronized
	/**
   *method to get the number of missed words
   *@return number of words missed
   */
	public int getMissed() {
		return missedWords;
	}
   /**
   *@return number of words caught
   */
   
	public int getCaught() {
		return caughtWords;
	}
   /**
   *@return total number of words played in the game ie. number of caught and missed words.
   */
	
	public int getTotal() {
		return (missedWords+caughtWords);
	}
   /**
   *@return the game's score. ie, the total length of words caught.
   */
	public int getScore() {
		return gameScore;
	}
	/**
   *increment the number of missed words
   */
	public void missedWord() {
		missedWords++;
	}
   /**
   *add the length of a caught word to the current game's score.
   */
	public void caughtWord(int length) {
		caughtWords++;
		gameScore+=length;
	}
   /**
   *sets every aspect of the score to zero.
   */

	public void resetScore() {
		caughtWords=0;
		missedWords=0;
		gameScore=0;
	}
}

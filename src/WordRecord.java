//package skeletonCodeAssgnmt2;
/**
*CSC2002S Assignment 2
*PGRSAM001
*2021
**/
public class WordRecord {
	private String text;
	private  int x;
	private int y;
	private int maxY;
	private boolean dropped;
	
	private int fallingSpeed;
	private static int maxWait=500;
	private static int minWait=100;

	public static WordDictionary dict;
	

	
	WordRecord() {
		text="";
		x=0;
		y=0;	
		maxY=300;
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
	}
	
	WordRecord(String text) {
		this();
		this.text=text;
	}
	
	WordRecord(String text,int x, int maxY) {
		this(text);
		this.x=x;
		this.maxY=maxY;
	}
	
// all getters and setters must be synchronized
   /**
   *changes the height of the word to "drop" it and check if it has reached the bottom
   */
	public synchronized  void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true;
		}
		this.y=y;
	}
	/**
   *set how far accross the screen the word is
   */
	public synchronized  void setX(int x) {
		this.x=x;
	}
	/**
   *set the word's text
   */
	public synchronized  void setWord(String text) {
		this.text=text;
	}
   /**
   *@return the word that the word is
   */
	public synchronized  String getWord() {
		return text;
	}
	/**
   *@return the x value of the word
   */
	public synchronized  int getX() {
		return x;
	}	
	/**
   *@return the y value ie. height of the word
   */
	public synchronized  int getY() {
		return y;
	}
	/**
   *@return the speed at which the word falls.
   */
	public synchronized  int getSpeed() {
		return fallingSpeed;
	}
   /**
   *sets the position of the word to an x and y co-ordinate
   */
	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}
   /**
   *put the word to the top of the screen
   */
	public synchronized void resetPos() {
		setY(0);
	}
   /**
   *changes the word and sends it to the top
   */
	public synchronized void resetWord() {
		resetPos();
		text=dict.getNewWord();
		dropped=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//System.out.println(getWord() + " falling speed = " + getSpeed());

	}
	/**
   *checks if a given word matches the word
   *@return boolean for if the word matches or not.
   */
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		if (typedText.equals(this.text)) {
			resetWord();
			return true;
		}
		else
			return false;
	}
	
   /**
   *move the word down by a given increment
   */
	public synchronized  void drop(int inc) {
		setY(y+inc);
	}
	/**
   *checks if the word has dropped to the bottom
   *@return boolean whether the word has dropped or not
   */
	public synchronized  boolean dropped() {
		return dropped;
	}

}

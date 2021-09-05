//package skeletonCodeAssgnmt2;
/**
*CSC2002S Assignment 2
*PGRSAM001
*2021
**/
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;


import java.util.Scanner;
import java.util.concurrent.*;
//model is separate from the view.

public class WordApp {
//shared variables
	static int noWords=4;
	static int totalWords;

   	static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

	static WordRecord[] words;
	static volatile boolean done;  //must be volatile
	static 	Score score = new Score();

	static WordPanel w;
	static JLabel caught;
	static JLabel missed;
	static JLabel scr;
    static JButton startB;
	
	/**
   *method to add everything and set up the graphic user interface
   */
	public static void setupGUI(int frameX,int frameY,int yLimit) {
		// Frame init and dimensions
    	JFrame frame = new JFrame("WordGame"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameX, frameY);
      JPanel g = new JPanel();
      g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
      g.setSize(frameX,frameY);
    	
		w = new WordPanel(words,yLimit);
		w.setSize(frameX,yLimit+100);
	  g.add(w); 
	    
      JPanel txt = new JPanel();
      txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS)); 
       caught =new JLabel("Caught: " + score.getCaught() + "    ");
       missed =new JLabel("Missed:" + score.getMissed()+ "    ");
      scr =new JLabel("Score:" + score.getScore()+ "    ");    
      txt.add(caught);
	   txt.add(missed);
	   txt.add(scr);
    
	    //[snip]
	    JButton quitB = new JButton("Quit");;//added a quit button that exits the application
		
			// add the listener to the jbutton to handle the "pressed" event
		quitB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
		      //[snip]
		      System.exit(0);
		   }
		});
  
	   final JTextField textEntry = new JTextField("",20);
	   textEntry.addActionListener(new ActionListener()
	   {
	      public void actionPerformed(ActionEvent evt) { //added an event on the text being entered to check for a matching word and to update scores if necessary.
	         String text = textEntry.getText();
	          //[snip]
	          for(WordRecord word : words)
	          	if (word.matchWord(text)){
	          		score.caughtWord(text.length());
	          		if (score.getTotal() != totalWords){
	          			word.resetWord();
	          		}
                  else{
                     
                  done = true;
                  endGame(); //if total words have been reaced end the game.
                  break;

                  }
	          		updateGUI();
	          		break;
	          		
	          	}
	          }
	         textEntry.setText("");
	         textEntry.requestFocus();
	      }
	   });
	   
	   txt.add(textEntry);
	   txt.setMaximumSize( txt.getPreferredSize() );
	   g.add(txt);
	    
	   JPanel b = new JPanel();
      b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS)); 
	   startB = new JButton("Start");;
		
			// add the listener to the jbutton to handle the "pressed" event
		startB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
		      //[snip]
		      done = false;
		      startB.setEnabled(false);
		      updateGUI();
		      Thread wThread;

		      for(int i = 0; i<noWords; i++){//for however many words are onscreen create a thread of a wordpanel so each word has its own thread
		      	wThread = new Thread(w);//wordPanel w is the shared resource in this situation
		      	wThread.start();//wordpanel has a run so that when start is called, it runs that function to drop the words an apply neccessary functions
		      	try{
		      		Thread.sleep(1);
		      	}
		      	catch(InterruptedException ee){ee.printStackTrace();}
		      }
		      textEntry.requestFocus();  //return focus to the text entry field
		   }
		});
		JButton endB = new JButton("End");;
			
				// add the listener to the jbutton to handle the "pressed" event
		endB.addActionListener(new ActionListener()
		{
		   public void actionPerformed(ActionEvent e)
		   {
		      //[snip]
		      endGame();
		      
		   }
		});
		
		b.add(startB);
		b.add(endB);
		b.add(quitB);
		g.add(b);
    	
      frame.setLocationRelativeTo(null);  // Center window on screen.
      frame.add(g); //add contents to window
      frame.setContentPane(g);     
       	//frame.pack();  // don't do this - packs it into small space
      frame.setVisible(true);
	}
   /**
   *updates the scores after functions that might affect the scores
   */
	public static synchronized void updateGUI(){
		caught.setText("Caught: " + score.getCaught());
		missed.setText("Missed: " + score.getMissed());
		scr.setText("Score: " + score.getScore());
	}
   /**
   *populates an array with words from an input file, if no input file is found it uses a default dictionary;
   *@return array of words for use in game
   */
   public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();
			//System.out.println("read '" + dictLength+"'");

			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]=new String(dictReader.next());
				//System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictReader.close();
		} catch (IOException e) {
	        System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
		return dictStr;
	}
   
   /**
   *method for ending the game that displays a summary message of scores and resets the game.
   */
   public static void endGame(){
      done = true;
      JOptionPane.showMessageDialog(null, "GAME OVER: you scored: " + score.getScore() + "\n you caught " + score.getCaught() + " words \n"+
         "you missed " + score.getMissed() + " words.\n Press start to play again.");
      startB.setEnabled(true);
		score.resetScore();
		updateGUI();
      
   }
   /**
   *main method that sets everything up and gets everything ready for the game to begin
   */
	public static void main(String[] args) {
    		System.setProperty("sun.java2d.opengl","true");
		//deal with command line arguments
		totalWords=Integer.parseInt(args[0]);  //total words to fall
		noWords=Integer.parseInt(args[1]); // total words falling at any point
		assert(totalWords>=noWords); // this could be done more neatly
		String[] tmpDict=getDictFromFile(args[2]); //file of words
		if (tmpDict!=null)
			dict= new WordDictionary(tmpDict);
		
		WordRecord.dict=dict; //set the class dictionary for the words.
		
		words = new WordRecord[noWords];  //shared array of current words
		
		//[snip]
		
		setupGUI(frameX, frameY, yLimit);  
    	//Start WordPanel thread - for redrawing animation

		int x_inc=(int)frameX/noWords;
	  	//initialize shared array of current words

		for (int i=0;i<noWords;i++) {
			words[i]=new WordRecord(dict.getNewWord(),i*x_inc,yLimit);
		}
	}
	
	
}

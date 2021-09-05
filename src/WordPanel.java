//package skeletonCodeAssgnmt2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.*;
import javax.swing.JButton;
import javax.swing.JPanel;
/**
*CSC2002S Assignment 2
*PGRSAM001
*2021
**/
public class WordPanel extends JPanel implements Runnable {
   public static volatile boolean done;
   private WordRecord[] words;
   private int noWords;
   private int maxY;
   private AtomicInteger wordsI = new AtomicInteger();

		/**
      *method that formats the panel
      */
   public void paintComponent(Graphics g) {
      int width = getWidth();
      int height = getHeight();
      g.clearRect(0,0,width,height);
      g.setColor(Color.red);
      g.fillRect(0,maxY-10,width,height);
   
      g.setColor(Color.black);
      g.setFont(new Font("Helvetica", Font.PLAIN, 26));
   	   //draw the words
   	   //animation must be added 
      for (int i=0;i<noWords;i++){	    	
          	//g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());	
         g.drawString(words[i].getWord(),words[i].getX(),words[i].getY()+20);  //y-offset for skeleton so that you can see the words	
      }
   	   
   }
		
   WordPanel(WordRecord[] words, int maxY) {
      this.words=words; //will this work? yes
      noWords = words.length;
      done=false;
      this.maxY=maxY;		
   }
		/**
      *run implementation for multiple threads
      *drops the words according to the speed by waiting for a period of their falling speed
      *checks are constantly done to assess whether the word has dropped or the words have reached total words
      */
   public void run() {
   		//add in code to animate this
      done =false;
      WordRecord word =words[wordsI.get()];
      wordsI.getAndIncrement();//uses an atomic variable to differentiate between threads.
      while(!WordApp.done){
      		//System.out.println("here " + word.getWord());
         word.drop(10);
      
         try{Thread.sleep(word.getSpeed());
         }
         catch(InterruptedException e){
            e.printStackTrace();
         }
         repaint();
         if(word.dropped()){//check if word has dropped
            WordApp.score.missedWord();
            word.resetWord();
            WordApp.updateGUI();
            if (WordApp.score.getTotal() == WordApp.totalWords){//if total words has reached the number of words for the game, it ends the game.
               WordApp.done = true;
               WordApp.endGame();
               break;
             
            }
         }
            
      }
      word.resetWord();
      wordsI.getAndSet(0);
   		
   			
   }
   public static synchronized void updateGUI(){
      WordApp.updateGUI();
   }

}



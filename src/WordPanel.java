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

public class WordPanel extends JPanel implements Runnable {
		public static volatile boolean done;
		private WordRecord[] words;
		private int noWords;
		private int maxY;
		private AtomicInteger wordsI = new AtomicInteger();

		
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
			this.words=words; //will this work?
			noWords = words.length;
			done=false;
			this.maxY=maxY;		
		}
		
		public void run() {
			//add in code to animate this
			done =false;
			WordRecord word =words[wordsI.get()];
			wordsI.getAndIncrement();
			while(!WordApp.done){
				System.out.println("here " + word.getWord());
				word.drop(10);
				try{Thread.sleep(word.getSpeed());
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
				repaint();
				if(word.dropped()){
					WordApp.score.missedWord();
					word.resetWord();
					WordApp.updateGUI();
				}
			}
				
		}
		public static synchronized void updateGUI(){
			WordApp.updateGUI();
		}

	}



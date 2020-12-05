package com.SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

//import com.SnakeGame.OurPanel.Keyboard;

public class OurFrame extends JFrame { //This class is an extensions or the child/sub class of the parent/super "JFrame" class.
	
	/* No need to make these variables public, hence, "private" keyword is used. No need to change these variables within the methods, hence, the
	"final" keyword is used. These variables are defined as instance variables (variables outside the methods) to be used inside all methods
	within the class. */
	private final int screenwidth = 500;  
	private final int screenheight = 400;
	private final int screenunits = 20; //Note that this will represent the size of the fruit and size of the body parts (i.e. head and body units).
	private final int totalunits = (screenwidth/screenunits) * (screenheight/screenunits); //Total number of screenunits.
	
	// These variable will need to be changed inside the methods, hence, the "final" keyword is not used.
	private int bodyunits = 4; //Head and body units
	private int fruitX; //Creates a x-coordinate variable for the fruit 
	private int fruitY; //Creates a y-coordinate variable for the fruit
	
	private int X []= new int[totalunits];
	private int Y []= new int[totalunits];
	private boolean rungame = true;
	private int direction = 39;
	private int score = 0;
	private int highestscore;
	
	
	
	
	//Timer thetime;
	
	OurFrame () {
		/* JFrame theframe = new JFrame() */; //The JFrame object variable does not need to be created as this is a JFrame sub class
		setVisible(true); //Makes window visible
		//setSize(400,300); //Gives window the a width and height dimensions in pixels
		setTitle("SNAKE"); //Gives window  a title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Stops program when the window is closed
		setResizable(false); //Prevents window from being resized and maximized by the user
		
		// getContentPane().setBackground(Color.blue); //Sets the background color of the frame/window
		
		add(new OurPanel()); //Calls the "OurPanel" class and runs its constructor then adds the panel and its elements to the frame/window 
		pack(); // Allows the frame/window to fit the content that is placed inside of it (i.e. a panel, a label, etc.)
		setLocationRelativeTo(null); //Places the frame/window in the middle of the screen
		/* Note that the "pack()" and "setLocationRelativeTo()" methods have to come after the adding the panel to the frame. Otherwise they 
		will not behave as they should. */
		addKeyListener(new Keyboard()); //Calls the keylistener methods using "this" keyword which refers to the implemented keylistener on the panel.
		
		
	}
	
	//First child class
	public class OurPanel extends JPanel implements ActionListener { /* This class is an extensions or the child/sub class of the parent/super 
		"JPanel" class. This class implements an actionlistener to that will run as specified by the timer object variable. */	
		
		
			OurPanel () {
				setPreferredSize(new Dimension(screenwidth,screenheight)); /* The "setBounds()" method does run properly since the "pack()" method is used. Hence, the
				"setPreferredSize()" is used instead. */
				setBackground(Color.black);
				

				fruit(); //Calls the specified method within the class.
				Timer thetime = new Timer(100, this); /* Creates the timer object with a delay value in milliseconds and calls the actionlistener 
				(i.e. the actionperformed method) using "this" keyword which refers to the implemented actionlistener on the panel*/
				thetime.start(); //Initiates the timer.
				
			}
			
			public void fruit () {
				Random rand = new Random();
				fruitX =  rand.nextInt(screenwidth/screenunits)*screenunits; //Creates a random x-coordinate for the fruit
				fruitY =  rand.nextInt(screenheight/screenunits)*screenunits; //Creates a random y-coordinate for the fruit
			}
			
			public void fruiteaten () {
				if (X[0] == fruitX && Y[0] == fruitY) {
					fruit ();
					bodyunits ++;
					score ++;
					try {
						fruitsound();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			public void fruitsound () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
				File beepfile = new File("Metallic Clank.wav"); //Imports the beep sound
				AudioInputStream audio1 = AudioSystem.getAudioInputStream(beepfile); //This adds the throws declaration to the constructor.
				Clip beepclip = AudioSystem.getClip(); //This adds another throws declaration to the constructor.
				beepclip.open(audio1); //Opens the beep sound clip using the audioInputStream object variable.
				beepclip.start();
			}
			
			public void crashsound () throws UnsupportedAudioFileException, IOException, LineUnavailableException {
				File crashfile = new File("Crash Metal Sweetener Distant.wav"); //Imports the beep sound
				AudioInputStream audio2 = AudioSystem.getAudioInputStream(crashfile); //This adds the throws declaration to the constructor.
				Clip crashclip = AudioSystem.getClip(); //This adds another throws declaration to the constructor.
				crashclip.open(audio2); //Opens the beep sound clip using the audioInputStream object variable.
				crashclip.start();
			}
			
			public void paint(Graphics g) { //Since this is a graphic method, it does not need to be called in the constructor or any other method.
				super.paint(g); //Allows the background color to be changed as normal when working with graphics. Otherwise, it will not changed
				
				if (rungame) {
					//The construction lines below do not need to be shown.
					/*
					for (int count= 0; count < screenwidth; count += screenunits) { //To draw vertical construction lines across the screen/panel. 
					g.drawLine(count, 0, count, screenheight); //Coordinates are in pixels.
					}
					for (int count= 0; count < screenheight; count += screenunits) { //To draw horizontal construction lines across the screen/panel. 
						g.drawLine(0, count, screenwidth, count); //Coordinates are in pixels.
					}
					*/
					
					g.setColor(Color.red); /* Sets the colour of the created graphic/s. Note that the colour needs to be set before the graphic/s, otherwise,
					it won't behave as it should. */
					g.fillOval(fruitX, fruitY, screenunits, screenunits); //Creates an oval/circle shape with specified x and y coordinates and width and height values
				
					for (int count = 0; count < bodyunits; count ++) {
						if (count == 0) {
							g.setColor(Color.cyan);
							g.fillRect(X[count], Y[count], screenunits, screenunits);
						} else {
							g.setColor(Color.blue);
							g.fillRect(X[count], Y[count], screenunits, screenunits);
						}
					}
					g.setColor(Color.lightGray);
					g.setFont(new Font("Lucida Calligraphy",Font.PLAIN, 30)); // font: (name, type, size)  
					FontMetrics align = getFontMetrics(g.getFont()); //Creates an object variable which will be used to centre the text on the screen by using its string width.
					g.drawString("Score: "+ score, (screenwidth-align.stringWidth("Score: " + score))/2, g.getFont().getSize()); //Retrieves the set Font size
					
				} else {
					g.setColor(Color.red);
					g.setFont(new Font("Lucida Calligraphy",Font.PLAIN, 30)); // font: (name, type, size)  
					FontMetrics align1 = getFontMetrics(g.getFont()); //Creates an object variable which will be used to centre the text on the screen by using its string width.
					g.drawString("Score: "+ score, (screenwidth-align1.stringWidth("Score: " + score))/2, g.getFont().getSize()); //Retrieves the set Font size
										
					g.setColor(Color.red);
					g.setFont(new Font("Algerian",Font.BOLD, 70)); // font: (name, type, size)  
					FontMetrics align2 = getFontMetrics(g.getFont()); //Creates an object variable which will be used to centre the text on the screen by using its string width.
					g.drawString("GAME OVER", (screenwidth-align2.stringWidth("GAME OVER"))/2, screenheight/2); //For drawing text. Note that the coordinates refer to the bottom left corner of the string.
				}
				
				
			}
			
			public void collision () {
				if (X[0]<0) { //To stop the game when colliding with the left screen border.
					rungame = false;
					System.out.println("Collision with left border");
					try {
						crashsound();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (X[0]>(screenwidth-screenunits)) { //To stop the game when colliding with the right screen border.
					rungame = false;
					System.out.println("Collision with right border");
					try {
						crashsound();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (Y[0]<0) { //To stop the game when colliding with the top screen border.
					rungame = false;
					System.out.println("Collision with top border");
					try {
						crashsound();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (Y[0]>(screenheight-screenunits)) { //To stop the game when colliding with the bottom screen border.
					rungame = false;
					System.out.println("Collision with bottom border");
					try {
						crashsound();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for (int count = 1; count< bodyunits; count ++) {
					if (X[0] == X[count] && Y[0] == Y[count]) { //To stop game when snake head collides with its body parts
						rungame = false;
						System.out.println("Collision with body parts");
						try {
							crashsound();
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rungame) {
					for (int count = bodyunits; count > 0; count --) {
					X[count] = X[count-1]; 
					Y[count] = Y[count-1];
					/* This arrangements allows the bodyunits (excluding the head) to move in the expected way, instead of 
					them being stacked on top of each other in both the x and y directions. */ 
				}
				
				switch(direction) {
				// Incrementing and decrementing the first index value (i.e. the snake head) every specified millisecond in the x or y direction:
				case 37: // arrow-key code for the left direction
 					X[0] -= screenunits;
					break;
				case 38: // arrow-key code for the up direction
					Y[0] -= screenunits;
					break;
				case 39: // arrow-key code for the right direction
					X[0] += screenunits; 
					break;
				case 40: // arrow-key code for the down direction
					Y[0] += screenunits;
					break;
					
				}
				
				collision();
				fruiteaten();
				repaint(); //Calls the paint method which repaints/redraws the graphics
				
				}
				
			}
				

	}
	
	//Second child class
	public class Keyboard extends KeyAdapter { /* This adapter class allows the other methods associated with the keylistener class that will not 
		be used to be eliminated, thus reducing unnecessary lines of code. Futhermore, */
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case 37:
				if (direction != 39) { //To prevent the snake from going left when going right
					direction = 37;
				}
				break;
			case 38:
				if (direction != 40) { //To prevent the snake from going up when going down	
					direction = 38;
				}
				break;
			case 39:
				if (direction != 37) { //To prevent the snake from going right when going left
					direction = 39;	
				}
				break;
			case 40:
				if (direction != 38) { //To prevent the snake from going down when going up
					direction = 40;	
				}
				break;
			}
			
			System.out.println(e.getKeyChar());
			System.out.println(e.getKeyCode());

		}
	}
}

package Asteroids;
//Made by Andrew Xue
//a3xue@edu.uwaterloo.ca
// Game is NOT FINISHED. Movement was implement using individual x and y vector components.
// Differential equations were used for speed deterioration. Use the left and right arrow keys to
// rotate the ship and the up arrow to move the ship forwards.

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javax.swing.JComponent;
import javax.swing.JFrame;


public class Asteroids {
	JFrame window = new JFrame("Asteroids");
	asteroids startinggrid = new asteroids();
	
	double shipx = 200;
	double shipy = 200;
	double angle = 180;
	double dxangle;
	
	double shipdx;
	double shipdy;
	double shipxaccel;
	double shipyaccel;
	
	double rightcornerx;
	double rightcornery;
	double leftcornerx;
	double leftcornery;
	double shiptipx;
	double shiptipy;
	
	boolean bullet;
	double bulletx;
	double bullety;
	double bulletangle;
	
	int angletoshipcorner=130;
	
	int[] pressedkeys =  new int[3];
	// {anglechange,accelerating,shooting}
	
	public static void main(String[] args) {
		new Asteroids().go();
	}
	
	private void go(){
		window.setSize(1000, 1000);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
		window.add(startinggrid);
		window.addKeyListener(new keyevents());
		playgame();
	}
	
	private void playgame(){
		while(true){
		if (pressedkeys[0]==1){
			angle+=5;
		}
		if (pressedkeys[0]==2){
			angle-=5;
		}
		if (pressedkeys[1]==1){
			shipdx+=0.08*Math.sin(angle * Math.PI / 180);
			shipdy+=0.08*Math.cos(angle * Math.PI / 180);
		}
		if (pressedkeys[2]==1&&!bullet){
			bulletangle=angle;
			bulletx=shipx;
			bullety=shipy;
			bullet=true;
		}
		if (bullet){
			bulletx+=5*Math.sin(bulletangle * Math.PI / 180);
			bullety+=5*Math.cos(bulletangle * Math.PI / 180);
		}
		//grap.fillRect(5,5,985,955);
		shipx+=shipdx;
		shipy+=shipdy;
		
		shipdx-=shipdx/100;
		shipdy-=shipdy/100;

		if (shipx<5){
			shipx=990;
		}
		if (shipy<5){
			shipy=960;
		}
		if (shipx>990){
			shipx=5;
		}
		if (shipy>960){
			shipy=5;
		}
		
		try{Thread.sleep(10);}
		catch(Exception e){System.out.println("UH OH!");}
		window.repaint();
	}}
	
	private class keyevents implements KeyListener{
		public void keyPressed(KeyEvent key) {
			if (key.getKeyCode()==KeyEvent.VK_LEFT){
				pressedkeys[0]=1;
			}
			if (key.getKeyCode()==KeyEvent.VK_RIGHT){
				pressedkeys[0]=2;
			}
			if (key.getKeyCode()==KeyEvent.VK_UP){
				pressedkeys[1] = 1;
			}
			if (key.getKeyCode()==KeyEvent.VK_SPACE&&!bullet){
				pressedkeys[2] = 1;
			}
			System.out.println(Arrays.toString(pressedkeys));
		}
		public void keyReleased(KeyEvent key) {
			if (key.getKeyCode()==KeyEvent.VK_UP){
				pressedkeys[1]=0;
			}
			if (key.getKeyCode()==KeyEvent.VK_SPACE){
				pressedkeys[2]=0;
			}
			else{
				pressedkeys[0]=0;
			}
			
		}
		public void keyTyped(KeyEvent key) {}
		
	}
	
	private class asteroids extends JComponent{
		public void paintComponent(Graphics g){	
			Graphics2D grap = (Graphics2D) g;
			grap.setColor(Color.RED);
			grap.fillRect(0, 0, 1050, 1050);
			grap.setColor(Color.BLACK);
			grap.fillRect(5,5,985,955);
			
			grap.setColor(Color.WHITE);
			rightcornerx = (shipx + 20 * Math.sin((angle-angletoshipcorner) * Math.PI / 180));
			rightcornery = (shipy + 20 * Math.cos((angle-angletoshipcorner) * Math.PI / 180 ));
			
			leftcornerx = (shipx + 20 * Math.sin((angle+angletoshipcorner) * Math.PI / 180));
			leftcornery= (shipy + 20 * Math.cos((angle+angletoshipcorner) * Math.PI / 180 ));
			
			shiptipx = (shipx + 10 * Math.sin(angle * Math.PI / 180));
			shiptipy = (shipy + 10 * Math.cos(angle * Math.PI / 180 ));
			
			grap.fillRect((int)bulletx, (int)bullety, 5, 5);
			
			if (bulletx<-5||bulletx>995||bullety<-5||bullety>965){
				bullet=false;
			}
			
			grap.drawLine((int)shiptipx, (int)shiptipy, (int)rightcornerx, (int)rightcornery);
			grap.drawLine((int)shiptipx, (int)shiptipy, (int)leftcornerx, (int)leftcornery);
			grap.drawLine((int)shipx, (int)shipy, (int)rightcornerx, (int)rightcornery);
			grap.drawLine((int)shipx, (int)shipy, (int)leftcornerx, (int)leftcornery);

		}
		
	}
}

package Asteroids;
//Made by Andrew Xue
//a3xue@edu.uwaterloo.ca
// Game is NOT FINISHED. Movement was implement using individual x and y vector components.
// Differential equations were used for speed deterioration. Use the left and right arrow keys to
// rotate the ship and the up arrow to move the ship forwards. THIS IS NOW A FIREWORK MAKING GAME

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class Asteroids {
	JFrame window = new JFrame("Asteroids");
	asteroids startinggrid = new asteroids();
	Color[] colors = {Color.RED,Color.GREEN,Color.BLUE,Color.WHITE,Color.ORANGE,Color.PINK,Color.MAGENTA,Color.CYAN,Color.YELLOW,Color.DARK_GRAY};
	Random colorpicker = new Random();
	
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
	double bulletspeed = 90;
	
	int angletoshipcorner=130;
	
	int[] pressedkeys =  new int[4];
	// {anglechange,accelerating,shooting}
	
	ArrayList<List<Double>> bullets = new ArrayList<List<Double>>();
	
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
		if (pressedkeys[1]==1){
			angle-=5;
		}
		if (pressedkeys[2]==1){
			shipdx+=0.08*Math.sin(angle * Math.PI / 180);
			shipdy+=0.08*Math.cos(angle * Math.PI / 180);
		}
		if (pressedkeys[3]==1){
			bullets.add(Arrays.asList(shipx,shipy,angle,(double)colorpicker.nextInt(10)));
		}
		for (int i=0;i<bullets.size();i++){
			bullets.get(i).set(0,bullets.get(i).get(0)+bulletspeed*Math.sin(bullets.get(i).get(2))* Math.PI / 180);
			bullets.get(i).set(1,bullets.get(i).get(1)+bulletspeed*Math.cos(bullets.get(i).get(2))* Math.PI / 180);
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
				pressedkeys[1]=1;
			}
			if (key.getKeyCode()==KeyEvent.VK_UP){
				pressedkeys[2] = 1;
			}
			if (key.getKeyCode()==KeyEvent.VK_SPACE){
				pressedkeys[3] = 1;
			}
		}
		public void keyReleased(KeyEvent key) {
			if (key.getKeyCode()==KeyEvent.VK_LEFT){
				pressedkeys[0]=0;
			}
			if (key.getKeyCode()==KeyEvent.VK_RIGHT){
				pressedkeys[1]=0;
			}
			if (key.getKeyCode()==KeyEvent.VK_UP){
				pressedkeys[2]=0;
			}
			if (key.getKeyCode()==KeyEvent.VK_SPACE){
				pressedkeys[3]=0;
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
			for (int i=0;i<bullets.size();i++){
				grap.setColor(colors[bullets.get(i).get(3).intValue()]);
				grap.fillRect(bullets.get(i).get(0).intValue(), bullets.get(i).get(1).intValue(), 5, 5);
			}
			grap.setColor(Color.WHITE);
			grap.drawLine((int)shiptipx, (int)shiptipy, (int)rightcornerx, (int)rightcornery);
			grap.drawLine((int)shiptipx, (int)shiptipy, (int)leftcornerx, (int)leftcornery);
			grap.drawLine((int)shipx, (int)shipy, (int)rightcornerx, (int)rightcornery);
			grap.drawLine((int)shipx, (int)shipy, (int)leftcornerx, (int)leftcornery);

		}
		
	}
}

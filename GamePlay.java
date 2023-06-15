package BrickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import javax.swing.JPanel;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
  private boolean play = false;
 
  private int score =0;
  private int totalbricks = 40;
  private Timer time;
  private int delay = 8;
  private int player1= 310;
  private int ballposX = 120;
  private int ballposY = 350;
  private int ballXdir = -1;
  private int ballYdir = -2;
  
  private MapGenerator map;


  
  public GamePlay() {
    map = new MapGenerator (5,8);
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    time = new Timer(delay, this);
    time.start();
    
    }
  
  public void paint(Graphics g) {
	  
    //background
    g.setColor(Color.black);
    g.fillRect(1,1, 692, 592);
    
    
    //draw
    map.draw((Graphics2D)g);
    
    
    
    //borders
    g.setColor(Color.yellow);
    g.fillRect(0, 0, 3, 592);
    g.fillRect(0,0 , 692, 3);
    g.fillRect(691,0,3, 592);
    
    
    
    //scores
    g.setColor(Color.white);
    g.setFont(new Font("serif", Font.BOLD, 25));
    g.drawString(""+score, 590, 30);
    
    
    //paddle
    g.setColor(Color.green);
    g.fillRect(player1, 550, 100,8);
    
    
    //ball 
    g.setColor(Color.yellow);
    g.fillOval(ballposX,ballposY, 20, 20);
    
    if(totalbricks <=0) {
      play = false;
      ballXdir = 0;
      ballYdir = 0;
      g.setColor(Color.red);
      g.setFont(new Font("serif", Font.BOLD, 35));
      g.drawString("YOU WON!", 190, 300);
      
      g.setFont(new Font("serif", Font.BOLD, 30));
      g.drawString("Press ENTER to restart",230, 350);
    }
    
    if (ballposY > 570) {
      play = false;
      ballXdir = 0;
      ballYdir = 0;
      g.setColor(Color.red);
      g.setFont(new Font("serif", Font.BOLD, 35));
      g.drawString("GAME OVER!", 190, 300);
      
      g.setFont(new Font("serif", Font.BOLD, 30));
      g.drawString("Press ENTER to restart",230, 350);
      
    }
    g.dispose();
  }
  
  
  @Override
  public void actionPerformed(ActionEvent e) {
    time.start();
    if(play) {
    	
      //detecting intersection of 2 objects
      if (new Rectangle(ballposX,ballposY, 20, 20).intersects(new Rectangle(player1, 550, 100, 8))) {
        ballYdir = -ballYdir; 
      }
      
      
      
      B: for (int i=0; i< map.map.length; i++) {
        for (int j=0; j<map.map[0].length; j++) {
          if (map.map[i][j]>0) {
            int brickx = j*map.brickWidth + 80;
            int bricky = i* map.brickHeight + 50; 
            int brickwidth = map.brickWidth;
            int brickheight = map.brickHeight;
            
            Rectangle rect = new Rectangle(brickx, bricky, brickwidth, brickheight);
            Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
            Rectangle brickrect = rect; 
            
            if(ballrect.intersects(brickrect)) {
              map.setBrickValue(0,i,j);
              totalbricks--;
              score += 5;
              
              if(ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + brickrect.width) {
                ballXdir = - ballXdir;
              } else {
                ballYdir = -ballYdir; 
              }
              
              break B;
            }
          }
        }
      }
      ballposX += ballXdir;
      ballposY += ballYdir;
      if (ballposX < 0) { 
        ballXdir = - ballXdir;
      }
      if (ballposY < 0) { 
        ballYdir = - ballYdir;
      }
      if (ballposX > 670) { 
        ballXdir = - ballXdir;
      }
      
    }
    repaint();
  }

  @Override
  public void keyTyped(KeyEvent e) {}
  
  @Override
  public void keyReleased(KeyEvent e) {}
  

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
      if(player1 >= 600) {
        player1 = 600;
      } else {
        moveright();
      }
    }
    if(e.getKeyCode() == KeyEvent.VK_LEFT) {
      if(player1 <10) {
        player1 = 10;
      } else {
        moveleft();
      }
    }
    
    if(e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (!play) {
        play = true;
        ballposX = 120;
        ballposY = 350;
        ballXdir = -1;
        ballYdir = -2;
        player1 = 310;
        score = 0;
        totalbricks = 40;
        map = new MapGenerator(5,8);
        
        repaint();
      };
      
    }
  }

  public void moveright() {
    play = true;
    player1+=20;
  }
  public void moveleft() {
    play = true;
    player1 -=20;
  }
  
}
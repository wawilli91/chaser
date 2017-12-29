package game;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class Game extends JPanel implements KeyListener {

	//environment settings
	static int frameWidth = 1000;
	static int frameHeight = 1000;
	static int numRocks = 2;
	static int rockSize = 30;
		
	//Obstacle coordinate variables
	static int[] obsXCoord = new int[numRocks];
	static int[] obsYCoord = new int[numRocks];
	
	//Player's coordinate variables -- starts in the center
	static int playerX = frameWidth / 2;
	static int playerY = frameHeight / 2;
	
	//Enemy's coordinate variables -- starts in the upper left corner
	static int enemyX = 0;
	static int enemyY = 0;
	
	//player settings
	static int moveDistance = 5;
	static int bulletSpeed = 20;
	static int bulletX = 50;
	static int bulletY = 50;
	
	public static void main(String[] args) {
				
		//generates coordinates for the obstacles.
		for(int i = 0; i<numRocks; i++) {
			obsXCoord[i] = randomCoords(frameWidth)-rockSize;
			obsYCoord[i] = randomCoords(frameHeight)-rockSize;
		}
		
		//game screen
		JFrame gameFrame = new JFrame("Chaser");
		gameFrame.setSize(frameWidth,frameHeight);
		gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gameFrame.setLocationRelativeTo(null); //centers window on screen
		gameFrame.setResizable(false);
		Game player1 = new Game();
		gameFrame.add(player1);
		gameFrame.setVisible(true);
		
		if(enemyX <= playerX + 5 && enemyY <= playerY + 5 &&
	       enemyX >= playerX - 5 && enemyY >= playerY - 5) {
			System.out.println("YOU LOSE");
		}
		
	}

	
	public void move(String direction) {
		
		boolean hitWall = false;
		
		//moves the player in the direction chosen
		if (direction == "up") {
			playerY = playerY - moveDistance;
		} else if (direction == "down") {
			playerY = playerY + moveDistance;
		} else if (direction == "left") {
			playerX = playerX - moveDistance;
		} else { //right
			playerX = playerX + moveDistance;
		}
		
		//checks if player ran into an obstacle
		for (int i = 0; i < obsXCoord.length; i++) {
				if (playerX <= obsXCoord[i] + 30 && playerX >= obsXCoord[i] - 30 && playerY <= obsYCoord[i] + 30 && playerY >= obsYCoord[i] - 30) {
					hitWall = true;
				}
		}
		
		//checks if player ran into a game wall
		if (playerX < 0 || playerX >= frameWidth - 30 || playerY < 0 || playerY > frameHeight - 70) {
			hitWall = true;
		}
		
		//moves player back if it hit something
		if(hitWall) {
			System.out.println("Ran into wall");
			if (direction == "up") {
				playerY = playerY + moveDistance;
			} else if (direction == "down") {
				playerY = playerY - moveDistance;
			} else if (direction == "left") {
				playerX = playerX + moveDistance;
			} else { //right
				playerX = playerX - moveDistance;
			}
		}	
		
		//move enemy closer
		if(enemyX < playerX && enemyY < playerY) {
			enemyX = enemyX + 1;
			enemyY = enemyY + 1;
		} else if (enemyX > playerX && enemyY > playerY) {
			enemyX = enemyX - 1;
			enemyY = enemyY - 1;
		} else if (enemyX < playerX && enemyY > playerY) {
			enemyX = enemyX + 1;
			enemyY = enemyY - 1;
		} else if (enemyX > playerX && enemyY < playerY){
			enemyX = enemyX - 1;
			enemyY = enemyY + 1;
		} else if (enemyX == playerX && enemyY < playerY) {
			enemyY = enemyY + 1;
		} else if (enemyX == playerX && enemyY > playerY) {
			enemyY = enemyY - 1;
		} else if (enemyY == playerY && enemyX < playerX) {
			enemyX = enemyX + 1;
		} else {
				enemyY = enemyY - 5;
		}
		
		if(enemyX <= playerX + 5 && enemyY <= playerY + 5 &&
			       enemyX >= playerX - 5 && enemyY >= playerY - 5) {
					System.out.println("YOU LOSE");
					System.exit(0);
				}
	}

	public Game() {
		this.setFocusable(true);
		this.addKeyListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//moves character's circle
		g2d.fillOval(playerX, playerY, 30, 30);
		
		//puts the obstacles in the game
		for(int i = 0; i < numRocks; i++) {
			g2d.fillRect(obsXCoord[i], obsYCoord[i], rockSize, rockSize);
		}
		
		//moves enemy closer
		g2d.fillRect(enemyX, enemyY, 10, 10);
	}
	
	public static int randomCoords(int coordLength) {
		int x = (int) (Math.random()*coordLength);
		return x;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub	
		int keyPressed = e.getKeyCode();
		switch(keyPressed) {
		case KeyEvent.VK_UP:
			move("up");
			repaint();
			break;
		case KeyEvent.VK_DOWN:
			move("down");
			repaint();
			break;
		case KeyEvent.VK_LEFT:
			move("left");
			repaint();
			break;
		case KeyEvent.VK_RIGHT:
			move("right");
			repaint();
			break;
		case KeyEvent.VK_SPACE:
			System.out.println("Spacebar");
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
package main;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TetrisPiece {
	
	final public static int SCREEN_WIDTH = 400;
	final public static int SCREEN_HEIGHT = 600;
	
	final public static int BOX_SIZE = SCREEN_WIDTH / 10;
	
	final private static Color[] colors = 
		{Color.RED,Color.BLUE,Color.ORANGE,Color.YELLOW,
				Color.GREEN,Color.BLUE,Color.PURPLE};
	
	ArrayList<Block> blocks = new ArrayList<>();
	
	public TetrisPiece() {		
		this("IJLOSTZ".charAt(new Random(System.nanoTime()).nextInt(6)));		
	}
	
	public TetrisPiece(char name) {
		Random rand = new Random(System.nanoTime());
		
		int col = 0;
		
		Color fill = colors[rand.nextInt(colors.length)];
		
		switch(name) {
			case 'I':
				col = rand.nextInt(10);
				newBlock(0,col, fill);
				newBlock(1,col, fill);
				newBlock(2,col, fill);
				newBlock(3,col, fill);
				break;
			case 'J':
				col = rand.nextInt(9) + 1;
				newBlock(0,col, fill);
				newBlock(1,col, fill);
				newBlock(2,col, fill);
				newBlock(2, col - 1, fill);
				break;
			case 'L':
				col = rand.nextInt(9);
				newBlock(0,col, fill);
				newBlock(1,col, fill);
				newBlock(2,col, fill);
				newBlock(2, col + 1, fill);
				break;
			case 'O':
				col = rand.nextInt(9);
				newBlock(0,col, fill);
				newBlock(1,col, fill);
				newBlock(0,col + 1, fill);
				newBlock(1,col + 1, fill);
				break;
			case 'S':
				col = rand.nextInt(8);
				newBlock(1,col, fill);
				newBlock(0,col + 1, fill);
				newBlock(1,col + 1, fill);
				newBlock(0,col + 2, fill);
				break;
			case 'T':
				col = rand.nextInt(8);
				newBlock(0,col, fill);
				newBlock(0,col + 1, fill);
				newBlock(0,col + 2, fill);
				newBlock(1,col + 1, fill);
				break;
			case 'Z':
				col = rand.nextInt(8);
				newBlock(0,col, fill);
				newBlock(0,col + 1, fill);
				newBlock(1,col + 1, fill);
				newBlock(1,col + 2, fill);
				break;
			default:
				System.out.println("Invalid Polynimo Selection");
				System.exit(0);
		}
	}
	
	public void newBlock(int row, int col, Color fill) {
		blocks.add(new Block(row,col,fill));
	}
	
	
	public void displayOnPane(Pane p) {
		for (Block r : blocks)
			p.getChildren().add(r);
	}
	
	public void moveDown() {
		for (Block b : blocks) {
			b.setY(b.getY() + BOX_SIZE);
		}
	}
	
	public void requestFocus() {
		for (Block b : blocks) {
			b.requestFocus();
		}
	}
	
	public boolean clearPath() {
		for (Block b : blocks) {
			if (b.getY() + b.getHeight() >= Tetris.topOfCol[b.getCol()])
				return false;
		}
		return true;
	}
	
	public void updateColHeight() {
		for (int i = 0; i < 10; ++i) {
			int max = Tetris.topOfCol[i];
			for (Block b : blocks) {
				if (b.getCol() == i && b.getY() < max) {
					max = (int)Math.round(b.getY());
				}
			}
			Tetris.topOfCol[i] = max;
		}
	}

	public void moveLeft() {
		
		boolean canMoveLeft = true;
		
		for (Block b : blocks) {
			if (b.getCol() <= 0) canMoveLeft = false;
		}
		
		
		if (canMoveLeft) {
			for (Block b : blocks) {
				b.setX(b.getX() - BOX_SIZE);
				b.setCol(b.getCol() - 1);
			}
		}
		
	}

	public void moveRight() {
		
		boolean canMoveRight = true;
		
		for (Block b : blocks) {
			if (b.getCol() >= 9) canMoveRight = false;
		}
		
		
		if (canMoveRight) {
			for (Block b : blocks) {
				b.setX(b.getX() + BOX_SIZE);
				b.setCol(b.getCol() + 1);
			}
		}
		
	}
}

package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle{
	
	private int row;
	private int col;

	public Block(int row, int col, Color fill) {
		
		this.row = row;
		this.col = col;
		
		this.setHeight(Tetris.BOX_SIZE);
		this.setWidth(Tetris.BOX_SIZE);
		this.setY(row * Tetris.BOX_SIZE);
		this.setX(col * Tetris.BOX_SIZE);
		this.setFill(fill);
		this.setStroke(Color.BLACK);
	}
	
	public int getRow() {return row;}
	
	public void setRow(int row) {this.row = row;}
	
	public int getCol() {return col;}
	
	public void setCol(int col) {this.col = col;}
}

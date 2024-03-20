package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.TetrisPiece;

public class Tetris extends Application{
	
	final public static int SCREEN_WIDTH = 400;
	final public static int SCREEN_HEIGHT = 600;
	
	final public static int BOX_SIZE = SCREEN_WIDTH / 10;
	
	final public static int[] topOfCol = new int[10];
	
	private Timeline animation;
	private static TetrisPiece active;
	
	public static void play(Pane p) {
		if(active.clearPath()) {
			active.moveDown();
		} else {
			active.updateColHeight();
			
			active = new TetrisPiece();
			active.displayOnPane(p);
		}
	}
	
	
	public static void main(String[] args) {launch(args);}

	@Override
	public void start(Stage arg0) throws Exception {
		
		arg0.setTitle("Tetris");
		
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: grey;");
		
		MediaPlayer player = new MediaPlayer(new Media(getClass().getResource("tetris.mp3").toString()));
		
		player.play();
		
		for (int i = 0; i < 10; ++i) topOfCol[i] = SCREEN_HEIGHT;
		
		active = new TetrisPiece();
		
		pane.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case LEFT:
				active.moveLeft();
				break;
			case RIGHT:
				active.moveRight();
				break;
			case M:
				
				if (player.getStatus()==Status.PAUSED) {
					player.play();
				} else {
					player.pause();
				}
				
			default:
			}
		});
		
		animation = new Timeline(new KeyFrame(Duration.millis(2000), e -> play(pane)));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.setRate(5.0);
		animation.play();
		
		
		active.displayOnPane(pane);
		
		arg0.setScene(new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT));
		arg0.show();
		
		active.requestFocus();
	}

}

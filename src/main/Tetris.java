package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
		
		BorderPane mainMenu = new BorderPane();
		
		
		
		HBox titleBox = new HBox();
		titleBox.setSpacing(5.0);
		
		Text[] titleLetters = {
				new Text("T"),
				new Text("E"),
				new Text("T"),
				new Text("R"),
				new Text("I"),
				new Text("S"),
		};
		
		Color[] titleColors = {
				Color.RED,
				Color.ORANGE,
				Color.YELLOW,
				Color.GREEN,
				Color.BLUE,
				Color.PURPLE
		};
		
		for (int i = 0; i < titleLetters.length; ++i) {
			titleLetters[i].setFont(Font.font("Helvetica",
					FontWeight.BOLD, 70.0));
			titleLetters[i].setFill(titleColors[i]);
			titleLetters[i].setStroke(Color.BLACK);
			titleBox.getChildren().add(titleLetters[i]);
		}
		
		titleBox.setAlignment(Pos.CENTER);
		mainMenu.setTop(titleBox);
		
		//BorderPane.setAlignment(mainMenu, Pos.CENTER);
		mainMenu.setStyle("-fx-background-color: grey;");
		
		VBox mainButtons = new VBox();
		mainButtons.setSpacing(20);
		
		Button start = new Button("START");
		start.setFont(Font.font("Helvetica", 30.0));
		
		Button sound = new Button("SOUND ON");
		sound.setFont(Font.font("Helvetica", 30.0));
		
		mainButtons.getChildren().addAll(start, sound);
		mainButtons.setAlignment(Pos.CENTER);
		mainMenu.setCenter(mainButtons);
		
		Scene menuScene = new Scene(mainMenu, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: grey;");
		
		MediaPlayer player = new MediaPlayer(new Media(getClass().getResource("tetris.mp3").toString()));
		player.pause();
		
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
				
				if (player.getStatus()==Status.PAUSED
					|| player.getStatus()==Status.READY) {
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
		
		Scene gameScene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		active.displayOnPane(pane);
		
		start.setOnAction(e -> {
			arg0.setScene(gameScene);
			animation.play();
		});
		
		sound.setOnAction(e -> {
			System.out.println(player.getStatus());
			if (player.getStatus()==Status.PAUSED
			   || player.getStatus() == Status.READY) {
				player.play();
				sound.setText("SOUND OFF");
			} else {
				player.pause();
				sound.setText("SOUND ON");
			}
		});
		
		arg0.setScene(menuScene);
		arg0.show();
		
		active.requestFocus();
	}

}

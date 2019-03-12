package Minesweeper;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class Minesweeper extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	int size = 5;
	int empty = 22;
	Button smiley;
	Label tilesLeft;
	boolean game = true;
	int[][] locations = {{1,1,1,0,0},
                         {1,9,2,1,1},
                         {1,1,3,9,2},
                         {0,0,2,9,2},
                         {0,0,1,1,1}};	
	
	public void start(Stage stage){
		game = true; 
		BorderPane border = new BorderPane();
		GridPane grid = new GridPane();
		myButton[][] buttons = new myButton[size][size];
		
		Label timeLabel = new Label();
		DoubleProperty time = new SimpleDoubleProperty();
		BooleanProperty going = new SimpleBooleanProperty();
		timeLabel.textProperty().bind(time.asString("%.2f secs"));				

        AnimationTimer timer = new AnimationTimer() {
            private long initialTime ;
            
            @Override
            public void start() {
                initialTime = System.currentTimeMillis();
                going.set(true);
                super.start();
            }
            @Override
            public void stop() {
                going.set(false);
                super.stop();
            }
            @Override
            public void handle(long timestamp) {
                long current = System.currentTimeMillis();
                time.set((current - initialTime) / 1000.0);
            }
        };
        timer.start();										
		
		for(int row = 0; row < size; row++){
			for(int col = 0; col < size; col++){
				int number = locations[row][col];
				buttons[row][col] = new myButton("", number);
				myButton button = buttons[row][col];								
								
				button.setOnMouseClicked(new EventHandler<MouseEvent>(){
					public void handle(MouseEvent event){
						if(game){
							if(number == 9){								
								timer.stop();
								button.setGraphic(button.mine_exp);
								smiley.setGraphic(new ImageView(new Image("file:face2.png")));																
								smiley.setOnMouseClicked(new EventHandler<MouseEvent>(){
									public void handle(MouseEvent event){
										restart(stage, timer);
									}
								});
								game = false;
							}
							else if(button.number != 9){
								empty--;
								tilesLeft.setText(""+empty);								
								if(number == 0) {
									button.setGraphic(button.zero);
									victory();
								}
								else if(number == 1) {
									button.setGraphic(button.one);
									victory();
								}
								else if(number == 2) {
									button.setGraphic(button.two);
									victory();
								}
								else if(number == 3) {
									button.setGraphic(button.three);
									victory();
								}								
							}
						}
					}
					public void victory(){
						if(empty == 0){
							timer.stop();
							smiley.setGraphic(new ImageView(new Image("file:face3.png")));
							smiley.setOnMouseClicked(new EventHandler<MouseEvent>(){
								public void handle(MouseEvent event){
									restart(stage, timer);
								}
							});
							game = false;
						}
					}
				});
				grid.add(button, col, row);
			}
		}

		HBox hb = new HBox();
		hb.setStyle("-fx-background-color: Pink;");
		hb.setSpacing(100);
		hb.setAlignment(Pos.CENTER);
		
		timeLabel.setStyle("-fx-background-color: transparent;");
		timeLabel.setFont(Font.font("Times New Roman", 24));
		
		smiley = new Button();
		smiley.setStyle("-fx-background-color: transparent;");				
		smiley.setGraphic(new ImageView(new Image("file:face1.png")));
		smiley.setPadding(Insets.EMPTY);
				
		tilesLeft = new Label(""+empty);
		tilesLeft.setStyle("-fx-background-color: transparent;");
		tilesLeft.setFont(Font.font("Times New Roman", 24));
		tilesLeft.setPadding(new Insets(0, 0, 0, 70));		
		
		hb.getChildren().addAll(timeLabel, smiley, tilesLeft);
		border.setTop(hb);
		border.setCenter(grid);		
		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.show();
	}
	public void restart(Stage stage, AnimationTimer timer) {
		timer.start();
		empty = 22;
		tilesLeft.setText(""+empty);
		start(stage);
	}
}

class myButton extends Button{
	ImageView zero, one, two, three, mine_exp;
	public int number;	
	
	public myButton(String s, int number){
		super(s);
		setStyle("-fx-background-color: HotPink;");
		setGraphic(new ImageView(new Image("file:grey.png")));
		this.number = number;		
		
		zero = new ImageView(new Image("file:0.png"));
		one = new ImageView(new Image("file:1.png"));
		two = new ImageView(new Image("file:2.png"));
		three = new ImageView(new Image("file:3.png"));
		mine_exp = new ImageView(new Image("file:mine_exp.png"));
		
		zero.setFitWidth(100);
		zero.setFitHeight(100);
		one.setFitWidth(100);
		one.setFitHeight(100);
		two.setFitWidth(100);
		two.setFitHeight(100);
		three.setFitWidth(100);
		three.setFitHeight(100);
		mine_exp.setFitWidth(100);
		mine_exp.setFitHeight(100);
    }
	
}
package joeri.vectorwarrior.techdemo;

import java.util.ArrayList;

import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class VectorWarriorTechMain extends Application {
	
	public final double baseWidth = 1280;
	public final double baseHeight = 720;
	public double width = 500;
	public double height = 500;
	
	Canvas canvas;
	
	@Override
	public void start(Stage stage) throws Exception {
		
		// Create a Canvas
		canvas = new Canvas(baseWidth, baseHeight);
		
		// Put the Canvas on a Scene
		GridPane gp = new GridPane();
		
		
		gp.add(canvas, 0, 0);
		Scene scene = new Scene(gp);
		
		// Add the Scene to the Stage in order to display it
		stage.setScene(scene);
		
		stage.sizeToScene();
		stage.setTitle("Vector Warrior Tech Demo - By Joeri");
		stage.show();
		
		
		// Load Entities
		ArrayList<Entity> entities = new ArrayList<Entity>();
		
		// adding some stuff to the scene
		int tempCount = 1;
		for (double i = 0; i < baseWidth; i += 10) {
			Rectangle rect = new Rectangle(i/2, i/2, Color.CYAN);
			//entities.add(new Entity(rect.snapshot(null, null), i, 0));
			
			//System.out.println("squares: " + tempCount);
			++tempCount;
		}
		/*
		Circle circle = new Circle(200, Color.YELLOW);
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		entities.add(new Entity(circle.snapshot(params, null), 150, 300));
		*/
		
		// adding the player
		entities.add(new Player("player", 200, 200));
		
		// adding other entities
		//entities.add(new Entity(null, 500, 400));
		
		// Prepare GameWorld
		GameWorld gameWorld = new GameWorld(entities, 1280, 720);
		
		// Create GameManager
		GameManager gameManager = new GameManager(gameWorld, canvas);
		
		// Set the renderwidth at launch
		gameManager.renderWidth = scene.getWidth();
		gameManager.scaleX = scene.getWidth() / baseWidth;
		
		gameManager.renderHeight = scene.getHeight();
		gameManager.scaleY = scene.getHeight() / baseHeight;
		
		
		// Override the Window Listener in order to scale the image
		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double scaleX = newValue.doubleValue() / baseWidth;
				width = baseWidth * scaleX;
				
				gameManager.scaleX = scaleX;
				gameManager.renderWidth = width;
				
				canvas.setWidth(width);
				
				gameManager.input.clear();
				
				//canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				
				//canvas.getGraphicsContext2D().drawImage(svgTest, 0, 0, width, height);
			}
		});
		
		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				double scaleY = newValue.doubleValue() / baseHeight;
				height = baseHeight * scaleY;
				
				gameManager.scaleY = scaleY;
				gameManager.renderHeight = height;
				
				canvas.setHeight(height);
				
				gameManager.input.clear();
				
				//canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				
				//canvas.getGraphicsContext2D().drawImage(svgTest, 0, 0, width, height);
			}
		});
		
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();

				// only add once... prevent duplicates
				if (!gameManager.input.contains(code))
					gameManager.input.add(code);
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				String code = e.getCode().toString();
				gameManager.input.remove(code);
			}
		});
		
		
		
		// Run the game
		gameManager.runAndPlayGame();
		
	}
	
	
	
	public static void main(String[] args) {
		SvgImageLoaderFactory.install();
		Application.launch(args);
	}

}

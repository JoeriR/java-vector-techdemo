package joeri.vectorwarrior.techdemo;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GameManager {
	public GameWorld gameWorld;
	public ArrayList<String> input = new ArrayList<String>();
	
	public Canvas canvas;
	
	public AnimationTimer gameloop;
	
	public double renderWidth = 1280;
	public double renderHeight = 720;
	
	public double scaleX = 1.0;
	public double scaleY = 1.0;
	
	public boolean drawPlayerHitBoxes = true;
	
	public GameManager(GameWorld gameWorld, Canvas canvas) {
		this.gameWorld = gameWorld;
		this.canvas = canvas;
	}
	
	public void runAndPlayGame() {
		if (gameloop == null) {
			gameloop = new AnimationTimer() {
				
				GraphicsContext gc = canvas.getGraphicsContext2D();
				
				SimpleFrameRateMeter frameRateMeter = new SimpleFrameRateMeter();
				
				
				
				@Override
				public void handle(long currentTime) {
					
					// Calculate FPS (source: https://stackoverflow.com/a/28287949
					double frameRate = frameRateMeter.handle(currentTime);
					
					// handle input
					for (String s : input) {
						switch (s) {
						// player KeyBindings
						case "W":
							if (gameWorld.player.y > 0) {
								gameWorld.player.y -= 5;
								
								gameWorld.player.setAnimation("idle", false);
							}
							break;
							
						case "S":
							if (gameWorld.player.y + gameWorld.player.defaultImage.getHeight() < gameWorld.gameHeight)
								gameWorld.player.y += 5;
							break;
							
						case "D": 
							if (gameWorld.player.x + gameWorld.player.defaultImage.getWidth() < gameWorld.gameWidth) {
								gameWorld.player.x += 5;
								
								if (gameWorld.player.currentAnimation != "walk") {
									gameWorld.player.setAnimation("walk", false);
								}
							}
							break;
							
						case "A":
							if (gameWorld.player.x > 0) {
								gameWorld.player.x -= 5;
								
								if (gameWorld.player.currentAnimation != "walk") {
									gameWorld.player.setAnimation("walk", true);
								}
							}
							break;
						}
					}
					
					// Play the idle animation when no input is given
					if (input.isEmpty()) {
						gameWorld.player.setAnimation("idle", false);
					}
					
					
					// clear the screen
					gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
					
					gc.setFill(Color.GRAY);
					gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
					
					gc.setStroke(Color.RED);
					
					// Render the screen
					for (Entity entity : gameWorld.entities) {
						Image image = entity.getImage(currentTime, 1);
						
						// Rendering a mirrored version, source: https://stackoverflow.com/a/41376249
						if (entity.isMirrored) {
							gc.drawImage( 
									image, 
									0, 0,  image.getWidth(), image.getHeight(), // source image
									entity.x * scaleX + image.getWidth() * scaleX, entity.y * scaleY, -image.getWidth() * scaleX, image.getHeight() * scaleY); // destination image with scaling
						}
						else {
							gc.drawImage(image, entity.x * scaleX, entity.y * scaleY, image.getWidth() * scaleX, image.getHeight() * scaleY);
						}
						
						if (drawPlayerHitBoxes && entity instanceof Player) {
							gc.strokeRect(entity.x * scaleX, entity.y * scaleY, image.getWidth() * scaleX, image.getHeight() * scaleY);
							
						}
					}
					
					gc.strokeText("" + frameRate, 20, 20);
					
				}
				
			};
			
			// After loading and preparing everything, we start the game here with this single line of code
			gameloop.start();
			
		}
	}
	
}

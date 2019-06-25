package joeri.vectorwarrior.techdemo;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import joeri.vectorwarrior.techdemo.exceptions.BadSpawnException;

public class Player extends Entity {

	public Player(String folderName, double x, double y) throws BadSpawnException {
		super(folderName, x, y);

		Image imga = new Image("file:./svg/player/idle/idle.svg");
		WritableImage testimg = new WritableImage(imga.getPixelReader(), (int) imga.getWidth(), (int) imga.getHeight());

		PixelReader reader = testimg.getPixelReader();
		PixelWriter writer = testimg.getPixelWriter();

		int startWidth = (int) testimg.getWidth();
		int endWidth = 0;
		int startHeight = (int) testimg.getHeight();
		int endHeight = 0;
		
		// WARNING: slightly broken code ahead.
		
		// calculate startWidth and endWith
		for (int j = 0; j < testimg.getHeight(); ++j) {

			boolean foundColorInLine = false;

			for (int i = 0; i < testimg.getWidth(); ++i) {
				Color color = reader.getColor(i, j);

				if (!color.equals(Color.TRANSPARENT)) { // Colored-in pixel found
					if (!foundColorInLine && i < startWidth) {
						startWidth = i;
						foundColorInLine = true;
					}
					else if (foundColorInLine && i > endWidth) { //todo: remove else statement
						endWidth = i;
					}
				}

			}
		}
		
		// calculate startHeight and endHeight
		for (int i = 0; i < testimg.getWidth(); ++i) {

			boolean foundColorInLine = false;

			for (int j = 0; j < testimg.getHeight(); ++j) {
				Color color = reader.getColor(i, j);

				if (!color.equals(Color.TRANSPARENT)) { // Colored-in pixel found
					if (!foundColorInLine && i < startHeight) {
						startHeight = i;
						foundColorInLine = true;
					}
					else if (foundColorInLine && j > endHeight) { //todo: remove else statement
						endHeight = j;
					}
				}

			}
		}
		
		Image imageWithoutSpaceTest = new WritableImage(reader, startWidth, startHeight, endWidth, endHeight);
		/*
		Image[] idle = new Image[] { 
				//imageWithoutSpaceTest 
				imga
				};

		Image[] walk = new Image[] { new Image("file:./svg/player/player_walk_1.svg"),
				new Image("file:./svg/player/player_walk_2.svg"), new Image("file:./svg/player/player_walk_3.svg"),
				new Image("file:./svg/player/player_walk_4.svg"), new Image("file:./svg/player/player_walk_5.svg"),
				new Image("file:./svg/player/player_walk_6.svg") };

		this.animations.put("walk", walk);
		this.animations.put("idle", idle);
*/
	}

}

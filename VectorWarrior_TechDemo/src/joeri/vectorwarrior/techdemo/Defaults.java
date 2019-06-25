package joeri.vectorwarrior.techdemo;

import java.io.ByteArrayInputStream;

import javafx.scene.image.Image;

/**
 * This class contains static default objects which are used when loading something of the object type fails.
 * For example: if loading an image fails then this program can call Defaults.image to still get an image.
 * 
 * Everything in this file is hardcoded and doesn't require loading in order to prevent errors, IOExceptions etc.
 * 
 * @author Joeri
 *
 */
public class Defaults {
	
	
	
	public static Image image = (
			new Image(
					new ByteArrayInputStream(
							new String ("<svg width=\"50\" height=\"50\" xmlns=\"http://www.w3.org/2000/svg\">"
									+ "<rect x=\"0\" y=\"0\" width=\"100%\" height=\"100%\" stroke-width=\"5px\" stroke=\"black\" fill=\"orange\"/>"
									+ "<text x=\"50%\" y=\"50%\" font-size=\"10\" text-anchor=\"middle\" fill=\"black\">DEFAULT</text>"
									+ "</svg>"
								).getBytes()
							)
					, 50, 50, true, true)
	);
	
}

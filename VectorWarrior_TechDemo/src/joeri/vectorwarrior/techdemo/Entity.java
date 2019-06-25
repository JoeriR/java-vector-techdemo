package joeri.vectorwarrior.techdemo;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.stream.Stream;

import javafx.scene.image.Image;
import joeri.vectorwarrior.techdemo.exceptions.BadSpawnException;

public class Entity {
	public Image defaultImage;
	public double x;
	public double y;
	
	public String currentAnimation;
	public Image currentImage;
	public long currentFrameStartTime;
	public int currentFrame;
	
	public boolean isMirrored;
	
	public Hashtable<String, Image[]> animations;
	
	public Entity (String folderName, double x, double y) throws BadSpawnException {
		
		if (x < 0 || y < 0)
			throw new BadSpawnException("m8, you're trying to spawn an Entity on negative coordinates \nPosition = (" + x + ", " + y + ") \nType = " + this.getClass());
		
		this.x = x;
		this.y = y;
		
		if (folderName == null || folderName.equals("")) {
			this.defaultImage = Defaults.image;
			
			return;
		}
		
		this.currentAnimation = null;
		this.currentImage = null;
		this.currentFrameStartTime = 0;
		this.currentFrame = 0;
		
		this.isMirrored = false;
		
		this.animations = new Hashtable<String, Image[]>();
		
		
		
		// Dynamic loading of animations by 
		ArrayList<String> animationNames = new ArrayList<String>();
		
		// new Image("file:./svg/player/player_walk_1.svg")
		
		File entityFolder = new File("svg/" + folderName);
		String[] directories = entityFolder.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		
		animationNames.addAll( Arrays.asList(directories));
		
		animationNames.forEach(animationName -> {
			File animationFolder = new File(String.format("svg/%s/%s", folderName, animationName));
			
			String[] animationFrames = animationFolder.list();
			Image[] frameImages = new Image[animationFrames.length];
			
			Arrays.sort(animationFrames);
			
			for (int i = 0; i < animationFrames.length; ++i) {
				String frameLocation = String.format("file:svg/%s/%s/%s", folderName, animationName, animationFrames[i]);
				frameImages[i] = new Image(frameLocation);
			}
			
			// add the animation to the hashmap
			this.animations.put(animationName, frameImages);
			});
		
		//folderNames.forEach(e -> System.out.println(e));
		
		
		// Set defaultImage
		if (animations.get("idle") != null && animations.get("idle")[0] != null) {
			this.defaultImage = animations.get("idle")[0];
		}
		else {
			this.defaultImage = Defaults.image;
		}
		
	}
	
	public Image getImage(long currentTime, double timeScale) {
		if (currentAnimation == null || !animations.containsKey(currentAnimation))
			return defaultImage;
		
		if (currentTime > currentFrameStartTime + (100000000 * timeScale)) {
			
			Image[] animation = animations.get(currentAnimation);
			currentImage = animation[currentFrame];

			if (currentFrame < animation.length - 1)
				++currentFrame;
			else
				currentFrame = 0;
			
			currentFrameStartTime = currentTime;
		}
		return currentImage;
	}
	
	public void setAnimation(String animationName, boolean isMirrored) {
		if (animations.containsKey(animationName)) {
			currentAnimation = animationName;
			currentFrame = 0;
			this.isMirrored = isMirrored;
		}
		else {
			System.out.println("Mate, you are trying to set an animation that doesn't exist. animationName = " + animationName);
		}
	}
	
}

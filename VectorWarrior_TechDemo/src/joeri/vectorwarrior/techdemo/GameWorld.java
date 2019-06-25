package joeri.vectorwarrior.techdemo;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {
	
	public List<Entity> entities = new ArrayList<Entity>();
	
	public Player player;
	
	public double gameWidth;
	public double gameHeight;
	
	public GameWorld(List<Entity> entities, double gameWidth, double gameHeight) {
		this.entities = entities;
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		
		// find and assign the player object
		entities.forEach(entity -> {
			if (entity instanceof Player) {
				this.player = (Player) entity;
				System.out.println("player object found");
			}
		});
		
	}
	
}

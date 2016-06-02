package engine.content;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Content {
    	
    	public static int PANEL_WIDTH = 320, PANEL_HEIGHT = 240;
    	public static BufferedImage SELECT = loadImage("/hud/hamburger.png");
    	public static BufferedImage COMBAT_MENU = loadImage("/backgrounds/parchment.jpg");
    	public static BufferedImage CAVE = loadImage("/backgrounds/cave.png");
    	public static BufferedImage DEAD = loadImage("/hud/dead.png");
    	public static BufferedImage SLEEPING = loadImage("/hud/sleeping.png");
    	public static BufferedImage BACKGROUND_FOOD = loadImage("/backgrounds/food.png");
    	
    	public static BufferedImage GRAVEL_TILE = loadImage("/tiles/gravelTile.jpg");
    	public static BufferedImage WALL_TILE = loadImage("/tiles/wallTile.jpg");
    	public static BufferedImage MONSTER_TILE = loadImage("/tiles/monsterTile.jpg");
    	public static int TILE_SIZE = 25, GRAVEL_TILE_INDEX = 0, WALL_TILE_INDEX = 1, MONSTER_TILE_INDEX = 2;
    	public static int MAX_TILES = 3;
    	
    	public static BufferedImage[][] HERO = load("/sprites/player/blueLady.png", 64, 64);
    	public static int HERO_UP = 0, HERO_HORIZONTAL = 1, HERO_DOWN = 2, HERO_WIDTH = 32, HERO_HEIGHT = 32;
    	
    	public static BufferedImage[][] MONSTER = load("/sprites/monsters/monster-lizard.png", 80, 56);
    	public static int MONSTER_UP = 6, MONSTER_DOWN = 4, MONSTER_HORIZONTAL = 2, MONSTER_WIDTH = 80, MOSNTER_HEIGHT = 56;
    	
    	public static String LEVEL_1_PATH = "/maps/level1.map";
    	    	    	
	public static BufferedImage[][] load(String s, int w, int h) {
		BufferedImage[][] ret;
		try {
			BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(s));
			int width = spritesheet.getWidth() / w;
			int height = spritesheet.getHeight() / h;
			ret = new BufferedImage[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();	
			System.exit(0);
		}
		return null;
	}
	
	public static BufferedImage loadImage(String path){
	    
	    BufferedImage image = null;
	    try{
		image = ImageIO.read(Content.class.getResourceAsStream(path));		
	    }
	    catch(Exception e){
		e.printStackTrace();
		System.exit(0);
	    }
	    return image;
	    
	}
	
	
}

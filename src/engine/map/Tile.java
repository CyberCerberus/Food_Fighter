package engine.map;

import java.awt.image.BufferedImage;

public class Tile {
    public static final int GRAVEL = 0, WALL = 1, MONSTER = 2, MAX_TYPES = 3;
    private BufferedImage image;
    private int type;
    
    public Tile(BufferedImage image, int type){
	this.image = image;
	if(type < 0 || type >= MAX_TYPES){
	    throw new IllegalArgumentException("Invalid type number.");
	}
	this.type = type;
    }
    
    public BufferedImage getImage(){return image;}
    public int getType(){return type;}
}

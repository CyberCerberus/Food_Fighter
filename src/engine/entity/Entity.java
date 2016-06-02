package engine.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import engine.content.animation.ImageAnimation;
import engine.map.Map;

public abstract class Entity {

    public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
    private Map map;
    private int xmap, ymap;
    private int x, y, dx, dy, tempx, tempy;
    protected int width, height;
    private int facing;
    private BufferedImage[][] frames;
    private ImageAnimation animation;
    private boolean topLeft, topRight, botRight, botLeft;
    private int currentAction;
    
    Rectangle topLeftr, topRightr, botLeftr, botRightr;

    public Entity(Map map, int width, int height, BufferedImage[]...frames){

	this.map = map;
	facing = RIGHT;
	if(map == null){
	    xmap = 0;
	    ymap = 0;
	}
	else{
	    xmap = map.getx();
	    ymap = map.gety();
	}
	
	x = 50; y = 50; dx = 0; dy = 0;
	tempx = x; tempy = y;
	this.width = width;
	this.height = height;
	this.frames = frames;
	
	
	
    }
    
    public Entity(int width, int height, int x, int y, BufferedImage[]...frames){

	facing = RIGHT;
	this.x = x; this.y = y; dx = 0; dy = 0;
	tempx = x; tempy = y;
	this.width = width;
	this.height = height;
	this.frames = frames;
	xmap = 0; ymap = 0;
	
	
	
    }
    
    public void setAnimation(ImageAnimation animation){
	this.animation = animation;
	animation.setDelay(100);
    }
    
    public void setVector(int dx, int dy){
	this.dx = dx; this.dy = dy;
    }
    
    public void setPosition(int x, int y){
	this.x = x; this.y = y;
    }
           
    public void checkCorners(int x, int y){

	int tileSize = map.getTileSize();	
		
	int leftTile = (x - width / 2) / tileSize;
	int rightTile = (x + width / 2 - 1) / tileSize;
	int topTile = (y - height / 2) / tileSize;
	int bottomTile = (y + height / 2 - 1) / tileSize;
	
	if(topTile < 0 || bottomTile >= map.getNumRows() ||
		leftTile < 0 || rightTile >= map.getNumCols()) {
		topLeft = topRight = botLeft = botRight = false;
		return;
	}
	
	botLeftr = new Rectangle(bottomTile, leftTile, 25, 25);
	botRightr = new Rectangle(bottomTile, rightTile, 25, 25);
	topRightr = new Rectangle(topTile, rightTile, 25, 25);
	topLeftr = new Rectangle(topTile, leftTile, 25, 25);

	topLeft = map.collides(topTile, leftTile);
	topRight = map.collides(topTile, rightTile);
	botLeft = map.collides(bottomTile, leftTile);
	botRight = map.collides(bottomTile, rightTile);
    }
    public void calculateTileMapCollision(){
	
	int tileSize = map.getTileSize();
	
	int curCol = x/tileSize;
	int curRow = y/tileSize;
	
	int xdest = x + dx;
	int ydest = y + dy;
	
	tempx = x;
	tempy = y;
	
	checkCorners(x, ydest);
	 if(dy < 0){
	     if(topRight || topLeft){
		 dy = 0;
		 tempy = curRow * tileSize + height / 2;
		 
	     }
	     else{
		 tempy += dy;
	     }
	 }
	 if(dy > 0){
	     if(botRight || botLeft){
		 dy = 0;
		 tempy = (curRow + 1) * tileSize - height / 2;
	     }
	     else{
		 tempy += dy;
	     }
	 }
	 checkCorners(xdest, y);
	 if(dx < 0){
	     if(topLeft || botLeft){
		 dx = 0;
		 tempx = curCol * tileSize + width / 2;
	     }
	     else{
		 tempx += dx;
	     }
	 }
	 if(dx > 0){
	     if(topRight || botRight){
		 dx = 0;
		 tempx = (curCol + 1) * tileSize - width / 2;
	     }
	     else{
		 tempx += dx;
	     }
	 }
	 
	 	
    }
    
    public void setMapPosition(){
	xmap = map.getx();
	ymap = map.gety();
    }
    
    public int getRelativex(){
	return x + xmap - width / 2;
    }
    
    public int getRelativey(){
	return y + ymap - height;
    }
         
    public void draw(Graphics2D g){
		
	if(animation != null){	    
	    if(map == null){
		animation.setCoordinates(x, y);
		if(facing == LEFT){
		    animation.draw(g, false);
		}
		else{
		    animation.draw(g, true);
		}
	    }
	    else{
		setMapPosition();
		animation.setCoordinates(x + xmap - width / 2, y + ymap - height / 2);
		if(facing == LEFT){
		    animation.draw(g, false);
		}
		else{
		    animation.draw(g, true);
		}
	    }
	}		
    }
    
    
    public int getDx(){return dx;}
    public int getDy(){return dy;}
    public int getTempx(){return tempx;}
    public int getTempy(){return tempy;}
    public int getx(){return x;}
    public int gety(){return y;}
    public ImageAnimation getAnimation(){return animation;}
    public void setFacing(int f){facing = f;}
    public int getFacing(){return facing;}
    public BufferedImage[] getFrames(int f){return frames[f];}
    public Map getMap(){return map;}
    public int getCurrentAnimation(){return currentAction;}
    public void setCurrentAnimation(int i){currentAction = i;}
    public void setMap(Map m){this.map = m;}
    






}

package gameCore;

import java.awt.Graphics2D;

import main.Animation;
import main.Content;
import main.ImageAnimation;

public class Navigator{
    private ImageAnimation animation;
    private int x, y, dx, dy, nextx, nexty;
    private boolean up, down, left, right;
    private Room room;
    private int moveRoom;
    private boolean facingRight;
    
    public Navigator(int x, int y){
	this.x = x;
	this.y = y;
	nextx = x;
	nexty = y;
	dx = 0;
	dy = 0;
	moveRoom = -1;
	facingRight = true;
	animation = new ImageAnimation(Content.HERO_WIDTH, Content.HERO_HEIGHT, Content.HERO_BALD[Content.HERO_HORIZONTAL]);
	animation.setDelay(100);
    }
    
    public int moveRoom(){
	return moveRoom;
    }
    public void setVector(int dx, int dy){
	this.dx = dx;
	this.dy = dy;
    }
    
    public void setPosition(int x, int y){
	this.x = x;
	this.y = y;
    }
    
    public void moveUp(boolean keyPressed){
	up = keyPressed;
    }
    
    public void moveDown(boolean keyPressed){
	down = keyPressed;
    }
    
    public void moveRight(boolean keyPressed){
	right = keyPressed;
    }
    
    public void moveLeft(boolean keyPressed){
	left = keyPressed;
    }
        
    public int[] getPosition(){
	return new int[]{x, y};
    }
    
    public int[] getVector(){
	return new int[]{dx, dy};
    }
    
    public void setRoom(Room r){
	this.room = r;
    }
    
    public void update(){
	animation.update();
	if(!room.inCombat()){
	    detectCollision();
	    x = nextx;
	    y = nexty;
	}
	else{
	    dx = 0;
	    dy = 0;
	}
	
    }
    
    public boolean inCombat(){
	return room.inCombat();
    }
    
    public void moveRoom(int m){
	moveRoom = m;
    }
    private void detectCollision(){
	
	if(!room.getMonster().isDead()){
	    moveRoom = -1;
	}
	if(up){
	    animation.setFrames(Content.HERO_BALD[Content.HERO_UP]);
	    animation.setDelay(10);
	    dy = -1;
	    nexty += dy;
	    if(room.getCollisionType(nexty / 20, nextx / 20) == 1 ||
		    room.getCollisionType(nexty / 20, nextx / 20) >= 2){
		dx = 0;
		dy = 0;	
		if(room.getExplored() && room.getCollisionType(nexty / 20, nextx / 20) == 3){
		   moveRoom = 3;
		   nexty = 200;
		}
		else{
		    nexty = y;
		}
		
	    }	    
	}
	else if(down){
	    animation.setFrames(Content.HERO_BALD[Content.HERO_DOWN]);
	    animation.setDelay(10);
	    dy = 1;
	    nexty += dy;
	    if(room.getCollisionType(nexty / 20, nextx / 20) == 1 ||
		    room.getCollisionType(nexty / 20, nextx / 20) >= 2){
		dx = 0;
		dy = 0;	
		
		if(room.getExplored() && room.getCollisionType(nexty / 20, nextx / 20) == 5){
		   moveRoom = 5;
		   nexty = 40;
		}
		else{
		    nexty = y;
		}
	    }
	}
	else if(right){
	    facingRight = true;
	    animation.setFrames(Content.HERO_BALD[Content.HERO_HORIZONTAL]);
	    animation.setDelay(10);
	    dx = 1;
	    nextx += dx;
	    if(room.getCollisionType(nexty / 20, nextx / 20) == 1 ||
		    room.getCollisionType(nexty / 20, nextx / 20) >= 2){
		dx = 0;
		dy = 0;	
		
		if(room.getExplored() && room.getCollisionType(nexty / 20, nextx / 20) == 4){
		   moveRoom = 4;
		   nextx = 40;
		}
		else{
		    nextx = x;
		}
	    }
	}
	else if(left){
	    facingRight = false;
	    animation.setFrames(Content.HERO_BALD[Content.HERO_HORIZONTAL]);
	    animation.setDelay(10);
	    dx = -1;
	    nextx += dx;	   
	    if(room.getCollisionType(nexty / 20, nextx / 20) == 1 ||
		    room.getCollisionType(nexty / 20, nextx / 20) >= 2){		
		dx = 0;
		dy = 0;	
		
		if(room.getExplored() && room.getCollisionType(nexty / 20, nextx / 20) == 2){
		   moveRoom = 2;
		   nextx = 280;
		}
		else{
		    nextx = x;
		}
		
	    }
	}
	else{
	    animation.setDelay(100);
	}

    }
    
    public void draw(Graphics2D g){
	int width = Content.HERO_WIDTH;
	int height = Content.HERO_HEIGHT;
	int x = getPosition()[0];
	int y = getPosition()[1];
	
	if(facingRight){
	    g.drawImage(animation.getImage(), (x - width/2) + width, y - height/2, -width, height, null);
	}
	else{
	    g.drawImage(animation.getImage(), x - width/2, y - height/2, width, height, null);
	}
	    
    }


}

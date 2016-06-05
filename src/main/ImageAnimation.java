package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageAnimation extends Animation{
    
    private BufferedImage[] frames;
    private int width, height;
    private boolean playedOnce;

    public ImageAnimation(int width, int height, BufferedImage...frames){
	this.frames = frames;	
	this.width = width;
	this.height = height;
	playedOnce = false;
    }
    
    @Override
    public void update() {
	
	if(delay != -1){
	    count++;
	    
	    if(count >= delay){
		cur++;
		count = 0;
	    }
	    if(cur == frames.length){
		cur = 0;
		playedOnce = true;
	    }	    
	}
	
    }
    
    public void draw(Graphics2D g, boolean flip) {
	if(flip) g.drawImage(frames[cur], x + width, y, -width, height, null);
	else g.drawImage(frames[cur], x, y, width, height, null);		
    }
    
    public BufferedImage getImage(){
	return frames[cur];
    }
    
    public void setFrame(int i){cur = i;}
    public boolean playedOnce(){return playedOnce;}
    public void setDimensions(int w, int h){width = w; height = h;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public void setFrames(BufferedImage[] frames){this.frames = frames;}

}

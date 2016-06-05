package main;

public abstract class Animation {
    
    protected int cur, count, delay;
    protected int x, y;
    
    public Animation(){	
	cur = 0;
	count = 0;
	delay = 5;
	x = 0;
	y = 0;
    }
    public void setDelay(int d) {
	delay = d;	
    }   
        
    public void setCoordinates(int x, int y){
	this.x = x; this.y = y;
    }
   
    public int getx(){return x;}  
    public int gety(){return y;}
    public abstract void update();
}

package gameCore;

import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

public abstract class Room implements Observer{
	private boolean[] walls;
	
	private boolean explored;
	private Observable map;
	
	public Room() {
	    	
		walls = new boolean[4];
		explored = false;
		
		
		
	}	
	
	public abstract boolean end();
	public abstract int[] getItem();
	public abstract boolean triggerEvent(Party p) throws GameOverException;
	public abstract boolean discovered(int c, int r);
	public abstract Monster getMonster();
	public abstract boolean inCombat();
	public abstract void setCombat(boolean c);
	public abstract int getCollisionType(int c, int r);
	public abstract void draw(Graphics2D g);
	public abstract String getDescript();
	public boolean[] getWalls(){
	    return walls;
	}
	
	protected void setWalls(boolean[] w) {
		for(int i = 0; i < 4; i++) {
			this.walls[i] = w[i];
		}
	}
	
	public void setObservable(Observable o){
	    this.map = o;
	    map.addObserver(this);
	    
	}
	
	public void removeObservable(){
	    map.deleteObserver(this);
	}
	void explored(boolean b) {
		explored = b;
	}
	public boolean getExplored() {
		return explored;
	}
	
	//0 up, 1 down, 2 left, 3 right
	public boolean canMove(int d) {
		if(!explored) {
			System.out.println("Room has not been explored!");
			return false;
		}
        if(!walls[d])
        	System.out.println("There is a wall there");
        return walls[d];
	}
	@Override
	public String toString() {
		String ret = "Layout:\n"
				+ " ";
		if(!walls[0]) {
			ret += "_";
		}
		ret+= "\n";
		if(!walls[2]) {
			ret += "|";
		}
		else {
			ret += " ";
		}
		
		if(!walls[1]) {
			ret += "_";
		}
		else {
			ret += " ";
		}
		
		if(!walls[3]) {
			ret += "|";
		}
		
		return ret;
	}
	
	
	
}

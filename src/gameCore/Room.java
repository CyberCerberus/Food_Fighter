package gameCore;

public abstract class Room {
	private boolean[] walls;
	private boolean explored;
	
	public Room() {
		walls = new boolean[4];
		explored = false;
	}	
	
	public abstract boolean triggerEvent(Party p) throws GameOverException;
	public abstract void draw();
	public abstract String getDescript();
	
	protected void setWalls(boolean[] w) {
		for(int i = 0; i < 4; i++) {
			this.walls[i] = w[i];
		}
	}
	
	void explored() {
		explored = true;
	}
	boolean getExplored() {
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

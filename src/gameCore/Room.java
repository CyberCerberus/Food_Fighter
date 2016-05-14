package gameCore;

public abstract class Room {
	private boolean[] walls;
	private boolean explored;
	
	public Room() {
		walls = new boolean[4];
		explored = false;
	}	
	
	public abstract boolean triggerEvent(Party p);
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
	
}

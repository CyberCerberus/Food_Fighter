package gameCore;

public class Map {
	private Room[][] rooms;
	private int level;
	private int startx;
	private int starty;
	
	public Map(int l, int h, int w) {
		level = l;
		rooms = new Room[h][w];
	}
	
	public void draw() {
		
	}
	
	void setStart(int[] xy) {
		startx = xy[0];
		starty = xy[1];
	}
	
	public int[] start(){
		int[] i = {startx, starty};
		return i;
	}
	
	public Room getRoom(int x, int y) {
		return rooms[x][y];
	}
	
	void addRoom(int x, int y, Room r) {
		rooms[x][y] = r;
	}
	
	public int getLevel() {
		return level;
	}
}

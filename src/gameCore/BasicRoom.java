package gameCore;

public class BasicRoom extends Room {
	private int item;
	private int itemnum;
	private int mon;
	private int monnum;
	private boolean end;
	
	public BasicRoom(boolean[] walls) {
		//empty room
		item = -1;
		itemnum = 0;
		mon = -1;
		monnum = 0;
		end = false;
		super.setWalls(walls);
	}
	
	public BasicRoom(boolean[] walls, boolean e) {
		//empty room
		item = -1;
		itemnum = 0;
		mon = -1;
		monnum = 0;
		end = e;
		super.setWalls(walls);
	}
	
	public BasicRoom(int i, int in, int m, int mn, boolean[] walls, boolean end) {
		super();
		item = i;
		itemnum = in;
		mon = m;
		monnum = mn;
		this.end = end;
		super.setWalls(walls);
	}
	
	@Override
	public boolean triggerEvent(Party p) {
		System.out.println("HELLO FROM THIS ROOM");
		//battle(p, mon, monnum);
		//reward(p, item, itemnum);
		super.explored();
		return end;
	}

	@Override
	public void draw() {
		//white box
	}

}

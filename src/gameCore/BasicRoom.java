package gameCore;

public class BasicRoom extends Room {
	private int item;
	private int itemnum;
	private int multi;
	private int mon1;
	private int mon2;
	private int mon3;
	private int mon4;
	private boolean end;
	
	public BasicRoom(boolean[] walls) {
		//empty room
		item = -1;
		itemnum = 0;
		mon1 = -1;
		mon2 = -1;
		mon3 = -1;
		mon4 = -1;
		end = false;
		super.setWalls(walls);
	}
	
	public BasicRoom(boolean[] walls, boolean e) {
		//empty room
		item = -1;
		itemnum = 0;
		mon1 = -1;
		mon2 = -1;
		mon3 = -1;
		mon4 = -1;
		end = e;
		super.setWalls(walls);
	}
	
	public BasicRoom(int i, int in, int multi, int m, boolean[] walls, boolean end) {
		super();
		item = i;
		itemnum = in;
		mon1 = m;
		mon2 = -1;
		mon3 = -1;
		mon4 = -1;
		this.end = end;
		super.setWalls(walls);
	}
	
	@Override
	public boolean triggerEvent(Party p) {
		System.out.println("HELLO FROM THIS ROOM");
		//Battle.fight(p, Factory.monsterFactory(mon));
		//p.reward(item, itemnum);
		super.explored();
		return end;
	}

	@Override
	public void draw() {
		//white box
	}
	
	@Override
	public String getDescript() {
		return "A standard room for testing purposes";
	}


}

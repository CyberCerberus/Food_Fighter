package gameCore;

public class BasicRoom extends Room {
	private int item;
	private int itemnum;
	private int multi;
	private int mon1;
	private boolean end;
	
	public BasicRoom(boolean[] walls) {
		//empty room
		item = -1;
		itemnum = 0;
		mon1 = -1;
		end = false;
		super.setWalls(walls);
	}
	
	public BasicRoom(boolean[] walls, boolean e) {
		//empty room
		item = -1;
		itemnum = 0;
		mon1 = -1;
		end = e;
		super.setWalls(walls);
	}
	
	public BasicRoom(int i, int in, int multi, int m, boolean[] walls, boolean end) {
		super();
		item = i;
		itemnum = in;
		this.multi = multi;
		mon1 = m;
		this.end = end;
		super.setWalls(walls);
	}
	
	@Override
	public boolean triggerEvent(Party p) throws GameOverException {
		if(!super.getExplored()) {
			System.out.println("You encountered Monsters!");
			Battle.fight(p, Factory.monsterFactory(mon1, multi));
			System.out.println("You found some items!");
			p.reward(item, itemnum);
			super.explored();
			return end;
		}
		else {
			System.out.println("The room is empty");
			return end;
		}
	}

	@Override
	public void draw() {
		//white box
	}
	
	@Override
	public String getDescript() {
		String ret = "A standard room for testing purposes\n";
		ret += super.toString();
		return ret;
	}


}

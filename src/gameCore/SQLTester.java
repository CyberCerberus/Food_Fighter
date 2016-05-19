package gameCore;



public class SQLTester {

	public static void main(String[] args) throws GameOverException {
		SQL.setup();
		System.out.println(SQL.getItemIndex("Test Item"));
		System.out.println(SQL.getItemDescr("Test Item"));
		
		int [] temp = {0};
		String [] stemp = SQL.getAllItems(temp);
		for(String s: stemp) {
			System.out.println(s);
		}
		Party p = new Party();
		Character h = Factory.heroFactory("Johny", 0, p);
		p.addHero((Hero)h);
		p.addHero(Factory.heroFactory("Garry", 0, p));
		p.addHero(Factory.heroFactory("Bobby", 0, p));
		p.addHero(Factory.heroFactory("Poppy", 0, p));

		Character[] m = {Factory.monsterFactory(0, 1)};
		for(Character c: m) {
			System.out.println(c);
		}
		Action a = Factory.itemFactory(0, h, m);
		a.takeAction();
		
		Map testMap = Factory.mapFactory(0);
		Cursor c = new Cursor(testMap, p);
		c.playMap();
	}

}

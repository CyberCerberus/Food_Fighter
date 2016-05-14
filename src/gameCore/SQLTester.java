package gameCore;

public class SQLTester {

	public static void main(String[] args) {
		SQL.setup();
		System.out.println(SQL.getItemName(0));
		System.out.println(SQL.getItemDescr(0));
		
		int [] temp = {0};
		String [] stemp = SQL.getAllItems(temp);
		for(String s: stemp) {
			System.out.println(s);
		}
		Party p = new Party();
		Character h = Factory.heroFactory("Johny", 0, p);
		System.out.println(h);
		
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

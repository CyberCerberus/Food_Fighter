package gameCore;

import java.util.Scanner;



public class SQLTester {

	public static void main(String[] args) throws GameOverException {
		SQL.verify();
		System.out.println(SQL.getItemIndex("Test Item"));
		System.out.println(SQL.getItemIndex("Test Attack Item"));
		System.out.println(SQL.getItemDescr("Test Item"));
		System.out.println(SQL.getItemDescr("Test Hammer"));

		
		String [] stemp = SQL.getAllItems(20);
		String [] etemp = SQL.getAllEquips(10, 20);
		for(String s: stemp) {
			if(s != null)
				System.out.println(s);
		}
		for(String s: etemp) {
			if(s != null)
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
		a = Factory.itemFactory(15, h, m);
		a.takeAction();
		
		p.reward(0, 10, 15, 10, 20, 4, 21, 4, 22, 4, 23, 4, 24, 4);

		Map testMap = Factory.mapFactory(0);
		Cursor c = new Cursor(testMap, p);
		c.playMap();
		Scanner kb =  new Scanner(System.in);
		Play.save(p, 0, kb);
		Play.load(kb);
	}

}

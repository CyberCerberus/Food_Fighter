package gameCore;

public class TestGame {
	public static void main(String[] args) {
		SQL.testSetup();
		
		Party p = new Party();
		p.addHero(Factory.heroFactory("Johny", 0, p));
		p.addHero(Factory.heroFactory("Garry", 0, p));
		p.addHero(Factory.heroFactory("Bobby", 0, p));
		p.addHero(Factory.heroFactory("Poppy", 0, p));
		
		Map testMap = Factory.mapFactory(0);
		Cursor c = new Cursor(testMap, p);
		try {
			c.playMap();
		}
		catch(GameOverException e) {
			System.out.println("Too bad for you");
		}
	}
}

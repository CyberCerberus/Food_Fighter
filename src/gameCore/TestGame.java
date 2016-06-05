package gameCore;

public class TestGame {
	public static void main(String[] args) {
		SQL.testSetup();
		
		Party p = new Party();
		p.addHero(Factory.heroFactory("Johny", "Butcher",0, p));
		p.addHero(Factory.heroFactory("Garry", "Butcher",0, p));
		p.addHero(Factory.heroFactory("Bobby", "Butcher",0, p));
		p.addHero(Factory.heroFactory("Poppy", "Butcher",0, p));
		
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

package gameCore;

public class SimpleCombatTest {
	public static void main(String...args) {
		Party p = new Party();
		p.addHero(Factory.heroFactory("Jerry", "Butcher", 0, p));
		p.addHero(Factory.heroFactory("Harry", "Butcher", 0, p));
		//Battle.testfight(p);
	}
}

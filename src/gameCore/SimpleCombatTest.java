package gameCore;

public class SimpleCombatTest {
	public static void main(String...args) {
		Party p = new Party();
		Character[] friend = {new Hero("Jerry", "Chef", 100, 35, 15, 10,
				p, null, null, null)};
		Character[] foe = {new Monster("MOTHER", 100, 10, 10, 10)};
		
		while(!(friend[0].isDead() || foe[0].isDead())) {
			friend[0].attack(foe).takeAction();
			System.out.println();
			foe[0].attack(friend).takeAction();	
			System.out.println();
		}
	}
}

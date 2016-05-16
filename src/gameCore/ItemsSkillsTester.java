package gameCore;

public class ItemsSkillsTester
{
	public static void main(String[] args){
		Party p = new Party();
		Skill s1 = new Skill("basic skill", " some basic skill ", -1, 1, 1, 1, 1, 0, false, 2);
		Character[] friend = {new Hero("Jerry", "Chef", 100, 35, 15, 10,
				p, s1, s1, s1)};
		Character[] foe = {new Monster("MOTHER", 100, 10, 10, 10)};
		
		while(!(friend[0].isDead() || foe[0].isDead())) {
			friend[0].attack(foe).takeAction();
			System.out.println();
			foe[0].attack(friend).takeAction();	
			System.out.println();
		}
		
	}
}

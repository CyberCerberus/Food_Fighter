
public class AttackTester {

	public static void main(String[] args){
		
		Character hero = new Warrior("Bill", 100, 10, 5);
		Character monster = new GenericMonster("Creep", 10, 5, 4);
		Attack heroAtk;
		Attack monAtk;
		
		System.out.println("Begin Battle\n\n" + hero.getName() + " vs " + monster.getName());
		
		while(hero.getHealth() > 0 && monster.getHealth() > 0){
			if(hero.getAttackSpeed() >= monster.getAttackSpeed()){
				heroAtk = new Attack(hero, monster);
				heroAtk.calcDamage();
				
				monAtk = new Attack(monster, hero);
				monAtk.calcDamage();
			}
			else{
				monAtk = new Attack(monster, hero);
				monAtk.calcDamage();
				
				heroAtk = new Attack(hero, monster);
				heroAtk.calcDamage();
			}
		}
		
	}
}

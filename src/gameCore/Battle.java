package gameCore;

import java.util.ArrayList;

public class Battle {

	public static boolean fight(Party p, int...monsters) {
		//monster construction
		
		
		return false;
	}
	
	public static boolean testfight(Party p) {
		Character[] friend = p.toArray();
		
		Skill ms1 = new Skill("Scold", " scolded ", 200, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
		
		Skill es1 = new Skill("Crush", " crushed ", 200, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
		
		Character[] foe = {new Monster("MOTHER", 90, 50, 20, 20, ms1, 0.4), 
				new Monster("EARTH", 90, 50, 20, 20, es1, 0.4)};
		ArrayList<Action> moves = new ArrayList<Action>();
		boolean alldead = false;
		
		while(true) {
			for(Character f: friend) {
				if(!(f.isDead())) {
					moves.add(f.attack(foe));
				}
			}
			for(Character f: foe) {
				if(!(f.isDead())) {
					moves.add(f.attack(friend));
				}			}
			moves.sort(null);
			
			for(Action a: moves) {
				a.takeAction();
				System.out.println();
			}
			
			moves.clear();
			
			alldead = true;
			for(Character f: friend) {
				alldead = alldead & f.isDead();
			}
			if(alldead) {
				System.out.println("Your party has fallen in battle!");
				return false;
			}
			
			alldead = true;
			for(Character f: foe) {
				alldead = alldead & f.isDead();
			}
			if(alldead) {
				System.out.println("All enemies have successfully been fed!");
				return true;
			}
		}
	}
	
	public static boolean testfight() {
		Party p = new Party();
		Skill js1 = new Skill("Stab", " stabbed ", 75, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
		Skill js2 = new Skill("Kick", " kicked ", 75, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
		Skill js3 = new Skill("Slap", " slapped ", 75, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
		
		Skill bs1 = new Skill("Tickle", " tickled ", 75, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
		Skill bs2 = new Skill("Stomp", " stompped ", 75, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
		Skill bs3 = new Skill("Punch", " punched ", 75, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
		
		Skill ms1 = new Skill("Scold", " scolded ", 200, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
		
		Skill es1 = new Skill("Crush", " crushed ", 200, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
		
		Character[] friend = {new Hero("Jerry", "Chef", 100, 50, 15, 4,
				p, js1, js2, js3), new Hero("Berry", "Cook", 100, 50, 15, 10,
				p, bs1, bs2, bs3)};
		Character[] foe = {new Monster("MOTHER", 90, 50, 20, 20, ms1, 0.4), 
				new Monster("EARTH", 90, 50, 20, 20, es1, 0.4)};
		ArrayList<Action> moves = new ArrayList<Action>();
		boolean alldead = false;
		
		while(true) {
			for(Character f: friend) {
				if(!(f.isDead())) {
					moves.add(f.attack(foe));
				}
			}
			for(Character f: foe) {
				if(!(f.isDead())) {
					moves.add(f.attack(friend));
				}			}
			moves.sort(null);
			
			for(Action a: moves) {
				a.takeAction();
				System.out.println();
			}
			
			moves.clear();
			
			alldead = true;
			for(Character f: friend) {
				alldead = alldead & f.isDead();
			}
			if(alldead) {
				System.out.println("Your party has fallen in battle!");
				return false;
			}
			
			alldead = true;
			for(Character f: foe) {
				alldead = alldead & f.isDead();
			}
			if(alldead) {
				System.out.println("All enemies have successfully been fed!");
				return true;
			}
		}
	}
}

package gameCore;

import java.util.*;

public class Battle {

	public static void fight(Party p, Monster...monsters) throws GameOverException {
		Character[] friend = p.toArray();
		Character[] foe = monsters;
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
				}			
			}
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
				System.out.println("Game Over!");
				int choice = 0;
				Scanner kb = new Scanner(System.in);
				while(true) {
					System.out.println("1. Menu screen\n2. Quit");
					try {
						choice = kb.nextInt();
						if(choice == 1) {
							throw new GameOverException();
						}
						else if(choice == 2) {
							kb.close();
							System.exit(0);
						}
						else {
							System.out.println("Please choose from the avalable options");
						}
					}
					catch(InputMismatchException e) {
						
					}
				}
			}
			
			alldead = true;
			for(Character f: foe) {
				alldead = alldead & f.isDead();
			}
			if(alldead) {
				System.out.println("All enemies have successfully been fed!");
				return;
			}
		}
	}
	
	public static boolean testfight(Party p) {
		Character[] friend = p.toArray();
		
		Skill ms1 = new Skill("Scold", "", " scolded ", -200, 0.0, 0.0, 0.0, 0.0, 0,
				false, 5, false);
		
		Skill es1 = new Skill("Crush", "", " crushed ", -200, 0.0, 0.0, 0.0, 0.0, 0,
				false, 5, false);
		
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
		Skill js1 = new Skill("Stab", "", " stabbed ", -75, 0.0, 0.0, 0.0, 0.0, 0, false, 5,
				false);
		Skill js2 = new Skill("Kick", "", " kicked ", -75, 0.0, 0.0, 0.0, 0.0, 0, false, 5,
				false);
		Skill js3 = new Skill("Slap", "", " slapped ", -75, 0.0, 0.0, 0.0, 0.0, 0, false, 5,
				false);
		
		Skill bs1 = new Skill("Tickle", "", " tickled ", 
				-75, 0.0, 0.0, 0.0, 0.0, 0, false, 5, false);
		Skill bs2 = new Skill("Stomp", "", " stompped ", 
				-75, 0.0, 0.0, 0.0, 0.0, 0, false, 5, false);
		Skill bs3 = new Skill("Punch", "", " punched ", 
				-75, 0.0, 0.0, 0.0, 0.0, 0, false, 5, false);
		
		Skill ms1 = new Skill("Scold", "", " scolded ", 
				-200, 0.0, 0.0, 0.0, 0.0, 0, false, 5, false);
		
		Skill es1 = new Skill("Crush", "", " crushed ", 
				-200, 0.0, 0.0, 0.0, 0.0, 0, false, 5, false);
		
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

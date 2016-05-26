package gameCore;

import java.util.*;

public class Battle {

	public static void fight(Party p, Monster...monsters) throws GameOverException {
		Character[] friend = p.toArray();
		int expReward = 0;
		for(Monster m: monsters) {
			expReward += m.rewardExp();
		}
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
						System.out.println("Please choose from the avalable options");
					}
				}
			}
			
			alldead = true;
			for(Character f: foe) {
				alldead = alldead & f.isDead();
			}
			if(alldead) {
				System.out.println("All enemies have successfully been fed!");
				p.rewardExp(expReward);
				p.clearBuffs();
				return;
			}
		}
	}
	
	public static boolean testfight(Party p) {
		Character[] friend = p.toArray();
		
		Skill ms1 = new Skill(-1,"Scold", "", " scolded ", -200, 0.0, 0.0, 0.0, 0.0, 0,
				false, 5, false);
		
		Skill es1 = new Skill(-1,"Crush", "", " crushed ", -200, 0.0, 0.0, 0.0, 0.0, 0,
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
}

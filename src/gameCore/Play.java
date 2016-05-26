package gameCore;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Play {
	private static final int END = 1;
	
	public static void playFromStart(Scanner kb) {
		int level = 0;
		Party p = new Party();
		makeHeros(p, kb);
		
		PlayGame(kb, level, p);
	}
	
	private static void playFromSave(int saveSlot, Scanner kb) {
		int level = 0;
		Party p = new Party();

		try {
			String[] temp = SQL.loadParty(saveSlot);
			p.importParty(temp);
			level = SQL.loadLevel(saveSlot);
		}
		catch(Exception e) {
			System.out.println("save corrupted");
			return;
		}
		
		PlayGame(kb, level, p);
	}

	private static void PlayGame(Scanner kb, int level, Party p) {
		while (level < END) {
			Map map = Factory.mapFactory(level);
			Cursor c = new Cursor(map, p);
			try {
				c.playMap();
				new FullRevive(p.toArray()).takeAction();
				new FullRestore(p.toArray()).takeAction();
				p.clearBuffs();
				level++;
				boolean save = true;
				while(save && level < END) {
					System.out.println("Do you wish to save?\ny/n");
					if(kb.nextLine().equalsIgnoreCase("y")) {
						save(p, level, kb);
						save = false;
					}
					else {
						System.out.println("Are you sure?\ny/n");
						if(kb.nextLine().equalsIgnoreCase("y")) {
							save = false;
						}
					}
				}
			}
			catch(GameOverException e) {
				return;
			}
		}
		System.out.println("Congratulations you have won the game!");
	}
	
	public static void save(Party p, int level, Scanner kb) {
		String[] strara = SQL.saveNames();
		int i, choice = 0;
		String temp;
		while(true) {
			System.out.println("Please choose a save slot.");
			for(i = 0; i < strara.length; i++) {
				System.out.println(i+1 + ". " + strara[i]);
			}
			System.out.println(i+1 + ". Create new save.");
			System.out.println(i+2 + ". Back.");
			try {
				choice = kb.nextInt();
				kb.nextLine();
				if(choice >= 1 && choice < i+1) {
					System.out.println("Are you sure you want to overwite this save?\ny/n");
					temp = kb.nextLine();
					if(temp.equalsIgnoreCase("y")) {
						strara = p.export();
						System.out.println("Saving...");
						SQL.overwrite(strara, level, choice - 1);
						System.out.println("Saved!");
						return;
					}
				}
				else if(choice == i + 1) {
					strara = p.export();
					System.out.println("Saving...");
					SQL.save(strara, level, choice - 1);
					System.out.println("Saved!");
					return;
				}
				else if(choice == i + 2) {
					return;
				}
				else {
					System.out.println("Invalid slot");
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid slot");
			}
		}
	}
	
	public static void load(Scanner kb) {
		String[] strara = SQL.saveNames();
		int i, choice = 0;
		while(true) {
			System.out.println("Please choose a save slot.");
			for(i = 0; i < strara.length; i++) {
				System.out.println(i+1 + ". " + strara[i]);
			}
			System.out.println(i+1 + ". Back to menu.");
			try {
				choice = kb.nextInt();
				if(choice >= 1 && choice < i+1) {
					playFromSave(choice -1, kb);
					return;
				}
				else if(choice == i+1) {
					return;
				}
				else {
					System.out.println("Invalid slot");
				}
			}
			catch(InputMismatchException e) {
				System.out.println("Invalid slot");
			}
		}
	}
	
	private static void makeHeros(Party p, Scanner kb) {
		p.addHero(Factory.heroFactory("Johny", 0, p));
		p.addHero(Factory.heroFactory("Garry", 0, p));
		p.addHero(Factory.heroFactory("Bobby", 0, p));
		p.addHero(Factory.heroFactory("Poppy", 0, p));
	}
}

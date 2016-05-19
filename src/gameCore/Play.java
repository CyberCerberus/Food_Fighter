package gameCore;

import java.util.Scanner;

public class Play {
	public static void playFromStart() {
		int level = 0;
		Party p = new Party();
		Scanner kb = new Scanner(System.in);
		makeHeros(p, kb);
		
		while (level < 1) {
			Map map = Factory.mapFactory(level);
			Cursor c = new Cursor(map, p);
			try {
				c.playMap();
				System.out.println("Do you wish to save?\ny/n");
				if(kb.nextLine().equalsIgnoreCase("y")) {
					save(p, level, kb);
				}
				//confirm no
			}
			catch(GameOverException e) {
				return;
			}
			level++;
		}
	}
	public static void playFromSave(int saveSlot) {
		
	}
	
	public static void save(Party p, int level, Scanner kb) {
		
	}
	
	private static void makeHeros(Party p, Scanner kb) {
		p.addHero(Factory.heroFactory("Johny", 0, p));
		p.addHero(Factory.heroFactory("Garry", 0, p));
		p.addHero(Factory.heroFactory("Bobby", 0, p));
		p.addHero(Factory.heroFactory("Poppy", 0, p));
	}
}

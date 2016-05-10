package gameCore;

public class Factory {
	public static Map mapFactory(int level) {
		//SQL call to the levle table to get the relevant information
		//then calls the room factory to fill the empty map
		
		return null;
	}
	
	private static Room roomFactory(int level, int x, int y) {
		/* SQL call to the room and roomtype tables to get the relevant information
		 * note that the walls is determined by an int from 0-15 in a binary fashion
		 * so you can use >> and mod 2 to figure out what walls there are
		 * 1000 up, 0100 down, 0010 left, 0001 right
		 */
		
		return null;
	}
	
	public static Action itemFactory(int itemid) {
		//SQL call to the item table to get the relevant information
		
		return null;
	}
	
	public static Monster monsterFactory(int monid, int multi) {
		//SQL call to the bestiary table to get the relevant information
		
		return null;
	}
	
	public static Hero heroFactory(String name, int heronum) {
		//no need to use database just plug in the base values and skills
		
		return null;
	}
	
	private static Skill skillFactory(int skillid) {
		//SQL call to the skill table to get the relevant information
		
		return null;
	}
	
}

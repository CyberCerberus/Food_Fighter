import java.util.Scanner;


public class Cursor {
	private int x;
	private int y;
	private Map map;
	private Party party;
	
	Cursor(Map m, Party p) {
		party = p;
		map = m;
		int[] i = map.start();
		x = i[0];
		y = i[1];
	}
	
	public void draw() {
		
	}
	
	private boolean enter() {
		return map.getRoom(x, y).triggerEvent(party);
	}
	
	private void move(int d) {
		if(map.getRoom(x, y).canMove(d)) {
			if(d == 0) {
				y++;
			}
			else if(d == 1) {
				y--;
			}
			else if(d == 2) {
				x--;
			}
			else {
				x++;
			}
			System.out.println("You have entered another room.");
		}
	}

	public void playMap() {
		Scanner kb = new Scanner(System.in);
		boolean done = false;
		String comm;
		while(!done) {
			System.out.print(":");
			comm = kb.nextLine();
			comm = comm.concat("                      ");
			if(comm.substring(0, 4).equalsIgnoreCase("use ")) {
				System.out.println("Unimplmented command");
			}
			else if(comm.substring(0, 5).equalsIgnoreCase("move ")) {
				if(comm.substring(5, 7).equalsIgnoreCase("up")) {
					move(0);
				}
				else if(comm.substring(5, 9).equalsIgnoreCase("down")) {
					move(1);
				}
				else if(comm.substring(5, 9).equalsIgnoreCase("left")) {
					move(2);
				}
				else if(comm.substring(5, 10).equalsIgnoreCase("right")) {
					move(3);
				}
				else {
					System.out.println("move command can only "
							+ "be used with up, down, left and right!");
				}
				
			}
			else if(comm.trim().equalsIgnoreCase("enter")) {
				done = enter();
			}
			else if(comm.substring(0, 6).equalsIgnoreCase("equip ")) {
				System.out.println("Unimplmented command");
			}
			else if(comm.substring(0, 8).equalsIgnoreCase("descibe ")) {
				System.out.println("Unimplmented command");
			}
			else if(comm.trim().equalsIgnoreCase("locate")) {
				System.out.println("Currently at: " + x + ", "+ y);
			}
			else if(comm.trim().equalsIgnoreCase("help")) {
				System.out.println("'move direction': move a direction (ie up)\n"
						+ "'use item on partyslot': use item on a party member (ie potion"
						+ "on 1 \n'enter': explore current room\n"
						+ "'equip item on partyslot': equip a piece of "
						+ "equipment on a party member\n"
						+ "'describe item/room': gives a description of what you enter\n"
						+ "'locate': gives current coordinates"
						+ "'help': display this menu");
			}
			else {
				System.out.println("invalid command, type 'help' to get a list of commands");
			}
		}
        System.out.println("You Cleared the Dungeon! (Dun dun dun dun dun dun, dun, dun dun!");
		kb.close();
	}
}

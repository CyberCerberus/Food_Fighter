package gameCore;
import java.util.Scanner;


public class Cursor {
	private int x;
	private int lastx;
	private int y;
	private int lasty;
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
	
	private boolean enter() throws GameOverException {
		return grabRoom().triggerEvent(party);
	}
	
	private Room grabRoom() {
		try {
			return map.getRoom(x, y);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("You have ventured outside the map, warping to start");
			int[] i = map.start();
			x = i[0];
			lastx = x;
			y = i[1];
			lasty = y;
		}
		return map.getRoom(x, y);
	}
	
	private void move(int d) {
		if(map.getRoom(x, y).canMove(d)) {
			if(d == 0) {
				lasty = y;
				y++;
			}
			else if(d == 1) {
				lasty = y;
				y--;
			}
			else if(d == 2) {
				lastx = x;
				x--;
			}
			else {
				lastx = x;
				x++;
			}
			System.out.println("You have moved to another room.");
		}
	}

	public void playMap() throws GameOverException {
		Scanner kb = new Scanner(System.in);
		boolean done = false;
		String comm;
		System.out.println("\n\nYour party entered:\n" + map);
		while(!done) {
			System.out.print(":");
			comm = kb.nextLine();
			comm = comm.concat("                                    ");
			if(comm.substring(0, 4).equalsIgnoreCase("use ")) {
				int i = comm.indexOf("on");
				try {
					if(i == -1) {
						throw new Exception();
					}
					String temp = comm.substring(i + 3, i + 4);
					int useon = Integer.parseInt(temp) - 1;
					
					if(useon < 0 || useon > 3) {
						throw new Exception();
					}
					int item = SQL.getItemIndex(comm.substring(4, i).trim());
					
					if(item != -1) {
						int[] items = party.getInvintory().getItems();
						if(items[item] > 0) {
							if(item >= party.getInvintory().FIRSTATTACKINDEX) {
								System.out.println("Can not use that outside of battle");
							}
							else {
								Character[] c = party.toArray();
								Action a = Factory.itemFactory(item,
										new EmptyUser(), c[useon]);
								a.takeAction();
							}
						}
						else {
							System.out.println("You don't have any of those...");
						}
					}
					else {
						System.out.println("Item was not found...");
					}
				}
				catch(Exception e) {
					System.out.println("useage: use <item> on <1,2,3,4> "
							+ "(e.g. use potion on 1)");
				}
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
				if(enter()) {
					System.out.println("Do you wish to exit the dungon?\ny/n");
					comm = kb.nextLine();
					if(comm.trim().equalsIgnoreCase("y")) {
						done = true;
					}
				}
			}
			else if(comm.trim().equalsIgnoreCase("status")) {
				System.out.println(party);
			}
			else if(comm.substring(0, 6).equalsIgnoreCase("stats ")) {
				try{
					String temp = comm.substring(6).trim();
					int select = Integer.parseInt(temp) - 1;
					
					if(select < 0 || select > 3) {
						throw new Exception();
					}
					Character[] cara = party.toArray();
					Character c = cara[select];
					System.out.println(c + "\nStr: " + c.getStr() + "\nDef: "
					+ c.getDef() + "\nSpd: " + c.getSpd());
				}
				catch (Exception e) {
					System.out.println("Usage: stats <1,2,3,4>");
				}
			}
			else if(comm.trim().equalsIgnoreCase("back")) {
				System.out.println("You have warped back to the previous room");
				x = lastx;
				y = lasty;
			}
			else if(comm.trim().equalsIgnoreCase("bag")) {
				System.out.println(party.getInvintory());
			}
			else if(comm.substring(0, 6).equalsIgnoreCase("equip ")) {
				int i = comm.indexOf("on");
				try {
					if(i == -1) {
						throw new Exception();
					}
					String temp = comm.substring(i + 3, i + 4);
					int useon = Integer.parseInt(temp) - 1;
					
					if(useon < 0 || useon > 3) {
						throw new Exception();
					}
					Equipment equip = Factory.equipFactory(comm.substring(6, i).trim());
					int index = equip.getIndex() - party.getInvintory().FIRSTEQUIPINDEX;
					if(index >= 0) {
						int[] equips = party.getInvintory().getEquips();
						if(equips[index] > 0) {
							Character[] cara = party.toArray();
							Hero h = (Hero) cara[useon];
							int res = h.equip(equip);
							if(res != -1) {
								equips[index]--;
								party.getInvintory().returnItem(res);
							}
							else if(res == -1) {
								equips[index]--;
							}
						}
						else {
							System.out.println("You don't have any of those...");
						}
					}
					else {
						System.out.println("Item was not found...");
					}
				}
				catch(Exception e) {
					System.out.println("useage: equip <equipment> on <1,2,3,4> "
							+ "(e.g. equip knife on 1)");
				}
			}
			else if(comm.substring(0, 8).equalsIgnoreCase("unequip ")) {
				int i = comm.indexOf("on");
				try{
					if(i == -1) {
						throw new Exception();
					}
					String temp = comm.substring(i + 3, i + 4);
					int select = Integer.parseInt(temp) - 1;
					
					if(select < 0 || select > 3) {
						throw new Exception();
					}
					temp = comm.substring(8, i).trim();
					
					Character[] cara = party.toArray();
					Hero h = (Hero) cara[select];
					
					if(temp.equalsIgnoreCase("hands")) {
						party.getInvintory().returnItem(h.unequip(0));
					}
					else if(temp.equalsIgnoreCase("body")) {
						party.getInvintory().returnItem(h.unequip(1));
					}
					else if(temp.equalsIgnoreCase("head")) {
						party.getInvintory().returnItem(h.unequip(2));
					}
					else {
						System.out.println("Usage: unequip <equipment slot> on <1,2,3,4> "
								+ "- equipment slot = hands, body, head");
					}
				}
				catch (Exception e) {
					System.out.println("Usage: unequip <equipment slot> on <1,2,3,4> "
							+ "- equipment slot = hands, body, head");
				}
			}
			else if(comm.substring(0, 9).equalsIgnoreCase("describe ")) {
				if(comm.substring(9, 13).equalsIgnoreCase("room")) {
					System.out.println(grabRoom().getDescript());
				}
				else {
					String item = comm.substring(8).trim();
					System.out.println(SQL.getItemDescr(item));
				}
			}
			else if(comm.trim().equalsIgnoreCase("locate")) {
				System.out.println("Floor: " + map +"\nCurrently at: " + x + ", "+ y);
			}
			else if(comm.trim().equalsIgnoreCase("help")) {
				System.out.println("'move <direction>': move a direction (ie up)\n"
						+ "'use <item> on <1,2,3,4>': use item on a party member (ie potion"
						+ " on 1 \n'enter': explore current room\n"
						+ "'equip <item> on <1,2,3,4>': equip a piece of "
						+ "equipment on a party member\n"
						+ "'unequip <equipment slot> on <1,2,3,4>': removes equipment from "
						+ "hero, equipment slot = hands, body, hat\n"
						+ "'describe <object>': gives a description of room or item\n"
						+ "'status': gives party status\n"
						+ "'stats <1,2,3,4>': gives hero's stats\n"
						+ "'bag': shows inventory contents\n"
						+ "'back': retun to previous room\n"
						+ "'locate': gives current coordinates\n"
						+ "'quit': exit the game, you will not be able to save\n"
						+ "'help': display this menu");
			}
			else if(comm.trim().equalsIgnoreCase("quit")) {
				System.out.println("Are you sure? You can not save inside a dungeon\ny/n");
					if(kb.nextLine().trim().equalsIgnoreCase("y")){
						kb.close();
						System.exit(0);
					}
			}
			else {
				System.out.println("invalid command, type 'help' to get a list of "
						+ "commands");
			}
		}
        System.out.println("You Cleared the Dungeon! (Dun dun dun dun dun "
        		+ "dun, dun, dun dun!)");
	}
}

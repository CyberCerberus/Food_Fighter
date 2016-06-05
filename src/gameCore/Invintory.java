package gameCore;

public class Invintory {
    // index should corresponds to an item, contents should be the amount
    public final int FIRSTATTACKINDEX = 15;
    public final int FIRSTEQUIPINDEX = 20;
	
	private int[] items;
	private int[] equips;
	private String[] itemnames;
	private String[] equipnames;
	
	Invintory() {
		items = new int[FIRSTEQUIPINDEX];
		equips = new int[10];
		itemnames = SQL.getAllItems(items.length);
		equipnames = SQL.getAllEquips(equips.length, items.length);
	}
	
	public void addItems(int item, int amount) {
		if(item >= 0 && item < items.length) {
			System.out.println("You got " + amount + " " + itemnames[item]);
			items[item] += amount;
		}
		else if(item >= 0 && item < FIRSTEQUIPINDEX + equips.length) {
			item -= FIRSTEQUIPINDEX;
			System.out.println("You got " + amount + " " +
						equipnames[item]);
			equips[item] += amount;
		}
	}
	
	public String getItemName(int i){
	    return itemnames[i];
	}
	
	public void returnItem(int item) {
		item -= FIRSTEQUIPINDEX;
		if(item >= 0 && item < items.length) {
			System.out.println(equipnames[item] + " was returned to your bag");
			equips[item]++;
		}
	}
	
	public int[] getItems(){
		return items;
	}
	
	public int[] getEquips(){
		return equips;
	}
	
	public String[] getItemNames(){
	    return itemnames;
	}
	
	@Override
	public String toString() {
		String ret = "";
		for(int i = 0; i < items.length; i++) {
			if(items[i] > 0) {
				ret += (i+1) + ". " + itemnames[i] + " " + items[i] + "\n";
			}
		}
		for(int i = 0; i < equips.length; i++) {
			if(equips[i] > 0) {
				ret += (equipnames[i] + " " + equips[i] + "\n");
			}
		}
		ret = ret.trim();
		if(ret.equals("")) {
			ret = "Bag is Empty";
		}
		return ret;
	}

	public String itemsToString() {
		String ret = "";
		for(int i = 0; i < items.length; i++) {
			if(items[i] > 0) {
				ret += (i+1) + ". " + itemnames[i] + " " + items[i] + "\n";
			}
		}
		ret = ret.trim();
		if(ret.equals("")) {
			ret = "Bag is Empty";
		}
		return ret;
	}
	
	public String[] exportItems() {
		String[] ret = new String[2];
		ret[0] = "";
		for(int i = 0; i < items.length; i++) {
			ret[0] += "" + items[i];
			if(i < items.length -1) {
				ret[0] += ",";
			}
		}
		ret[1] = "";
		for(int i = 0; i < equips.length; i++) {
			ret[1] += "" + equips[i];
			if(i < equips.length -1) {
				ret[1] += ",";
			}
		}
		return ret;
	}
	
	 void importItems(String[] ara) {
		 String temp = ara[0];
		 int i, j = 0;
		 for(i = 0; i < items.length && j != -1; i++) {
			 j = temp.indexOf(',');
			 if(j != -1) {
				 items[i] = Integer.parseInt(temp.substring(0, j));
				 temp = temp.substring(j+1);
			 }
		 }
		 items[i - 1] = Integer.parseInt(temp);
		 j = 0;
		 temp = ara[1];
		 for(i = 0; i < equips.length && j != -1; i++) {
			 j = temp.indexOf(',');
			 if(j != -1) {
				 equips[i] = Integer.parseInt(temp.substring(0, j));
				 temp = temp.substring(j+1);
			 }
		 }
		 equips[i - 1] = Integer.parseInt(temp);
	}
}

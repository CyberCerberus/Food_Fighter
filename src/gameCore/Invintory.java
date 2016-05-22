package gameCore;

public class Invintory {
    // index should corresponds to an item, contents should be the amount
    
	private int[] items;
	private String[] itemnames;
	Invintory() {
		items = new int[1];
		itemnames = SQL.getAllItems(items);
	}
	
	public void addItems(int item, int amount) {
		if(item >= 0 && item < items.length) {
			System.out.println("You got " + amount + " " + itemnames[item]);
			items[item] += amount;
		}
	}
	
	public int[] getItems(){
		return items;
	}
	
	public String toString() {
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
}

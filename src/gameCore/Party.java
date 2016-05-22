package gameCore;

public class Party {
	private Hero slot1;
	private Hero slot2;
	private Hero slot3;
	private Hero slot4;
	private Invintory bag;
	
	public Party(){
		bag = new Invintory();
	}
	
	public void addHero(Hero h) {
		if(slot1 == null) {
			slot1 = h;
			System.out.println(h + " added to party slot 1");
		}
		else if(slot2 == null) {
			slot2 = h;
			System.out.println(h + " added to party slot 2");
		}
		else if(slot3 == null) {
			slot3 = h;
			System.out.println(h + " added to party slot 3");
		}
		else if(slot4 == null) {
			slot4 = h;
			System.out.println(h + " added to party slot 4");
		}
		else {
			System.out.println("The party is full!");
		}
	}
	
	public Character[] toArray() {
		Character[] c = {slot1, slot2, slot3, slot4};
		return c;
	}
	
	public Invintory getInvintory() {
		return bag;
	}
	
	public void reward(int...items) {
		if(items.length % 2 == 0) {
			for(int i = 0; i < items.length / 2; i++) {
				bag.addItems(items[i], items[i+1]);
			}
		}
		else {
			System.out.println("Inproper use, requires the item and then its amount");
		}
	}
	
	public String toString() {
		String ret = "1. " + slot1.getStatus() + "\n";
		ret += "2. " + slot2.getStatus() + "\n";
		ret += "3. " + slot3.getStatus() + "\n";
		ret += "4. " + slot4.getStatus();
		return ret;
	}
}

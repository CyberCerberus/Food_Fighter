package engine.entity.party;

import engine.entity.Hero;
import engine.map.Map;
import engine.entity.Character;

public class Party {
    	
    	public static final int PLAYER = 0;
	private Hero slot1;
	private Hero slot2;
	private Hero slot3;
	private Hero slot4;
	private Invintory bag;
	
	public Party(){
		bag = new Invintory();
	}
	
	public void setMap(Map map){
	    slot1.setMap(map);
	    slot2.setMap(map);
	    slot3.setMap(map);
	    slot4.setMap(map);
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
	
	public void rewardExp(int exp) {
		System.out.println("Party was awarded " + exp + " experiance points");
		slot1.rewardExp(exp);
		slot2.rewardExp(exp);
		slot3.rewardExp(exp);
		slot4.rewardExp(exp);
	}
	
	public void reward(int...items) {
		if(items.length % 2 == 0) {
			for(int i = 0; i < items.length; i += 2) {
				bag.addItems(items[i], items[i+1]);
			}
		}
		else {
			System.out.println("Inproper use, requires the item and then its amount");
		}
	}
	
	@Override
	public String toString() {
		String ret = "1. " + slot1.getStatus() + "\n";
		ret += "2. " + slot2.getStatus() + "\n";
		ret += "3. " + slot3.getStatus() + "\n";
		ret += "4. " + slot4.getStatus();
		return ret;
	}
	
	public String [] export() {
		String [] ret = new String[30];
		String [] temp;
		temp = slot1.export();
		ret[0] = temp[0];
		ret[1] = temp[1];
		ret[2] = temp[2];
		ret[3] = temp[3];
		ret[4] = temp[4];
		ret[5] = temp[5];
		ret[6] = temp[6];
		temp = slot2.export();
		ret[7] = temp[0];
		ret[8] = temp[1];
		ret[9] = temp[2];
		ret[10] = temp[3];
		ret[11] = temp[4];
		ret[12] = temp[5];
		ret[13] = temp[6];
		temp = slot3.export();
		ret[14] = temp[0];
		ret[15] = temp[1];
		ret[16] = temp[2];
		ret[17] = temp[3];
		ret[18] = temp[4];
		ret[19] = temp[5];
		ret[20] = temp[6];
		temp = slot4.export();
		ret[21] = temp[0];
		ret[22] = temp[1];
		ret[23] = temp[2];
		ret[24] = temp[3];
		ret[25] = temp[4];
		ret[26] = temp[5];
		ret[27] = temp[6];
		temp = bag.exportItems();
		ret[28] = temp[0];
		ret[29] = temp[1];

		return ret;
	}
	
	public void importParty(String[] ara) {
		String[] temp = new String[7];
		int i = 0;
		temp[0] = ara[i++];
		temp[1] = ara[i++];
		temp[2] = ara[i++];
		temp[3] = ara[i++];
		temp[4] = ara[i++];
		temp[5] = ara[i++];
		temp[6] = ara[i++];
		slot1 = Hero.retriveHero(this, temp);
		temp[0] = ara[i++];
		temp[1] = ara[i++];
		temp[2] = ara[i++];
		temp[3] = ara[i++];
		temp[4] = ara[i++];
		temp[5] = ara[i++];
		temp[6] = ara[i++];
		slot2 = Hero.retriveHero(this, temp);
		temp[0] = ara[i++];
		temp[1] = ara[i++];
		temp[2] = ara[i++];
		temp[3] = ara[i++];
		temp[4] = ara[i++];
		temp[5] = ara[i++];
		temp[6] = ara[i++];
		slot3 = Hero.retriveHero(this, temp);
		temp[0] = ara[i++];
		temp[1] = ara[i++];
		temp[2] = ara[i++];
		temp[3] = ara[i++];
		temp[4] = ara[i++];
		temp[5] = ara[i++];
		temp[6] = ara[i++];
		slot4 = Hero.retriveHero(this, temp);
		temp[0] = ara[i++];
		temp[1] = ara[i++];
		bag.importItems(temp);
	}
	
	public void clearBuffs() {
		slot1.clearBuffs();
		slot2.clearBuffs();
		slot3.clearBuffs();
		slot4.clearBuffs();
	}
}

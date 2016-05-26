package gameCore;

public class Equipment {
	/* str def and spd are the amounts of stat changes to add to a character
	 * eclass is the weight and ability that it takes to wear something
	 * slot is the equipment slot it can be in
	 * 0 weapon, 1 armor, 2 helm
	 */
	private String name;
	private int index;
	private int str;
	private int def;
	private int spd;
	private int eclass;
	private int slot;
	private String type;
	
	
	public Equipment(String name, int index, int str, int def, 
			int spd, int eclass, int slot) {
		this.name = name;
		this.index = index;
		this.str = str;
		this.def = def;
		this.spd = spd;
		this.eclass = eclass;
		this.slot = slot;
		fillType();
	}
	
	public int getStr() {
		return str;
	}
	
	public int getDef() {
		return def;
	}
	
	public int getSpd() {
		return spd;
	}
	
	public boolean canUse(int match) {
		if(eclass > match) {
			return false;
		}
		return true;
	}
	
	private void fillType() {
		if(eclass == 1) {
			type = "Light ";
		}
		else if(eclass == 2) {
			type = "Medium ";
		}
		else {
			type = "Heavy ";
		}
		if(slot == 0) {
			type += "Weapon";
		}
		else if(slot == 1) {
			type += "Armor";
		}
		else if(slot == 2) {
			type += "Helm";
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getType() {
		return slot;
	}
	
	public String toString() {
		return name + "-" + type;
	}
	
}

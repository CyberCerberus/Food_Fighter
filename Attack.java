
public class Attack {
	
	private Character attacker, reciever;
	
	public Attack(Character attacker, Character reciever){
		this.attacker = attacker;
		this.reciever = reciever;
	}
	
	public void calcDamage(){
		this.reciever.setHealth(attacker.getAttackDamage() - reciever.getHealth());
		System.out.println("");
		if(reciever.getHealth() <= 0)
			System.out.println(reciever.getName() + " is defeated\n");
	}

}

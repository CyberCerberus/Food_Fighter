package gameCore;
import java.util.*;

public class Hero extends Character{
    private String className;
    private int hp, equipclass, curexp, nextLVUP;
    private Party party;
    private Skill skill1, skill2, skill3; //can't be null
    private Equipment[] equips;
    private LevelUpBuild build;
   
   public Hero(String name, String className, int maxHP, int str, int def, int spd,
               Party p, Skill s1, Skill s2, Skill s3, int ec, LevelUpBuild b){
       super(name, maxHP, str, def, spd);
       this.hp = maxHP;
       this.className = className;
       this.party = p;
       this.skill1 = s1;
       this.skill2 = s2;
       this.skill3 = s3;
       this.equipclass = ec;
       this.equips = new Equipment[3];
       this.equips[0] = new BareHands();
       this.equips[1] = new Cloths();
       this.equips[2] = new Hat();
       this.curexp = 0;
       this.nextLVUP = 10;
       this.build = b;
   }
    public int getHP() {
        return hp;
    }
    
    public void takeEffect(int raw, double skillchagre, double strChange, double defChange,
                           double spdChange, int time, boolean rev) {
        if(!isDead() && !rev) {
            //healing
            if(raw > 0) {
                if(hp == super.getMax()) {
                    System.out.println(this + " is already at full hp");
                }
                else if(super.getMax() - hp < raw) {
                	int healed = super.getMax() - hp;
                    hp = super.getMax();
                    System.out.printf("%s was healed by %d\n", this, healed);
                }
                else {
                    hp = hp + raw;
                    System.out.printf("%s was healed by %d\n", this, raw);
                }
            }
            //damage
            else if (raw < 0) {
                if(this.getDef() < Math.abs(raw)) {
                    int dmg = raw + this.getDef();
                    hp = hp + dmg;
                    System.out.printf("%s took %d point(s) of damage\n", 
                    		this, Math.abs(dmg));
                }
                else {
                    hp--;
                    System.out.printf("%s took 1 point of damage\n", this);
                }
                
                if(hp <= 0) { //killed
                    System.out.printf("%s was defeated in battle\n", this);
                    hp = 0;
                    killed();
                }
            }
        }
        //not dead, can't revive
        else if(!isDead() && rev) {
            System.out.println(this + " isn't dead and can not be revived");
        }
        //reviving
        else if(isDead() && rev) {
        	raw = Math.abs(raw);
            revived();
            if(super.getMax() - hp < raw) {
                hp = super.getMax();
                System.out.printf("%s was revived and healed by %d\n", 
                		this, super.getMax());
            }
            else {
                hp = hp + raw;
                System.out.printf("%s was revived and healed by %d\n", this, raw);
            }
        }
        //already dead
        else {
            System.out.println(this + " is dead");
        }
        
        if(!isDead()) {
        	updateBuff(strChange, defChange, spdChange, time);
            skill1.restore(skillchagre);
            skill2.restore(skillchagre);
            skill3.restore(skillchagre);
        }
    }
    
    //basic concept for attack
    public Action attack(Character[] enemies) {
        if(enemies.length == 0) {
            System.out.println("enemies is empty!");
            return null;
        }
        Scanner kb = new Scanner(System.in); //do not close!
        
        Action a = chooseAction(enemies, kb);
        return a;
    }
    
    private Action chooseAction(Character[] enemies, Scanner kb) {
        int choice = 0;
        while(true) {
            System.out.println(this);
            System.out.println("1. attack, 2. skills, 3. items");
            
            
             try {
                choice = kb.nextInt();
             }
             catch(Exception e) {
                System.out.println("Ok, im pretty sure that dosen't count as a number...");
                choice = 0;
             }
             kb.nextLine();
             
            if(choice == 1) {
                Character[] target = chooseTarget(enemies, kb);
                Character user = this;
                return new Action(user, toString() + " attacked " + target[0].toString(),
                                  -1 * super.getStr(), false, target);
            }
            else if(choice == 2) {
                Action a = null;
                a = chooseSkill(enemies, kb);
                if(a != null) {
                    return a;
                }
            }
            else if(choice == 3) {
                Action a = null;
                a = chooseItem(enemies, kb);
                if(a != null) {
                    return a;
                }
            }
            else {
                System.out.println("Invalid choice!");
            }
        }
    }
    
    private Character[] chooseTarget(Character[] choices, Scanner kb) {
        int choice = 0, count = 0;
        //forced to make choice
        System.out.println("Choose one of the folowing targets.");
        while(true) {
            for(Character c: choices) {
                System.out.printf("%d. %s\n", count + 1, c);
                count++;
            }
            count = 0;
            try{
                choice = kb.nextInt();
                Character[] c = {choices[choice -1]};
                return c;
            }
            catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("INVALID, That was not a given option!");
            }
            catch(InputMismatchException e) {
                System.out.println("INVALID, You must enter an acutal number!");
            }
            catch(Exception e) {
                System.out.println("INVALID, What the heck are you trying to enter?!?");
            }
            kb.nextLine();
            choice = 0;
        }
    }
    
    private Action chooseSkill(Character[] enemies, Scanner kb) {
        //use party reference to get array of team mates for heal skills and items
        //should allow for players to go back and choose another option
        //unless skill has been picked
    	int choice = 0;
    	while(true){
    		System.out.println("Choose one of the following skills:");
        	System.out.println(	"1. " + skill1.display() + "\n" +
        						"2. " + skill2.display() + "\n" +
        						"3. " + skill3.display() + "\n" +
        						"4. Back");
    		try{
        		choice = kb.nextInt();
        		if(choice < 1 || choice > 4){
        			System.out.println("Invalid option!");
        		}
        		else{
        			Character [] targets = null;
        			switch(choice){
        				case 1: 
        					if(skill1.canUse()) {
        						targets = chooseSkillTarget(skill1, enemies, kb);
        						return skill1.makeAction(this, targets);
        					}
        					else {
        						System.out.println(skill1 + " move is out of pp");
        					}
        					break;
        				case 2:
        					if(skill2.canUse()) {
        						targets = chooseSkillTarget(skill2, enemies, kb);
        						return skill2.makeAction(this, targets);
        					}
        					else {
        						System.out.println(skill2 + " move is out of pp");
        					}
        					break;
        				case 3:
        					if(skill3.canUse()) {
        						targets = chooseSkillTarget(skill3, enemies, kb);
        						return skill3.makeAction(this, targets);
        					}
        					else {
        						System.out.println(skill3 + " move is out of pp");
        					}
        					break;
        				case 4:
        					return null;
        			}
        		}
        	}
        	catch(InputMismatchException e){
        		System.out.println("Invalid number!");
        	}	
    	}   
    }
    
    private Character [] chooseSkillTarget(Skill s, Character[] enemies, Scanner kb) {
    	if(s.isAttack()) {
    		if(s.isAOE()) {
    			return enemies;
    		}
			return chooseTarget(enemies, kb);
		}
		else {
			if(s.isAOE()) {
				return party.toArray();
			}
			return chooseTarget(party.toArray(), kb);
		}
    }
    
    private Action chooseItem(Character[] enemies, Scanner kb) {
        //use party reference to get array of team mates for heal skills and items
        //you can also use party to retrieve the inventory object
        //should allow for players to go back and choose another option
        //unless item has been picked
    	int[] items = party.getInvintory().getItems();
    	int choice = 0;
    	while(true){
    		System.out.println("Select an item from the following list: ");
    		System.out.println(party.getInvintory().itemsToString());
			System.out.println((items.length + 1) + ". Back");
			
    		try {
    			choice = kb.nextInt();
    			choice--;
    			if(choice < 0 || choice >= items.length) {
    				return null;
    			} //based on the choice, an action will be created
    			else if(items[choice] > 0){
    				Character user = this;
    				Character[] target = null;
    				if(choice >= party.getInvintory().FIRSTATTACKINDEX) {
    					target = chooseTarget(enemies, kb);
    				}
    				else {
    					target = chooseTarget(party.toArray(), kb);
    				}
    				items[choice]--;
    				return Factory.itemFactory(choice, user, target);
    			}
    			else{
    				System.out.println("No more of that item!");
    				return null;
    			}
    		}
    		catch(InputMismatchException e){
    			System.out.println("Invalid number.");
    		}
    	}
    }
    
    public String toString() {
        if(super.isDead()) {
            return getName() + " the " + className + " (dead)";
        }
        return getName() + " the " + className + " " + hp + "/" + super.getMax();
    }
    
    public int equip(Equipment equip) {
    	System.out.print(getName());
    	int ret = -2;
    	
    	if(equip.canUse(equipclass)) {
    		int slot = equip.getType();
    		ret = equips[slot].getIndex();
    		System.out.println(" equiped " + equip);
    		equips[slot] = equip;
    	}
    	else {
    		System.out.println(" does not meet the requierments to use this");
    	}
    	return ret;
    }
    
    public int unequip(int slot) {
    	int ret = -1;
    	if(slot < 0 || slot >= equips.length) {
    		System.out.println("Invalid equipment slot");
    	}
    	
    	ret = equips[slot].getIndex();
    	if(ret == -1) {
    		System.out.println("Nothing is equiped");
   			return -1;
    	}
    	       	
       	Equipment temp = null;
       	if(slot == 0) {
       		temp = new BareHands();
       	}
       	else if(slot == 1) {
       		temp = new Cloths();
       	}
       	else {
       		temp = new Hat();
       	}
       	equips[slot] = temp;
    	
       	return ret;
    }
    
    public String getStatus() {
    	String ret = toString() + "\n   ";
    	ret += skill1.display() + "\n   ";
    	ret += skill2.display() + "\n   ";
    	ret += skill3.display();
    	return ret;
    }
	public void rewardExp(int exp) {
		if(!isDead()) {
			curexp += exp;
			checkLvl();
		}
	}
	private void checkLvl() {
		if(curexp > nextLVUP) {
			System.out.println(this + " leveled up!");
			upStats(build);
			nextLVUP *= 2;
			checkLvl();
		}
	}
	
	@Override
	public int getStr() {
		int temp = super.getStr();
		for(Equipment e: equips) {
			temp += e.getStr();
		}
		return temp;
	}
	
	@Override
	public int getDef() {
		int temp = super.getDef();
		for(Equipment e: equips) {
			temp += e.getDef();
		}
		return temp;
	}
	
	@Override
	public int getSpd() {
		int temp = super.getSpd();
		for(Equipment e: equips) {
			temp += e.getSpd();
		}
		return temp;
	}
	
	String [] export() {
		super.clearBuffs();
		String[] ret = new String[7];
		ret[0] = getName();
		ret[1] = className;
		ret[2] = "("+super.getMax()+","+super.getStr()+","+super.getDef()
				+","+super.getSpd()+")";
		ret[3] = "("+skill1.getID()+","+skill2.getID()+","+skill3.getID()+")";
		ret[4] = "("+equips[0].getIndex()+","+equips[1].getIndex()+","
					+equips[2].getIndex()+")";
		ret[5] = "("+equipclass+")("+build.getStr()+","+build.getDef()
				+","+build.gedSpd()+")";
		ret[6] = "("+curexp+","+nextLVUP+")";
		return ret;
	}
	
	static Hero retriveHero(Party p, String[] ara) {
		String temp = ara[2];
		int i = temp.indexOf(',');
		int max = Integer.parseInt(temp.substring(1, i));
		temp = temp.substring(i + 1);
		i = temp.indexOf(',');
		int str = Integer.parseInt(temp.substring(0, i));
		temp = temp.substring(i + 1);
		i = temp.indexOf(',');
		int def = Integer.parseInt(temp.substring(0, i));;
		temp = temp.substring(i + 1);
		i = temp.indexOf(')');
		int spd = Integer.parseInt(temp.substring(0, i));;
		return new Hero(ara[0], ara[1], max, str, def, spd, p, ara);
	}
	
	private Hero(String name, String className, int maxHP, int str, int def, int spd,
            Party p, String [] ara) {
		super(name, maxHP, str, def, spd);
		this.party = p;
		hp = maxHP;
		this.className = className;
		String temp = ara[3];
		int i = temp.indexOf(',');
		skill1 = Factory.skillFactory(Integer.parseInt(temp.substring(1, i)));
		temp = temp.substring(i + 1);
		i = temp.indexOf(',');
		skill2 = Factory.skillFactory(Integer.parseInt(temp.substring(0, i)));
		temp = temp.substring(i + 1);
		i = temp.indexOf(')');
		skill3 = Factory.skillFactory(Integer.parseInt(temp.substring(0, i)));
		equips = new Equipment[3];
		temp = ara[4];
		i = temp.indexOf(',');
		equips[0] = Factory.equipFactory(Integer.parseInt(temp.substring(1, i)));
		temp = temp.substring(i + 1);
		i = temp.indexOf(',');
		equips[1] = Factory.equipFactory(Integer.parseInt(temp.substring(0, i)));
		temp = temp.substring(i + 1);
		i = temp.indexOf(')');
		equips[2] = Factory.equipFactory(Integer.parseInt(temp.substring(0, i)));
		temp = ara[5];
		i = temp.indexOf(')');
		equipclass = Integer.parseInt(temp.substring(1, i));
		temp = temp.substring(i + 2);
		i = temp.indexOf(',');
		int bstr = Integer.parseInt(temp.substring(0, i));
		temp = temp.substring(i + 1);
		i = temp.indexOf(',');
		int bdef = Integer.parseInt(temp.substring(0, i));
		temp = temp.substring(i + 1);
		i = temp.indexOf(')');
		int bspd = Integer.parseInt(temp.substring(0, i));
		build = new LevelUpBuild(bstr, bdef, bspd);
		temp = ara[6];
		i = temp.indexOf(',');
		curexp = Integer.parseInt(temp.substring(1, i));
		temp = temp.substring(i + 1);
		i = temp.indexOf(')');
		nextLVUP = Integer.parseInt(temp.substring(0, i));
	}
}






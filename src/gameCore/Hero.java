package gameCore;
import java.util.*;

public class Hero extends Character{
    private String className;
    private int hp;
    private Party party;
    private Skill skill1, skill2, skill3; //can't be null
   
   public Hero(String name, String className, int maxHP, int str, int def, int spd,
               Party p, Skill s1, Skill s2, Skill s3){
       super(name, maxHP, str, def, spd);
       this.hp = maxHP;
       this.className = className;
       this.party = p;
       this.skill1 = s1;
       this.skill2 = s2;
       this.skill3 = s3;
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
                    System.out.printf("%s took %d point(s) of damage\n", this, Math.abs(dmg));
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
            
            updateBuff(strChange, defChange, spdChange, time);
            skill1.restore(skillchagre);
            skill2.restore(skillchagre);
            skill3.restore(skillchagre);
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
    }
    
    //basic concept for attack
    public Action attack(Character[] enemies) {
        if(enemies.length == 0) {
            System.out.println("enemies is empty!");
            return null;
        }
        Scanner kb = new Scanner(System.in); //do not close!
        
        Action a = chooseAction(enemies, kb);
       /*
        Character user = this;
        Action a = new Action(user, toString() + " punched " + enemies[0].toString(),
        		-1 * super.getStr(), false, enemies);
        */
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
    		System.out.println(party.getInvintory());
			System.out.println((items.length + 1) + ". Back");
			
    		try {
    			choice = kb.nextInt();
    			if(choice < 1 || choice > items.length) {
    				return null;
    			} //based on the choice, an action will be created
    			else if(items[choice - 1] > 0){
    				Character user = this;
    				Character[] target = null;
    				if(choice > 10) {
    					target = chooseTarget(enemies, kb);
    				}
    				else {
    					target = chooseTarget(party.toArray(), kb);
    				}
    				items[choice - 1]--;
    				return Factory.itemFactory(choice -1, user, target);
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
    
    public String getStatus() {
    	String ret = toString() + "\n   ";
    	ret += skill1.display() + "\n   ";
    	ret += skill2.display() + "\n   ";
    	ret += skill3.display();
    	return ret;
    }
}






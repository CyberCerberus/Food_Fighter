package gameCore;
import java.util.*;

public class Hero extends Character{
    private String className;
    private int hp;
    private Party party;
    private Skill skill1, skill2, skill3; //may become an array later, can't be null
   
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
        if(!isDead()) {
            //healing
            if(raw > 0) {
                if(hp == super.getMax()) {
                    System.out.println(this + " is already at full hp");
                }
                else if(super.getMax() - hp < raw) {
                    System.out.printf("%s was healed by %d\n", this, super.getMax() - hp);
                    hp = super.getMax();
                }
                else {
                    System.out.printf("%s was healed by %d\n", this, raw);
                    hp = hp + raw;
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
                    killed();
                }
            }
            
            updateBuff(strChange, defChange, spdChange, time);
            skill1.restore(skillchagre);
            skill2.restore(skillchagre);
            skill3.restore(skillchagre);
        }
        //reviving
        else if(isDead() && rev) {
        	raw = Math.abs(raw);
            if(super.getMax() - hp < raw) {
                System.out.printf("%s was revived and healed by %d\n", 
                		this, super.getMax() - hp);
                hp = super.getMax();
            }
            else {
                System.out.printf("%s was revived and healed by %d\n", this, raw);
                hp = hp + raw;
            }
            revived();
        }
        //already dead
        else {
            System.out.println(this + " is already dead");
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
             
            
            //choice = 1;
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
        	System.out.println(	"1: " + skill1 + "\n" +
        						"2: " + skill2 + "\n" +
        						"3: " + skill3);
    		try{
        		choice = kb.nextInt();
        		if(choice < 1 || choice > 3){
        			System.out.println("Invalid option!");
        		}
        		else{
        			switch(choice){
        				case 1:
        					return skill1.makeAction(this, enemies);
        				case 2:
        					return skill2.makeAction(this, enemies);
        				case 3:
        					return skill3.makeAction(this, enemies);
        					
        			}
        		}
        		
        		
        	}
        	catch(InputMismatchException e){
        		System.out.println("Invalid number!");
        	}
    		
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
    		for(int i = 1; i <= items.length; i++){
    			System.out.println(i + ". " + "health pot.");
    			//Based on the index this will print out the item
    		}
    		try{
    			choice = kb.nextInt();
    			if(choice < 1 || choice > items.length){
    				System.out.println("Invalid choice.");
    			}
    			//based on the choice, an action will be created
    			else if(items[choice - 1] > 0){
    				Character user = this;
    				Character[] c = {user};
        			Action a = new Action(user, user + "uses a health pot", 1, false, c);
        			party.getInvintory().decrementItem(choice - 1);
        			return a;
    			}
    			else{
    				System.out.println("No more of that item!");
    				//will just return null for now to exit the loop
    				return null;
    			}
    			
    			
    		}catch(NumberFormatException e){
    			System.out.println("Invalid number.");
    		}
    		
    	}
        
    }
    
    public String toString() {
        if(super.isDead()) {
            return "(dead)" + getName() + " the " + className;
        }
        return getName() + " the " + className;
    }
}






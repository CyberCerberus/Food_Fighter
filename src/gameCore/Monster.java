package gameCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Monster extends Character{
    
    private int fill, exp;
    private double skill1chance, skill2chance, skill3chance;
    private Skill skill1, skill2, skill3;
    //skills can be null
    
    //any skill monster (0-3)
    public Monster(String name,int exp, int maxHP, int str, int def, int spd,
                   Skill s1, double sc1, Skill s2, double sc2, Skill s3, double sc3) {
        super(name, maxHP, str, def, spd);
        this.exp = exp;
        this.fill = 0;
        this.skill1 = s1;
        this.skill1chance = sc1;
        this.skill2 = s2;
        this.skill2chance = sc2;
        this.skill3 = s3;
        this.skill3chance = sc3;
    }
    
    //no skill fodder mob
    public Monster(String name, int maxHP, int str, int def, int spd) {
        super(name, maxHP, str, def, spd);
        this.fill = 0;
        this.skill1 = new NullSkill();
        this.skill1chance = 0;
        this.skill2 = new NullSkill();
        this.skill2chance = 0;
        this.skill3 = new NullSkill();
        this.skill3chance = 0;
   }
    
    //1 skill monster
    public Monster(String name, int maxHP, int str, int def, int spd, Skill s, double d) {
        super(name, maxHP, str, def, spd);
        this.fill = 0;
        this.skill1 = s;
        this.skill1chance = d;
        this.skill2 = new NullSkill();
        this.skill2chance = 0;
        this.skill3 = new NullSkill();
        this.skill3chance = 0;
 }
 
    public int getHP() {
        return this.getMax() - fill;
    }
    
    public void takeEffect(int raw, double skillcharge, double strChange, double defChange,
                           double spdChange, int time, boolean rev) {
        raw = raw * -1;
        if(!isDead() && !rev) {
            //healing
            if(raw < 0) {
                if(fill == 0) {
                    System.out.println(this + " is already on an empty stomach");
                }
                else if(fill < Math.abs(raw)) {
                	int healed = fill;
                    fill = 0;
                    System.out.printf("%s was healed by %d\n", this, healed);
                }
                else {
                    fill = fill - raw;
                    System.out.printf("%s was healed by %d\n", this, Math.abs(raw));
                }
            }
            //damage
            else if(raw > 0) {
                if(this.getDef() < raw) {
                    int dmg = raw - this.getDef();
                    fill = fill + dmg;
                    System.out.printf("%s took %d point(s) of food\n", this, dmg);
                }
                else {
                    fill++;
                    System.out.printf("%s took 1 point of food\n", this);
                }
                
                if(fill >= this.getMax()) { //killed
                    System.out.printf("%s is full and has collapsed in battle\n", this);
                    fill = getMax();
                    killed();
                }
            }
            
            super.updateBuff(strChange, defChange, spdChange, time);
            //monsters can't charge skills currently
        }
        //not dead, can't revive
        else if(!isDead() && rev) {
            System.out.println(this + " isn't dead and can not be revived");
        }
        //reviving
        else if(isDead() && rev) {
            raw = -1 * Math.abs(raw);
            if(fill < Math.abs(raw)) {
                fill = 0;
            	System.out.printf("%s was healed by %i\n", this, getMax());
            }
            else {
                fill = fill - raw;
                System.out.printf("%s was healed by %i\n", this, Math.abs(raw));
            }
            revived();
        }
        //already dead
        else {
            System.out.println(this + " is already full");
        }
    }
    
    public int rewardExp() {
    	return exp;
    }
    
    public Action attack(Character[] enemies) {
        if(enemies.length == 0) {
            System.out.println("enemies is empty!");
            return null;
        }
        Random random = new Random();
        Character user = this; 
        /* OK, so this should first randomly select a skill OR a basic attack
         * Then after that if a basic attack or attack skill is selected, to
         * randomly select a (Living) target from the avalable options (ie the heros)
         * Alternitively if a healing or even revive(?) skill is selected... well that
         * requires more intelegence to the monster ai (as well as a monster party)
         * so we could go for a self healing skill but we may stay at that
         */
        Character[] targets = validTargets(enemies);
        int targetNum = random.nextInt(targets.length);
        
        Skill useSkill = skillChoice(random);
        
        if(useSkill.canUse()) {
        	if(useSkill.isAttack()) {
        		if(useSkill.isAOE()) {
        			return useSkill.makeAction(user, targets);
        		}
        		return useSkill.makeAction(user, targets[targetNum]);
        	}
        	else {
        		return useSkill.makeAction(user, user);
        	}
        }
        else{
        	return new Action(user, toString() + " attacked " +
            enemies[targetNum].toString(), -1 * super.getStr(), false, targets[targetNum]);
        }
    }
    
    private Character[] validTargets(Character[] heroes){
    	try{
    		int liveHeroes = 0;
	    	List<Character> ara = new ArrayList<Character>();
	    	for(int i = 0; i < heroes.length; i++) {
	    		if(!heroes[i].isDead()){
	    			ara.add(heroes[i]);
	    			liveHeroes++;
	    		}
	    	}
	    	Character[] targets = new Character[liveHeroes];
	    	for(int j =0; j < liveHeroes; j++)
	    		targets[j] = ara.get(j);
	    	return targets;
    	}
    	catch(Exception e){
    		System.out.println(e);
    	}
		return heroes;
    }
    
    private Skill skillChoice(Random r){
    	double chance = r.nextDouble();
    	
    	if(fill / (super.getMax() * 1.0) < .75) {
    		if (skill1.canUse() && !skill1.isAttack()) {
    			return skill1;
    		}
    		else if(skill2.canUse() && !skill2.isAttack()) {
    			return skill2;
    		}
    		else if(skill3.canUse() && !skill3.isAttack()) {
    			return skill3;
   			}
    	}
    	
    	if(skill1.canUse() && skill1chance >= (1 - chance))
    			return skill1;
    	else if(skill2.canUse() && skill2chance >= (1 - chance))
    			return skill2;
    	else if(skill3.canUse() && skill3chance >= (1 - chance))
    			return skill3;
    	return new NullSkill();
    }
}
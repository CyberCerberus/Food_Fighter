package gameCore;
public class Monster extends Character{
    
    private int fill;
    private double skill1chance, skill2chance, skill3chance;
    private Skill skill1, skill2, skill3;
    //skills can be null
    
    //any skill monster (0-3)
    public Monster(String name, int maxHP, int str, int def, int spd,
                   Skill s1, double sc1, Skill s2, double sc2, Skill s3, double sc3) {
        super(name, maxHP, str, def, spd);
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
        this.skill1 = null;
        this.skill1chance = 0;
        this.skill2 = null;
        this.skill2chance = 0;
        this.skill3 = null;
        this.skill3chance = 0;
   }
    
    //1 skill monster
    public Monster(String name, int maxHP, int str, int def, int spd, Skill s, double d) {
        super(name, maxHP, str, def, spd);
        this.fill = 0;
        this.skill1 = s;
        this.skill1chance = d;
        this.skill2 = null;
        this.skill2chance = 0;
        this.skill3 = null;
        this.skill3chance = 0;
 }
 
    public int getHP() {
        return this.getMax() - fill;
    }
    
    public void takeEffect(int raw, double skillcharge, double strChange, double defChange,
                           double spdChange, int time, boolean rev) {
        raw = raw * -1;
        if(!isDead()) {
            //healing
            if(raw < 0) {
                if(fill == 0) {
                    System.out.println(this + " is already on an empty stomach");
                }
                else if(fill < Math.abs(raw)) {
                    System.out.printf("%s was healed by %d\n", this, fill);
                    fill = 0;
                }
                else {
                    System.out.printf("%s was healed by %d\n", this, Math.abs(raw));
                    fill = fill - raw;
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
                    killed();
                }
            }
            
            super.updateBuff(strChange, defChange, spdChange, time);
            //monsters can't charge skills currently
        }
        //reviving
        else if(isDead() && rev) {
            raw = -1 * Math.abs(raw);
            if(fill < Math.abs(raw)) {
                System.out.printf("%s was healed by %i\n", this, fill);
                fill = 0;
            }
            else {
                System.out.printf("%s was healed by %i\n", this, Math.abs(raw));
                fill = fill - raw;
            }
            revived();
        }
        //already dead
        else {
            System.out.println(this + " is already full");
        }
    }
    
    public Action attack(Character[] enemies) {
        if(enemies.length == 0) {
            System.out.println("enemies is empty!");
            return null;
        }
        Character user = this;
        /* OK, so this should first randomly select a skill OR a basic attack
         * Then after that if a basic attack or attack skill is selected, to
         * randomly select a (Living) target from the avalable options (ie the heros)
         * Alternitively if a healing or even revive(?) skill is selected... well that
         * requires more intelegence to the monster ai (as well as a monster party)
         * so we could go for a self healing skill but we may stay at that
         */
        Action a = new Action(user, this + " punched " + enemies[0].toString(),
        		-1 * super.getStr(), false, enemies[0]);
        return a;
    }
    
}







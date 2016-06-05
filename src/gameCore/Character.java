package gameCore;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Character implements Comparable<Character>{
   
    private String name;
    private int maxHP, str, def, spd;
    private Buff strBuff, defBuff, spdBuff;
    private boolean dead;
    private int x, y;
    private int maxHealthbar, curHealthbar;
   
   public Character(String name, int maxHP, int str, int def, int spd){
       this.name = name;
       this.maxHP = maxHP;
       this.str = str;
       this.def = def;
       this.spd = spd;
       strBuff = new Buff();
       defBuff = new Buff();
       spdBuff = new Buff();
       dead = false;
       x = 0;
       y = 0;
       maxHealthbar = 30;
       curHealthbar = 0;
   }
     
    //getters
    public String getName(){return this.name;}
    public int[] getPosition(){
	return new int[]{x, y};
    }
    public void setPosition(int x, int y){
	this.x = x;
	this.y = y;
    }
    public int getMax(){return this.maxHP;}
    public int getStr(){
        return (int) (this.str * strBuff.getVal());
    }
    public int getDef(){
        return (int) (this.def * defBuff.getVal());
    }
    public int getSpd(){
    	return (int) (this.spd * spdBuff.getVal());
    }
    public boolean isDead() {
        return dead;
    }
    
    protected void killed() {
    	dead = true;
    }
    protected void revived() {
    	dead = false;
    }
    
    public abstract void takeEffect(int raw, double skillcharge, double strChange, 
    		double defChange, double spdChange, int time, boolean rev);
    
    protected void updateBuff(double strChange, double defChange, 
    		double spdChange, int time) {
        strBuff.update(strChange, time);
        defBuff.update(defChange, time);
        spdBuff.update(spdChange, time);
    }
    
    public void passTurn() {
        strBuff.passTime();
        defBuff.passTime();
        spdBuff.passTime();
    }
    
    public void clearBuffs() {
    	strBuff.clear();
    	defBuff.clear();
    	spdBuff.clear();
    }
    
    public abstract int getHP();
    
    //public abstract Action attack(Character[] enemies);
    
    @Override
    public String toString() {
        if(dead) {
            return name + " (dead)";
        }
        return name;
    }
    
    public void setHealthbar(int hb){
	curHealthbar = hb;
    }
    
    public int getHealthbar(){
	return curHealthbar;
    }
    
    public int getMaxHealthbar(){
	return maxHealthbar;
    }
    public void draw(Graphics2D g){
	
    }
    @Override
    public int compareTo(Character that){
	return getSpd() - that.getSpd();
    }
    
    void upStats(LevelUpBuild b) {
    	System.out.print(str + " + " + b.getStr() + " = ");
    	str += b.getStr();
    	System.out.println(str);
    	System.out.print(def + " + " + b.getDef() + " = ");
    	def += b.getDef();
    	System.out.println(def);
    	System.out.print(spd + " + " + b.gedSpd() + " = ");
    	spd += b.gedSpd();
    	System.out.println(spd);
    }
    
    public abstract ArrayList<Skill> getSkills();
}
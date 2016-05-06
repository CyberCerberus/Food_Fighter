package gameCore;
public abstract class Character{
   
    private String name;
    private int maxHP, str, def, spd;
    private Buff strBuff, defBuff, spdBuff;
    private boolean dead;
   
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
   }
     
    //getters
    public String getName(){return this.name;}
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
    
    public abstract int getHP();
    
    public abstract Action attack(Character[] enemies);
    
    public String toString() {
        if(dead) {
            return "(dead)"+ name;
        }
        return name;
    }
}
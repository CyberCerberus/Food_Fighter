package gameCore;
public class Action implements Comparable<Action> {
	
    private Character[] recievers;
    private Character user;
    private String flavtext;
    private int raw, time;
    private double skillcharge, strChange, defChange, spdChange;
    private boolean revive;
    private int userIndex;
    private boolean friend;
    private boolean hostile;
    
    /* flavor text is something that can be printed out as the action is taken 
     * can contain the using charicter's name (e.g. "Hero Stabbed Monster")
     * 
     * raw is the amount of damage/healing (- for damage, + for healing)
     *
     * skill charge is used to restore skill uses, number from 0-1 for a % of skills restored
     *
     * the change values are for buff/debuffs they will be % in decimal form
     * x > 1 for a buff 0 < x < 1 when a debuff (values should be slight like .5 - 2 max)
     * time is how long the effect lasts 
     * more powerful should disappear sooner but always greater then 1
     * buffs and debuffs will take effect on the next turn 
     * and only one will be in effect at a time (NO STACKING)
     *
     * revive determines whether or not the action will take effect if the target is dead
     * healing the dead will do nothing if this is not true
	*/
    
    //full constructor
    public Action(Character user, String ft, int raw, double sc,
                  double strc, double defc, double spdc, int time, 
                  boolean rev, Character...recievers) {
        this.flavtext = ft;
		this.recievers = recievers;
        this.user = user;
        this.raw = raw;
        this.skillcharge = sc;
        this.strChange = strc;
        this.defChange = defc;
        this.spdChange = spdc;
        this.time = time;
        this.revive = rev;
	}
    
    //simple constructor
    public Action(Character user, String ft, int raw, boolean rev, Character...recievers) {
        this.flavtext = ft;
        this.recievers = recievers;
        this.user = user;
        this.raw = raw;
        this.skillcharge = 0;
        this.strChange = 1.0;
        this.defChange = 1.0;
        this.spdChange = 1.0;
        this.time = 0;
        this.revive = rev;
    }
    
    private int getSpeed() {
        return user.getSpd();
    }
    
    @Override
    public int compareTo(Action that) {
        return this.getSpeed() - that.getSpeed();
    }
    
    //if user died does nothing
    public void takeAction() {
        if(!(user.isDead())) {
            System.out.println(flavtext);
            for(Character r: recievers) {
                r.takeEffect(raw, skillcharge, strChange, 
                		defChange, spdChange, time, revive);
            }
        }
        else {
        	System.out.println(user + " is defeated and can not act");
        }
    }
    
    public int getUser(){
	return userIndex;
    }
    
    public boolean friend(){
	return friend;
    }
    
    public boolean hostile(){
	return hostile;
    }
    
    public void setUserIndex(int i, boolean friend, boolean hostile){
	userIndex = i;
	this.friend = friend;
	this.hostile = hostile;
    }
    
    
    
    
}

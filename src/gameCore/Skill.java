package gameCore;

public class Skill {
    private String name, flavor;
    private int raw, time, usesMax, uses;
    private double skillcharge, strChange, defChange, spdChange;
    private boolean revive;
    
    //flavor in skills should be " blah blah " to add the user and target(s)
    
    public Skill(String name, String flavor, int raw, double sc, double strc,
                 double defc, double spdc, int time, boolean rev, int uses) {
        this.name = name;
        this.flavor = flavor;
        this.raw = raw;
        this.skillcharge = sc;
        this.strChange = strc;
        this.defChange = defc;
        this.spdChange = spdc;
        this.time = time;
        this.revive = rev;
        this.uses = this.usesMax = uses;
    }
    
    public boolean canUse() {
        if(uses > 0) {
            return true;
        }
        return false;
    }
    
    public void restore(double percent) {
        int num = (int) (usesMax * percent);
        if(usesMax < uses + num) {
            uses = usesMax;
        }
        else {
            uses = uses + num;
        }
    }
    public Action makeAction(Character user, Character...targets) {
        if(targets.length == 0) {
            System.out.println("targets is empty!");
            return null;
        }
        if(uses > 0) {
            String s = "";
            if(targets.length > 1) {
                s = user + flavor + "everyone";
            }
            else {
                s = user + flavor + targets[0];
            }
            uses--;
            return new Action(user, s, raw, skillcharge, strChange, defChange, 
            		spdChange, time, revive, targets);
        }
        return null;
    }
           
    public String toString() {
        return name;
    }
    
}

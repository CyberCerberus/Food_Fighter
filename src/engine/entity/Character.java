package engine.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.entity.attributes.Action;
import engine.entity.attributes.Buff;
import engine.entity.attributes.LevelUpBuild;
import engine.map.Map;

public abstract class Character extends Entity{

    private String name;
    private int maxHP, str, def, spd;
    private Buff strBuff, defBuff, spdBuff;
    private boolean dead;
    protected boolean movingUp, movingDown, movingRight, movingLeft;
    private boolean inCombat;

    public Character(String name, int maxHP, int str, int def, int spd,
	    Map map, int width, int height, BufferedImage[]...frames){

	super(map, width, height, frames);
	this.name = name;
	this.maxHP = maxHP;
	this.str = str;
	this.def = def;
	this.spd = spd;
	strBuff = new Buff();
	defBuff = new Buff();
	spdBuff = new Buff();
	dead = false;
	inCombat = false;
	movingUp = false;
	movingDown = false;
	movingRight = false;
	movingLeft = false;

    }

    public Character(String name, int maxHP, int str, int def, int spd,
	    int width, int height, int x, int y, BufferedImage[]...frames){

	super(width, height, x, y, frames);
	this.name = name;
	this.maxHP = maxHP;
	this.str = str;
	this.def = def;
	this.spd = spd;
	strBuff = new Buff();
	defBuff = new Buff();
	spdBuff = new Buff();
	dead = false;
	inCombat = false;
	movingUp = false;
	movingDown = false;
	movingRight = false;
	movingLeft = false;

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

    public void clearBuffs() {
	strBuff.clear();
	defBuff.clear();
	spdBuff.clear();
    }

    public abstract int getHP();

    public abstract Action attack(Character[] enemies);

    @Override
    public String toString() {
	if(dead) {
	    return name + " (dead)";
	}
	return name;
    }

    public void setUp(boolean b){movingUp = b;}
    public void setDown(boolean b){movingDown = b;}
    public void setRight(boolean b){movingRight = b;}
    public void setLeft(boolean b){movingLeft = b;}
    public void setCombat(boolean b){inCombat = b;}
    public boolean getCobmat(){return inCombat;}


    public void update(){


	if(getMap() != null){
	    getNextPosition();
	    calculateTileMapCollision();
	    setPosition(getTempx(), getTempy());
	}


    }

    public void getNextPosition(){
	int maxSpeed = 1;

	if(movingUp){
	    setVector(0, -1);
	    if(getDy() < - maxSpeed){
		setVector(0, -maxSpeed);
	    }
	}
	else if(movingDown){
	    setVector(0, 1);
	    if(getDy() > maxSpeed){
		setVector(0, maxSpeed);
	    }
	}
	else if(movingRight){
	    setVector(1, 0);
	    if(getDx() > maxSpeed){
		setVector(maxSpeed, 0);
	    }
	}
	else if(movingLeft){
	    setVector(-1, 0);
	    if(getDx() < -maxSpeed){
		setVector(-maxSpeed, 0);
	    }
	}
	else{
	    if(getDx() > 0){
		setVector(-1, 0);
		if(getDx() < 0){
		    setVector(0, 0);
		}
	    }
	    else if(getDx() < 0){
		setVector(1, 0);
		if(getDx() > 0){
		    setVector(0, 0);
		}
	    }
	    else if(getDy() < 0){
		setVector(0, -1);
		if(getDy() < 0){
		    setVector(0, 0);
		}
	    }
	    else if(getDy() > 0){
		setVector(0, 1);
		if(getDy() > 0){
		    setVector(0, 0);
		}
	    }

	}
    }

    @Override
    public void draw(Graphics2D g){
	super.draw(g);
	g.setColor(Color.WHITE);
	if(getMap() == null){
	    g.drawString(name, getx(), gety() - 1);


	}
	else{
	    g.drawString(name, getRelativex() + 5, getRelativey() + 15);
	}


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
}
package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import gameCore.Action;
import gameCore.Monster;
import gameCore.Party;
import gameCore.Room;
import gameCore.Skill;
import gameCore.Character;
import gameCore.Factory;
import gameCore.Invintory;

public class CombatState extends State {

    private Party party;
    private Room room;
    private Monster monster;
    private boolean inCombat;
    private ArrayList<Action> moves;
    private boolean chooseAction;
    private boolean chooseItem;
    private boolean chooseSkill;
    private boolean chooseMonster;
    private boolean allDead;
    private String[] currentOptions;
    private int optionsLength;
    private int currentChoice;
    private int currentHero;
    private int alive;
    private int readyHeros;
    private int timer, timer2;
    private boolean monsterReady;
    private ArrayList<Skill> skills;
    private Invintory invintory;
    private int heroCasting;
    private boolean casting;
    private int castingHerox, castingHeroy;
    private int[] casted;

    public CombatState(StateManager sm) {
	super(sm);
    }

    @Override
    public void init() {

	party = getStateManager().getParty();
	room = getStateManager().getMap().getCurrentRoom();
	monster = room.getMonster();
	inCombat = true;

	moves = new ArrayList<Action>();
	currentOptions = new String[] { "Attack", "Skills", "Items" };
	optionsLength = currentOptions.length;
	currentChoice = 0;
	currentHero = 0;
	chooseAction = true;
	chooseItem = false;
	chooseSkill = false;
	chooseMonster = false;
	allDead = false;
	alive = party.toArray().length;
	readyHeros = 0;
	timer = 0;
	timer2 = 0;
	monsterReady = false;
	invintory = party.getInvintory();
	casting = false;
	casted = new int[4];
	
	
    }

    @Override
    public void update() {
	party.update();
	monster.update();
	if (monster.isDead()) {
	    inCombat = false;
	}

	if (inCombat) {
	    handleInput();

	    Character[] c = party.toArray();
	    int talive = 0;

	    // Counting alive heros.
	    for (int i = 0; i < c.length; i++) {
		if (!c[i].isDead()) {
		    talive++;
		}
	    }
	    alive = talive;
	    if (alive == 0) {
		allDead = true;
	    }

	    // Setting current Hero's turn.
	    if (currentHero >= c.length || currentHero == 0) {
		for (int i = 0; i < c.length; i++) {
		    if (!c[i].isDead()) {
			currentHero = i;
			break;
		    }
		}
		if (currentHero >= c.length) {
		    allDead = true;
		}
	    }

	    if (readyHeros >= alive && !allDead) {
		chooseAction = false;
		if (!monsterReady) {
		    moves.add(monster.attack(party.toArray()));
		    monsterReady = true;
		    Collections.sort(moves);
		} else {
		    if (!moves.isEmpty()) {
			timer2++;
			if (timer2 > 100) {
			    timer2 = 0;
			    heroCasting = moves.get(0).getUser();
			    casted[heroCasting]++;
			    moves.get(0).takeAction();
			    moves.remove(0);
			    casting = true;
			    if (moves.isEmpty()) {
				casted = new int[4];
				chooseAction = true;
				monsterReady = false;
				readyHeros = 0;
			    }
			}

		    }
		}

	    }
	    //	    System.out.println("currentHero: " + currentHero);
	    //	    System.out.println("alive: " + alive);
	    //	    System.out.println("readHeros: " + readyHeros);

	    if (allDead) {
		timer++;
		if (timer > 500) {
		    getStateManager().setState(StateManager.MENU_STATE);
		}

	    }
	} else {
	    
	    timer++;
	    if (timer > 300) {
		room.setCombat(false);
		monster.setInCombat(false);
		Sound.stop("combat");
		Sound.loop("levelOne");
		getStateManager().retrieveState();
	    }
	}
    }

    @Override
    public void draw(Graphics2D g) {

	g.drawImage(Content.CAVE, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);

	monster.draw(g);
	party.draw(g);

	if ((chooseAction || chooseItem || chooseSkill) && !allDead && inCombat) {	    
	    Character c = party.toArray()[currentHero];
	    int x = c.getPosition()[0] + 20;
	    int y = c.getPosition()[1] - 40;
	    int height = 20 * optionsLength;
	    // HUD
	    g.setColor(Color.BLACK);
	    g.fillRect(0, 0, 100, 60);
	    g.setColor(Color.GREEN);
	    int hp = c.getHP();
	    int max = c.getMax();
	    int str = c.getStr();
	    int def = c.getDef();
	    int spd = c.getSpd();


	    g.drawString(c.toString(), 0, 10);
	    g.drawString("Health: " + hp + "/" + max, 0, 20);
	    g.drawString("Strenth: " + str, 0, 30);
	    g.drawString("Defence: " + def, 0, 40);
	    g.drawString("Speed: " + spd, 0, 50);

	    // MENU
	    g.drawImage(Content.COMBAT_MENU, x, y, 100, height, null);

	    for (int i = 0; i < optionsLength; i++) {
		g.setColor(Color.BLACK);
		if (currentChoice == i) {
		    g.setColor(Color.RED);
		}
		if(chooseSkill){
		    ArrayList<Skill> s = c.getSkills();
		    int uses = s.get(i).getUses();
		    g.drawString(uses + " " + currentOptions[i], x + 15, y + 15);
		    g.setColor(Color.WHITE);
		    g.drawString("<-ESC", 250, 10);
		}
		else if(chooseItem){
		    if(currentOptions[i] != null){
			g.drawString(invintory.getItems()[i] + " " + invintory.getItemName(i), x + 15, y + 15);
			g.setColor(Color.WHITE);
			g.drawString("<-ESC", 250, 10);
		    }

		}
		else{
		    g.drawString(currentOptions[i], x + 15, y + 15);			
		}
		height += 20;
		y += 15;

	    }

	}
	else{
	    int mx = monster.getPosition()[0];
	    int my = monster.getPosition()[1];

	    if(casting){
		castingHerox = party.toArray()[heroCasting].getPosition()[0];
		castingHeroy = party.toArray()[heroCasting].getPosition()[1];
		casting = false;
	    }	    
	    else if(castingHerox > mx){
		if(castingHerox != mx){
		    if(castingHerox - mx > 0){
			castingHerox += -1;
		    }
		    else{
			castingHerox += 1;
		    }
		    g.drawImage(Content.SELECT, castingHerox, 
			    castingHeroy, 25, 25, null);

		}
		if(castingHeroy != my){
		    if(castingHeroy - my > 0){
			castingHeroy += -1;
		    }
		    else{
			castingHeroy += 1;
		    }
		    g.drawImage(Content.SELECT, castingHerox, 
			    castingHeroy, 25, 25, null);
		}
	    }



	}



	if (allDead) {
	    g.drawString("GAME OVER", 100, 100);
	}

	if (!inCombat) {
	    g.drawString(monster.toString() + " is full and has fallen asleep!", 50, 50);
	}

    }

    private void select() {

	if (chooseAction) {
	    chooseAction();
	} else if (chooseItem) {
	    chooseItem();
	} else if (chooseSkill) {
	    chooseSkill();
	}

    }

    private void chooseAction() {
	if (currentChoice == 0) {	    
	    Character c = party.toArray()[currentHero];
	    Action a = new Action(c, c.toString() + " attacked " + monster.toString(), -1 * c.getStr(), false,
		    new Monster[] { monster });
	    a.setUserIndex(currentHero);
	    moves.add(a);
	    currentHero++;
	    readyHeros++;
	} 
	else if (currentChoice == 1) {
	    skills = party.toArray()[currentHero].getSkills();
	    if (skills.size() > 0) {
		optionsLength = skills.size();
		currentOptions = new String[skills.size()];
		int i = 0;
		for (Skill s : skills) {
		    currentOptions[i++] = s.toString();
		}
		chooseAction = false;
		chooseSkill = true;

	    } else {
		chooseSkill = false;
		chooseAction = true;

	    }

	}
	else if(currentChoice == 2){
	    currentOptions = invintory.getItemNames();
	    optionsLength = currentOptions.length;
	    chooseItem = true;
	    chooseAction = false;
	}

    }

    private void chooseItem() {
	for(int i = 0; i < optionsLength; i++){
	    if(currentChoice == i && currentOptions[i] != null){
		currentOptions = new String[] { "Attack", "Skills", "Items" };
		optionsLength = currentOptions.length;
		if(invintory.getItems()[i] > 0){
		    Action a = null;
		    if(i >= invintory.FIRSTATTACKINDEX){
			a = Factory.itemFactory(i, party.toArray()[currentHero], monster);
			a.setUserIndex(currentHero);
			moves.add(a);

		    }
		    else{
			a = Factory.itemFactory(i, party.toArray()[currentHero], party.toArray());
			a.setUserIndex(currentHero);
			moves.add(a);
		    }
		    invintory.getItems()[i]--;
		    currentHero++;
		    readyHeros++;		    
		}
		chooseItem = false;
		chooseAction = true;
		break;
	    }
	    else{
		currentChoice = 0;
	    }
	}
    }

    private void chooseSkill() {
	for (int i = 0; i < optionsLength; i++) {
	    if (currentChoice == i) {		
		currentOptions = new String[] { "Attack", "Skills", "Items" };
		optionsLength = currentOptions.length;		
		if(skills.get(i).getUses() > 0){
		    Action a = null;
		    if(i == 2){
			int w = findWeakHero();
			a = skills.get(i).makeAction(party.toArray()[currentHero], party.toArray()[w]);
			a.setUserIndex(currentHero);
			moves.add(a);
		    }
		    else{
			a = skills.get(i).makeAction(party.toArray()[currentHero], monster);
			a.setUserIndex(currentHero);
			moves.add(a);
		    }

		    chooseSkill = false;
		    chooseAction = true;
		    currentHero++;
		    readyHeros++;		    
		}
		else{
		    chooseSkill = false;
		    chooseAction = true;
		}
		break;

	    }

	}

    }

    private int findWeakHero(){	
	int hp = 1000;
	int weakest = 0;
	for(int i = 0; i < party.toArray().length; i++){
	    if(party.toArray()[i].getHP() < hp && !party.toArray()[i].isDead()){
		hp = party.toArray()[i].getHP();
		weakest = i;
	    }
	}

	return weakest;
    }

    @Override
    public void handleInput() {

	if (chooseAction || chooseItem || chooseSkill) {
	    if (Keys.isPressed(Keys.ENTER))
		select();
	    if (Keys.isPressed(Keys.UP)) {
		if (currentChoice > 0) {
		    currentChoice--;
		}
	    }
	    if (Keys.isPressed(Keys.DOWN)) {
		if (currentChoice < optionsLength - 1) {
		    currentChoice++;
		}
	    }
	} else if (chooseMonster) {
	    if (Keys.isPressed(Keys.ENTER))
		select();
	    if (Keys.isPressed(Keys.LEFT)) {
		if (currentChoice > 0) {
		    currentChoice--;
		}
	    }
	    if (Keys.isPressed(Keys.RIGHT)) {
		if (currentChoice < optionsLength - 1) {
		    currentChoice++;
		}
	    }
	}

	if(Keys.keyState[Keys.ESCAPE]){
	    currentOptions = new String[] { "Attack", "Skills", "Items" };
	    optionsLength = currentOptions.length;
	    chooseAction = true;
	    chooseItem = false;
	    chooseSkill = false;
	}

    }
}

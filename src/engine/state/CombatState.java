package engine.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import engine.content.BackGround;
import engine.content.Content;
import engine.entity.Hero;
import engine.entity.Monster;
import engine.entity.attributes.Action;
import engine.entity.attributes.Skill;
import engine.entity.party.Party;
import engine.factory.Factory;
import engine.entity.Character;
import engine.handlers.Keys;

public class CombatState extends State{

    private Hero navigator;
    private BufferedImage background;
    private BackGround bg;
    private boolean inCombat;
    private Monster monster;
    private Party p;
    private Hero hero;
    private boolean inBetweenTurns, gameOver;
    private int currentChoice, optionsLength;
    private int currentHero, aliveHeroes;
    private boolean chooseAction, chooseSkill, chooseItem;
    private ArrayList<Action> moves;
    private int timer, curTurnCount;


    public CombatState(StateManager sm){
	super(sm);
    }

    @Override
    public void init() {
	p = sm.getParty();
	background = Content.CAVE;
	bg = new BackGround(background, sm.PANEL_WIDTH, sm.PANEL_HEIGHT);
	inCombat = true;
	navigator = sm.getNavigator();
	monster = Factory.monsterFactory(0, navigator.getMonsterMult());

	inBetweenTurns = true;
	currentChoice = 0;
	optionsLength = 0;
	currentHero = 0;
	hero = (Hero)p.toArray()[currentHero];

	int x = 200; int y = 60;
	for(Character c : p.toArray()){
	    ((Hero)c).setPosition(x, y);	    
	    y += 40;
	}

	monster.setPosition(50	, 100);
	gameOver = false;
	chooseAction = true;

	moves = new ArrayList<>();
	for(Character c : p.toArray()){
	    Hero h = (Hero)c;
	    moves.add(h.getSkills().get(0).makeAction(h, monster));
	}
	aliveHeroes = p.toArray().length;
	timer = 0;
	curTurnCount = 0;
    }

    /****** Graphics *******/
    @Override
    public void update() {

	if(aliveHeroes == 0){
	    gameOver = true;
	}

	if(!gameOver && !monster.isDead()){
	    
	    monster.update();	    

	    if(currentHero == p.toArray().length){
		currentHero = 0;
	    }
	    int tempAlive = p.toArray().length;
	    for(int i = 0; i < p.toArray().length; i++){
		if(p.toArray()[i].isDead()){
		    tempAlive--;
		}
		if(p.toArray()[i].isDead() && i == currentHero){
		    currentHero++;
		    if(currentHero == p.toArray().length){
			currentHero = 0;
		    }

		}

	    }
	    if(tempAlive == 0){
		gameOver = true;
	    }
	    else{
		aliveHeroes = tempAlive;
	    }

	    hero = (Hero)p.toArray()[currentHero];
	    for(Character c : p.toArray()){
		((Hero)c).update();
	    }	
	    
	    handleInput();
	    
	}
	else{
	    timer++;
	}
	
	if(!inCombat)	    
		sm.retrieveState();

    }

    @Override
    public void draw(Graphics2D g) {	
	bg.draw(g);

	if(!gameOver){

	    if(!monster.isDead()){
		g.setColor(Color.RED);
		g.drawOval(monster.getx() + 15, monster.gety() + 28, 30, 30);		
	    }
	    monster.draw(g);

	    if(!hero.isDead()){
		g.setColor(Color.GREEN);
		g.drawOval(hero.getx() + 8, hero.gety() + 20, 15, 15);				
	    }

	    for(Character c : p.toArray()){		
		((Hero)c).draw(g);	    
	    }

	    if(inBetweenTurns){

		if(chooseAction)chooseAction(g);
		else if(chooseSkill)chooseSkill(g);
	    }
	    if(monster.isDead()){
		if(timer > 500){
		    inCombat = false;
		}
		g.setColor(Color.GREEN);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		g.drawString(monster.getName() + " is full!", Content.PANEL_WIDTH / 2 - 100, 25);
		g.setFont(new Font("comicsans", Font.PLAIN, 10));
		
	    }
	}


	if(gameOver){
	    if(timer > 500){
		System.exit(0);
	    }
	    g.setColor(Color.RED);
	    g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
	    g.drawString("GAME OVER", Content.PANEL_WIDTH / 2 - 100, Content.PANEL_HEIGHT / 2);

	}

    }

    private void select(){	

	if(chooseAction){

	    if(currentChoice == 0){
		curTurnCount++;
		if(curTurnCount == aliveHeroes){
		    moves.sort(null);
		    for(Action a : moves){
			a.takeAction();
		    }
		    if(!monster.isDead())
			monster.attack(p.toArray()).takeAction();
		    curTurnCount = 0;
		}
		else{
		    currentHero++;
		}

	    }		
	    else if(currentChoice == 1){
		chooseSkill = true;
		chooseAction = false;
	    }
	    else if(currentChoice == 2){
		chooseItem = true;
		chooseAction = false;
	    }
	}
	else if(chooseSkill){
	    chooseSkill = false;
	    chooseAction = true;
	    Hero h = (Hero)p.toArray()[currentChoice];
	    Skill s = h.getSkills().get(currentChoice);
	    if(h.isDead()){
		moves.remove(currentHero);
	    }
	    else{
		moves.set(currentHero, s.makeAction(h, monster));
	    }


	}
	else if(chooseItem){
	    chooseItem = false;
	    chooseAction = true;
	}
    }

    @Override
    protected void handleInput() {

	if(Keys.isPressed(Keys.ENTER)) select();
	if(Keys.isPressed(Keys.UP)) {
	    if(currentChoice > 0) {
		currentChoice--;
	    }
	}
	if(Keys.isPressed(Keys.DOWN)) {
	    if(currentChoice < optionsLength - 1) {
		currentChoice++;
	    }
	}
    }

    public boolean inCombat(){
	return inCombat;
    }


    private void chooseAction(Graphics2D g){
	optionsLength = 3;
	g.drawImage(Content.COMBAT_MENU, hero.getx() + 50, hero.gety(), 50, 50, null);
	g.setColor(Color.BLACK);
	g.drawString("Attack", hero.getx() + 70, hero.gety() + 15);
	g.drawString("Skills", hero.getx() + 70, hero.gety() + 28);
	g.drawString("Items", hero.getx() + 70, hero.gety() + 41);

	if(currentChoice == 0){
	    g.drawImage(Content.SELECT, hero.getx() + 55, hero.gety() + 4, 15, 15, null);
	}
	else if(currentChoice == 1){
	    g.drawImage(Content.SELECT, hero.getx() + 55, hero.gety() + 16, 15, 15, null);
	}
	else if(currentChoice == 2){
	    g.drawImage(Content.SELECT, hero.getx() + 55, hero.gety() + 28, 15, 15, null);
	}
    }

    private void chooseSkill(Graphics2D g){
	ArrayList<Skill> skills = ((Hero) (p.toArray()[currentHero])).getSkills();
	if(skills.size() > 0){
	    optionsLength = skills.size();
	}
	else{
	    chooseSkill = false;
	    chooseAction = true;
	    g.setColor(Color.RED);
	    g.drawString("No Skills!", hero.getx() + 70, hero.gety() + 15);
	    return;
	}
	int length = skills.size() * 17;

	g.drawImage(Content.COMBAT_MENU, hero.getx() + 50, hero.gety(), 50, length, null);
	g.setColor(Color.BLACK);
	int x = hero.getx() + 70; int y = hero.gety() + 15;
	for(Skill s : skills){
	    g.drawString(s.toString(), x, y);
	    y += 13;
	}	

	x = hero.getx() + 55; y = hero.gety() + 4;
	for(int i = 0; i < optionsLength; i++){
	    if(currentChoice == i){
		g.drawImage(Content.SELECT, x, y, 15, 15, null);
	    }
	    y += 12;
	}

    }
}

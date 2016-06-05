package main;
import java.awt.Graphics2D;

import gameCore.Map;
import gameCore.Party;




public class StateManager {

    private State[] states;
    private int currentState;
    private int savedState;
    private int levels;
    public final int PANEL_HEIGHT, PANEL_WIDTH;
    public static final int MENU_STATE = 0, LEVEL_1_STATE = 1, COMBAT_STATE = 10, LEVEL_2_STATE = 2, SANCTUARY_STATE = 11;
    private Map map;
    private Party party;
    


    public StateManager(int panelWidth, int panelHeight){
	states = new State[15];
	states[MENU_STATE] = new MenuState(this);
	states[SANCTUARY_STATE] = new SanctuaryState(this);
	states[LEVEL_1_STATE] = new LevelOneState(this);
	states[COMBAT_STATE] = new CombatState(this);
	
	levels = 2;	
	currentState = MENU_STATE;
	states[currentState].init();
	this.PANEL_HEIGHT = panelHeight;
	this.PANEL_WIDTH = panelWidth;

    }

    public void setState(int state){
	savedState = currentState;
	currentState = state;
	states[currentState].init();

    }

    public void retrieveState(){
	currentState = savedState;
    }
    public void update(){
	states[currentState].update();
    }

    public void draw(Graphics2D g){
	states[currentState].draw(g);
    }

    public LevelState[] getLevelStates(){	
	LevelState[] s = {(LevelState)states[LEVEL_1_STATE], 
		(LevelState)states[LEVEL_2_STATE]};	
	
	return s;
    }
    public boolean levelExists(int level){
	return level <= levels;
    }
    
    public void setMap(Map map){
	this.map = map;
    }
    
    public void setParty(Party p){
	party = p;
    }
    
    public Map getMap(){
	return map;
    }
    
    public Party getParty(){
	return party;
    }

}

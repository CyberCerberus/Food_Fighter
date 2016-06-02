package engine.state;

import java.awt.Graphics2D;
import java.util.ArrayList;

import engine.entity.Hero;
import engine.entity.party.Party;

public class StateManager {
    
    private Hero navigator;
    private Party party;
    private ArrayList<State> states;
    private int currentState;
    private int savedState;
    public final int PANEL_HEIGHT, PANEL_WIDTH;
    public static final int MENU_STATE = 0, LEVEL_1_STATE = 1, COMBAT_STATE = 2;
    
    
    public StateManager(int panelWidth, int panelHeight){
	states = new ArrayList<>();
	states.add(new MenuState(this));
	states.add(new Level1State(this));
	states.add(new CombatState(this));
	
	currentState = MENU_STATE;
	states.get(MENU_STATE).init();
	this.PANEL_HEIGHT = panelHeight;
	this.PANEL_WIDTH = panelWidth;

    }
    
    public void setState(int state){
	savedState = currentState;
	currentState = state;
	states.get(state).init();
	
    }
    
    public void retrieveState(){
	currentState = savedState;
    }
    public void update(){
	states.get(currentState).update();
    }
    
    public void draw(Graphics2D g){
	states.get(currentState).draw(g);
    }
    
    public Party getParty(){return party;}
    public void setParty(Party p){party = p;}
    public Hero getNavigator(){return navigator;}
    public void setNavigator(Hero n){navigator = n;}
    
}

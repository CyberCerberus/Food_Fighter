package engine.state;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.entity.Hero;
import engine.entity.party.Party;
import engine.factory.Factory;
import engine.map.Map;
import engine.handlers.Keys;
	
public class Level1State extends State{
    
    private Map map;
    private Party p;
    private boolean combatSwitch;
    private Hero player;
    
    public Level1State(StateManager sm){
	super(sm);
    }
    @Override
    public void init() {
		
	map = Factory.mapFactory(0);	
	map.setPosition(0, 0);
	
	p = new Party();
	p.addHero(Factory.heroFactory("Johny", 0, p));
	p.addHero(Factory.heroFactory("Garry", 0, p));
	p.addHero(Factory.heroFactory("Bobby", 0, p));
	p.addHero(Factory.heroFactory("Poppy", 0, p));
	sm.setParty(p);
	
	player = Factory.heroFactory("Party", 0, p);
	player.setMap(map);
	sm.setNavigator(player);
	combatSwitch = false;		
    }

    @Override
    public void update() {
	if(combatSwitch){
	    removeMonster();
	    combatSwitch = false;
	}
	handleInput();
	player.update();
	if(player.getCobmat()){
	    combatSwitch = true;
	    sm.setState(StateManager.COMBAT_STATE);
	}
	map.setPosition(sm.PANEL_WIDTH / 2 - player.getx(), sm.PANEL_HEIGHT / 2 - player.gety());
	
    }

    @Override
    public void draw(Graphics2D g) {
	//Clear the screen.
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, sm.PANEL_WIDTH, sm.PANEL_HEIGHT);
	
	//Draw the tile map.
	map.draw(g);
	player.draw(g);
	
	
    }

    @Override
    protected void handleInput() {
	
	player.setUp(Keys.keyState[Keys.UP]);
	player.setDown(Keys.keyState[Keys.DOWN]);
	player.setLeft(Keys.keyState[Keys.LEFT]);
	player.setRight(Keys.keyState[Keys.RIGHT]);	
	
	
    }
    
    public void removeMonster(){
	player.removeMonster();
    }
    

}

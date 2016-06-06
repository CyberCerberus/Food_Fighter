package main;

import java.awt.Color;
import java.awt.Graphics2D;

import gameCore.Factory;
import gameCore.Map;
import gameCore.Party;

public class LevelOneState extends LevelState {

    // private Map map;
    private Party party;
    private boolean combatSwitch;
    private Map map;
    private int timer, timer2;
    private boolean showTittle;
    private boolean rewardAlert;
    private int rewardCount;
    private boolean cleared;
    // private Hero player;

    public LevelOneState(StateManager sm) {
	super(sm);
	setUnlocked(true);
    }

    @Override
    public void init() {

	party = new Party();

	if (godMode()) {
	    party.addHero(Factory.godFactory("Jeff", "Chef", 0, party));
	    party.addHero(Factory.godFactory("Beefcake", "Butcher", 0, party));
	    party.addHero(Factory.godFactory("Sunflower", "Vegan", 0, party));
	    party.addHero(Factory.godFactory("Leven", "Baker", 0, party));
	} else {
	    party.addHero(Factory.heroFactory("Jeff", "Chef", 0, party));
	    party.addHero(Factory.heroFactory("Beefcake", "Butcher", 0, party));
	    party.addHero(Factory.heroFactory("Sunflower", "Vegan", 0, party));
	    party.addHero(Factory.heroFactory("Leven", "Baker", 0, party));
	}

	combatSwitch = false;
	map = Factory.mapFactory(0);
	getStateManager().setMap(map);
	getStateManager().setParty(party);
	timer = 0;
	timer2 = 0;
	showTittle = true;
	rewardCount = 0;
	cleared = false;
	Sound.init();
	Sound.load("/sound/metroid.wav", "levelOne");
	Sound.load("/sound/reaper.wav", "combat");
	Sound.load("/sound/gb.wav", "gotcha");
	Sound.reduceVolume("levelOne", -5);
	Sound.reduceVolume("combat", -10);

	Sound.loop("levelOne");

    }

    @Override
    public void update() {

	map.update();

	if (map.getCurrentRoom().getExplored() && map.getCurrentRoom().end()
		&& map.getCurrentRoom().getMonster().isDead() && !rewardAlert) {
	    cleared = true;
	    setCleared(true);

	    timer++;
	    if (timer == 1) {
		Sound.stop("levelOne");
		//Sound.play("gotcha");
	    }
	    if (timer > 200) {
		timer = 0;
		getStateManager().setState(StateManager.SANCTUARY_STATE);
		
	    }
	}

	if (!map.getCurrentRoom().getMonster().isDead()) {
	    rewardCount = 0;
	    rewardAlert = true;
	} else if (rewardAlert) {
	    timer2++;
	    if (timer2 < 200) {
		rewardAlert = true;
	    } else {
		timer2 = 0;
		rewardAlert = false;
	    }
	}
	handleInput();
	// player.update();
	if (map.getCurrentRoom().inCombat()) {

	    combatSwitch = true;
	    timer++;
	    if (timer == 1) {
		Sound.stop("levelOne");
		Sound.loop("combat");
	    }

	    if (timer > 200) {
		timer = 0;
		combatSwitch = false;

		getStateManager().setState(StateManager.COMBAT_STATE);

	    }
	}

    }

    @Override
    public void draw(Graphics2D g) {
	// Clear the screen.
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	g.setColor(Color.WHITE);

	map.draw(g);

	if (cleared && !rewardAlert) {
	    g.setColor(Color.YELLOW);
	    g.drawString(map.toString() + " cleared!", 50, 50);

	}
	if (showTittle) {
	    timer++;
	    if (timer > 100) {
		timer = 0;
		showTittle = false;
	    } else {
		g.drawString(map.toString(), 100, 50);
	    }
	}
	if (combatSwitch) {
	    String s = map.getCurrentRoom().getMonster().getName() + " has been engaged!";
	    g.drawString(s, 50, 50);
	}

	if (rewardAlert && map.getCurrentRoom().getMonster().isDead()) {
	    int id = map.getCurrentRoom().getItem()[0];
	    int num = map.getCurrentRoom().getItem()[1];
	    if (rewardCount < 1) {
		party.reward(id, num);
		party.rewardExp(map.getCurrentRoom().getMonster().rewardExp());
		party.clearBuffs();
		rewardCount++;
	    }
	    g.setColor(Color.GREEN);
	    String s = "Your were awarded " + num + " " + party.getInvintory().getItemName(id);
	    g.drawString(s, 50, 50);
	}

    }

    @Override
    public void handleInput() {

	map.getNavigator().moveUp(Keys.keyState[Keys.UP]);
	map.getNavigator().moveDown(Keys.keyState[Keys.DOWN]);
	map.getNavigator().moveLeft(Keys.keyState[Keys.LEFT]);
	map.getNavigator().moveRight(Keys.keyState[Keys.RIGHT]);
	if (Keys.keyState[Keys.ESCAPE]) {
	    Sound.stop("levelOne");
	    getStateManager().setState(StateManager.MENU_STATE);
	}


    }

}

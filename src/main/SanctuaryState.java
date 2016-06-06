package main;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class SanctuaryState extends State{
    private LevelState[] levelStates;
    private int currentChoice;
    private int timer;
    
    public SanctuaryState(StateManager sm){
	super(sm);
    }
    @Override
    public void init() {
	levelStates = getStateManager().getLevelStates();	
	currentChoice = 0;
	timer = 0;
	Sound.load("/sound/sweetVictory.wav", "sv");
    }

    @Override
    public void update() {
	handleInput();
	
	timer++;
	if(timer == 1 && levelStates[0].cleared()){
	    Sound.loop("sv");
	}
	
    }

    @Override
    public void draw(Graphics2D g) {
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
	g.setComposite(ac);
	g.drawImage(Content.BACKGROUND_MENU, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
	AlphaComposite full = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
	g.setComposite(full);
	
	g.setFont(new Font("Aerial", Font.PLAIN, 14));
	g.setColor(Color.WHITE);
	g.drawString("SANCTUARY", GamePanel.WIDTH / 2 - 40, 30);
	
	if(getStateManager().levelExists(2)){
	    g.drawImage(Content.BACKGROUND_CROPPED_LEVEL1, 50, 50, 100, 100, null);
	    g.drawImage(Content.BACKGROUND_CROPPED_LEVEL2, 180, 50, 100, 100, null);
	    g.setFont(new Font("Aerial", Font.PLAIN, 10));
	    g.drawString("Restaurant Le Dungeon", 45, 160);
	    g.drawString("Candy Factory", 197, 160);
	    
	    if(currentChoice == 0){
		g.setColor(Color.RED);
		g.drawOval(50, 50, 100, 100);
		if(levelStates != null && levelStates[0].unlocked() && !levelStates[0].cleared()){
		    g.setColor(Color.GREEN);
		    g.drawString("ENTER", 85, 180);
		}
		else if(levelStates[0] != null && levelStates[0].cleared()){
		    g.setColor(Color.RED);
		    g.drawString("CLEARED", 80, 180);
		}
	    }
	    else if(currentChoice == 1){
		g.setColor(Color.RED);
		g.drawOval(180, 50, 100, 100);
		if(levelStates[1] != null && levelStates[1].unlocked()){
		    g.setColor(Color.GREEN);
		    g.drawString("ENTER", 217, 180);
		}
		else {
		    g.setColor(Color.RED);
		    g.drawString("LOCKED", 212, 180);
		}
		
	    }
	    
	    
	}
	
	
	
    }
    
    private void select() {
	if(currentChoice == 0) {
	   if(levelStates[0].unlocked() && levelStates[0] != null && !levelStates[0].cleared()){
	       getStateManager().setState(StateManager.LEVEL_1_STATE);	      
	       
	       
	   }
	 
	}
	else if(currentChoice == 1) {
	    //System.exit(0);
	}
    }

    @Override
    public void handleInput() {
	if(Keys.isPressed(Keys.ENTER)){
	    Sound.play("menuoption");
	    select();
	}
	if(Keys.isPressed(Keys.RIGHT)) {
	    if(currentChoice < 1) {
		currentChoice++;
		Sound.play("menuoption");
	    }
	}
	if(Keys.isPressed(Keys.LEFT)) {
	    if(currentChoice > 0) {
		currentChoice--;
		Sound.play("menuoption");
	    }
	}
	if(Keys.isPressed(Keys.ESCAPE)){
	    Sound.stop("sv");
	    getStateManager().setState(StateManager.MENU_STATE);
	}
    }

}

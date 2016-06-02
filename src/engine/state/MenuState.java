package engine.state;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import engine.content.Content;
import engine.handlers.Keys;

public class MenuState extends State {

    private BufferedImage selectIcon;

    private int currentChoice = 0;
    private String[] options = {
	    "Start",
	    "Quit"
    };

    private Color titleColor;
    private Font titleFont;

    private Font font;
    private Font font2;

    public MenuState(StateManager sm) {		
	super(sm);			
    }

    @Override
    public void init(){
	try {
	    selectIcon = ImageIO.read(
		    getClass().getResourceAsStream("/hud/hamburger.png"));

	    titleColor = Color.WHITE;
	    titleFont = new Font("Times New Roman", Font.PLAIN, 28);
	    font = new Font("Arial", Font.PLAIN, 14);
	    font2 = new Font("Arial", Font.PLAIN, 10);

	}
	catch(Exception e) {
	    e.printStackTrace();
	}
    }

    @Override
    public void update() {
	handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, Content.PANEL_WIDTH, Content.PANEL_HEIGHT);
	AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
	g.setComposite(ac);
	g.drawImage(Content.BACKGROUND_FOOD, 0, 0, Content.PANEL_WIDTH, Content.PANEL_HEIGHT, null);
	AlphaComposite full = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
	g.setComposite(full);
	
	g.setColor(titleColor);
	g.setFont(titleFont);
	g.drawString("FOOD FIGHTER", 60, 90);

	g.setFont(font);
	g.setColor(Color.WHITE);
	g.drawString("Start", 145, 165);
	g.drawString("Quit", 145, 185);
	

	if(currentChoice == 0) g.drawImage(selectIcon, 123, 146, 25, 25, null);
	else if(currentChoice == 1) g.drawImage(selectIcon, 123, 166, 25, 25, null);

	g.setFont(font2);
	g.drawString("2016 Cyber Cerberus", 10, 232);

    }

    private void select() {
	if(currentChoice == 0) {
	    sm.setState(StateManager.LEVEL_1_STATE);
	 
	}
	else if(currentChoice == 1) {
	    System.exit(0);
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
	    if(currentChoice < options.length - 1) {
		currentChoice++;
	    }
	}
    }

}
package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MenuState extends State {

    private BufferedImage selectIcon;
    private int currentChoice;
    private String[] options = { "Start", "God Mode", "Quit" };

    public MenuState(StateManager sm) {
	super(sm);
    }

    @Override
    public void init() {
	currentChoice = 0;
	Sound.init();
	Sound.load("/sound/menuSelect.wav", "menuoption");
	Sound.reduceVolume("menuoption", -15);
    }

    @Override
    public void update() {
	handleInput();
    }

    @Override
    public void draw(Graphics2D g) {
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
	g.setComposite(ac);
	g.drawImage(Content.BACKGROUND_MENU, 0, 0, GamePanel.WIDTH, GamePanel.HEIGHT, null);
	AlphaComposite full = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
	g.setComposite(full);

	g.setColor(Color.WHITE);
	g.setFont(new Font("Aerial", Font.PLAIN, 10));
	int y = 165;
	for (String s : options) {
	    g.drawString(s, 145, y);
	    y += 15;
	}

	selectIcon = Content.SELECT;
	if (currentChoice == 0) {
	    g.drawImage(selectIcon, 123, 147, 25, 25, null);
	} else if (currentChoice == 1) {
	    g.drawImage(selectIcon, 123, 163, 25, 25, null);
	} else if (currentChoice == 2) {
	    g.drawImage(selectIcon, 123, 179, 25, 25, null);
	}
	g.setFont(new Font("Times New Roman", Font.PLAIN, 10));
	g.drawString("2016 Cyber Cerberus", 10, 232);
	if (getStateManager().getLevelStates()[0].godMode()) {
	    g.setColor(Color.GREEN);
	    g.drawString("Enabled", 200, 180);
	}

    }

    private void select() {
	if (currentChoice == 0) {
	    getStateManager().setState(StateManager.SANCTUARY_STATE);

	} else if (currentChoice == 1) {
	    getStateManager().getLevelStates()[0].toggleGodMode();
	} else if (currentChoice == 2) {
	    System.exit(0);
	}
    }

    @Override
    public void handleInput() {
	if (Keys.isPressed(Keys.ENTER)) {
	    Sound.play("menuoption");
	    select();
	}
	if (Keys.isPressed(Keys.UP)) {
	    if (currentChoice > 0) {
		currentChoice--;
		Sound.play("menuoption");
	    }
	}
	if (Keys.isPressed(Keys.DOWN)) {
	    if (currentChoice < options.length - 1) {
		currentChoice++;
		Sound.play("menuoption");
	    }
	}
	
    }

}
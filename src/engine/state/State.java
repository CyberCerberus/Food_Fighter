package engine.state;

import java.awt.Graphics2D;

public abstract class State {
    
    protected StateManager sm;
    
    public State(StateManager sm){
	this.sm = sm;
    }
    
    public abstract void init();
    public abstract void update();
    public abstract void draw(Graphics2D g);
    protected abstract void handleInput();
}

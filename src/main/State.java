package main;
import java.awt.Graphics2D;

public abstract class State {
    
    private StateManager sm;
    
    public State(StateManager sm){
	this.sm = sm;
    }
    
    public abstract void init();
    public abstract void update();
    public abstract void draw(Graphics2D g);
    public abstract void handleInput();
    public StateManager getStateManager(){
	return sm;
    }
}

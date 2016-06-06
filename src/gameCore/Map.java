package gameCore;

import java.awt.Graphics2D;
import java.util.Observable;


public class Map extends Observable{
    private Navigator navigator;
    private Room[][] rooms;
    private String name;
    private int level;
    private int startx;
    private int starty;
    private int navx, navy;

    public Map(int l, String n, int h, int w) {
	level = l;
	name = n;
	rooms = new Room[h][w];
	navigator = new Navigator(50, 100);
	
	navx = navigator.getPosition()[0];
	navy = navigator.getPosition()[1];
	
	
    }

    
    void setStart(int[] xy) {
	startx = xy[0];
	starty = xy[1];
	
	
    }

    public int[] start(){
	int[] i = {startx, starty};
	return i;
    }
    	   
    @Override
    public String toString() {
	return name;
    }

    public Room getRoom(int x, int y) {
	return rooms[x][y];
    }
    
    public void setCurrentRoom(int x, int y){
	rooms[startx][starty].removeObservable();
	startx = x;
	starty = y;
	navigator.setRoom(rooms[startx][starty]);
	rooms[startx][starty].setObservable(this);	
	
    }
    public Room getCurrentRoom(){
	return rooms[startx][starty];
    }

    void addRoom(int x, int y, Room r) {
	rooms[x][y] = r;
	if(x == startx && y == starty){
	    navigator.setRoom(rooms[starty][startx]);
	    rooms[startx][starty].setObservable(this);
	}
	
    }

    public int getLevel() {
	return level;
    }
    
    public Navigator getNavigator(){
	return navigator;
    }
    
     
    public void update(){
	if(getCurrentRoom().getExplored() && navigator.moveRoom() != -1){
	    int room = navigator.moveRoom();
	    if(room == 2){
		setCurrentRoom(startx - 1, starty);
		navigator.moveRoom(-1);
	    }
	    else if(room == 3){
		setCurrentRoom(startx, starty + 1);
		navigator.moveRoom(-1);
	    }
	    else if(room == 4){
		setCurrentRoom(startx + 1, starty);
		navigator.moveRoom(-1);
	    }
	    else if(room == 5){
		setCurrentRoom(startx, starty - 1);
		navigator.moveRoom(-1);
	    }
	   
	}
	
	navigator.update();
	setChanged();
		
	navx = navigator.getPosition()[0];
	navy = navigator.getPosition()[1];	

	notifyObservers(new Report(navx/20, navy/20));
    }
    
 
    public void draw(Graphics2D g){
	rooms[startx][starty].draw(g);
	navigator.draw(g);
    }
}

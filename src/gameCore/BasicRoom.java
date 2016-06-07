package gameCore;

import java.awt.Graphics2D;
import java.util.Observable;

import main.Content;
import main.GamePanel;

public class BasicRoom extends Room {
    private int item;
    private int itemnum;
    private int mon1;
    private boolean end;
    private int[][] room;
    private boolean[][] discovered;
    private Monster monster;
    private boolean monsterDiscovered;
    private boolean inCombat;
    private int type;


    public BasicRoom(boolean[] walls) {
	//empty room
	item = -1;
	itemnum = 0;
	mon1 = -1;
	end = false;
	super.setWalls(walls);
	room = new int[GamePanel.HEIGHT/20][GamePanel.WIDTH/20];
	discovered = new boolean[GamePanel.HEIGHT/20][GamePanel.WIDTH/20];
	monsterDiscovered = false;
	inCombat = false;
	initTileRoomMap();

    }

    public BasicRoom(boolean[] walls, boolean e) {
	//empty room
	item = -1;
	itemnum = 0;
	mon1 = -1;
	end = e;
	super.setWalls(walls);
	room = new int[GamePanel.HEIGHT/20][GamePanel.WIDTH/20];
	discovered = new boolean[GamePanel.HEIGHT/20][GamePanel.WIDTH/20];
	monsterDiscovered = false;
	inCombat = false;
	initTileRoomMap();

    }

    public BasicRoom(int i, int in, int multi, int m, boolean[] walls, boolean end, int type) {
	super();
	item = i;
	itemnum = in;
	mon1 = m;
	this.end = end;
	monster = Factory.monsterFactory(mon1, multi);
	room = new int[GamePanel.HEIGHT/20][GamePanel.WIDTH/20];
	discovered = new boolean[GamePanel.HEIGHT/20][GamePanel.WIDTH/20];
	super.setWalls(walls);
	monsterDiscovered = false;
	inCombat = false;
	monster.setInCombat(false);
	this.type = type;
	initTileRoomMap();
    }

    private void initTileRoomMap(){
	for(int c = 0; c < room.length; c++){
	    for(int r = 0; r < room[c].length; r++){

		//Left door
		if(c == room.length/ 2 && r == 0 && getWalls()[2]){			

		    room[c][r] = 2;
		}
		//Top dooor
		else if(r == room[c].length / 2 && c == 0 && getWalls()[0]){

		    room[c][r] = 3;
		}
		//Right door
		else if(c == room.length / 2 && r == room[c].length - 1 && getWalls()[3]){

		    room[c][r] = 4;
		}
		//Bottom door
		else if(r == room[c].length / 2 && c == room.length - 1 && getWalls()[1]){

		    room[c][r] = 5;
		}
		else if(c == 0 || r == 0 || c == room.length - 1 || r == room[c].length - 1){

		    room[c][r] = 1;
		}
		else{

		    room[c][r] = 0;
		}


	    }
	}

    }

    @Override
    public boolean triggerEvent(Party p) throws GameOverException {
	if(!super.getExplored()) {
	    System.out.println("You encountered Monsters!");
	    // Battle.fight(p, Factory.monsterFactory(mon1, multi));
	    System.out.println("You found some items!");
	    p.reward(item, itemnum);
	    super.explored(true);
	    return end;
	}
	else {
	    System.out.println("The room is empty");
	    return end;
	}
    }
    
    @Override
    public boolean end(){
	return end;
    }

    @Override
    public void draw(Graphics2D g) {	    
	for(int c = 0; c < room.length; c++){
	    for(int r = 0; r < room[c].length; r++){

		if(discovered[c][r]){
		    if(c == room.length/ 2 && r == 0 && getWalls()[2]){			
			g.drawImage(Content.DOOR, r * 20, c * 20, 20, 20, null);
			g.drawImage(Content.DOOR, r * 20, c * 20 - 20, 20, 20, null);
			//room[c][r] = 2;
		    }
		    else if(r == room[c].length / 2 && c == 0 && getWalls()[0]){
			g.drawImage(Content.DOOR, r * 20, c * 20, 20, 20, null);
			g.drawImage(Content.DOOR, r * 20 - 20, c * 20, 20, 20, null);
			//room[c][r] = 2;
		    }
		    else if(c == room.length / 2 && r == room[c].length - 1 && getWalls()[3]){
			g.drawImage(Content.DOOR, r * 20, c * 20, 20, 20, null);
			g.drawImage(Content.DOOR, r * 20, c * 20 - 20, 20, 20, null);
			//room[c][r] = 2;
		    }
		    else if(r == room[c].length / 2 && c == room.length - 1 && getWalls()[1]){
			g.drawImage(Content.DOOR, r * 20, c * 20, 20, 20, null);
			g.drawImage(Content.DOOR, r * 20 - 20, c * 20, 20, 20, null);
			//room[c][r] = 2;
		    }
		    else if(c == 0 || r == 0 || c == room.length - 1 || r == room[c].length - 1){
			g.drawImage(Content.GRAVEL_TILE, r * 20, c * 20, 20, 20,null);	
			//room[c][r] = 1;
		    }
		    else if(type == 0){
			g.drawImage(Content.WALL_TILE, r * 20, c * 20, 20, 20,null);
			//room[c][r] = 0;
		    }

		}
	    }
	}


	if(monsterDiscovered){
	    monster.draw(g);

	}


    }

    @Override
    public int getCollisionType(int c, int r){
	return room[c][r]; 
    }

    @Override
    public boolean inCombat(){
	return inCombat;
    }
    public boolean withing(int x, int y, int range){
	int x1 = monster.getPosition()[0];
	int y1 = monster.getPosition()[1];
	int x2 = x;
	int y2 = y;

	int distance = (int)Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
	return distance < range;
    }

    public int[][] getRoom(){
	return room;
    }

    @Override
    public boolean discovered(int c, int r){
	return discovered[c][r];
    }

    @Override
    public String getDescript() {
	String ret = "A standard room for testing purposes\n";
	ret += super.toString();
	return ret;
    }

    @Override
    public Monster getMonster(){
	return monster;
    }

    @Override
    public void update(Observable o, Object arg) {

	if(monster.isDead()){
	    explored(true);
	    
	}

	if(getExplored()){
	    for(int i = 0; i < discovered.length; i++){
		for(int j = 0; j < discovered[i].length; j++){
		    discovered[i][j] = true;
		}
	    }
	}
	else{
	    Report report = (Report)arg;
	    int r = report.getCoordinates()[0];
	    int c = report.getCoordinates()[1];
	    int tc = c;
	    int tr = r;

	    if(withing(r * 20, c * 20, 50) && !monster.isDead()){
		monster.setInCombat(true);
		inCombat = true;
		monsterDiscovered = true;
	    }


	    if(c >= 0 && c < discovered.length && r >= 0 && r < discovered[c].length && !getExplored()){
		discovered[c][r] = true;
		tr = c - 1; tc = r - 1;
		if(tr >= 0 && tr < discovered.length && tc >= 0 && tc < discovered[0].length){
		    discovered[tr][tc] = true;
		}
		tr = c; tc = r - 1;
		if(tr >= 0 && tr < discovered.length && tc >= 0 && tc < discovered[0].length){
		    discovered[tr][tc] = true;
		}
		tr = c + 1; tc = r - 1;
		if(tr >= 0 && tr < discovered.length && tc >= 0 && tc < discovered[0].length){
		    discovered[tr][tc] = true;
		}
		tr = c - 1; tc = r;
		if(tr >= 0 && tr < discovered.length && tc >= 0 && tc < discovered[0].length){
		    discovered[tr][tc] = true;
		}
		tr = c + 1; tc = r;
		if(tr >= 0 && tr < discovered.length && tc >= 0 && tc < discovered[0].length){
		    discovered[tr][tc] = true;
		}
		tr = c - 1; tc = r + 1;
		if(tr >= 0 && tr < discovered.length && tc >= 0 && tc < discovered[0].length){
		    discovered[tr][tc] = true;
		}
		tr = c; tc = r + 1;
		if(tr >= 0 && tr < discovered.length && tc >= 0 && tc < discovered[0].length){
		    discovered[tr][tc] = true;
		}
		tr = c + 1; tc = r + 1;
		if(tr >= 0 && tr < discovered.length && tc >= 0 && tc < discovered[0].length){
		    discovered[tr][tc] = true;
		}
	    }
	}





	monster.update();

    }

    @Override
    public void setCombat(boolean c) {
	inCombat = c;
    }

    @Override
    public int[] getItem(){
	return new int[]{item, itemnum};
    }

}

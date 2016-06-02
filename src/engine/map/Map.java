package engine.map;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Map {
    
    private String name;
    private int x, y, lvl;
    private int xmin, ymin, xmax, ymax;
    private int[][] map;
    private int tileSize;
    private int rows, cols, width, height;
    private Tile[] tiles;

    public Map(int lvl, String name, int tileSize, int panelHeight, int panelWidth, String mapPath, Tile...tiles){
	
	init(lvl, name, tileSize, panelHeight, panelWidth, mapPath, tiles);
    }

    private void init(int lvl, String name, int tileSize, int panelHeight, int panelWidth, String mapPath, Tile...tiles){
	this.tiles = tiles;
	this.tileSize = tileSize;
	this.name = name;
	this.lvl = lvl;

	try{
	    InputStream in = getClass().getResourceAsStream(mapPath);
	    BufferedReader br = new BufferedReader(new InputStreamReader(in));

	    cols = Integer.parseInt(br.readLine());
	    rows = Integer.parseInt(br.readLine());
	    map = new int[rows][cols];
	    width = cols * tileSize;
	    height = rows * tileSize;

	    xmin = panelWidth - width;
	    xmax = 0;
	    ymin = panelHeight - height;
	    ymax = 0;

	    String delims = "\\s+";
	    for(int row = 0; row < rows; row++){
		String line = br.readLine();
		String[] tokens = line.split(delims);
		for(int col = 0; col < cols; col++){
		    map[row][col] = Integer.parseInt(tokens[col]);
		}
	    }

	}
	catch(Exception e){
	    e.printStackTrace();
	}
    }

    public void setPosition(int x, int y){
	this.x = x;
	this.y = y;

	fixBounds();	
    }

    private void fixBounds() {
	if(x < xmin) x = xmin;
	if(x > xmax) x = xmax;
	if(y < ymin) y = ymin;
	if(y > ymax) y = ymax;
    }

    public void draw(Graphics2D g){

	for(int row = 0; row < rows; row++){	    
	    for(int col = 0; col < cols; col++){
		int type = map[row][col];
		if(type >= 2) type = 2;
		g.drawImage(tiles[type].getImage(), x + col * tileSize, y + row * tileSize, null);		
	    }
	}		
    }
    
    public boolean collides(int row, int col){
	if(map[row][col] == Tile.WALL){
	    return true;
	}
	return false;
    }
    
    public ArrayList<int[]> getMonsterPositions(){
	
	ArrayList<int[]> result = new ArrayList<>(); 
	
	for(int row = 0; row < map.length; row++){
	    for(int col = 0; col < map[row].length; col++){
		if(map[row][col] >= Tile.MONSTER){
		    int[] cords = {col * tileSize, row * tileSize, map[row][col]};
		    result.add(cords);
		}
	    }
	}
	
	return result;
	
    }
    
    public int getTileSize() { return tileSize; }
    public int getx() { return x; }
    public int gety() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getNumRows() { return rows; }
    public int getNumCols() { return cols; }
    public String getName() {return name;}



}

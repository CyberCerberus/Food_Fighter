package gameCore;

public class Report {
    private int x, y;
    
    public Report(int x, int y){
	this.x = x;
	this.y = y;
    }
    
    public void setCoordinates(int x, int y){
	this.x = x;
	this.y = y;
    }
    
    public int[] getCoordinates(){
	return new int[]{x, y};
    }
    
}

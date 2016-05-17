package gameCore;

public class Invintory {
    // index should corresponds to an item, contents should be the amount
    
	private int[] items;
	Invintory() {
		items = new int[]{1};
		
	}
	
	public int[] getItems(){
		return items;
	}
	
	public void decrementItem(int i){
		if(i >= 0 && i < items.length){
			if(items[i] > 0){
				items[i]--;
			}
		}
		else{
			throw new IllegalArgumentException("Invalid inventory index.");
		}
	}
}

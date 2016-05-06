package gameCore;

public class Party {
	private Hero slot1;
	private Hero slot2;
	private Hero slot3;
	private Hero slot4;
	private Invintory bag;
	
	public Character[] toArray() {
		Character[] c = {slot1, slot2, slot3, slot4};
		return c;
	}
	
	public Invintory getInvintory() {
		return bag;
	}
}

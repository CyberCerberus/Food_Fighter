package gameCore;

public class FullRevive extends Action{
	
	FullRevive(Character[] c) {
		super(new EmptyUser(), "All party members are revived",
				1, 0, 0.0, 0.0, 0.0, 0, true, c);
	}
}

package gameCore;

public class FullRestore extends Action{
	FullRestore(Character[] c) {
		super(new EmptyUser(), "Party is restored to full health",
				999, 1.0, 0.0, 0.0, 0.0, 0, false, c);
	}
}

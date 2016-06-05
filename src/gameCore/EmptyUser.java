package gameCore;

import java.util.ArrayList;

public class EmptyUser extends Character {

	public EmptyUser() {
		super("You", 1, 0, 0, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void takeEffect(int raw, double skillcharge, double strChange,
			double defChange, double spdChange, int time, boolean rev) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getHP() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Skill> getSkills() {
	    // TODO Auto-generated method stub
	    return null;
	}

//	@Override
//	public Action attack(Character[] enemies) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}

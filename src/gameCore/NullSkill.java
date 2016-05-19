package gameCore;

public class NullSkill extends Skill {
	public NullSkill() {
		super("", "", "", 0, 0.0, 0.0, 0.0, 0.0, 0, false, 0, false);
	}
	
	public String toString() {
		return "";
	}
	
	@Override
	public String display() {
		return "";
	}
}

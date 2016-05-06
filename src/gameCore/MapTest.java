package gameCore;

public class MapTest {
	public static void main(String...args) {
		Map map = new Map(1, 3);
		boolean[] w = {true, false, false, false};
		map.addRoom(0, 0, new BasicRoom(w));
		boolean[] w1 = {true, true, false, false};
		map.addRoom(0, 1, new BasicRoom(w1));
		boolean[] w2 = {false, true, false, false};
		map.addRoom(0, 2, new BasicRoom(w2, true));
		int[] i = {0,0};
		map.setStart(i);
		
		Cursor me = new Cursor(map, new Party());
		me.playMap();
		
	}
}

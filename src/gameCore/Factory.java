package gameCore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Factory {
	public static Map mapFactory(int level) {
		//SQL call to the levle table to get the relevant information
		//then calls the room factory to fill the empty map
		Connection conn = SQL.connectFF();
		PreparedStatement selectMap;
		PreparedStatement selectRooms;
		ResultSet mapres;
		ResultSet rooms;
		int multi, x, y, endi;
		boolean end = false;
		Map map = null;
		String sql1 = "SELECT * FROM Levels WHERE ? = levelid";
		String sql2 = "SELECT * FROM Rooms Where ? = levelid";
		try {
			selectMap = conn.prepareStatement(sql1);
			selectRooms = conn.prepareStatement(sql2);
			selectMap.setInt(1, level);
			selectRooms.setInt(1, level);
			mapres = selectMap.executeQuery();
			
			map = new Map(level, mapres.getString(2), mapres.getInt(3), mapres.getInt(4));
			multi = mapres.getInt(5);
			
			rooms = selectRooms.executeQuery();
			
			while(rooms.next()) {
				x = rooms.getInt(2);
				y = rooms.getInt(3);
				endi = rooms.getInt(5);
				
				if(endi < 0) {
					int[] temp = {x, y};
					map.setStart(temp);
				}
				else if (endi > 0){
					end = true;
				}
				
				Room r = roomFactory(rooms.getInt(6), rooms.getInt(7),rooms.getInt(8),
						multi, rooms.getInt(9), rooms.getInt(10), rooms.getInt(11), 
						rooms.getInt(12), end, rooms.getInt(4));				
				map.addRoom(x, y, r);
				
				end = false;
			}
			conn.close();
		}
		catch(SQLException e) {
			System.err.println("Something went wrong in the database\n" + e.getMessage());
		}
		return map;
	}
	
	private static Room roomFactory(int type, int item, int itemn, int multi,
			int mon1, int mon2, int mon3, int mon4, boolean end, int wallsn) {
		/* SQL call to the room and roomtype tables to get the relevant information
		 * note that the walls is determined by an int from 0-15 in a binary fashion
		 * so you can use >> and mod 2 to figure out what walls there are
		 * 0001 up, 0010 down, 0100 left, 1000 right
		 */
		boolean [] walls = new boolean[4];
		for(int i = 0; i < 4; i++) {
			int temp = wallsn % 2;
			if(temp == 1) {
				walls[i] = true;
			}
			wallsn = wallsn >> 1;
		}
		Room r = null;
		if(type == 1) {
			r = new BasicRoom(item, itemn, multi, mon1, walls, end);
		}
		else {
			r = new BasicRoom(item, itemn, multi, mon1, walls, end);
		}
		
		return r;
	}
	
	public static Action itemFactory(int itemid, Character user, Character[] targets) {
		Connection conn = SQL.connectFF();
		PreparedStatement selectItem;
		ResultSet item;
		Action a = null;
		String sql = "SELECT * FROM ItemList WHERE ? = itemid";
		try {
			selectItem = conn.prepareStatement(sql);
			selectItem.setInt(1, itemid);
			item = selectItem.executeQuery();
			a = new Action(user, user + " used " + item.getString(2), 
					item.getInt(4), item.getDouble(5), item.getDouble(6),
					item.getDouble(7), item.getDouble(8), item.getInt(9), 
					item.getBoolean(10), targets);
			conn.close();
		}
		catch(SQLException e) {
			System.err.println("Something went wrong in the database\n" + e.getMessage());
		}
		
		return a;
	}
	
	public static Monster monsterFactory(int monid, int multi) {
		//SQL call to the bestiary table to get the relevant information
		Connection conn = SQL.connectFF();
		PreparedStatement selectMonster;
		ResultSet monster;
		Monster m = null;
		String sql = "SELECT * FROM Bestiary WHERE ? = monid";
		try {
			selectMonster = conn.prepareStatement(sql);
			selectMonster.setInt(1, monid);
			monster = selectMonster.executeQuery();
			Skill s1 = skillFactory(monster.getInt(8));
			Skill s2 = skillFactory(monster.getInt(10));
			Skill s3 = skillFactory(monster.getInt(12));
			
			m = new Monster(monster.getString(2), monster.getInt(4),
					monster.getInt(5), monster.getInt(6), monster.getInt(7), 
					s1, monster.getDouble(9), s2, monster.getDouble(11), s3, 
					monster.getDouble(13));
			conn.close();
		}
		catch(SQLException e) {
			System.err.println("Something went wrong in the database\n" + e.getMessage());
		}
		return m;
	}
	
	public static Hero heroFactory(String name, int heronum, Party p) {
		//no need to use database just plug in the base values and skills
		Hero h = null;
		if(heronum == 1) {
			
		}
		else {
			Skill s1 = new Skill("Stab", " stabbed ", -75, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
			Skill s2 = new Skill("Kick", " kicked ", -75, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
			Skill s3 = new Skill("Slap", " slapped ", -75, 0.0, 0.0, 0.0, 0.0, 0, false, 5);
			
			h = new Hero(name, "Cook", 150, 25, 25, 25, p, s1, s2, s3);
		}
		return h;
	}
	
	private static Skill skillFactory(int skillid) {
		//SQL call to the skill table to get the relevant information
		if(skillid == -1) {
			return null;
		}
		Connection conn = SQL.connectFF();
		PreparedStatement selectSkill;
		ResultSet skill;
		Skill s = null;
		String sql = "SELECT * FROM SkillList WHERE ? = skillid";
		try {
			selectSkill = conn.prepareStatement(sql);
			selectSkill.setInt(1, skillid);
			skill = selectSkill.executeQuery();
			s = new Skill(skill.getString(2), skill.getString(4), 
					skill.getInt(5), skill.getDouble(6), skill.getDouble(7),
					skill.getDouble(8), skill.getDouble(9), skill.getInt(10),
					skill.getBoolean(11), skill.getInt(12));
			conn.close();
		}
		catch(SQLException e) {
			System.err.println("Something went wrong in the database\n" + e.getMessage());
		}
		
		return s;
	}
	
}

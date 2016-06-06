package gameCore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Factory {
    private static final int LIGHT = 1;
    private static final int MEDIUM = 2;
    private static final int HEAVY = 3;
    //if you add a build i have the stats add to 12
    private static final LevelUpBuild BALBUILD = new LevelUpBuild(4,4,4);
    private static final LevelUpBuild STRBUILD = new LevelUpBuild(6,3,3);
    private static final LevelUpBuild DEFBUILD = new LevelUpBuild(3,7,2);
    private static final LevelUpBuild SPDBUILD = new LevelUpBuild(3,3,6);
    private static final LevelUpBuild STRSPDBUILD = new LevelUpBuild(5,2,5);
    private static final LevelUpBuild STRDEFBUILD = new LevelUpBuild(5,5,2);



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
	    r = new BasicRoom(item, itemn, multi, mon1, walls, end, type);
	}
	else {
	    r = new BasicRoom(item, itemn, multi, mon1, walls, end, type);
	}

	return r;
    }

    public static Action itemFactory(int itemid, Character user, Character...targets) {
	Connection conn = SQL.connectFF();
	PreparedStatement selectItem;
	ResultSet item;
	Action a = null;
	String sql = "SELECT * FROM ItemList WHERE ? = itemid";
	try {
	    selectItem = conn.prepareStatement(sql);
	    selectItem.setInt(1, itemid);
	    item = selectItem.executeQuery();
	    a = new Action(user, user + " used a(n) " + item.getString(2), 
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
	    Skill s1 = skillFactory(monster.getInt(9));
	    Skill s2 = skillFactory(monster.getInt(11));
	    Skill s3 = skillFactory(monster.getInt(13));

	    m = new Monster(monster.getString(2),monster.getInt(4), 
		    monster.getInt(5) * multi, monster.getInt(6)  * multi, 
		    monster.getInt(7)  * multi, monster.getInt(8)  * multi, 
		    s1, monster.getDouble(10), s2, monster.getDouble(12), 
		    s3, monster.getDouble(14));
	    conn.close();
	}
	catch(SQLException e) {
	    System.err.println("Something went wrong in the database\n" + e.getMessage());
	}
	return m;
    }

    public static Hero heroFactory(String name, String c, int heronum, Party p) {
	//no need to use database just plug in the base values and skills
	Hero h = null;
	if(heronum == 1) {

	}
	else {
	    Skill s1 = null;
	    Skill s2 = null;
	    Skill s3 = null;
	    
	    /*
	     * Skill1: hostile
	     * Skill2: hostile
	     * Skill3: friendly
	     */

	    switch(c){
	    case "Butcher":
		s1 = skillFactory(1);
		s2 = skillFactory(6);
		s3 = skillFactory(4);
		h = new Hero(name, c, 185, 60 , 8, 20, p, s1, s2, s3, HEAVY, DEFBUILD);
		break;
	    case "Chef":
		s1 = skillFactory(5);
		s2 = skillFactory(10);
		s3 = skillFactory(7);
		h = new Hero(name, c, 150, 40, 10, 10, p, s1, s2, s3, MEDIUM, STRDEFBUILD);
		break;
	    case "Vegan":
		s1 = skillFactory(8);
		s2 = skillFactory(3);
		s3 = skillFactory(9);
		h = new Hero(name, c, 115, 25, 15, 7, p, s1, s2, s3, LIGHT, SPDBUILD);
		break;
	    case "Baker":
		s1 = skillFactory(5);
		s2 = skillFactory(6);
		s3 = skillFactory(12);
		h = new Hero(name, c, 130, 35, 10, 10, p, s1, s2, s3, LIGHT, STRSPDBUILD);
		break;
	    }

	}
	return h;
    }
    
    public static Hero godFactory(String name, String c, int heronum, Party p) {
   	//no need to use database just plug in the base values and skills
   	Hero h = null;
   	if(heronum == 1) {

   	}
   	else {
   	    Skill s1 = null;
   	    Skill s2 = null;
   	    Skill s3 = null;
   	    
   	    /*
   	     * Skill1: hostile
   	     * Skill2: hostile
   	     * Skill3: friendly
   	     */

   	    switch(c){
   	    case "Butcher":
   		s1 = skillFactory(1);
   		s2 = skillFactory(6);
   		s3 = skillFactory(5);
   		h = new Hero(name, c, 1000, 1000 , 1000, 1, p, s1, s2, s3, HEAVY, DEFBUILD);
   		break;
   	    case "Chef":
   		s1 = skillFactory(5);
   		s2 = skillFactory(10);
   		s3 = skillFactory(8);
   		h = new Hero(name, c, 1000, 1000, 1000, 1, p, s1, s2, s3, MEDIUM, STRDEFBUILD);
   		break;
   	    case "Vegan":
   		s1 = skillFactory(8);
   		s2 = skillFactory(3);
   		s3 = skillFactory(6);
   		h = new Hero(name, c, 1000, 1000, 1000, 1, p, s1, s2, s3, LIGHT, SPDBUILD);
   		break;
   	    case "Baker":
   		s1 = skillFactory(5);
   		s2 = skillFactory(6);
   		s3 = skillFactory(1);
   		h = new Hero(name, c, 1000, 1000, 1000, 1, p, s1, s2, s3, LIGHT, STRSPDBUILD);
   		break;
   	    }

   	}
   	return h;
       }

    static Skill skillFactory(int skillid) {
	//SQL call to the skill table to get the relevant information
	if(skillid == -1) {
	    return new NullSkill();
	}
	Connection conn = SQL.connectFF();
	PreparedStatement selectSkill;
	ResultSet skill;
	Skill s = new NullSkill();
	String sql = "SELECT * FROM SkillList WHERE ? = skillid";
	try {
	    selectSkill = conn.prepareStatement(sql);
	    selectSkill.setInt(1, skillid);
	    skill = selectSkill.executeQuery();
	    s = new Skill(skill.getInt(1), skill.getString(2), skill.getString(3),
		    skill.getString(4), skill.getInt(5), skill.getDouble(6), 
		    skill.getDouble(7), skill.getDouble(8), skill.getDouble(9), 
		    skill.getInt(10), skill.getBoolean(11), skill.getInt(12), 
		    skill.getBoolean(13));
	    conn.close();
	}
	catch(SQLException e) {
	    System.err.println("Something went wrong in the database\n" + e.getMessage());
	}

	return s;
    }

    public static Equipment equipFactory(String equip) {
	Connection conn = SQL.connectFF();
	PreparedStatement selectItem;
	ResultSet item;
	Equipment e = new Hat();
	String sql = "SELECT * FROM EquipList WHERE name LIKE ?";
	try {
	    selectItem = conn.prepareStatement(sql);
	    selectItem.setString(1, equip);
	    item = selectItem.executeQuery();
	    if(item.next()) {
		e = new Equipment(item.getString(2), item.getInt(1), item.getInt(4), 
			item.getInt(5), item.getInt(6), item.getInt(7), item.getInt(8));
	    }
	    conn.close();
	}
	catch(SQLException excpet) {
	    System.err.println("Something went wrong in the database\n"
		    + excpet.getMessage());
	}

	return e;
    }

    public static Equipment equipFactory(int equipid) {
	if(equipid == -1) {
	    return new BareHands();
	}
	else if(equipid == -2) {
	    return new Cloths();
	}
	else if(equipid == -3) {
	    return new Hat();
	}
	Connection conn = SQL.connectFF();
	PreparedStatement selectItem;
	ResultSet item;
	Equipment e = new Hat();
	String sql = "SELECT * FROM EquipList WHERE itemid = ?";
	try {
	    selectItem = conn.prepareStatement(sql);
	    selectItem.setInt(1, equipid);
	    item = selectItem.executeQuery();
	    if(item.next()) {
		e = new Equipment(item.getString(2), item.getInt(1), item.getInt(4), 
			item.getInt(5), item.getInt(6), item.getInt(7), item.getInt(8));
	    }
	    conn.close();
	}
	catch(SQLException excpet) {
	    System.err.println("Something went wrong in the database\n"
		    + excpet.getMessage());
	}

	return e;
    }

}

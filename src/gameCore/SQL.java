package gameCore;
import java.sql.*;

public class SQL {
	
	static Connection connectFF() {
		  Connection c = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:FoodFight.db");
		      //System.out.println("Opened database successfully");
		    } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		return c;
	}
	
	public static void verify() {
		System.out.println("Cheaking files...");
		try {
			Connection conn = connectFF();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM ItemList;";
			ResultSet res = stmt.executeQuery(sql);
			int count = 0;
			while(res.next()) {
				count++;
			}
			if(count != 2) {
				throw new SQLException();
			}
			sql = "SELECT * FROM EquipList;";
			res = stmt.executeQuery(sql);
			count = 0;
			while(res.next()) {
				count++;
			}
			if(count != 5) {
				throw new SQLException();
			}
			sql = "SELECT * FROM SkillList;";
			res = stmt.executeQuery(sql);
			count = 0;
			while(res.next()) {
				count++;
			}
			if(count != 4) {
				throw new SQLException();
			}
			sql = "SELECT * FROM Bestiary;";
			res = stmt.executeQuery(sql);
			count = 0;
			while(res.next()) {
				count++;
			}
			if(count != 1) {
				throw new SQLException();
			}
			sql = "SELECT * FROM Levels;";
			res = stmt.executeQuery(sql);
			count = 0;
			while(res.next()) {
				count++;
			}
			if(count != 1) {
				throw new SQLException();
			}
			sql = "SELECT * FROM Rooms;";
			res = stmt.executeQuery(sql);
			count = 0;
			while(res.next()) {
				count++;
			}
			if(count != 3) {
				throw new SQLException();
			}
			System.out.println("Files are up to date.");
		}
		catch(SQLException e) {
			System.out.println("Files were either missing or courrpted");
			System.out.println("Restoreing...");
			//TODO change to setup();
			testSetup();
			System.out.println("Finished!");
		}
	}
	
	static void setup() {
		Connection conn = connectFF();
		try {
			dropTables(conn);
			makeTables(conn);
			fillTables(conn);
			conn.close();
		}
		catch(SQLException e) {
			System.err.println("Something went wrong in the setup\n"
					+ "Try to add/remove drop statemnts in makeTables\n" + e.getMessage());
		}
	}
	
	public static int getItemIndex(String iname) {
		Connection conn = connectFF();
		PreparedStatement selectItem;
		ResultSet item;
		int ret = -1;
		String sql = "SELECT itemid FROM ItemList WHERE name LIKE ?";
		try {
			selectItem = conn.prepareStatement(sql);
			selectItem.setString(1, iname);
			item = selectItem.executeQuery();
			if(item.next()) {
				ret = item.getInt(1);
			}
			selectItem.close();
			item.close();
			conn.close();
		}
		catch(SQLException e) {
			System.err.println("Something went wrong with the query\n" + e.getMessage());
		}
		return ret;
	}
	
	public static String getItemDescr(String iname) {
		Connection conn = connectFF();
		PreparedStatement selectItem;
		ResultSet item;
		String ret = "Item not found!";
		String sql1 = "SELECT description FROM ItemList WHERE name LIKE ?";
		String sql2 = "SELECT description FROM EquipList WHERE name LIKE ?";
		try {
			selectItem = conn.prepareStatement(sql1);
			selectItem.setString(1, iname);
			item = selectItem.executeQuery();
			if(item.next()) {
				ret = item.getString(1);
			}
			selectItem = conn.prepareStatement(sql2);
			selectItem.setString(1, iname);
			item = selectItem.executeQuery();
			if(item.next()) {
				ret = item.getString(1);
			}
			selectItem.close();
			item.close();
			conn.close();
		}
		catch(SQLException e) {
			System.err.println("Something went wrong with the query\n" + e.getMessage());
		}
		return ret;
	}
	
	public static String[] getAllItems(int itemslength) {
		Connection conn = connectFF();
		PreparedStatement selectItem;
		ResultSet item;
		String ret[] = new String[itemslength];
		String sql = "SELECT itemid, name FROM ItemList";
		try {
			selectItem = conn.prepareStatement(sql);
			item = selectItem.executeQuery();
			while(item.next()) {
				ret[item.getInt(1)] = item.getString(2);
			}
			selectItem.close();
			item.close();
			conn.close();
		}
		catch(SQLException e) {
			System.err.println("Something went wrong with the query\n" + e.getMessage());
		}
		return ret;
	}
	
	public static String[] getAllEquips(int equipslength, int itemslength) {
		Connection conn = connectFF();
		PreparedStatement selectItem;
		ResultSet item;
		String ret[] = new String[equipslength];
		String sql = "SELECT itemid, name FROM EquipList";
		try {
			selectItem = conn.prepareStatement(sql);
			item = selectItem.executeQuery();
			while(item.next()) {
				ret[item.getInt(1) - itemslength] = item.getString(2);
			}
			selectItem.close();
			item.close();
			conn.close();
		}
		catch(SQLException e) {
			System.err.println("Something went wrong with the query\n" + e.getMessage());
		}
		return ret;
	}
	
	static void save(String[] ara, int lvl, int slot) {
		Connection c = connectFF();
		PreparedStatement savestmt;
		String sql = "INSERT INTO Saves (savenum, nextlvl, h1name, h1class,"
	                  + "h1stats, h1skills, h1equips, h1build, h1exp, h2name,"
	                  + "h2class, h2stats, h2skills, h2equips, h2build, h2exp, "
	                  + "h3name, h3class, h3stats, h3skills, h3equips, h3build, "
	                  + "h3exp, h4name, h4class, h4stats, h4skills, h4equips, "
	                  + "h4build, h4exp, items, equips)"
	                  + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
	                  + "?,?,?,?,?,?,?,?,?,?);";
		try {
			savestmt = c.prepareStatement(sql);
			savestmt.setInt(1, slot);
			savestmt.setInt(2, lvl);
			savestmt.setString(3, ara[0]);
			savestmt.setString(4, ara[1]);
			savestmt.setString(5, ara[2]);
			savestmt.setString(6, ara[3]);
			savestmt.setString(7, ara[4]);
			savestmt.setString(8, ara[5]);
			savestmt.setString(9, ara[6]);
			savestmt.setString(10, ara[7]);
			savestmt.setString(11, ara[8]);
			savestmt.setString(12, ara[9]);
			savestmt.setString(13, ara[10]);
			savestmt.setString(14, ara[11]);
			savestmt.setString(15, ara[12]);
			savestmt.setString(16, ara[13]);
			savestmt.setString(17, ara[14]);
			savestmt.setString(18, ara[15]);
			savestmt.setString(19, ara[16]);
			savestmt.setString(20, ara[17]);
			savestmt.setString(21, ara[18]);
			savestmt.setString(22, ara[19]);
			savestmt.setString(23, ara[20]);
			savestmt.setString(24, ara[21]);
			savestmt.setString(25, ara[22]);
			savestmt.setString(26, ara[23]);
			savestmt.setString(27, ara[24]);
			savestmt.setString(28, ara[25]);
			savestmt.setString(29, ara[26]);
			savestmt.setString(30, ara[27]);
			savestmt.setString(31, ara[28]);
			savestmt.setString(32, ara[29]);
			savestmt.execute();
			savestmt.close();
			c.close();
		}
		catch(SQLException e) {
			System.out.println("Save is courrupted!\n" + e.getMessage());
		}
	}
	
	static void overwrite(String[] ara, int lvl, int slot) {
		Connection c = connectFF();
		PreparedStatement savestmt;
		String sql = "UPDATE Saves SET nextlvl=?, h1name=?, h1class=?,"
	                  + "h1stats=?, h1skills=?, h1equips=?, h1build=?, h1exp=?, h2name=?, "
	                  + "h2class=?, h2stats=?, h2skills=?, h2equips=?, h2build=?, h2exp=?, "
	                  + "h3name=?, h3class=?, h3stats=?, h3skills=?, h3equips=?, h3build=?,"
	                  + "h3exp=?, h4name=?, h4class=?, h4stats=?, h4skills=?, h4equips=?, "
	                  + "h4build=?, h4exp=?, items=?, equips=? WHERE savenum = ?";
		try {
			savestmt = c.prepareStatement(sql);
			savestmt.setInt(1, lvl);
			savestmt.setString(2, ara[0]);
			savestmt.setString(3, ara[1]);
			savestmt.setString(4, ara[2]);
			savestmt.setString(5, ara[3]);
			savestmt.setString(6, ara[4]);
			savestmt.setString(7, ara[5]);
			savestmt.setString(8, ara[6]);
			savestmt.setString(9, ara[7]);
			savestmt.setString(10, ara[8]);
			savestmt.setString(11, ara[9]);
			savestmt.setString(12, ara[10]);
			savestmt.setString(13, ara[11]);
			savestmt.setString(14, ara[12]);
			savestmt.setString(15, ara[13]);
			savestmt.setString(16, ara[14]);
			savestmt.setString(17, ara[15]);
			savestmt.setString(18, ara[16]);
			savestmt.setString(19, ara[17]);
			savestmt.setString(20, ara[18]);
			savestmt.setString(21, ara[19]);
			savestmt.setString(22, ara[20]);
			savestmt.setString(23, ara[21]);
			savestmt.setString(24, ara[22]);
			savestmt.setString(25, ara[23]);
			savestmt.setString(26, ara[24]);
			savestmt.setString(27, ara[25]);
			savestmt.setString(28, ara[26]);
			savestmt.setString(29, ara[27]);
			savestmt.setString(30, ara[28]);
			savestmt.setString(31, ara[29]);
			savestmt.setInt(32, slot);
			savestmt.execute();
			savestmt.close();
			c.close();
		}
		catch(SQLException e) {
			System.out.println("Save is courrupted!\n" + e.getMessage());
		}
	}
	
	static int loadLevel(int save) {
		int ret = -1;
		Connection c = connectFF();
		PreparedStatement load;
		ResultSet party;
		String sql = "SELECT nextlvl FROM Saves WHERE savenum = ?;";
		try {
			load = c.prepareStatement(sql);
			load.setInt(1, save);
			party = load.executeQuery();
			ret = party.getInt(1);
			load.close();
			party.close();
			c.close();
		}
		catch (SQLException e) {
			System.out.println("Save is courrupted!\n" + e.getMessage());
		}
		return ret;
	}
	
	static String[] loadParty(int save) {
		String[] ret = new String[30];
		Connection c = connectFF();
		PreparedStatement load;
		ResultSet party;
		String sql = "SELECT * FROM Saves WHERE savenum = ?;";
		try {
			load = c.prepareStatement(sql);
			load.setInt(1, save);
			party = load.executeQuery();
			ret[0] = party.getString(3);
			ret[1] = party.getString(4);
			ret[2] = party.getString(5);
			ret[3] = party.getString(6);
			ret[4] = party.getString(7);
			ret[5] = party.getString(8);
			ret[6] = party.getString(9);
			ret[7] = party.getString(10);
			ret[8] = party.getString(11);
			ret[9] = party.getString(12);
			ret[10] = party.getString(13);
			ret[11] = party.getString(14);
			ret[12] = party.getString(15);
			ret[13] = party.getString(16);
			ret[14] = party.getString(17);
			ret[15] = party.getString(18);
			ret[16] = party.getString(19);
			ret[17] = party.getString(20);
			ret[18] = party.getString(21);
			ret[19] = party.getString(22);
			ret[20] = party.getString(23);
			ret[21] = party.getString(24);
			ret[22] = party.getString(25);
			ret[23] = party.getString(26);
			ret[24] = party.getString(27);
			ret[25] = party.getString(28);
			ret[26] = party.getString(29);
			ret[27] = party.getString(30);
			ret[28] = party.getString(31);
			ret[29] = party.getString(32);
			load.close();
			party.close();
			c.close();
		}
		catch (SQLException e) {
			System.out.println("Save is courrupted!\n" + e.getMessage());
		}
		return ret;
	}
	
	static String[] saveNames() {
		String[] ret = {};
		Connection c = connectFF();
		PreparedStatement load;
		ResultSet party;
		int count = 0, i = 0;
		String sql = "SELECT h1name, h2name, h3name, h4name, nextlvl "
				+ "FROM Saves ORDER BY savenum;";
		try {
			load = c.prepareStatement(sql);
			party = load.executeQuery();
			while(party.next()) {
				count++;
			}
			party.close();
			party = load.executeQuery();
			ret = new String[count];
			
			while(party.next()) {
				ret[i] = "Level: " + party.getInt(5) + " Party: " + party.getString(1) + 
						", " + party.getString(2) + ", " + party.getString(3) + 
						", " + party.getString(4);
				i++;
			}
			load.close();
			party.close();
			c.close();
		}
		catch (SQLException e) {
			System.out.println("Database is courrupted!\n" + e.getMessage());
		}
		return ret;
	}
	
	private static void dropTables(Connection c) throws SQLException {
	    System.out.println("Clearing data");
		Statement stmt;
		stmt = c.createStatement();
		String sql = ""
    		      + "DROP TABLE IF EXISTS ItemList;"
                  + "DROP TABLE IF EXISTS EquipList;"
                  + "DROP TABLE IF EXISTS SkillList;"
                  + "DROP TABLE IF EXISTS Bestiary;"
                  + "DROP TABLE IF EXISTS Levels;"
                  + "DROP TABLE IF EXISTS Rooms;"
    		      + "";
		stmt.executeUpdate(sql);
	    stmt.close();
	}
		
	private static void makeTables(Connection c) throws SQLException {
		Statement stmt;
		stmt = c.createStatement();
	      String sql = ""
	      			  + "CREATE TABLE IF NOT EXISTS ItemList " 
	      			  + "(itemid int,"
	                  + "name varchar,"
	                  + "description varchar,"
	                  + "raw int DEFAULT 10,"
	                  + "skill float DEFAULT 0.0,"
	                  + "str float DEFAULT 1.0,"
	                  + "def float DEFAULT 1.0,"
	                  + "spd float DEFAULT 1.0,"
	                  + "time int DEFAULT 0,"
	                  + "revive boolean DEFAULT false,"
	                  + "PRIMARY KEY (itemid));"
	                  + ""
	                  + "CREATE TABLE IF NOT EXISTS EquipList"
	                  + "(itemid int,"
	                  + "name varchar,"
	                  + "description varchar,"
	                  + "str int,"
	                  + "def int,"
	                  + "spd int,"
	                  + "class int,"
	                  + "slot int,"
	                  + "PRIMARY KEY (itemid));"
	                  + ""
	                  + "CREATE TABLE IF NOT EXISTS SkillList " 
	                  + "(skillid int,"
	                  + "name varchar,"
	                  + "description varchar,"
	                  + "flavor varchar,"
	                  + "raw int default -10,"
	                  + "skill float DEFAULT 0.0,"
	                  + "str float DEFAULT 1.0,"
	                  + "def float DEFAULT 1.0,"
	                  + "spd float DEFAULT 1.0,"
	                  + "time int DEFAULT 0,"
	                  + "revive boolean DEFAULT false,"
	                  + "uses int,"
	                  + "aoe boolean DEFAULT false,"
	                  + "PRIMARY KEY (skillid));"
	                  + ""
	                  + "CREATE TABLE IF NOT EXISTS Bestiary " 
	                  + "(monid int,"
	                  + "name varchar,"
	                  + "description varchar,"
	                  + "exp int,"
	                  + "hp int DEFAULT 100,"
	                  + "str int DEFAULT 10,"
	                  + "def int DEFAULT 10,"
	                  + "spd int DEFAULT 10,"
	                  + "skill1 int DEFAULT -1,"
	                  + "skill1chance float DEFAULT 0.0,"
	                  + "skill2 int DEFAULT -1,"
	                  + "skill2chance float DEFAULT 0.0,"
	                  + "skill3 int DEFAULT -1,"
	                  + "skill3chance float DEFAULT 0.0,"
	                  + "PRIMARY KEY (monid));"
	                  + ""
	                  + "CREATE TABLE IF NOT EXISTS Levels " 
	    		  	  + "(levelid int,"
	                  + "name varchar,"
	                  + "height int,"
	                  + "width int,"
	                  + "multi int DEFAULT 1,"
	                  + "PRIMARY KEY (levelid));"
	                  + ""
	                  + "CREATE TABLE IF NOT EXISTS Rooms " 
	                  + "(levelid int,"
	                  + "x int,"
	                  + "y int,"
	                  + "walls int DEFAULT 15,"
	                  + "exits int DEFAULT 0,"
	                  + "type int,"
	                  + "reward int,"
	                  + "rewardnum int,"
	                  + "mon1 int,"
	                  + "mon2 int,"
	                  + "mon3 int,"
	                  + "mon4 int,"
	                  + "PRIMARY KEY (levelid, x, y),"
	                  + "FOREIGN KEY (levelid) REFERENCES Levels(levelid));"
	                  + ""
	                  + "CREATE TABLE IF NOT EXISTS Saves"
	                  + "(savenum int,"
	                  + "nextlvl int,"
	                  + "h1name varchar,"
	                  + "h1class varchar,"
	                  + "h1stats varchar,"
	                  + "h1skills varchar,"
	                  + "h1equips varchar,"
	                  + "h1build varchar,"
	                  + "h1exp varchar,"
	                  + "h2name varchar,"
	                  + "h2class varchar,"
	                  + "h2stats varchar,"
	                  + "h2skills varchar,"
	                  + "h2equips varchar,"
	                  + "h2build varchar,"
	                  + "h2exp varchar,"
	                  + "h3name varchar,"
	                  + "h3class varchar,"
	                  + "h3stats varchar,"
	                  + "h3skills varchar,"
	                  + "h3equips varchar,"
	                  + "h3build varchar,"
	                  + "h3exp varchar,"
	                  + "h4name varchar,"
	                  + "h4class varchar,"
	                  + "h4stats varchar,"
	                  + "h4skills varchar,"
	                  + "h4equips varchar,"
	                  + "h4build varchar,"
	                  + "h4exp varchar,"
	                  + "items varchar,"
	                  + "equips varchar,"
	                  + "PRIMARY KEY(savenum));"
	                  + ""; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      System.out.println("Files created");
	}
	
	private static void fillTables(Connection c) throws SQLException {
		Statement stmt;
		stmt = c.createStatement();// true = 1, false = 0
	      String sql = ""
	      			 + "INSERT INTO ItemList (itemid, name, description, raw, skill, str, "
	      			 + "def, spd, time)"
	      			 + "VALUES (0, 'Test Item', 'For testing only', "
	      			 + "35, 0, 0, 0, 0, 0)"
	      		     + ";"
	      		     + ""//only include index's above or equal to the FIRSTEQUIPINDEX
	      		     //0 is weapon, 1 is armor, 2 is hat
	      		     + "INSERT INTO EquipList (itemid, name, description, str, "
	      			 + "def, spd, class, slot)"
	      			 + "VALUES (20, 'Test Hat', 'For testing only', "
	      			 + "5, 5, 5, 1, 3)"
	      			 + ";"
	      			 + ""
	      		     + "INSERT INTO SkillList (skillid, name, description,flavor,raw,skill,"
	      		     + "str, def, spd, time, revive, uses, aoe)"
	      		     + "VALUES (0, 'Test Skill', 'For testing only', "
	      		     + "' used shop da whop on ', -500, 0, 1.0, 1.0, 1.0, 0, 0, "
	      		     + "1, 0)"
	      		     + ";"
	      		     + ""
	      		     + "INSERT INTO Bestiary (monid, name, description,exp,hp,str,def,spd,"
	      		     + "skill1, skill1chance, skill2, skill2chance, skill3, skill3chance)"
	      		     + "VALUES (0, 'Test Monster', 'For testing only', 15, 300, 30, 5, 15,"
	      		     + "0, .25, -1, 0, -1, 0)"
	      		     + ";"
	      		     + ""
	      		     + "INSERT INTO Levels (levelid, name, height, width, multi)"
	      		     + "VALUES (0, 'Test Level', 1, 3, 1)"
	      		     + ";"
	      		     + ""
	      		     + "INSERT INTO Rooms (levelid, x, y, walls,exits,type,reward,"
	      		     + "rewardnum, mon1, mon2, mon3, mon4)"
	      		     + "VALUES (0, 0, 0, 1, -1, 0, 0, 3, 0, -1, -1, -1),"
	      		     + "(0, 0, 1, 3, 0, 0, 0, 3, 0, -1, -1, -1),"
	      		     + "(0, 0, 2, 2, 1, 0, 0, 3, 0, -1, -1, -1)"
	      		     + ";"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      System.out.println("Data inserted successfully");
	}
	
	private static void fillTestTables(Connection c) throws SQLException {
		Statement stmt;
		stmt = c.createStatement();// true = 1, false = 0
	      String sql = ""
	      			 + "INSERT INTO ItemList (itemid, name, description, raw, skill, str, "
	      			 + "def, spd, time)"
	      			 + "VALUES (0, 'Test Item', 'For testing only', "
	      			 + "35, 0, 0, 0, 0, 0),"
	      			 + "(15,'Test Attack Item', 'For testing only', -999,0,0,0,0,0)"
	      		     + ";"
	      		     + ""
	      		     + "INSERT INTO EquipList (itemid, name, description, str, "
	      			 + "def, spd, class, slot)"
	      			 + "VALUES (20, 'Test Hammer', 'For testing only', "
	      			 + "20, 5, -5, 3, 0), (21, 'Test Knife', 'For testing only',"
	      			 + "7, 0, 7, 1, 0), (22, 'Test Armor', 'For testing only',"
	      			 + "0, 10, -1, 2, 1), (23, 'Test Leather', 'For testing only',"
	      			 + "0, 4, 3, 1, 1), (24, 'Test Hat', 'For testing only', "
	      			 + "0, 3, 2, 1, 2)"
	      			 + ";"
	      		     + ""
	      		     + "INSERT INTO SkillList (skillid, name, description,flavor,raw,skill,"
	      		     + "str, def, spd, time, revive, uses, aoe)"
	      		     + "VALUES (0, 'Test Skill', 'For testing only', "
	      		     + "' used shop da whop on ', -500, 0, 1.0, 1.0, 1.0, 0, 0, "
	      		     + "1, 0), (1, 'Stab', 'Stab one foe', ' stabbed ', "
					 + "-75, 0.0, 0.0, 0.0, 0.0, 0, 0, 5, 0), (2, 'Kick', 'Kick one foe', "
					 + "' kicked ', -75, 0.0, 0.0, 0.0, 0.0, 0, 0, 5, 0), (3, "
					 + "'Slap', 'Slap one foe', ' slapped ', -75, 0.0, 0.0, 0.0, 0.0, 0, "
					 + "0, 5, 0)"
	      		     + ";"
	      		     + ""
	      		     + "INSERT INTO Bestiary (monid, name, description,exp,hp,str,def,spd,"
	      		     + "skill1, skill1chance, skill2, skill2chance, skill3, skill3chance)"
	      		     + "VALUES (0, 'Test Monster', 'For testing only', 15, 300, 30, 5, 15,"
	      		     + "0, .25, -1, 0, -1, 0)"
	      		     + ";"
	      		     + ""
	      		     + "INSERT INTO Levels (levelid, name, height, width, multi)"
	      		     + "VALUES (0, 'Test Level', 1, 3, 1)"
	      		     + ";"
	      		     + ""
	      		     + "INSERT INTO Rooms (levelid, x, y, walls,exits,type,reward,"
	      		     + "rewardnum, mon1, mon2, mon3, mon4)"
	      		     + "VALUES (0, 0, 0, 1, -1, 0, 0, 3, 0, -1, -1, -1),"
	      		     + "(0, 0, 1, 3, 0, 0, 0, 3, 0, -1, -1, -1),"
	      		     + "(0, 0, 2, 2, 1, 0, 0, 3, 0, -1, -1, -1)"
	      		     + ";"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      System.out.println("Data inserted successfully");
	}
	
	static void testSetup() {
		Connection conn = connectFF();
		try {
			dropTables(conn);
			makeTables(conn);
			fillTestTables(conn);
			conn.close();
		}
		catch(SQLException e) {
			System.err.println("Something went wrong in the setup\n"
					+ "Try to add/remove drop statemnts in makeTables\n" + e.getMessage());
		}
	}
}

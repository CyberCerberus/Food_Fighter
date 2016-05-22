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
	
	static void setup() {
		Connection conn = connectFF();
		try {
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
		String sql = "SELECT description FROM ItemList WHERE name LIKE ?";
		try {
			selectItem = conn.prepareStatement(sql);
			selectItem.setString(1, iname);
			item = selectItem.executeQuery();
			if(item.next()) {
				ret = item.getString(1);
			}
			conn.close();
		}
		catch(SQLException e) {
			System.err.println("Something went wrong with the query\n" + e.getMessage());
		}
		return ret;
	}
	
	public static String[] getAllItems(int[] itemids) {
		Connection conn = connectFF();
		PreparedStatement selectItem;
		ResultSet item;
		String ret[] = new String[itemids.length];
		String sql = "SELECT itemid, name FROM ItemList";
		try {
			selectItem = conn.prepareStatement(sql);
			item = selectItem.executeQuery();
			while(item.next()) {
				ret[item.getInt(1)] = item.getString(2);
			}
			conn.close();
		}
		catch(SQLException e) {
			System.err.println("Something went wrong with the query\n" + e.getMessage());
		}
		return ret;
	}
	
	private static void makeTables(Connection c) throws SQLException {
		Statement stmt;
		stmt = c.createStatement();
	      String sql = ""
	      		      + "DROP TABLE ItemList;"
	      		      + ""
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
	                  + "DROP TABLE SkillList;"
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
	                  + "DROP TABLE Bestiary;"
	                  + ""
	                  + "CREATE TABLE IF NOT EXISTS Bestiary " 
	                  + "(monid int,"
	                  + "name varchar,"
	                  + "description varchar,"
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
	                  + "DROP TABLE Levels;"
	                  + ""
	                  + "CREATE TABLE IF NOT EXISTS Levels " 
	    		  	  + "(levelid int,"
	                  + "name varchar,"
	                  + "height int,"
	                  + "width int,"
	                  + "multi int DEFAULT 1,"
	                  + "PRIMARY KEY (levelid));"
	                  + ""
	                  + "DROP TABLE Rooms;"
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
	                  //+ "linkx int"
	                  //+ "linky int"
	                  + "PRIMARY KEY (levelid, x, y),"
	                  + "FOREIGN KEY (levelid) REFERENCES Levels(levelid),"
	                  + "FOREIGN KEY (type) REFERENCES RoomType(type)"
	                  + ");"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      System.out.println("Tables created successfully");
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
	      		     + ""
	      		     + "INSERT INTO SkillList (skillid, name, description,flavor,raw,skill,"
	      		     + "str, def, spd, time, revive, uses, aoe)"
	      		     + "VALUES (0, 'Test Skill', 'For testing only', "
	      		     + "' used shop da whop on ', -500, 0, 1.0, 1.0, 1.0, 0, 0, "
	      		     + "1, 0)"
	      		     + ";"
	      		     + ""
	      		     + "INSERT INTO Bestiary (monid, name, description,hp,str,def,spd,"
	      		     + "skill1, skill1chance, skill2, skill2chance, skill3, skill3chance)"
	      		     + "VALUES (0, 'Test Monster', 'For testing only', 300, 30, 5, 15,"
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
	      			 + "35, 0, 0, 0, 0, 0)"
	      		     + ";"
	      		     + ""
	      		     + "INSERT INTO SkillList (skillid, name, description,flavor,raw,skill,"
	      		     + "str, def, spd, time, revive, uses, aoe)"
	      		     + "VALUES (0, 'Test Skill', 'For testing only', "
	      		     + "' used shop da whop on ', -500, 0, 1.0, 1.0, 1.0, 0, 0, "
	      		     + "1, 0)"
	      		     + ";"
	      		     + ""
	      		     + "INSERT INTO Bestiary (monid, name, description,hp,str,def,spd,"
	      		     + "skill1, skill1chance, skill2, skill2chance, skill3, skill3chance)"
	      		     + "VALUES (0, 'Test Monster', 'For testing only', 300, 30, 5, 15,"
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

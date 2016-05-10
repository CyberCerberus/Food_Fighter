package gameCore;
import java.sql.*;

public class SQLTest {
	
	public static void main(String...args) {
		
	  Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:FoodFight.db");
	      System.out.println("Opened database successfully");

	      makeTables(c);
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
}

	private static void makeTables(Connection c) throws SQLException {
		Statement stmt;
		stmt = c.createStatement();
	      String sql = ""
	      		       //+ "DROP TABLE ItemList;"
	      		       + ""
	      			   + "CREATE TABLE IF NOT EXISTS ItemList " 
	    		  	   + "(itemid int NOT NULL,"
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
	                   //+ "DROP TABLE SkillList;"
	                   + ""
	                   + "CREATE TABLE IF NOT EXISTS SkillList " 
	    		  	   + "(skillid int NOT NULL,"
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
	                   + "uses int NOT NULL,"
	                   + "PRIMARY KEY (skillid));"
	                   + ""
	                   //+ "DROP TABLE Bestiary;"
	                   + ""
	                   + "CREATE TABLE IF NOT EXISTS Bestiary " 
	    		  	   + "(monid int NOT NULL,"
	                   + "name varchar,"
	                   + "description varchar,"
	                   + "hp int DEFAULT 100,"
	                   + "str int DEFAULT 10,"
	                   + "def int DEFAULT 10,"
	                   + "spd int DEFAULT 10,"
	                   + "skill1 int,"
	                   + "skill1chance float DEFAULT 0.0,"
	                   + "skill2 int,"
	                   + "skill2chance float DEFAULT 0.0,"
	                   + "skill3 int,"
	                   + "skill3chance float DEFAULT 0.0,"
	                   + "PRIMARY KEY (monid));"
	                   + ""
	                   //+ "DROP TABLE RoomType;"
	                   + ""
	                   + "CREATE TABLE IF NOT EXISTS RoomType " 
	    		  	   + "(type int not null,"
	                   + "name, varchar,"
	                   + "description varchar,"
	                   + "PRIMARY KEY (type));"
	                   + "DROP TABLE Levels;"
	                   + "CREATE TABLE IF NOT EXISTS Levels " 
	    		  	   + "(levelid int NOT NULL,"
	                   + "name varchar,"
	                   + "length int,"
	                   + "width int,"
	                   + "multi int DEFAULT 1,"
	                   + "PRIMARY KEY (levelid));"
	                   + ""
	                   //+ "DROP TABLE Rooms;"
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
	                   + "FOREIGN KEY (levelid) REFERENCES Levels(levelid),"
	                   + "FOREIGN KEY (type) REFERENCES RoomType(type)"
	                   + ");"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	      System.out.println("Tables created successfully");
	}
}

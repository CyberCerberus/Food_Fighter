package gameCore;
import java.sql.*;

public class SQLTest {
	
	public static void main(String...args) {
		
	  Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:FoodFight.db");
	      System.out.println("Opened database successfully");

	      makeTable(c);
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
}

	private static void makeTable(Connection c) throws SQLException {
		Statement stmt;
		stmt = c.createStatement();
	      String sql = "CREATE TABLE COMPANY " +
	                   "(ID INT PRIMARY KEY     NOT NULL," +
	                   " NAME           TEXT    NOT NULL, " + 
	                   " AGE            INT     NOT NULL, " + 
	                   " ADDRESS        CHAR(50), " + 
	                   " SALARY         REAL)"; 
	      stmt.executeUpdate(sql);
	      stmt.close();
	}
}

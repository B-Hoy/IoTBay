package uts.iotbay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;

public class Database{
	Connection conn = null;
	public Database(){
		try {
			Class.forName("org.sqlite.JDBC"); // Need this in case Glassfish doesn't load it automatically
		} catch (ClassNotFoundException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		try{
			conn = DriverManager.getConnection("jdbc:sqlite:" + File.createTempFile("sql", ".db").getAbsolutePath());
			// makes a temp file wherever so it doesn't break on someone else's system
        } catch (SQLException e){
            System.out.println("ERROR: " + e.getMessage());
        } catch (IOException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	public Connection get_connection(){
		return this.conn;
	}
	public String[][] driver(){
		ArrayList<String[]> list = new ArrayList<String[]>();
		if (conn != null){
			try{
				Statement stmt = conn.createStatement();
				stmt.setQueryTimeout(5);
				stmt.executeUpdate("DROP TABLE IF EXISTS test");
				stmt.executeUpdate("CREATE TABLE test (i1 integer, s1 string)");
				stmt.executeUpdate("INSERT INTO test VALUES(1, 'ABCDE')");
				stmt.executeUpdate("INSERT INTO test VALUES(2, 'FGHJK')");
				ResultSet rs = stmt.executeQuery("SELECT * FROM test");
				while (rs.next()){
					list.add(new String[]{rs.getString("i1"), rs.getString("s1")});
				}
			}catch (SQLException e){
				System.out.println("ERROR: " + e.getMessage());
			}
		}
		return list.toArray(new String[][]{});
	}
	public void disconnect(){
		if (conn != null){
			try{
				conn.close();
			} catch (SQLException e){
				System.out.println(e.getMessage());
			}
		}
	}
}

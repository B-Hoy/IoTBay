package uts.iotbay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.File;
import java.util.ArrayList;
import java.io.IOException;

public class PeopleData{
	Connection conn2 = null;
	public PeopleData(){
		File temp_file = null;
		try {
			Class.forName("org.sqlite.JDBC"); // Need this in case Glassfish doesn't load it automatically
		} catch (ClassNotFoundException e){
			System.out.println("ERROR: " + e.getMessage());
		}
		try{
			temp_file = File.createTempFile("sql", ".db");
			temp_file.deleteOnExit();
			conn2 = DriverManager.getConnection("jdbc:sqlite:" + temp_file.getAbsolutePath());
			// makes a temp file wherever so it doesn't break on someone else's system
        } catch (SQLException e){
            System.out.println("ERROR: " + e.getMessage());
        } catch (IOException e){
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	public Connection get_connection(){
		return this.conn2;
	}
	public String[][] driver(){
		ArrayList<String[]> list = new ArrayList<String[]>();
		if (conn2 != null){
			try{
				Statement stmt = conn2.createStatement();
				stmt.setQueryTimeout(5);
				stmt.executeUpdate("DROP TABLE IF EXISTS test");
				stmt.executeUpdate("CREATE TABLE test (i1 integer, s1 string)");
				stmt.executeUpdate("INSERT INTO test VALUES(1, 'TEST Person 1')");
				stmt.executeUpdate("INSERT INTO test VALUES(2, 'TEST Person 2')");
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
		if (conn2 != null){
			try{
				conn2.close();
			} catch (SQLException e){
				System.out.println(e.getMessage());
			}
		}
	}
}

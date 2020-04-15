package server.database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.security.MessageDigest;

public class Database {
	private String dbPath;
	private String initSQLPath;
	public Database(String dbPath){
		// Pass in "" as jsonPath if the database has already established.
		// Pass in the jsonPath to clear the database and init the database by jsonPath.
		this.dbPath = dbPath;
		this.initSQLPath = "Database/init_table.sql";
		try{
			File f = new File(this.dbPath);
			if(!f.exists()){
				System.out.println("DB file not exist, establishing DB...");
				initDB();
			}
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void initDB(){
	    try{	
	    	File f = new File(this.initSQLPath);
	    	Scanner s = new Scanner(f);
	    	s.useDelimiter("(;(\r)?\n)|((\r)?\n)?(--)?.*(--(\r)?\n)");
	    	Statement st = this.connect().createStatement();
	        while (s.hasNext()){
	            String line = s.next();
	            if (line.startsWith("/*!") && line.endsWith("*/")){
	                int i = line.indexOf(' ');
	                line = line.substring(i + 1, line.length() - " */".length());
	            }

	            if (line.trim().length() > 0){
	                st.execute(line);
	            }
	        }
	        s.close();
	        st.close();
	    }catch (Exception e) {
			System.out.println(e);
		}
	}

	private Connection connect(){

		Connection conn = null;
		
		try {
			//Class.forName("org.sqlite.JDBC"); 
			String url = "jdbc:sqlite:" + this.dbPath;
			conn = DriverManager.getConnection(url);
			//System.out.println("Connection to SQLite has been established.");
		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;
	}
	
	private int maxID(String table) {
		//String sql = "INSERT INTO JsonHotel (star, locality, street_address) VALUES (1, 'Taipei', 'abc street');";

		String sql = "SELECT MAX(id) FROM " + "`" + table + "`" ;
		//System.out.println(sql);
		//String sql = "INSERT INTO " + table + " (star, locality, street_address) " + " VALUES " + "(1, 'Taipei', 'abc street');";

		try(Connection conn = this.connect()) {
			Statement stmt = conn.createStatement();
			// pstmt.executeUpdate();
			ResultSet rs = stmt.executeQuery(sql);
            // loop through the result set
            while (rs.next()) {
            	return rs.getInt("MAX(id)");
            }
            conn.close();
            return -1;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}
	
	private int len(String table) {
		//String sql = "INSERT INTO JsonHotel (star, locality, street_address) VALUES (1, 'Taipei', 'abc street');";

		String sql = "SELECT * FROM " + "`" + table + "`" ;
		//System.out.println(sql);
		//String sql = "INSERT INTO " + table + " (star, locality, street_address) " + " VALUES " + "(1, 'Taipei', 'abc street');";

		try(Connection conn = this.connect()) {
			Statement stmt = conn.createStatement();
			// pstmt.executeUpdate();
			ResultSet rs = stmt.executeQuery(sql);
            int count = 0;
            // loop through the result set
            while (rs.next()) {
            	count++;
            }
            conn.close();
            return count;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	private Boolean insert(String table,  HashMap<String, String> attr) {
		//String sql = "INSERT INTO JsonHotel (star, locality, street_address) VALUES (1, 'Taipei', 'abc street');";
		String columns = "", values = "";
		for(String key: attr.keySet()){
			String value = attr.get(key);
			columns += (key + ", ");
			values += ("'" + value + "'" + ", ");
		}
		columns = columns.substring(0, columns.length()-2);
		values = values.substring(0, values.length()-2);

		String sql = "INSERT INTO " + "`" + table + "`"  + " ( " + columns + " ) " + " VALUES " + " ( " + values + " ) ;";
		//System.out.println(sql);
		//String sql = "INSERT INTO " + table + " (star, locality, street_address) " + " VALUES " + "(1, 'Taipei', 'abc street');";

		try(Connection conn = this.connect()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			conn.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	private Boolean update(String table,  HashMap<String, String> attr, HashMap<String, String> cond_attr) {
		//String sql = "INSERT INTO JsonHotel (star, locality, street_address) VALUES (1, 'Taipei', 'abc street');";
		String set_values = "";
		for(String key: attr.keySet()){
			String value = attr.get(key);
			set_values += (key + "=" + "'" + value + "'") + " , ";
		}
		set_values = set_values.substring(0, set_values.length()-3);

		String conditions = "";
		for(String key: cond_attr.keySet()){
			String value = cond_attr.get(key);
			conditions += key + "=" + "'" + value + "'" + " and ";
		}
		conditions = conditions.substring(0, conditions.length()-5);

		String sql = "UPDATE " + "`" + table + "`" + " SET " + set_values + " WHERE " + conditions + " ;";
		//System.out.println(sql);
		//String sql = "INSERT INTO " + table + " (star, locality, street_address) " + " VALUES " + "(1, 'Taipei', 'abc street');";

		try(Connection conn = this.connect()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			conn.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	private Boolean delete(String table,  HashMap<String, String> attr) {
		//String sql = "INSERT INTO JsonHotel (star, locality, street_address) VALUES (1, 'Taipei', 'abc street');";
		String conditions = "";
		for(String key: attr.keySet()){
			String value = attr.get(key);
			conditions += key + "=" + "'" + value + "'" + " and ";
		}
		conditions = conditions.substring(0, conditions.length()-5);

		String sql = "DELETE FROM " + "`" + table + "`" + " WHERE " + conditions + " ;";
		//System.out.println(sql);
		//String sql = "INSERT INTO " + table + " (star, locality, street_address) " + " VALUES " + "(1, 'Taipei', 'abc street');";

		try(Connection conn = this.connect()) {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			conn.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	private List<HashMap<String, String>> select(String table,  HashMap<String, String> attr) {
		//String sql = "INSERT INTO JsonHotel (star, locality, street_address) VALUES (1, 'Taipei', 'abc street');";
		String conditions = "";
		for(String key: attr.keySet()){
			String value = attr.get(key);
			conditions += key + "=" + "'" + value + "'" + " and ";
		}
		conditions = conditions.substring(0, conditions.length()-5);

		String sql = "SELECT * FROM " + "`" + table + "`" + " WHERE " + conditions + ";" ;
		//System.out.println(sql);
		//String sql = "INSERT INTO " + table + " (star, locality, street_address) " + " VALUES " + "(1, 'Taipei', 'abc street');";

		try(Connection conn = this.connect()) {
			Statement stmt = conn.createStatement();
			// pstmt.executeUpdate();
			ResultSet rs = stmt.executeQuery(sql); 
            
            List<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
            // loop through the result set
            while (rs.next()) {
            	ResultSetMetaData rsmd = rs.getMetaData();
            	int n_column = rsmd.getColumnCount();
            	HashMap<String, String> result = new HashMap<>();
            	for (int i = 1; i <= n_column; i++) 
            		result.put(rsmd.getColumnName(i), rs.getString(i));
            	results.add(result);
            	//System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
                // System.out.println(rs.getInt("id") +  "\t" + 
                //                    rs.getString("name") + "\t" +
                //                    rs.getDouble("capacity"));
            }
            conn.close();
            return results;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private List<HashMap<String, String>> selectCondition(String table,  HashMap<String, String> attr) {
		//String sql = "INSERT INTO JsonHotel (star, locality, street_address) VALUES (1, 'Taipei', 'abc street');";
		String conditions = "";
		for(String key: attr.keySet()){
			String value = attr.get(key);
			conditions += key + "'" + value + "'" + " and ";
		}
		conditions = conditions.substring(0, conditions.length()-5);

		String sql = "SELECT * FROM " + "`" + table + "`" + " WHERE " + conditions + ";" ;
		//System.out.println(sql);
		//String sql = "INSERT INTO " + table + " (star, locality, street_address) " + " VALUES " + "(1, 'Taipei', 'abc street');";

		try(Connection conn = this.connect()) {
			Statement stmt = conn.createStatement();
			// pstmt.executeUpdate();
			ResultSet rs = stmt.executeQuery(sql);
            
            List<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
            // loop through the result set
            while (rs.next()) {
            	ResultSetMetaData rsmd = rs.getMetaData();
            	int n_column = rsmd.getColumnCount();
            	HashMap<String, String> result = new HashMap<>();
            	for (int i = 1; i <= n_column; i++) 
            		result.put(rsmd.getColumnName(i), rs.getString(i));
            	results.add(result);
            	//System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3));
                // System.out.println(rs.getInt("id") +  "\t" + 
                //                    rs.getString("name") + "\t" +
                //                    rs.getDouble("capacity"));
            }
            conn.close();
            return results;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	private List<HashMap<String, String>> selectAll(String table){
		HashMap<String, String> attr = new HashMap<>();
		attr.put("'1'", "1");
		return this.select(table, attr);
	}
	
	private String hashPassword(String password){
		String encryptedString = "";
		try{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(password.getBytes());
			encryptedString = new String(messageDigest.digest());
			encryptedString = Base64.getEncoder().encodeToString(encryptedString.getBytes());
		}catch(Exception e){}
		return encryptedString;
	}
}

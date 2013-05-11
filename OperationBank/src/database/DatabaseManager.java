package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

	private static final String loginUser = "postgres";
	private static final String loginPasswd = "sander";
	private static final String loginUrl = "jdbc:postgresql://localhost/bank";
	private static final String dataBaseString = "org.postgresql.Driver";
	private Connection databaseConnection;  // Connection for scope of ShowBedrock


	private static DatabaseManager instance = null;

	private DatabaseManager(){
		// Load the PostgreSQL driver
		try 
		{
			Class.forName(dataBaseString);
			databaseConnection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		}
		catch (ClassNotFoundException ex)
		{
			System.err.println("ClassNotFoundException: " + ex.getMessage());
		}
		catch (SQLException ex)
		{
			System.err.println("SQLException: " + ex.getMessage());
		}
	}

	public static DatabaseManager getInstance(){
		if(instance == null)
			instance = new DatabaseManager();
		return instance;
	}
	
	public ResultSet executeQuery(String sql_string){
		try{
			Statement statment = databaseConnection.createStatement();
			return statment.executeQuery(sql_string);
		} catch (SQLException ex){
			System.out.println(ex);
		}
		return null;
	}
	
	public void executeUpdate(String sql_query){
		try{
			Statement statment = databaseConnection.createStatement();
			statment.executeUpdate(sql_query);
		} catch (SQLException ex){
			System.out.println(ex);
		}
	}
	
	public String querySingleValue(String sql_query, String columnLabel){
		try{
			Statement statement = databaseConnection.createStatement();
			ResultSet rs = statement.executeQuery(sql_query);
			if(rs.next()){
				return rs.getString(columnLabel);
			}
		} catch (SQLException ex){
			System.out.println(ex);
		}
		return null;
	}


}

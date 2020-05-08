package ParkingApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCMySQLConnection {
	
	private static JDBCMySQLConnection instance = new JDBCMySQLConnection();
	public static final String url = "jdbc:mysql://localhost:3306/ParkingSchema";
	public static final String user = "root";
	public static final String password = "Wee33bug";
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	
	JDBCMySQLConnection() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Connection createConnection()
	{
		Connection conn = null;
		try{
			conn = DriverManager.getConnection(url,user,password);
		}
		catch(SQLException e)
		{
			System.out.print("Unable To Connect To Database:");
		}
		return conn;
	}
	
	public static Connection getConnection()
	{
		return instance.createConnection();
	}
}

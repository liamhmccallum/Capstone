package ParkingApp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Queries {
	public ParkingSpot getSpot(int id) {
		ResultSet rs = null;
		Connection con = null;

		ParkingSpot spot = null;
		String query = "SELECT * FROM ParkingSchema.ParkingTable where id = " + id;
		try {
			con = JDBCMySQLConnection.getConnection();
			Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = statement.executeQuery(query);

			if (rs.next()) {
				spot = new ParkingSpot();
				spot.setId(rs.getInt("id"));
				spot.setLatitude(rs.getBigDecimal("Latitude"));
				spot.setLongitude(rs.getBigDecimal("Longitude"));
				spot.setAvailable(rs.getBoolean("Available"));
				spot.setDesignation(rs.getString("Designation"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return spot;
	}

	public void updateParkingSpot(int id, boolean available) throws SQLException {
		int bool;
		if(available = true)
			bool = 1;
		else
			bool = 0;
		
		Connection con = JDBCMySQLConnection.getConnection();
		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sqlString = "update ParkingTable SET"
					+ " Available = " + bool
					+ " WHERE id = " + id;
			System.out.println("\nExecuting: " + sqlString);
			stmt.execute(sqlString);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<ParkingSpot> findAvailable() {
		List<ParkingSpot> parkingList = new LinkedList<ParkingSpot>();
		ParkingSpot current = null;
		ResultSet rs = null;
		Connection con = null;
		int True = 1;

		String query = "SELECT * FROM ParkingSchema.ParkingTable WHERE available = " + True;
		try {
			con = JDBCMySQLConnection.getConnection();
			Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println("\nExecuting: " + query);
			rs = statement.executeQuery(query);

			while (rs.next()) {
				current = new ParkingSpot();
				current.setId(rs.getInt("id"));
				current.setLatitude(rs.getBigDecimal("Latitude"));
				current.setLongitude(rs.getBigDecimal("Longitude"));
				current.setAvailable(rs.getBoolean("Available"));
				current.setDesignation(rs.getString("Designation"));

				parkingList.add(current);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return parkingList;
	}

	public List<ParkingSpot> findSpecificAvailable(String desired) {
		List<ParkingSpot> parkingList = new LinkedList<ParkingSpot>();
		ParkingSpot current = null;
		ResultSet rs = null;
		Connection con = null;
		int True = 1;
		
		String query = "SELECT * FROM ParkingSchema.ParkingTable WHERE available = " + True + " AND designation LIKE '%"
				+ desired + "%'";
		try {
			con = JDBCMySQLConnection.getConnection();
			Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println("\nExecuting: " + query);
			rs = statement.executeQuery(query);

			while (rs.next()) {
				current = new ParkingSpot();
				current.setId(rs.getInt("id"));
				current.setLatitude(rs.getBigDecimal("Latitude"));
				current.setLongitude(rs.getBigDecimal("Longitude"));
				current.setAvailable(rs.getBoolean("Available"));
				current.setDesignation(rs.getString("Designation"));

				parkingList.add(current);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return parkingList;

	}
}

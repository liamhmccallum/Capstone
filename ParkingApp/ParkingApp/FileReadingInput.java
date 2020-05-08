package ParkingApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileReadingInput {

	public static void main(String[] args) throws IOException {
		Connection con = null;
		
		String csvFile = "ParkingSpots.csv";
		String line = "";
		String cvsSplitBy = ",";

		try{
			BufferedReader br = new BufferedReader(new FileReader(csvFile));
			con = JDBCMySQLConnection.getConnection();
			String sqlString = "Insert into PARKINGTABLE (id,latitude,longitude,available,designation)" + 
					"values(?,?,?,?,?)";
			int id = 1;
			int available = 1;
			while ((line = br.readLine()) != null) {
				String[] spot = line.split(cvsSplitBy);

				PreparedStatement preparedStmt = con.prepareStatement(sqlString);
				preparedStmt.setInt(1, id);
				preparedStmt.setFloat(2, Float.parseFloat(spot[0]));
				preparedStmt.setFloat(3, Float.parseFloat(spot[1]));
				preparedStmt.setInt(4, available);
				preparedStmt.setString(5, spot[3]);

				preparedStmt.execute();
				id++;
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

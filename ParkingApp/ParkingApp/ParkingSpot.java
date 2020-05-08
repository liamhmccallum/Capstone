package ParkingApp;

import java.sql.Connection;

public class ParkingSpot {
	Connection con = null;

	private int id;
	private java.math.BigDecimal latitude;
	private java.math.BigDecimal longitude;
	private boolean available;
	private String designation;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.math.BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(java.math.BigDecimal latitude) {
		this.latitude = latitude;
	}

	public java.math.BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(java.math.BigDecimal longitude) {
		this.longitude = longitude;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public String toString() {
		return "ParkingSpot [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", available="
				+ available + ", designation=" + designation + "]";
	}

}

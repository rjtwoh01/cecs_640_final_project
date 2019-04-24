package edu.louisville.edu.twohey.final_project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

public class ShoeController {
	private Connection dbConnection = null;
	private ResultSet resultSet = null;
	private String sqlResult = "";
	private Statement statement = null;
	private String shoeName;
	private int shoeID;
	private double shoeDistance;
	private double shoeGoalDistance;
	private int retired;

	public ShoeController(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}

//	CREATE TABLE SHOES(
//			ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
//			USERID INTEGER NOT NULL,
//			BRAND VARCHAR(10) NOT NULL,
//			NAME VARCHAR(10) NOT NULL,
//			DISTANCE_RUN DOUBLE NOT NULL,
//			DISTANCE_GOAL DOUBLE NOT NULL,
//			RETIRED SMALLINT NOT NULL WITH DEFAULT 0,
//			PRIMARY KEY (ID))

	public int insertShoe(String shoeName, int userID) {
		int rc = 0;

		String template = "INSERT INTO SHOES(USERID, NAME, BRAND, DISTANCE_RUN, DISTANCE_GOAL, RETIRED) VALUES(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = dbConnection.prepareStatement(template);
			ps.setInt(1, userID);
			ps.setString(2, shoeName);
			ps.setString(3, shoeName);
			ps.setDouble(4, 0);
			ps.setDouble(5, 500);
			ps.setInt(6, 0);
			rc = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return rc;
	}

	public int insertFullShoe(String shoeName, int userID, double distance, double distanceGoal, int retired) {
		int rc = 0;

		String template = "INSERT INTO SHOES(USERID, NAME, BRAND, DISTANCE_RUN, DISTANCE_GOAL, RETIRED) VALUES(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = dbConnection.prepareStatement(template);
			ps.setInt(1, userID);
			ps.setString(2, shoeName);
			ps.setString(3, shoeName);
			ps.setDouble(4, distance);
			ps.setDouble(5, distanceGoal);
			ps.setInt(6, retired);
			rc = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return rc;
	}

	public int updateShoe(String shoeName, int userID, double distance, double distanceGoal, int retired, int shoeID) {
		int rc = 0;

		String template = "UPDATE SHOES SET (USERID, NAME, BRAND, DISTANCE_RUN, DISTANCE_GOAL, RETIRED) = (?, ?, ?, ?, ?, ?) WHERE ID = "
				+ String.valueOf(shoeID);
		try {
			PreparedStatement ps = dbConnection.prepareStatement(template);
			ps.setInt(1, userID);
			ps.setString(2, shoeName);
			ps.setString(3, shoeName);
			ps.setDouble(4, distance);
			ps.setDouble(5, distanceGoal);
			ps.setInt(6, retired);
			rc = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return rc;
	}

	public boolean findShoe(String shoeName, int userID) {
		boolean rc = false;
		this.shoeName = "";
		String template = "SELECT * FROM SHOES WHERE USERID = '" + userID + "' AND NAME = '" + shoeName + "'";
		try {
			Statement s = dbConnection.createStatement();
			ResultSet rs = s.executeQuery(template);
			if (rs.next()) {
				rc = true;
				setShoeName(rs.getString("NAME"));
				setShoeID(rs.getInt("ID"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return (rc);
	}

	public boolean findShoeByID(int shoeID) {
		boolean rc = false;
		this.shoeName = "";
		String template = "SELECT * FROM SHOES WHERE ID = '" + shoeID + "'";
		try {
			Statement s = dbConnection.createStatement();
			ResultSet rs = s.executeQuery(template);
			if (rs.next()) {
				rc = true;
				setShoeName(rs.getString("NAME"));
				setShoeID(rs.getInt("ID"));
				setShoeDistance(rs.getDouble("DISTANCE_RUN"));
				setShoeGoalDistance(rs.getDouble("DISTANCE_GOAL"));
				setRetired(rs.getInt("RETIRED"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return (rc);
	}

	public void getAllShoes() {
		String sqlStatement = "SELECT * FROM SHOES";
		try {
			statement = dbConnection.createStatement();

			try {
				resultSet = statement.executeQuery(sqlStatement);
				setSqlResult(SQLUtil.getHtmlTable(resultSet));
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("Cannot execute query");
				e.printStackTrace();
			}

		} catch (SQLException e) {
			System.out.println("Could not create statment: " + e.getMessage());
		}
	}
	
	public int deleteShoe(int shoeID) {
		int rc = 0;
    	
    	String template = "DELETE FROM SHOES WHERE ID = " + String.valueOf(shoeID); 
    	try {
    		PreparedStatement ps = dbConnection.prepareStatement(template);
    		rc = ps.executeUpdate();
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	return rc;
	}

	public void setShoeName(String name) {
		this.shoeName = name;
	}

	public void setShoeID(int id) {
		this.shoeID = id;
	}

	public String getShoeName() {
		return this.shoeName;
	}

	public int getShoeID() {
		return this.shoeID;
	}

	public String getSqlResult() {
		return sqlResult;
	}

	public void setSqlResult(String sqlResult) {
		this.sqlResult = sqlResult;
	}

	public double getShoeDistance() {
		return shoeDistance;
	}

	public void setShoeDistance(double shoeDistance) {
		this.shoeDistance = shoeDistance;
	}

	public double getShoeGoalDistance() {
		return shoeGoalDistance;
	}

	public void setShoeGoalDistance(double shoeGoalDistance) {
		this.shoeGoalDistance = shoeGoalDistance;
	}

	public int getRetired() {
		return retired;
	}

	public void setRetired(int retired) {
		this.retired = retired;
	}

}

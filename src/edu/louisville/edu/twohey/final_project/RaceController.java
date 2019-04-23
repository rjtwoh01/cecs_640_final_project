package edu.louisville.edu.twohey.final_project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import edu.louisville.edu.twohey.final_project.*;

public class RaceController {
	private Connection dbConnection = null;
	private ResultSet resultSet = null;
	private Statement statement = null;
	private String sqlResult = "";
	private double runDistance = 0;
	private String dateRun = "";
	private double goalTime = 0;
	private double runTime = 0;
	private int shoeID = 0;
	private int userID = 0;

	public RaceController(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}

//	CREATE TABLE RJTWOH01.RACES (
//			ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
//			USERID INTEGER NOT NULL,
//			RACE_DISTANCE DOUBLE NOT NULL,
//			TIME_COMPLETED DOUBLE NOT NULL,
//			DATE_RUN VARCHAR(30) NOT NULL,
//			GOAL_TIME DOUBLE NOT NULL,
//			SHOE INTEGER NOT NULL,
//			PLACE INTEGER NOT NULL,
//			PRIMARY KEY (ID))

	public int insertRace(double distance, String dateRun, double goalTime, double runTime, int shoeID, int userID) {
		int rc = 0;

		String template = "INSERT INTO RACES(RACE_DISTANCE, DATE_RUN, GOAL_TIME, TIME_COMPLETED, SHOE, USERID, PLACE) VALUES(?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = dbConnection.prepareStatement(template);
			ps.setDouble(1, distance);
			ps.setString(2, dateRun);
			ps.setDouble(3, goalTime);
			ps.setDouble(4, runTime);
			ps.setInt(5, shoeID);
			ps.setInt(6, userID);
			ps.setInt(7, 1);
			rc = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return rc;
	}

	public int updateRace(double distance, String dateRun, double goalTime, double runTime, int shoeID, int userID, int runID) {
		int rc = 0;

		String template = "UPDATE RACES SET (DISTANCE, DATE_RUN, GOAL_TIME, RUN_TIME, SHOE, USERID, PLACE) = (?, ?, ?, ?, ?, ?, ?) WHERE ID = "
				+ String.valueOf(runID);
		try {
			PreparedStatement ps = dbConnection.prepareStatement(template);
			ps.setDouble(1, distance);
			ps.setString(2, dateRun);
			ps.setDouble(3, goalTime);
			ps.setDouble(4, runTime);
			ps.setInt(5, shoeID);
			ps.setInt(6, userID);
			ps.setInt(7, 1);
			rc = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return rc;
	}

	public void getAllRaces() {
		String sqlStatement = "SELECT * FROM RACES";
		try {
			statement = dbConnection.createStatement();

			try {
				resultSet = statement.executeQuery(sqlStatement);
				sqlResult = SQLUtil.getHtmlTable(resultSet);
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("Cannot execute query");
				e.printStackTrace();
			}

		} catch (SQLException e) {
			System.out.println("Could not create statment: " + e.getMessage());
		}
	}

	public boolean getRunsByID(int runID) {
		boolean rc = false;
		String template = "SELECT * FROM RUNS WHERE ID = '" + runID + "'";
		try {
			// (DISTANCE DATE_RUN, GOAL_TIME, RUN_TIME, GOAL_DISTANCE, SHOE, USERID, PLACE)
			Statement s = dbConnection.createStatement();
			ResultSet rs = s.executeQuery(template);
			if (rs.next()) {
				rc = true;
				setDistance(rs.getDouble("DISTANCE"));
				setDateRun(rs.getString("DATE_RUN"));
				setGoalTime(rs.getDouble("GOAL_TIME"));
				setRunTime(rs.getDouble("RUN_TIME"));
				setUserID(rs.getInt("USERID"));
				setShoeID(rs.getInt("SHOE"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return (rc);
	}

	public int deleteRun(int runID) {
		int rc = 0;

		String template = "DELETE FROM RUNS WHERE ID = " + String.valueOf(runID);
		try {
			PreparedStatement ps = dbConnection.prepareStatement(template);
			rc = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return rc;
	}

//	private double runDistance = 0;
//    private String dateRun = "";
//    private double goalTime = 0;
//    private double runTime = 0;
//    private int shoeID = 0;
//    private int userID = 0;

	public void setDistance(double distance) {
		this.runDistance = distance;
	}

	public void setDateRun(String date) {
		this.dateRun = date;
	}

	public void setRunTime(double time) {
		this.runTime = time;
	}

	public void setGoalTime(double time) {
		this.goalTime = time;
	}

	public void setShoeID(int shoe) {
		this.shoeID = shoe;
	}

	public void setUserID(int user) {
		this.userID = user;
	}

	public double getDistance() {
		return this.runDistance;
	}

	public String getDateRun() {
		return this.dateRun;
	}

	public double getRunTime() {
		return this.runTime;
	}

	public double getGoalTime() {
		return this.goalTime;
	}

	public int getShoeID() {
		return this.shoeID;
	}

	public int getUserID() {
		return this.userID;
	}

	public ResultSet getResultSet() {
		return this.resultSet;
	}

	public String getSqlResult() {
		return this.sqlResult;
	}

}

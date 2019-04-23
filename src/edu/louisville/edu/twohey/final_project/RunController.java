package edu.louisville.edu.twohey.final_project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import edu.louisville.edu.twohey.final_project.*;

public class RunController {
    private Connection dbConnection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;
    private String sqlResult = "";
    private double runDistance = 0;
    private String dateRun = "";
    private double goalTime = 0;
    private double runTime = 0;
    private double goalDistance = 0;
    private int shoeID = 0;
    private int userID = 0;
    
	public RunController(Connection dbConnection)
    {
        this.dbConnection = dbConnection;
    }
	
//	CREATE Table RJTWOH01.RUNS ( 
//			ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
//			USERID INTEGER NOT NULL,
//			DISTANCE DOUBLE NOT NULL,
//			RUN_TIME TIME NOT NULL,
//			DATE_RUN DATE NOT NULL,
//			GOAL_DISTANCE DOUBLE NOT NULL,
//			GOAL_TIME TIME NOT NULL,
//			SHOE INTEGER NOT NULL,
//			PLACE INTEGER NOT NULL,
//			PRIMARY KEY (ID))

	
	public int insertRun(double distance, String dateRun, double goalTime, double runTime, double goalDistance, int shoeID, int userID) {
    	int rc = 0;
    	
    	String template = "INSERT INTO RUNS(DISTANCE, DATE_RUN, GOAL_TIME, RUN_TIME, GOAL_DISTANCE, SHOE, USERID, PLACE) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    	try {
    		PreparedStatement ps = dbConnection.prepareStatement(template);
    		ps.setDouble(1, distance);
    		ps.setString(2, dateRun);
    		ps.setDouble(3, goalTime);
    		ps.setDouble(4, runTime);
    		ps.setDouble(5, goalDistance);
    		ps.setInt(6, shoeID);
    		ps.setInt(7, userID);
    		ps.setInt(8, 1);
    		rc = ps.executeUpdate();
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	return rc;
    }
	
	public int updateRun(double distance, String dateRun, double goalTime, double runTime, double goalDistance, int shoeID, int userID, int runID) {
    	int rc = 0;
    	
    	String template = "UPDATE RUNS SET (DISTANCE, DATE_RUN, GOAL_TIME, RUN_TIME, GOAL_DISTANCE, SHOE, USERID, PLACE) = (?, ?, ?, ?, ?, ?, ?, ?) WHERE ID = " + String.valueOf(runID); 
    	try {
    		PreparedStatement ps = dbConnection.prepareStatement(template);
    		ps.setDouble(1, distance);
    		ps.setString(2, dateRun);
    		ps.setDouble(3, goalTime);
    		ps.setDouble(4, runTime);
    		ps.setDouble(5, goalDistance);
    		ps.setInt(6, shoeID);
    		ps.setInt(7, userID);
    		ps.setInt(8, 1);
    		rc = ps.executeUpdate();
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	return rc;
    }
	
	public void getAllRuns()
    {
		String sqlStatement = "SELECT * FROM RUNS";
        try
        {
            statement = dbConnection.createStatement();
            
            try
            {
                resultSet = statement.executeQuery(sqlStatement);
                sqlResult = SQLUtil.getHtmlTable(resultSet);
                resultSet.close();
            }
            catch (SQLException e)
            {
                System.out.println("Cannot execute query");
                e.printStackTrace();
            }
            
        }
        catch (SQLException e)
        {
            System.out.println("Could not create statment: " + e.getMessage());
        }
    }
	
	public boolean getRunsByID(int runID) {
		boolean rc = false;
        String template = "SELECT * FROM RUNS WHERE ID = '" + runID + "'";
        try
        {
        	//(DISTANCE DATE_RUN, GOAL_TIME, RUN_TIME, GOAL_DISTANCE, SHOE, USERID, PLACE)
            Statement s = dbConnection.createStatement();
            ResultSet rs = s.executeQuery(template);
            if (rs.next())
            {
                rc = true;
                setDistance(rs.getDouble("DISTANCE"));
                setDateRun(rs.getString("DATE_RUN"));
                setGoalTime(rs.getDouble("GOAL_TIME"));
                setGoalDistance(rs.getDouble("GOAL_DISTANCE"));
                setRunTime(rs.getDouble("RUN_TIME"));
                setUserID(rs.getInt("USERID"));
                setShoeID(rs.getInt("SHOE"));
            }
        }
        catch (SQLException e)
        {
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
//    private double goalDistance = 0;
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
	
	public void setGoalDistance(double distance) {
		this.goalDistance = distance;
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
	
	public double getGoalDistance() {
		return this.goalDistance;
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

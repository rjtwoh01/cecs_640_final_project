package edu.louisville.edu.twohey.final_project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import edu.louisville.edu.twohey.final_project.*;

public class EllipticalController {
    private Connection dbConnection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;
    private String sqlResult = "";
    private double ellipticalDistance = 0;
    private String dateElliptical = "";
    private double goalTime = 0;
    private double ellipticalTime = 0;
    private double goalDistance = 0;
    private int intensity = 0;
    private int goalIntensity = 0;
    private int userID = 0;
    
	public EllipticalController(Connection dbConnection)
    {
        this.dbConnection = dbConnection;
    }
	
//	CREATE TABLE RJTWOH01.ELLIPTICAL (
//			ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
//			USERID INTEGER NOT NULL,
//			DISTANCE DOUBLE NOT NULL,
//			ELLIPTICAL_TIME DOUBLE NOT NULL,
//			DATE_ELLIPTICAL VARCHAR(30) NOT NULL,
//			GOAL_DISTANCE DOUBLE NOT NULL,
//			GOAL_TIME DOUBLE NOT NULL,
//			INTENSITY INTEGER NOT NULL,
//			GOAL_INTENSITY INTEGER NOT NULL,
//			PRIMARY KEY (ID))
	
	public int insertElliptical(double distance, String dateElliptical, double goalTime, double ellipticalTime, double goalDistance, int goalIntensity, int userID, int intensity) {
    	int rc = 0;
    	
    	String template = "INSERT INTO ELLIPTICAL(DISTANCE, DATE_ELLIPTICAL, GOAL_TIME, ELLIPTICAL_TIME, GOAL_DISTANCE, INTENSITY, USERID, GOAL_INTENSITY) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    	try {
    		PreparedStatement ps = dbConnection.prepareStatement(template);
    		ps.setDouble(1, distance);
    		ps.setString(2, dateElliptical);
    		ps.setDouble(3, goalTime);
    		ps.setDouble(4, ellipticalTime);
    		ps.setDouble(5, goalDistance);
    		ps.setInt(6, intensity);
    		ps.setInt(7, userID);
    		ps.setInt(8, goalIntensity);
    		rc = ps.executeUpdate();
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	return rc;
    }
	
	public int updateElliptical(double distance, String dateElliptical, double goalTime, double ellipticalTime, double goalDistance, int goalIntensity, int userID, int intensity, int ellipticalID) {
    	int rc = 0;
    	
    	String template = "UPDATE ELLIPTICAL SET (DISTANCE, DATE_ELLIPTICAL, GOAL_TIME, ELLIPTICAL_TIME, GOAL_DISTANCE, INTENSITY, USERID, GOAL_INTENSITY) = (?, ?, ?, ?, ?, ?, ?, ?) WHERE ID = " + String.valueOf(ellipticalID); 
    	try {
    		PreparedStatement ps = dbConnection.prepareStatement(template);
    		ps.setDouble(1, distance);
    		ps.setString(2, dateElliptical);
    		ps.setDouble(3, goalTime);
    		ps.setDouble(4, ellipticalTime);
    		ps.setDouble(5, goalDistance);
    		ps.setInt(6, intensity);
    		ps.setInt(7, userID);
    		ps.setInt(8, goalIntensity);
    		rc = ps.executeUpdate();
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	return rc;
    }
	
	public void getAllElliptical()
    {
		String sqlStatement = "SELECT * FROM ELLIPTICAL";
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
	
	public boolean getEllipticalByID(int ellipticalID) {
		boolean rc = false;
        String template = "SELECT * FROM ELLIPTICAL WHERE ID = '" + ellipticalID + "'";
        try
        {
        	//DISTANCE, DATE_ELLIPTICAL, GOAL_TIME, ELLIPTICAL_TIME, GOAL_DISTANCE, INTENSITY, USERID, GOAL_INTENSITY
            Statement s = dbConnection.createStatement();
            ResultSet rs = s.executeQuery(template);
            if (rs.next())
            {
                rc = true;
                setDistance(rs.getDouble("DISTANCE"));
                setDateRun(rs.getString("DATE_ELLIPTICAL"));
                setGoalTime(rs.getDouble("GOAL_TIME"));
                setGoalDistance(rs.getDouble("GOAL_DISTANCE"));
                setRunTime(rs.getDouble("ELLIPTICAL_TIME"));
                setUserID(rs.getInt("USERID"));
                setIntensity(rs.getInt("INTENSITY"));
                setGoalIntensity(rs.getInt("GOAL_INTENSITY"));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return (rc);
	}
	
	public int deleteElliptical(int ellipticalID) {
		int rc = 0;
    	
    	String template = "DELETE FROM ELLIPTICAL WHERE ID = " + String.valueOf(ellipticalID); 
    	try {
    		PreparedStatement ps = dbConnection.prepareStatement(template);
    		rc = ps.executeUpdate();
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	return rc;
	}
	
	public void setDistance(double distance) {
		this.ellipticalDistance = distance;
	}
	
	public void setDateRun(String date) {
		this.dateElliptical = date;
	}
	
	public void setRunTime(double time) {
		this.ellipticalTime = time;
	}
	
	public void setGoalDistance(double distance) {
		this.goalDistance = distance;
	}
	
	public void setGoalTime(double time) {
		this.goalTime = time;
	}
	
	public void setUserID(int user) {
		this.userID = user;
	}
	
	public double getDistance() {
		return this.ellipticalDistance;
	}
	
	public String getDateRun() {
		return this.dateElliptical;
	}
	
	public double getRunTime() {
		return this.ellipticalTime;
	}
	
	public double getGoalDistance() {
		return this.goalDistance;
	}
	
	public double getGoalTime() {
		return this.goalTime;
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

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	public int getGoalIntensity() {
		return goalIntensity;
	}

	public void setGoalIntensity(int goalIntensity) {
		this.goalIntensity = goalIntensity;
	}
	
}

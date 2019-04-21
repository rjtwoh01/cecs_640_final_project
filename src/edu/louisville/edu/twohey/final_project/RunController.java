package edu.louisville.edu.twohey.final_project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class RunController {
    private Connection dbConnection = null;
    
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
	
}

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
    private String shoeName;
    private int shoeID;
    
	public ShoeController(Connection dbConnection)
    {
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
	
	public boolean findShoe(String shoeName, int userID)
    {
        boolean rc = false;
        this.shoeName = "";
        String template = "SELECT * FROM SHOES WHERE USERID = '" + userID + "' AND NAME = '" + shoeName + "'";
        try
        {
            Statement s = dbConnection.createStatement();
            ResultSet rs = s.executeQuery(template);
            if (rs.next())
            {
                rc = true;
                setShoeName(rs.getString("NAME"));
                setShoeID(rs.getInt("ID"));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return (rc);
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

}

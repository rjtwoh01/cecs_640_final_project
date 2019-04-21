package edu.louisville.edu.twohey.final_project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersController
{
    private Connection dbConnection = null;
    private String fullName = "";
    private String username = "";
    private int id = -1;

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    public void setID(int id) {
    	this.id = id;
    }
    public void setUsername(String username) {
    	this.username = username;
    }
    /**
     * 
     */
    public UsersController(Connection dbConnection)
    {
        this.dbConnection = dbConnection;
    }
    public boolean findUser(String username, String password)
    {
        boolean rc = false;
        fullName     = "";
        String template = "SELECT * FROM USERS WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "'";
        try
        {
            Statement s = dbConnection.createStatement();
            ResultSet rs = s.executeQuery(template);
            if (rs.next())
            {
                rc = true;
                setFullName((rs.getString("FIRSTNAME") + " " + rs.getString("LASTNAME")));
                setID(rs.getInt("ID"));
                setUsername(rs.getString("USERNAME"));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return (rc);
    }
    
    //we do not the id value for the db because it is an auto generated primary key
    public int insertUser(String username, String password, String fname, String lname) {
    	//rc is the number of rows effected
    	//if 0 rows are effected then rc will hold a value of 0 at the end, 1 row rc = 1, etc...
    	int rc = 0;
    	
    	String template = "INSERT INTO USERS(FIRSTNAME, LASTNAME, USERNAME, PASSWORD) VALUES(?, ?, ?, ?)";
    	try {
    		PreparedStatement ps = dbConnection.prepareStatement(template);
    		ps.setString(1, fname);
    		ps.setString(2, lname);
    		ps.setString(3, username);
    		ps.setString(4, password);
    		rc = ps.executeUpdate();
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    	}
    	
    	return rc;
    }
    
    public String getFullName()
    {
        return fullName;
    }
    
    public int getID() {
    	return id;
    }
    
    public String getUsername() {
    	return username;
    }
}

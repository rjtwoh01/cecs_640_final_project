package edu.louisville.edu.twohey.final_project;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EditRunServlet
 */
@WebServlet("/EditRunServlet")
public class EditRunServlet extends HttpServlet {

	private static Connection connection = null;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditRunServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("userID") != null || session.getAttribute("username") != null) {
			int userID = Integer.parseInt(session.getAttribute("userID").toString());
			if (request.getParameter("submitNewRun") != null) {
				String successMessage = "Run successfully updated";
				String errorMessage = "Error adding run";
				try {
					String distanceString = request.getParameter("distance");
					String dateRun = request.getParameter("dateRan");
					String goalTimeString = request.getParameter("goalTime");
					String runTimeString = request.getParameter("runTime");
					String goalDistanceString = request.getParameter("goalDistance");
					String shoeName = request.getParameter("shoe");
					Object runIDObject = session.getAttribute("runID");
					String runIDString = "0";
					System.out.println("distanceString - " + distanceString);
					System.out.println("dateRun - " + dateRun);
					System.out.println("goalDistanceString - " + goalDistanceString);
					System.out.println("shoeName - " + shoeName);
					System.out.println("runIDObject - " + runIDObject.toString());
					System.out.println("runIDString - " + runIDString);
		
					if (runIDObject != null)
						runIDString = runIDObject.toString();
					System.out.println("runIDString: " + runIDString);
						 
						
					if (distanceString == null || dateRun == null || goalTimeString == null || runTimeString == null
							|| goalDistanceString == null || shoeName == null || distanceString.length() == 0
							|| dateRun.length() == 0 || goalTimeString.length() == 0 || runTimeString.length() == 0
							|| goalDistanceString.length() == 0 || shoeName.length() == 0 || runIDString == null || runIDString.length() == 0) {
						if (runIDString != null && runIDString.length() != 0 && !runIDString.equals("0"))
							session.setAttribute("errorMessage", "You must fill out all fields to submit");
						else
							session.setAttribute("errorMessage", "This run does not exist");
					} else {
						System.out.println("No missing fields");
						double distance = Double.parseDouble(distanceString);
						System.out.println("distance - " + distance);
						double goalDistance = Double.parseDouble(goalDistanceString);
						System.out.println("goalDistance - " + goalDistance);
						double goalTime = Double.parseDouble(goalTimeString);
						System.out.println("goalTime - " + goalTime);
						double runTime = Double.parseDouble(runTimeString);
						System.out.println("runTime - " + runTime);
						int runID = Integer.parseInt(runIDString);
						System.out.println("runID - " + runID);
						
						System.out.println("Getting connection...");
						ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
						connection = pool.getConnection();
						if (connection != null) {
							System.out.println("Got connection");
							ShoeController sc = new ShoeController(connection);
							RunController rc = new RunController(connection);
							
							if (sc.findShoe(shoeName, userID) == false ) {
								System.out.println("No shoe");
								if (sc.insertShoe(shoeName, userID) != 0) {
									System.out.println("Shoe inserted");
									if (sc.findShoe(shoeName, userID) == true ) {
										System.out.println("Shoe found");
										int shoeID = sc.getShoeID();
										if (rc.updateRun(distance, dateRun, goalTime, runTime, goalDistance, shoeID, userID, runID) != 0) {
											session.setAttribute("successMessage", successMessage);
											System.out.println("run updated");
										}
										else {
											session.setAttribute("errorMessage", errorMessage);
										}
									}
									else {
										session.setAttribute("errorMessage", errorMessage);
									}
								}
								else {
									session.setAttribute("errorMessage", errorMessage);
								}
							} else if (sc.findShoe(shoeName, userID) == true ) {
								System.out.println("Shoe found");
								int shoeID = sc.getShoeID();
								System.out.println(shoeID);
								if (rc.updateRun(distance, dateRun, goalTime, runTime, goalDistance, shoeID, userID, runID) != 0) {
									System.out.println("run updated");
									session.setAttribute("successMessage", successMessage);
								} else {
									session.setAttribute("errorMessage", errorMessage);
								}
							}
							else {
								session.setAttribute("errorMessage", errorMessage);
							}
							pool.freeConnection(connection);
						} else {
							session.setAttribute("errorMessage", "Could not connect to the session");
						}

					}
				} catch (Exception e) {
					System.out.println(e);
					session.setAttribute("errorMessage", e.toString());
				}
				response.sendRedirect("/FinalProject/Runs/EditRun.jsp");
			} else if (request.getParameter("returnToDashboard") != null)
				response.sendRedirect("/FinalProject/Dashboard/Dashboard.jsp");
			else
				response.sendRedirect("/FinalProject/Runs/EditRun.jsp");
		} else {
			response.sendRedirect("/FinalProject/LoginPage/Login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

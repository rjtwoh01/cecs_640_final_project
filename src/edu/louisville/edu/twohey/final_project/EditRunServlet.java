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
		String url = "/FinalProject/Runs/ViewRuns.jsp";
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
		
					if (runIDObject != null)
						runIDString = runIDObject.toString();
						 
						
					if (distanceString == null || dateRun == null || goalTimeString == null || runTimeString == null
							|| goalDistanceString == null || shoeName == null || distanceString.length() == 0
							|| dateRun.length() == 0 || goalTimeString.length() == 0 || runTimeString.length() == 0
							|| goalDistanceString.length() == 0 || shoeName.length() == 0 || runIDString == null || runIDString.length() == 0) {
						if (runIDString != null && runIDString.length() != 0 && !runIDString.equals("0"))
							session.setAttribute("errorMessage", "You must fill out all fields to submit");
						else
							session.setAttribute("errorMessage", "This run does not exist");
					} else {
						double distance = Double.parseDouble(distanceString);
						double goalDistance = Double.parseDouble(goalDistanceString);
						double goalTime = Double.parseDouble(goalTimeString);
						double runTime = Double.parseDouble(runTimeString);
						int runID = Integer.parseInt(runIDString);
						
						ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
						connection = pool.getConnection();
						if (connection != null) {
							ShoeController sc = new ShoeController(connection);
							RunController rc = new RunController(connection);
							
							if (sc.findShoe(shoeName, userID) == false ) {
								if (sc.insertShoe(shoeName, userID) != 0) {
									if (sc.findShoe(shoeName, userID) == true ) {
										int shoeID = sc.getShoeID();
										if (rc.updateRun(distance, dateRun, goalTime, runTime, goalDistance, shoeID, userID, runID) != 0) {
											session.setAttribute("successMessage", successMessage);
											rc.getAllRuns();
											String runs = rc.getSqlResult();
											session.setAttribute("runs", runs);
										}
										else {
											url = "/FinalProject/Runs/EditRun.jsp";
											session.setAttribute("errorMessage", errorMessage);
										}
									}
									else {
										url = "/FinalProject/Runs/EditRun.jsp";
										session.setAttribute("errorMessage", errorMessage);
									}
								}
								else {
									url = "/FinalProject/Runs/EditRun.jsp";
									session.setAttribute("errorMessage", errorMessage);
								}
							} else if (sc.findShoe(shoeName, userID) == true ) {
								int shoeID = sc.getShoeID();
								if (rc.updateRun(distance, dateRun, goalTime, runTime, goalDistance, shoeID, userID, runID) != 0) {
									session.setAttribute("successMessage", successMessage);
									rc.getAllRuns();
									String runs = rc.getSqlResult();
									session.setAttribute("runs", runs);
								} else {
									url = "/FinalProject/Runs/EditRun.jsp";
									session.setAttribute("errorMessage", errorMessage);
								}
							}
							else {
								url = "/FinalProject/Runs/EditRun.jsp";
								session.setAttribute("errorMessage", errorMessage);
							}
							pool.freeConnection(connection);
						} else {
							url = "/FinalProject/Runs/EditRun.jsp";
							session.setAttribute("errorMessage", "Could not connect to the session");
						}

					}
				} catch (Exception e) {
					System.out.println(e);
					session.setAttribute("errorMessage", e.toString());
				}
				response.sendRedirect(url);
			} else if (request.getParameter("returnToViewRuns") != null)
				response.sendRedirect("/FinalProject/Runs/ViewRuns.jsp");
			else
				response.sendRedirect(url);
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

package edu.louisville.edu.twohey.final_project;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class NewRunServlet
 */
@WebServlet("/NewRaceServlet")
public class NewRaceServlet extends HttpServlet {
	private static Connection connection = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewRaceServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session.getAttribute("userID") != null || session.getAttribute("username") != null) {
			int userID = Integer.parseInt(session.getAttribute("userID").toString());
			if (request.getParameter("submitNewRace") != null) {
				String successMessage = "Race successfully added";
				String errorMessage = "Error adding race";
				try {
					String distanceString = request.getParameter("distance");
					String dateRun = request.getParameter("dateRan");
					String goalTimeString = request.getParameter("goalTime");
					String runTimeString = request.getParameter("runTime");
					String shoeName = request.getParameter("shoe");

					System.out.println(distanceString);

					if (distanceString == null || dateRun == null || goalTimeString == null || runTimeString == null
							 || shoeName == null || distanceString.length() == 0
							|| dateRun.length() == 0 || goalTimeString.length() == 0 || runTimeString.length() == 0
							|| shoeName.length() == 0) {
						session.setAttribute("errorMessage", "You must fill out all fields to submit");
					} else {
						double distance = Double.parseDouble(distanceString);;
						double goalTime = Double.parseDouble(goalTimeString);
						double runTime = Double.parseDouble(runTimeString);
						ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
						connection = pool.getConnection();
						if (connection != null) {
							ShoeController sc = new ShoeController(connection);
							RaceController rc = new RaceController(connection);
							if (sc.findShoe(shoeName, userID) == false ) {
								if (sc.insertShoe(shoeName, userID) != 0) {
									if (sc.findShoe(shoeName, userID) == true ) {
										int shoeID = sc.getShoeID();
										if (rc.insertRace(distance, dateRun, goalTime, runTime, shoeID, userID) != 0) {
											session.setAttribute("successMessage", successMessage);
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
								int shoeID = sc.getShoeID();
								if (rc.insertRace(distance, dateRun, goalTime, runTime, shoeID, userID) != 0) {
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
				response.sendRedirect("/FinalProject/Races/NewRace.jsp");
			} else if (request.getParameter("returnToDashboard") != null)
				response.sendRedirect("/FinalProject/Dashboard/Dashboard.jsp");
			else
				response.sendRedirect("/FinalProject/Races/NewRace.jsp");
		} else {
			response.sendRedirect("/FinalProject/LoginPage/Login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

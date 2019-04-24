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
@WebServlet("/NewEllipticalServlet")
public class NewEllipticalServlet extends HttpServlet {
	private static Connection connection = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewEllipticalServlet() {
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
		if (session.getAttribute("userID") != null) {
			int userID = Integer.parseInt(session.getAttribute("userID").toString());
			if (request.getParameter("submitNewElliptical") != null) {
				String successMessage = "Elliptical successfully added";
				String errorMessage = "Error adding elliptical";
				try {
					String distanceString = request.getParameter("distance");
					String dateRun = request.getParameter("dateRan");
					String goalTimeString = request.getParameter("goalTime");
					String runTimeString = request.getParameter("runTime");
					String goalDistanceString = request.getParameter("goalDistance");
					String intensityString = request.getParameter("intensity");
					String goalIntensityString = request.getParameter("goalIntensity");

					System.out.println(goalIntensityString);

					if (distanceString == null || dateRun == null || goalTimeString == null || runTimeString == null
							|| goalDistanceString == null || intensityString == null || distanceString.length() == 0
							|| dateRun.length() == 0 || goalTimeString.length() == 0 || runTimeString.length() == 0
							|| goalDistanceString.length() == 0 || intensityString.length() == 0 
							|| goalIntensityString == null ||  goalIntensityString.length() == 0) {
						session.setAttribute("errorMessage", "You must fill out all fields to submit");
					} else {
						double distance = Double.parseDouble(distanceString);
						double goalDistance = Double.parseDouble(goalDistanceString);
						double goalTime = Double.parseDouble(goalTimeString);
						double runTime = Double.parseDouble(runTimeString);
						int intensity = Integer.parseInt(intensityString);
						int goalIntensity = Integer.parseInt(goalIntensityString);
						ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
						connection = pool.getConnection();
						if (connection != null) {
							EllipticalController ec = new EllipticalController(connection);
								if (ec.insertElliptical(distance, dateRun, goalTime, runTime, goalDistance, goalIntensity, userID, intensity) != 0) {
									session.setAttribute("successMessage", successMessage);
								} else {
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
				response.sendRedirect("/FinalProject/Elliptical/NewElliptical.jsp");
			} else if (request.getParameter("returnToDashboard") != null)
				response.sendRedirect("/FinalProject/Dashboard/Dashboard.jsp");
			else
				response.sendRedirect("/FinalProject/Elliptical/NewElliptical.jsp");
		}else

	{
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

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
@WebServlet("/EditEllipticalServlet")
public class EditEllipticalServlet extends HttpServlet {

	private static Connection connection = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditEllipticalServlet() {
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
		String url = "/FinalProject/Elliptical/ViewElliptical.jsp";
		if (session.getAttribute("userID") != null || session.getAttribute("username") != null) {
			int userID = Integer.parseInt(session.getAttribute("userID").toString());
			if (request.getParameter("submitNewElliptical") != null) {
				String successMessage = "Elliptical successfully updated";
				String errorMessage = "Error adding elliptical";
				try {
					String distanceString = request.getParameter("distance");
					String dateRun = request.getParameter("dateRan");
					String goalTimeString = request.getParameter("goalTime");
					String runTimeString = request.getParameter("runTime");
					String goalDistanceString = request.getParameter("goalDistance");
					String intensityString = request.getParameter("intensity");
					String goalIntensityString = request.getParameter("goalIntensity");
					Object ellipticalIDObject = session.getAttribute("ellipticalID");
					String ellipticalIDString = "0";

					if (ellipticalIDObject != null)
						ellipticalIDString = ellipticalIDObject.toString();

					if (distanceString == null || dateRun == null || goalTimeString == null || runTimeString == null
							|| goalDistanceString == null || distanceString.length() == 0 || dateRun.length() == 0
							|| goalTimeString.length() == 0 || runTimeString.length() == 0
							|| goalDistanceString.length() == 0 || ellipticalIDString == null
							|| ellipticalIDString.length() == 0) {
						if (ellipticalIDString != null && ellipticalIDString.length() != 0
								&& !ellipticalIDString.equals("0"))
							session.setAttribute("errorMessage", "You must fill out all fields to submit");
						else
							session.setAttribute("errorMessage", "This elliptical does not exist");
					} else {
						double distance = Double.parseDouble(distanceString);
						double goalDistance = Double.parseDouble(goalDistanceString);
						double goalTime = Double.parseDouble(goalTimeString);
						double runTime = Double.parseDouble(runTimeString);
						int intensity = Integer.parseInt(intensityString);
						int goalIntensity = Integer.parseInt(goalIntensityString);
						int ellipticalID = Integer.parseInt(ellipticalIDString);
						ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
						connection = pool.getConnection();
						if (connection != null) {
							EllipticalController ec = new EllipticalController(connection);
							if (ec.updateElliptical(distance, dateRun, goalTime, runTime, goalDistance, goalIntensity,
									userID, intensity, ellipticalID) != 0) {
								session.setAttribute("successMessage", successMessage);
								ec.getAllElliptical();
								String runs = ec.getSqlResult();
								session.setAttribute("elliptical", runs);
							} else {
								session.setAttribute("errorMessage", errorMessage);
							}

							pool.freeConnection(connection);
						} else {
							url = "/FinalProject/Elliptical/EditElliptical.jsp";
							session.setAttribute("errorMessage", "Could not connect to the session");
						}

					}
				} catch (Exception e) {
					System.out.println(e);
					session.setAttribute("errorMessage", e.toString());
				}
				response.sendRedirect(url);
			} else if (request.getParameter("returnToViewElliptical") != null)
				response.sendRedirect("/FinalProject/Elliptical/ViewElliptical.jsp");
			else
				response.sendRedirect(url);
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

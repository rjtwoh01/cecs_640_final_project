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
@WebServlet("/NewShoeServlet")
public class NewShoeServlet extends HttpServlet {
	private static Connection connection = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewShoeServlet() {
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
			if (request.getParameter("submitNewShoe") != null) {
				String successMessage = "Shoe successfully added";
				String errorMessage = "Error adding shoe";
				try {
					String distanceString = request.getParameter("distanceRun");
					String shoeName = request.getParameter("shoe");
					String distanceGoalString = request.getParameter("distanceGoal");
					String retiredString = request.getParameter("retired");

					if (distanceString == null ||  distanceGoalString == null || distanceString.length() == 0  || distanceGoalString.length() == 0) {
						session.setAttribute("errorMessage", "You must fill out all fields to submit");
					} else {
						double distance = Double.parseDouble(distanceString);
						double distanceGoal = Double.parseDouble(distanceGoalString);
						int retired = Integer.parseInt(retiredString);
						ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
						connection = pool.getConnection();
						if (connection != null) {
							ShoeController sc = new ShoeController(connection);
								if (sc.insertFullShoe(shoeName, userID, distance, distanceGoal, retired) != 0) {
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
				response.sendRedirect("/FinalProject/Shoes/NewShoe.jsp");
			} else if (request.getParameter("returnToDashboard") != null)
				response.sendRedirect("/FinalProject/Dashboard/Dashboard.jsp");
			else
				response.sendRedirect("/FinalProject/Shoes/NewShoe.jsp");
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

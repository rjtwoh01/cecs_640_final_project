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
@WebServlet("/EditShoeServlet")
public class EditShoeServlet extends HttpServlet {

	private static Connection connection = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditShoeServlet() {
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
		String url = "/FinalProject/Shoes/ViewShoes.jsp";
		if (session.getAttribute("userID") != null || session.getAttribute("username") != null) {
			int userID = Integer.parseInt(session.getAttribute("userID").toString());
			if (request.getParameter("submitNewShoe") != null) {
				String successMessage = "Shoe successfully updated";
				String errorMessage = "Error updating shoe";
				try {
					String distanceString = request.getParameter("distanceRun");
					String shoeName = request.getParameter("shoe");
					String distanceGoalString = request.getParameter("distanceGoal");
					String retiredString = request.getParameter("retired");
					Object shoeIDObject = session.getAttribute("shoeID");
					String shoeIDString = "0";
					
					if (shoeIDObject != null)
						shoeIDString = shoeIDObject.toString();

					if (distanceString == null ||  distanceGoalString == null || distanceString.length() == 0  || distanceGoalString.length() == 0 || shoeIDObject == null) {
						session.setAttribute("errorMessage", "You must fill out all fields to submit");
					} else {
						double distance = Double.parseDouble(distanceString);
						double distanceGoal = Double.parseDouble(distanceGoalString);
						int retired = Integer.parseInt(retiredString);
						int shoeID = Integer.parseInt(shoeIDString);
						ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
						connection = pool.getConnection();
						if (connection != null) {
							ShoeController sc = new ShoeController(connection);
								if (sc.updateShoe(shoeName, userID, distance, distanceGoal, retired, shoeID) != 0) {
									session.setAttribute("successMessage", successMessage);
									sc.getAllShoes();
									String runs = sc.getSqlResult();
									session.setAttribute("shoes", runs);
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
				response.sendRedirect(url);
			} else if (request.getParameter("returnToViewShoes") != null)
				response.sendRedirect("/FinalProject/Shoes/ViewShoes.jsp");
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

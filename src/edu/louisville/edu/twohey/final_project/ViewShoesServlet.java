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
 * Servlet implementation class ViewRunsServlet
 */
@WebServlet("/ViewShoesServlet")
public class ViewShoesServlet extends HttpServlet {
	private static Connection connection = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewShoesServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("userID") != null) {
			if (request.getParameter("returnToDashboard") != null) {
				response.sendRedirect("/FinalProject/Dashboard/Dashboard.jsp");
			} else if (request.getParameter("numberOfRows") != null) {
				int rowCount = Integer.parseInt(request.getParameter("numberOfRows"));
				int id = 0;

				for (int i = 1; i <= rowCount; i++) {
					String editName = "table_" + String.valueOf(i);
					String deleteName = "delete_table_" + String.valueOf(i);
					if (request.getParameter(editName) != null) {
						// Perform logic to redirect to edit page and set appropriate session values...
						// Will need to do another query to get this specific set of values to edit
						id = i;

						ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
						connection = pool.getConnection();
						if (connection != null) {
							ShoeController sc = new ShoeController(connection);

							if (sc.findShoeByID(id) == true) {
								double runDistance = sc.getShoeDistance();
								String name = sc.getShoeName();
								double goalDistance = sc.getShoeGoalDistance();
								int retired = sc.getRetired();

								session.setAttribute("distanceMessage", runDistance);
								session.setAttribute("nameMessage", name);
								session.setAttribute("distanceGoalMessage", goalDistance);
								session.setAttribute("retiredMessage", retired);
								session.setAttribute("shoeID", id);
								response.sendRedirect("/FinalProject/Shoes/EditShoe.jsp");
							} else
								response.sendRedirect("/FinalProject/Dashboard/Dashboard.jsp");
							pool.freeConnection(connection);
						}
					} else if (request.getParameter(deleteName) != null) {
						id = i;
						ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
						connection = pool.getConnection();
						if (connection != null) {
							ShoeController sc = new ShoeController(connection);
							if (sc.deleteShoe(id) != 0) {
								sc.getAllShoes();
								String runs = sc.getSqlResult();
								session.setAttribute("shoes", runs);
								session.setAttribute("deletedSuccessMessage", "Shoe Deleted");
							} else
								session.setAttribute("errorMessage", "There was a problem deleting the elliptical");

							pool.freeConnection(connection);
							response.sendRedirect("/FinalProject/Shoes/ViewShoes.jsp");
						}
					}
				}
			}
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

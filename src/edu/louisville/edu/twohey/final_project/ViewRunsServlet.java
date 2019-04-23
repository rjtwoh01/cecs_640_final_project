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
@WebServlet("/ViewRunsServlet")
public class ViewRunsServlet extends HttpServlet {
	private static Connection connection = null;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewRunsServlet() {
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
		if (request.getParameter("returnToDashboard") != null) {
			response.sendRedirect("/FinalProject/Dashboard/Dashboard.jsp");
		} else if (request.getParameter("numberOfRows") != null) {
			System.out.println("numberOfRows != null");
			int rowCount = Integer.parseInt(request.getParameter("numberOfRows"));
			int id = 0;

			for (int i = 1; i <= rowCount; i++) {
				String editName = "table_" + String.valueOf(i);
				if (request.getParameter(editName) != null) {
					// Perform logic to redirect to edit page and set appropriate session values...
					// Will need to do another query to get this specific set of values to edit
					System.out.println("Found the edit button! - " + editName);
					id = i;
					System.out.println("id: " + id);

					ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
					connection = pool.getConnection();
					if (connection != null) {
						ShoeController sc = new ShoeController(connection);
						RunController rc = new RunController(connection);

						if (rc.getRunsByID(id) == true) {
							double runDistance = rc.getDistance();
							String dateRun = rc.getDateRun();
							double goalTime = rc.getGoalTime();
							double runTime = rc.getRunTime();
							double goalDistance = rc.getGoalDistance();
							int shoeID = rc.getShoeID();
							int userID = rc.getUserID();
							System.out.println("Found run");
							
							if (sc.findShoeByID(shoeID) == true) {
								System.out.println("Found shoe");
								String shoeName = sc.getShoeName();
								session.setAttribute("distanceMessage", runDistance);
								session.setAttribute("runTimeMessage", runTime);
								session.setAttribute("dateRanMessage", dateRun);
								session.setAttribute("goalDistanceMessage", goalDistance);
								session.setAttribute("goalTimeMessage", goalTime);
								session.setAttribute("shoeMessage", shoeName);
								session.setAttribute("runID", id);
								response.sendRedirect("/FinalProject/Runs/EditRun.jsp");
							}
							else
								response.sendRedirect("/FinalProject/Dashboard/Dashboard.jsp");
						}
						else
							response.sendRedirect("/FinalProject/Dashboard/Dashboard.jsp");
						pool.freeConnection(connection);
					}
				}
			}
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

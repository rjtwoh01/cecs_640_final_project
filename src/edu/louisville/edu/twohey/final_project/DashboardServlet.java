package edu.louisville.edu.twohey.final_project;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class WelcomeServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection connection = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "/FinalProject/LoginPage/Login.jsp";
		if (request.getParameter("logout") != null)
			response.sendRedirect(url);
		else if (request.getParameter("newRun") != null)
			response.sendRedirect("/FinalProject/Runs/NewRun.jsp");
		else if (request.getParameter("viewRuns") != null)
		{	
			getRuns(request);
			response.sendRedirect("/FinalProject/Runs/ViewRuns.jsp");
		}
		else
			response.sendRedirect("/FinalProject/Dashboard/Dashboard.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void getRuns(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
		connection = pool.getConnection();
		RunController rc = new RunController(connection);
		rc.getAllRuns();
		String runs = rc.getSqlResult();
		pool.freeConnection(connection);
		session.setAttribute("runs", runs);
	}

}

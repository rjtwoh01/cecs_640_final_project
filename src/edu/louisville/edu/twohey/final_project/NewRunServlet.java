package edu.louisville.edu.twohey.final_project;

import java.io.IOException;
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
@WebServlet("/NewRunServlet")
public class NewRunServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewRunServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("submitNewRun") != null) {
			String successMessage = "Run successfully added";
			try {
				String distanceString = request.getParameter("distance");
				String dateRun = request.getParameter("dateRan");
				String goalTime = request.getParameter("goalTime");
				String runTime = request.getParameter("runTime");
				String goalDistanceString = request.getParameter("goalDistance");
				String shoeName = request.getParameter("shoe");
				
				System.out.println(distanceString);
				
				if (distanceString == null || dateRun == null || goalTime == null || runTime == null || goalDistanceString == null || shoeName == null
						|| distanceString.length() == 0 || dateRun.length() == 0 || goalTime.length() == 0 || runTime.length() == 0 
						|| goalDistanceString.length() == 0 || shoeName.length() == 0)
				{
					HttpSession session = request.getSession(false);
					session.setAttribute("errorMessage", "You must fill out all fields to submit");
				}
				else {
					double distance = Double.parseDouble(distanceString);
					
				}
			} catch (Exception e) {
				System.out.println(e);
				HttpSession session = request.getSession(false);
				session.setAttribute("errorMessage", e.toString());
			}
			response.sendRedirect("/FinalProject/Runs/NewRun.jsp");
		}
		else if (request.getParameter("returnToDashboard") != null)
			response.sendRedirect("/FinalProject/Dashboard/Dashboard.jsp");
		else
			response.sendRedirect("/FinalProject/Runs/NewRun.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

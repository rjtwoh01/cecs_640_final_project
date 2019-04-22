package edu.louisville.edu.twohey.final_project;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewRunsServlet
 */
@WebServlet("/ViewRunsServlet")
public class ViewRunsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewRunsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("returnToDashboard") != null) {
			response.sendRedirect("/FinalProject/Dashboard/Dashboard.jsp");
		}
		else if (request.getParameter("numberOfRows") != null)
		{
			System.out.println("numberOfRows != null");
			int rowCount = Integer.parseInt(request.getParameter("numberOfRows"));
			int id = 0;
			
			for (int i = 1; i <= rowCount; i++) {
				String editName = "table_" + String.valueOf(i);
				if (request.getParameter(editName) != null) {
					//Perform logic to redirect to edit page and set appropriate session values...
					//Will need to do another query to get this specific set of values to edit
					System.out.println("Found the edit button! - " + editName);
					id = i;
					System.out.println("id: " + id);
				}
			}
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

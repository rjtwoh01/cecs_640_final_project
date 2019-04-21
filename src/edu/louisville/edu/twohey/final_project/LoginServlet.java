package edu.louisville.edu.twohey.final_project;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection connection = null;
	private UsersController uc;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "/FinalProject/Dashboard/Dashboard.jsp";
		String user = request.getParameter("usernameInput");
		String password = request.getParameter("passwordInput");

		// We don't want to display any other error message than this one
		// doing so would give a hacker too much information
		// ie, if the username is correct than the password is wrong we don't want to
		// say incorrect password
		// that would let a hacker know that they at least have a valid user that they
		// can now run attacks against
		String errorMessage = "Username or password was incorrect";

		if (request.getParameter("loginAction") != null) {
			if (user == null || user.length() == 0) {
				url = "/FinalProject/LoginPage/Login.jsp";
				request.setAttribute("errorMessage", errorMessage);
				ServletContext context = getServletContext();
				RequestDispatcher dispatcher = context.getRequestDispatcher(url);
				// if request is not from HttpServletRequest, you should do a typecast before
				HttpSession session = request.getSession(false);
				// save message in session
				session.setAttribute("errorMessage", errorMessage);

				response.sendRedirect(url);
			} else {
				ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
				connection = pool.getConnection();
				if (connection != null) {
					uc = new UsersController(connection);
					if (uc.findUser(user, password) == true) {
						pool.freeConnection(connection);
						HttpSession session = request.getSession(false);
						String username = uc.getUsername();
						int id = uc.getID();
						session.setAttribute("userID", id);
						session.setAttribute("username", username);
						session.setAttribute("fullName", uc.getFullName());
						response.sendRedirect(url);
					} else {
						pool.freeConnection(connection);
						url = "/FinalProject/LoginPage/Login.jsp";
						// if request is not from HttpServletRequest, you should do a typecast before
						HttpSession session = request.getSession(false);
						// save message in session
						session.setAttribute("errorMessage", errorMessage);
						response.sendRedirect(url);
					}

				} else {
					url = "/FinalProject/LoginPage/Login.jsp";
					// if request is not from HttpServletRequest, you should do a typecast before
					HttpSession session = request.getSession(false);
					// save message in session
					session.setAttribute("errorMessage", "There was an error connecting");
					response.sendRedirect(url);
				}
			}

		} else if (request.getParameter("registerAction") != null) {
			url = "/FinalProject/LoginPage/Register.jsp";
			response.sendRedirect(url);
		}
		else {
			url = "/FinalProject/LoginPage/Login.jsp";
			errorMessage = "Something went wrong";
			request.setAttribute("errorMessage", errorMessage);
			// if request is not from HttpServletRequest, you should do a typecast before
			HttpSession session = request.getSession(false);
			// save message in session
			session.setAttribute("errorMessage", errorMessage);

			response.sendRedirect(url);
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

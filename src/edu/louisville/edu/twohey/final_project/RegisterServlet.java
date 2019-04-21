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
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection connection = null;
	private UsersController uc;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = "/FinalProject/LoginPage/Login.jsp";
		String user = request.getParameter("usernameInput");
		String password = request.getParameter("passwordInput");
		String verifiedPassword = request.getParameter("passwordInputVerify");
		String fname = request.getParameter("fnameInput");
		String lname = request.getParameter("lnameInput");

		String errorMessage = "You must fill out all fields to register";
		if ((user == null || user.length() == 0) || (password == null || password.length() == 0) || (fname == null || fname.length() == 0) || (lname == null || lname.length() == 0)) {
			url = "/FinalProject/LoginPage/Register.jsp";
			
			HttpSession session = request.getSession(false);
			session.setAttribute("errorMessage", errorMessage);
			response.sendRedirect(url);
		} else if (!password.equals(verifiedPassword)) {
			url = "/FinalProject/LoginPage/Register.jsp";
			HttpSession session = request.getSession(false);
			session.setAttribute("errorMessage", "Passwords should match!");
			System.out.println(password);
			System.out.println(verifiedPassword);
			response.sendRedirect(url);
		} else {
			ConnectionPool pool = ConnectionPool.getInstance("jdbc/RJTWOH01");
			connection = pool.getConnection();
			if (connection != null) {
				uc = new UsersController(connection);
				if (uc.insertUser(user, password, fname, lname) != 0) {
					url = "/FinalProject/LoginPage/Login.jsp";
					HttpSession session = request.getSession(false);
					session.setAttribute("registerMessage", "User successfully registered");
					pool.freeConnection(connection);
					response.sendRedirect(url);
				} else {
					pool.freeConnection(connection);
					
					url = "/FinalProject/LoginPage/Register.jsp";
					HttpSession session = request.getSession(false);
					session.setAttribute("errorMessage", errorMessage);
					response.sendRedirect(url);
				}

			} else {
				url = "/FinalProject/LoginPage/Register.jsp";
				// if request is not from HttpServletRequest, you should do a typecast before
				HttpSession session = request.getSession(false);
				// save message in session
				session.setAttribute("errorMessage", "There was an error connecting");
				response.sendRedirect(url);
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

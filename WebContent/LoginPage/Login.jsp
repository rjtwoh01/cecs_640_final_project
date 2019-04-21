<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="../Styles/main.css" type="text/css">
</head>
<body>
<%
	String errorMessage ="";
	Object error = session.getAttribute("errorMessage");
	if (error != null) {
		errorMessage = error.toString();
		session.removeAttribute("errorMessage");
	}
	
	String registerMessage ="";
	Object register = session.getAttribute("registerMessage");
	if (register != null) {
		registerMessage = register.toString();
		session.removeAttribute("registerMessage");
	}
		
	//Here we want to clear user information just in case they logged out
	Object userID = session.getAttribute("userID");
	if (userID != null) {
		session.removeAttribute("userID");	
	}
	Object username = session.getAttribute("username");
	if (username != null) {
		session.removeAttribute("username");
	}
	Object fullName = session.getAttribute("fullName");
	if (fullName != null) {
		session.removeAttribute("fullName");
	}
	
%>

<form action="../LoginServlet">
	<div class="login-pannel-container">
		<div class="login-pannel">
			<div class="credentials">
				<div class="username-label">Username</div>
				<div class="username-entry">
					<input type="text" class="username-input" name="usernameInput"/>
				</div>
				<div class="password-label">Password</div>
				<div class="password-entry">
					<input type="password" class="password-input" name="passwordInput"/>
				</div>
				
				<input type="Submit" value="Submit" name="loginAction" class="login-submit"/>
				<input type="Submit" value="Register" name="registerAction" class="login-submit">
				<div class="errorMessage"><%=errorMessage%></div>
				<div class="registerMessage"><%=registerMessage%></div>
			</div>
			
		
		</div>
	</div>
</form>
</body>
</html>
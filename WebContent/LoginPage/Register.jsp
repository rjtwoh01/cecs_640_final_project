<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register</title>
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
%>

<form action="../RegisterServlet">
	<div class="login-pannel-container">
		<div class="register-pannel">
			<div class="credentials">
				<div class="username-label">Username</div>
				<div class="username-entry">
					<input type="text" class="username-input" name="usernameInput"/>
				</div>
				<div class="password-label">Password</div>
				<div class="password-entry">
					<input type="password" class="password-input" name="passwordInput"/>
				</div>
				<div class="password-verfiy-label">Verify Password</div>
				<div class="password-entry">
					<input type="password" class="password-input" name="passwordInputVerify"/>
				</div>
				<div class="username-label">First Name</div>
				<div class="username-entry">
					<input type="text" class="username-input" name="fnameInput"/>
				</div>
				<div class="password-label">Last Name</div>
				<div class="username-entry">
					<input type="text" class="username-input" name="lnameInput"/>
				</div>
				
				<input type="Submit" value="Submit" name="loginAction" class="register-submit"/>
				<div class="errorMessage"><%=errorMessage%></div>
			</div>
			
		
		</div>
	</div>
</form>
</body>
</html>
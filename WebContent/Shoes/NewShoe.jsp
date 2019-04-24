<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>New Shoe</title>
<link rel="stylesheet" href="../Styles/main.css" type="text/css">
</head>
<body>
	<%
		String errorMessage = "";
		Object error = session.getAttribute("errorMessage");
		if (error != null) {
			errorMessage = error.toString();
			session.removeAttribute("errorMessage");
		}
		
		String successMessage = "";
		Object success = session.getAttribute("successMessage");
		if (success != null) {
			successMessage = success.toString();
			session.removeAttribute("successMessage");
		}
	%>

	<form action="../NewShoeServlet">
		<div class="login-pannel-container">
			<div class="new-run-pannel">
				<div class="NewRunTitleWrapper">
					<span class="newRunTitle">Add New Shoe</span>
				</div>
				<div class="newRunInfoWrapper">

					<div class="race-row">
					
						<div class="run-entry">
							<div class="run-label">Shoe Name</div>
							<input type="text" class="run-input" name="shoe" />
						</div>
						
						<div class="run-entry">
							<div class="run-label">Distance Run</div>
							<input type="text" class="run-input" name="distanceRun" />
						</div>
						<div class="run-entry">
							<div class="run-label">Distance Goal</div>
							<input type="text" class="run-input" name="distanceGoal" />
						</div>
						
					</div>
					
					<div class="race-row-last">
						<div class="run-entry">
							<div class="run-label">Retired</div>
							<input type="text" class="run-input" name="retired" />
						</div>
					</div>
						
						

					<input class="new-run-submit"  type="Submit" value="Submit" name="submitNewShoe"/>
					<input class="new-run-return-dashboard"  type="Submit" value="Dashboard" name="returnToDashboard"/>
					<div class="newRunErrorMessage"><%=errorMessage%></div>
					<div class="newRunAddedMessage"><%=successMessage%></div>
				</div>


			</div>
		</div>
	</form>
</body>
</html>
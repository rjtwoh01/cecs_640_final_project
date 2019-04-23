<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Run</title>
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

	<form action="../EditRunServlet">
		<div class="login-pannel-container">
			<div class="new-run-pannel">
				<div class="NewRunTitleWrapper">
					<span class="newRunTitle">Edit Run</span>
				</div>
				<div class="newRunInfoWrapper">

					<div class="row">
						<div class="run-entry">
							<div class="run-label">Distance</div>
							<input type="text" class="run-input" name="distance" value='<%=session.getAttribute("distanceMessage")%>' />
						</div>
						<div class="run-entry">
							<div class="run-label">Run Time</div>
							<input type="text" class="run-input" name="runTime" value='<%=session.getAttribute("runTimeMessage")%>'/>
						</div>
					</div>

					<div class="row">
						<div class="run-entry">
							<div class="run-label">Date Ran</div>
							<input type="text" class="run-input" name="dateRan" value='<%=session.getAttribute("dateRanMessage")%>'/>
						</div>
						<div class="run-entry">
							<div class="run-label">Goal Distance</div>
							<input type="text" class="run-input" name="goalDistance" value='<%=session.getAttribute("goalDistanceMessage")%>'/>
						</div>
					</div>

					<div class="row">
						<div class="run-entry">
							<div class="run-label">Goal Time</div>
							<input type="text" class="run-input" name="goalTime" value='<%=session.getAttribute("goalTimeMessage")%>'/>
						</div>
						<div class="run-entry">
							<div class="run-label">Shoe</div>
							<input type="text" class="run-input" name="shoe" value='<%=session.getAttribute("shoeMessage")%>'/>
						</div>
					</div>

					<input class="new-run-submit"  type="Submit" value="Submit" name="submitNewRun"/>
					<input class="new-run-return-dashboard"  type="Submit" value="Dashboard" name="returnToDashboard"/>
					<div class="newRunErrorMessage"><%=errorMessage%></div>
					<div class="newRunAddedMessage"><%=successMessage%></div>
				</div>


			</div>
		</div>
	</form>
</body>
</html>
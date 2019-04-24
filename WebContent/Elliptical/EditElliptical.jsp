<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Elliptical</title>
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

	<form action="../EditEllipticalServlet">
		<div class="login-pannel-container">
			<div class="new-elliptical-pannel">
				<div class="NewRunTitleWrapper">
					<span class="editRunTitle">Edit Elliptical</span>
				</div>
				<div class="newRunInfoWrapper">

					<div class="race-row">
						<div class="run-entry">
							<div class="run-label">Distance</div>
							<input type="text" class="run-input" name="distance" value='<%=session.getAttribute("distanceMessage")%>' />
						</div>
						<div class="run-entry">
							<div class="run-label">Time</div>
							<input type="text" class="run-input" name="runTime" value='<%=session.getAttribute("runTimeMessage")%>'/>
						</div>
						<div class="run-entry">
							<div class="run-label">Date</div>
							<input type="text" class="run-input" name="dateRan" value='<%=session.getAttribute("dateRanMessage")%>'/>
						</div>
					</div>

					<div class="race-row">
						<div class="run-entry">
							<div class="run-label">Goal Time</div>
							<input type="text" class="run-input" name="goalTime" value='<%=session.getAttribute("goalTimeMessage")%>'/>
						</div>
						<div class="run-entry">
							<div class="run-label">Goal Distance</div>
							<input type="text" class="run-input" name="goalDistance" value='<%=session.getAttribute("goalDistanceMessage")%>'/>
						</div>
						<div class="run-entry">
							<div class="run-label">Intensity</div>
							<input type="text" class="run-input" name="intensity" value='<%=session.getAttribute("intensityMessage")%>'/>
						</div>
					</div>

					<div class="final-race-row">
						
						<div class="elliptical-entry">
							<div class="run-label">Goal Intensity</div>
							<input type="text" class="run-input" name="goalIntensity" value='<%=session.getAttribute("goalIntensityMessage")%>'/>
						</div>
					</div>

					<input class="new-run-submit"  type="Submit" value="Submit" name="submitNewElliptical"/>
					<input class="new-run-return-dashboard"  type="Submit" value="View Elliptical" name="returnToViewElliptical"/>
					<div class="newRunErrorMessage"><%=errorMessage%></div>
					<div class="newRunAddedMessage"><%=successMessage%></div>
				</div>


			</div>
		</div>
	</form>
</body>
</html>
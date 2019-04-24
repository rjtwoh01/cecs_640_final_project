<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
<link rel="stylesheet" href="../Styles/main.css" type="text/css">
</head>
<body>
	<%
		String name = "";
		Object userID = session.getAttribute("userID");
		Object fullName = session.getAttribute("fullName");
		Object username = session.getAttribute("username");
		if (userID != null && username != null && fullName != null) {
			name = fullName.toString();
		}
	%>


	<form action="../DashboardServlet">
		<div class="login-pannel-container">
			<div class="dashboard-pannel">
				<div class="userInfo">
					<%=name%>

					<input type="Submit" value="Log Out" class="dashboard-submit" name="logout"/>
				</div>

				<div class="options-container">
					<div class="view-row">
						
					</div>
					<div class="options-set-1">
						<div class="options-row">
							<input class="newRunPannel" name="newRun" type="Submit" value="New Run" ></input>
							<input class="newRacePannel" name="newRace" type="Submit" value="New Race"></input>
							<input class="newShoesPannel" name="newElliptical" type="Submit" value="New Elliptical"></input>
							<input class="newEllipticalPannel" name="newShoe" type="Submit" value="New Shoes"></input>
						</div>
					</div>
					<div class="options-set-2">
						<div class="options-row">
							<input class="viewRunPannel" name="viewRuns" type="Submit" value="View Run"></input>
							<input class="viewRacePannel" name="viewRaces" type="Submit" value="View Races"></input>
							<input class="viewShoesPannel" name="viewElliptical" type="Submit" value="View Elliptical"></input>
							<input class="viewEllipticalPannel" name="viewShoes" type="Submit" value="View Shoes"></input>
						</div>
					</div>
				</div>

			</div>
		</div>
	</form>

</body>
</html>
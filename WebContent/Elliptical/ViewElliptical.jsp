<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>View Runs</title>
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
		
		String deletedSuccessMessage = "";
		Object deletedSuccess = session.getAttribute("deletedSuccessMessage");
		if (deletedSuccess != null) {
			deletedSuccessMessage = deletedSuccess.toString();
			session.removeAttribute("deletedSuccessMessage");
		}
	%>
	
	<form action="../ViewEllipticalServlet">
		<div class="view-runs-pannel-container">
			<div class="view-elliptical-pannel">
				<div class="newEllipticleTitleWrapper">
					<span class="viewRunsTitle">View Elliptical</span>
				</div>
				<div class="viewEllipticalTableWrapper">${elliptical}</div>
				
				
				<!-- <input class="new-run-submit" type="Submit" value="Submit" name="submitNewRun" />  -->
				<input class="view-elliptical-return-dashboard" type="Submit"
					value="Dashboard" name="returnToDashboard" />
					<div class="viewRunsErrorMessage"><%=errorMessage%></div>
				<div class="viewRunsSuccessMessage"><%=successMessage%></div>
				<div class="viewRunsDeletedSuccessMessage"><%=deletedSuccessMessage%></div>
			</div>
		</div>
	</form>

</body>
</html>
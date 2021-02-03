<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Email Sourcing Result</title>
</head>
<body>
<h3>Welcome "${user.name}",</h3>
<label>You have sourced ${user.sourcedFileCounter} files from ${user.selectedFolder} of ${user.email}</label>
</body>
</html>
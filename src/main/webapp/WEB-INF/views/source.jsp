<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
    <head>
    </head>
    <body>
        <h3>Welcome , Select the folder to source resume from email</h3>
        <form:form method="POST" action="source-folder" modelAttribute="user">
             <table>
                <tr>
                    <td><form:label path="name">Name</form:label></td>
                    <td><form:input path="name"/></td>
                </tr>
                <tr>
                    <td><form:label path="email">Email</form:label></td>
                    <td><form:input path="email"/></td>
                </tr>
                <tr>
                	<td><form:label path="selectedFolder">Select Folder</form:label></td>
                    <td>
	                    <form:select path="selectedFolder">	
	                    	<c:forEach var="folder" items="${user.optionFolders}">
					         	<form:option value="${folder}" label="${folder}"/>
					      	</c:forEach>	                    	
	                    </form:select>
                    </td>                    
                </tr>
                <tr>
                    <td><input type="submit" value="Submit"/></td>
                </tr>
            </table>
        </form:form>
    </body>
</html>
 <%
    if ((session.getAttribute("userid") == null) || (session.getAttribute("userid") == "")) {
    	response.sendRedirect("Login.jsp");
    }
%>
 <%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Upload Example in JSP and Servlet - Java web application</title>
    </head>
 
    <body> 
        <div>
        <div>${message}</div>
            <h3> Choose File to Upload in Server </h3>
            <form action="upload" method="post" enctype="multipart/form-data">
                <input type="file" name="file" /><br><br>
                Key : <input type="text" id="fname" name="fname"><br><br>
                Check to decrypt: <input type="checkbox" name="checkbox"/><br><br>
                <input type="submit" value="upload" /><br>
            </form>
            <a href="asimetric.jsp">Asimetric</a> 
            <br>
            <br>	
            <div id="box">${requestScope["InfoLog"]}</div>   
        </div>
      <a href='logout.jsp'>Log out</a>
    </body>
</html>

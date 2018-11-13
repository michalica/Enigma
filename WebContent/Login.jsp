
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
            <h3> Login yourself</h3>
            <br/>
            <form action="loginHandler" method="post" accept-charset="UTF-8" enctype="text/html">
                user name : <input type="text" id="username" name="username" required="required" /><br><br>
                password: <input type="password" name="password" required="required" /><br><br>
                <input type="submit" value="login" /><br>
            </form>
            <br>
              <div style="color:red;">      ${errorMessage}
        </div>
            <br>	
            <h4>Don't have an account?</h4>
             <a href="Registration.jsp">Click here to Register</a>        
        </div>
      
    </body>
</html>

<%
session.setAttribute("userid", null);
session.invalidate();
response.sendRedirect("Login.jsp");
%>

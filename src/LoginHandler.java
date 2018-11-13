

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginHandler
 */
public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		String user = request.getParameter("username");
		String pwd = request.getParameter("password");
		
		try {
			this.loginUser(user, pwd, request, response);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(user);
	}
	private boolean loginUser(String username, String password, HttpServletRequest request, HttpServletResponse response) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, ServletException {
		Connection con; // The JDBC provided connection object.
		Class.forName("com.mysql.jdbc.Driver").newInstance(); // instantiate the JDBC framework within Java
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/enigmadb?characterEncoding=utf8", "root", "root"); // Ask the framework																				
		System.out.println(password);
		Statement pInsertOid = con.createStatement();

		ResultSet rs = pInsertOid.executeQuery("select * from User where username='" + username + "' and password='" + password + "'");
	
		if (rs.next()) { 
			System.out.println(rs.getInt("id"));
			request.getSession().setAttribute("userid", rs.getInt("id"));
			response.sendRedirect("index.jsp");
			con.close();
			return true;
		} else {
			System.out.println("problem occured");
			request.setAttribute("errorMessage", "Try it again");
			request.getRequestDispatcher("Login.jsp").forward(request, response);
		}
		con.close();
		return false;
	}
}

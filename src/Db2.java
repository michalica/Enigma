

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Db2 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con; // The JDBC provided connection object.
		System.out.println("start");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance(); //instantiate the JDBC framework within Java
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/enigmadb", "root", "root"); // Ask the framework to make a connection and give me a connection reference. ( default TCP port 3306 )
			System.out.println(con);
			if ( con.isClosed() ) {
			return; // No database connection could be established.
			}
		} catch(Exception e) {
			System.out.println(e);
		}
		request.setAttribute("products", "kokot"); // Will be available as ${products} in JSP
        request.getRequestDispatcher("skuska.jsp").forward(request, response);
	}

//	public static void main(String[] args) {
//		System.out.println("establishing");
//			Connection con; // The JDBC provided connection object.
//			try {
//				Class.forName("com.mysql.jdbc.Driver").newInstance(); //instantiate the JDBC framework within Java
//				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/enigmadb”, “root”, “root"); // Ask the framework to make a connection and give me a connection reference. ( default TCP port 3306 )
//				if ( con.isClosed() ) {
//				return; // No database connection could be established.
//				}
//			} catch(Exception e) {
//				System.out.println(e);
//			}
//			return;
//		}
}
package deleteTable;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Servlet implementation class deleteMember
 */
public class deleteMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteMember() {
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
		doGet(request, response);
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("01");
		int idmember=Integer.parseInt(req.getParameter("idmember"));
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String jdbcUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=thuvien;username=sa;password=123";
			//
			
			    Connection connection = DriverManager.getConnection(jdbcUrl);
				System.out.println("01");

				Statement statement = connection.createStatement();
				statement.executeUpdate("DELETE FROM memberThuthu WHERE id="+idmember);
				System.out.println("01");
				resp.getWriter().print("OK");

	}
		catch(Exception e) {
			}
		}
		
	}


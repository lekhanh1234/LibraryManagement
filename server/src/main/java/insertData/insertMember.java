package insertData;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class insertMember
 */
public class insertMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public insertMember() {
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

		String dinhdanhmember = request.getParameter("dinhdanhmember");

		String tenmember = request.getParameter("tenmember");

		int idthuthu = Integer.parseInt(request.getParameter("idthuthu"));
		
		String jdbcUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=thuvien;username=sa;password=123";
		PreparedStatement preparedStatement;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(jdbcUrl);
			preparedStatement = connection
					.prepareStatement("INSERT INTO memberThuthu (madinhdanh,namemember,idthuthu) VALUES (?,?,?)");
			preparedStatement.setString(1,dinhdanhmember);
			preparedStatement.setString(2,tenmember);
			preparedStatement.setInt(3, idthuthu);
			preparedStatement.executeUpdate();
			

			
			/// thuc hien tiep truy van lay ra id
			Statement statement = connection.createStatement();
			ResultSet resuft2= statement.executeQuery("select * from memberThuthu where idthuthu="+idthuthu+" and madinhdanh='"+dinhdanhmember+"'");
			if(resuft2.next())
			{
				response.setStatus(200);
				response.getWriter().println(""+resuft2.getInt("id"));
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

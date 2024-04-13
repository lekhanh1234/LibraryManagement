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
 * Servlet implementation class insertBook
 */
public class insertBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public insertBook() {
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
		System.out.println("9999999:");

		String masach = request.getParameter("masach");
		System.out.println("9999999:");

		String tensach = request.getParameter("tensach");
		System.out.println("9999999:");

		int giathue = Integer.parseInt(request.getParameter("giathue"));
		
		System.out.println("9999999:");
		
		int idLoaiSach = Integer.parseInt(request.getParameter("idloaisach"));

		int idThuthu = Integer.parseInt(request.getParameter("idthuthu"));

		
		String jdbcUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=thuvien;username=sa;password=123";
		PreparedStatement preparedStatement;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(jdbcUrl);
			preparedStatement = connection
					.prepareStatement("INSERT INTO book (masach,namesach,giathue,idLoaisach,idthuthu) VALUES (?,?,?,?,?)");
			preparedStatement.setString(1,masach);
			preparedStatement.setString(2,tensach);
			preparedStatement.setInt(3, giathue);
			preparedStatement.setInt(4, idLoaiSach);
			preparedStatement.setInt(5, idThuthu);

			preparedStatement.executeUpdate();
			
			System.out.println("888888889");

			
			/// thuc hien tiep truy van lay ra id
			Statement statement = connection.createStatement();
			ResultSet resuft2= statement.executeQuery("select * from book where idthuthu="+idThuthu+" and masach='"+masach+"'");
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

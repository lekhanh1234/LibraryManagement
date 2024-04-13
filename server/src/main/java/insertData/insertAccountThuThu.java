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
 * Servlet implementation class insertAccountThuThu
 */
public class insertAccountThuThu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public insertAccountThuThu() {
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
		String name = request.getParameter("name");

		String username = request.getParameter("username");

		String password = request.getParameter("password");
		
		String dinhdanh = request.getParameter("dinhdanh");

		
		String jdbcUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=thuvien;username=sa;password=123";
		PreparedStatement preparedStatement;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(jdbcUrl);
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("select * from accountThuthu where dinhdanh='"+dinhdanh+"'");
			if(resultSet.next()) {
				response.setStatus(403);
				return;
			}
			preparedStatement = connection
					.prepareStatement("INSERT INTO accountThuthu (nameUser,matkhau,nameThuThu,dinhdanh) VALUES (?,?, ?, ?)");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3,name);
			preparedStatement.setString(4, dinhdanh);
			preparedStatement.executeUpdate();
			ResultSet resuft= statement.executeQuery("select * from accountThuthu where dinhdanh='"+dinhdanh+"'");
			resuft.next();
			response.setStatus(200);
			response.getWriter().println(resuft.getInt(1));
			/// thuc hien tiep truy van lay ra id
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

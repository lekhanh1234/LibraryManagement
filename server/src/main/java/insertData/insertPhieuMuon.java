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
 * Servlet implementation class insertPhieuMuon
 */
public class insertPhieuMuon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public insertPhieuMuon() {
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
		System.out.println("777:");

		String maphieu = request.getParameter("maphieu");
		System.out.println("777:");

		int idthuthu=  Integer.parseInt(request.getParameter("idthuthu"));
		System.out.println("777:");

		int idbook = Integer.parseInt(request.getParameter("idbook"));
		int idmember = Integer.parseInt(request.getParameter("idmember"));
		System.out.println("777:");

		int tinhtrang = Integer.parseInt(request.getParameter("tinhtrang"));
		String ngaythue = request.getParameter("ngaythue");
		System.out.println("777:");

		String thoihan = request.getParameter("thoihan");



		
		String jdbcUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=thuvien;username=sa;password=123";
		PreparedStatement preparedStatement;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection connection = DriverManager.getConnection(jdbcUrl);
			preparedStatement = connection
					.prepareStatement("INSERT INTO phieumuonmember (maphieu,idthuthu,idBook,id_member,tinhtrang,ngaythue,thoihan) VALUES (?,?,?,?,?,?,?)");
			preparedStatement.setString(1,maphieu);
			preparedStatement.setInt(2,idthuthu);
			preparedStatement.setInt(3, idbook);
			preparedStatement.setInt(4, idmember);
			preparedStatement.setInt(5,tinhtrang);
			preparedStatement.setString(6,ngaythue);
			preparedStatement.setString(7,thoihan);
			preparedStatement.executeUpdate();
			
			/// thuc hien tiep truy van lay ra id
			Statement statement = connection.createStatement();
			ResultSet resuft2= statement.executeQuery("select * from phieumuonmember where idthuthu="+idthuthu+" and maphieu='"+maphieu+"'");
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

package UpdateTable;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class updateTrangThaiPhieu
 */
public class updateTrangThaiPhieu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateTrangThaiPhieu() {
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
		int idThuThu=Integer.parseInt(request.getParameter("idthuthu"));
		String maphieu=request.getParameter("maphieu");
		int trangthai=Integer.parseInt(request.getParameter("trangthai"));
		System.out.println("tham so tu client :"+idThuThu+":"+maphieu+":"+trangthai);
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String urlSqlsever="jdbc:sqlserver://127.0.0.1:1433;databaseName=thuvien;username=sa;password=123";
			Connection connect=DriverManager.getConnection(urlSqlsever);
			PreparedStatement preparedStatement =connect.prepareStatement("update phieumuonmember set tinhtrang=? where idthuthu=? and maphieu= ?");
			preparedStatement.setInt(1,trangthai);
			preparedStatement.setInt(2,idThuThu);
			preparedStatement.setString(3,maphieu);

			preparedStatement.execute();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

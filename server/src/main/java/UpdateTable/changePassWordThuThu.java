package UpdateTable;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.beans.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Servlet implementation class changePassWordThuThu
 */
public class changePassWordThuThu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public changePassWordThuThu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String newPassWord=request.getParameter("newpassword");
		String dinhdanhthuthu=request.getParameter("dinhdanh");
		try {
			
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String urlSqlsever="jdbc:sqlserver://127.0.0.1:1433;databaseName=thuvien;username=sa;password=123";
			Connection connect=DriverManager.getConnection(urlSqlsever);
			PreparedStatement preparedStatement =connect.prepareStatement("update accountThuthu set matkhau=? where dinhdanh=?");
			preparedStatement.setString(1,newPassWord);
			preparedStatement.setString(2,dinhdanhthuthu);
			preparedStatement.execute();

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

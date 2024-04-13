package processLogin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class checkloginAdmin
 */
public class checkloginAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkloginAdmin() {
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
		System.out.println("ket noi den sv");

		String userName= request.getParameter("username");
		String password= request.getParameter("password");
		//System.out.println("giatri username va password tu client de login :"+userName+":"+password);

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String jdbcUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=thuvien;username=sa;password=123";
			//
			   Connection connection = DriverManager.getConnection(jdbcUrl);

				Statement statement = connection.createStatement();
				System.out.println("ket noi den database de kiem tra account login thanh cong");
				// thuc hien cau lenh select truy van kiem tra username và password

				String querySelect="select * from accountAdmin where nameUser ='"+userName+"' and passwordAdmin='"+password+"'";
				ResultSet resuft= statement.executeQuery(querySelect);
					while(resuft.next())
					{
						String usednameResult=resuft.getString("nameUser");
						String passwordResulf=resuft.getString("passwordAdmin");
						System.out.println("username va password dung login:"+usednameResult+":"+passwordResulf);
						response.setStatus(200);
						new getAllDataTableThuThu().getData(request,response, statement, usednameResult, passwordResulf);
						return;
					}
					System.out.println("0123:");
					response.setStatus(404);
			}

		 catch (Exception e) {
		}
	}

}

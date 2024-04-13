package processLogin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet implementation class checkloginthuthu
 */
public class checkloginthuthu extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static int amount=0;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public checkloginthuthu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	

		String userName=    request.getParameter("usedname");
		String password=    request.getParameter("password");
		System.out.println("giatri username va password tu client de login :"+userName+":"+password);

        // Kiểm tra nếu không có giá trị trong header X-Forwarded-For
    

        // In địa chỉ IP của client ra console (hoặc làm gì đó khác với nó)

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			String jdbcUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=thuvien;username=sa;password=123";
			//
			   Connection connection = DriverManager.getConnection(jdbcUrl);

				Statement statement = connection.createStatement();
				System.out.println("ket noi den database de kiem tra account login thanh cong");
				String querySelect="select * from accountThuthu where nameUser ='"+userName+"' and matkhau='"+password+"'";
				
				String usednameResult="";
				String passwordResulf="";
				InputStream a=request.getInputStream();
				try 
				{
					ResultSet resuft= statement.executeQuery(querySelect);
					if(resuft.next())
					{
						usednameResult=resuft.getString("nameUser");
						passwordResulf=resuft.getString("matkhau");
						System.out.println("username va password dung login:"+usednameResult+":"+passwordResulf);
						response.setStatus(200);
						new getAllDataDataBase().getAllData(request,response, statement, usednameResult, passwordResulf);
						return;
					}
					response.setStatus(404);
				}
				catch(Exception e){}
				
			}

		 catch (Exception e) {
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

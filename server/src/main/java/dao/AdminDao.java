package dao;

import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminDao {
	public void checkLogin(String userName, String password, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Connection connection = new OpenConnectDatabase().openConnectDatabase();
			Statement statement = connection.createStatement();
			String querySelect="select * from Admin where nameUser ='"+userName+"' and password='"+password+"'";
			ResultSet result = statement.executeQuery(querySelect);
			if (result.next()) {
				response.setStatus(200);
				getData(request, response, statement, userName, password);
				return;
			}
			response.setStatus(404);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void getData(HttpServletRequest request, HttpServletResponse response, Statement statement,
			String username, String password) {
    	try {
			String separatorKeyword = "abcxyz987";
			ServletOutputStream out = response.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			    {  
			    ResultSet result = statement.executeQuery("select * from Admin where nameUser ='" + username + "' and password ='" + password + "'");
			    result.next();
				int idAdmin = result.getInt("id");
				String usednameResult = result.getString("nameUser");
				String passwordResult = result.getString("password");
				String name = result.getString("name");
				String dinhdanh = result.getString("dinhdanh");
				String newIdAdmin =idAdmin+ separatorKeyword;
				String new_usedname = usednameResult + separatorKeyword;
				String new_password = passwordResult + separatorKeyword;
				String new_nameAdmin = name + separatorKeyword;
				String result_queryTableAdmin = newIdAdmin + new_usedname + new_password + new_nameAdmin + dinhdanh;
				byte[] response_tableAdmin = result_queryTableAdmin.getBytes(StandardCharsets.UTF_8);
				dataOutputStream.writeInt(response_tableAdmin.length); // gui ve so luong byte cua phan hoi chua thong									// tin bang thu thu
				out.write(response_tableAdmin);
			    }
					
			ResultSet resuft2 = statement.executeQuery("select * from  Librarian");
			while (resuft2.next()) {
				String id = resuft2.getInt("id") + separatorKeyword;
				String nameUser = resuft2.getString("nameUser") + separatorKeyword;
				String passwordAd = resuft2.getString("password") + separatorKeyword;
				String name = resuft2.getString("name") + separatorKeyword;
				String dinhdanh = resuft2.getString("dinhdanh");
				String resuft_queryLibrarianTable = id + nameUser + passwordAd + name + dinhdanh;
				byte[] response_librarianTable = resuft_queryLibrarianTable.getBytes(StandardCharsets.UTF_8);
				dataOutputStream.writeInt(response_librarianTable.length);
				out.write(response_librarianTable);
			}
			dataOutputStream.writeInt(0);
			out.close();
		} catch (Exception e) {
		}
    }


}

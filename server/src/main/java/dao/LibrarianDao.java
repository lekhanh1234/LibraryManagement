package dao;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LibrarianDao {
	public void checkLogin(String userName, String password, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Connection connection = new OpenConnectDatabase().openConnectDatabase();
			Statement statement = connection.createStatement();
			String querySelect = "select * from Librarian where userName ='" + userName + "' and password='" + password
					+ "'";
			ResultSet result = statement.executeQuery(querySelect);
			if (result.next()) {
				response.setStatus(200);
				getAllLoginData(request, response, statement, userName, password);
				return;
			}
			response.setStatus(404);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public int addLibrarian(String userName, String password, String name, String dinhdanh) {
    	try {
			Connection connection = new OpenConnectDatabase().openConnectDatabase();
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("select * from librarian where dinhdanh='"+dinhdanh+"'");
			if(resultSet.next()) {
				return -1;// add fail
			}
			PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO Librarian (userName,password,name,dinhdanh) VALUES (?,?, ?, ?)");
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3,name);
			preparedStatement.setString(4, dinhdanh);
			preparedStatement.executeUpdate();
			ResultSet result= statement.executeQuery("select * from librarian where dinhdanh='"+dinhdanh+"'");
			result.next();		
			return result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return -1;
    }
    
    public void updatePassword(String newPassword, String dinhdanh) {
    	Connection connect = new OpenConnectDatabase().openConnectDatabase();
    	PreparedStatement preparedStatement;
		try {
			preparedStatement = connect.prepareStatement("update Librarian set password=? where dinhdanh=?");
			preparedStatement.setString(1,newPassword);
			preparedStatement.setString(2,dinhdanh);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
	public void getAllLoginData(HttpServletRequest request, HttpServletResponse response, Statement statement,
			String username, String password) {
		try {
			String separatorKeyword = "abcxyz987";
			ServletOutputStream out = response.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			ResultSet result = statement.executeQuery(
					"select * from Librarian where userName ='" + username + "' and password='" + password + "'");
			int idLibrarian = 0;
			result.next();
			idLibrarian = result.getInt("id");
			String usednameResult = result.getString("userName");
			String passwordResult = result.getString("password");
			String name = result.getString("name");
			String dinhdanh = result.getString("dinhdanh");
			String id_ST = idLibrarian + separatorKeyword;
			String new_usedname = usednameResult + separatorKeyword;
			String new_password = passwordResult + separatorKeyword;
			String new_name = name + separatorKeyword;
			String new_dinhdanh = dinhdanh;
			String result_queryLibrarianTable = id_ST + new_usedname + new_password + new_name + new_dinhdanh;
			byte[] responseLibrarianTable = result_queryLibrarianTable.getBytes(StandardCharsets.UTF_8);
			dataOutputStream.writeInt(responseLibrarianTable.length); // gui ve so luong byte cua phan hoi chua thong //
																	// tin bang thu thu
			out.write(responseLibrarianTable); // dư lieu chua duoc gui ve client vi neu dung thead.sleep. client se
												// khong the nhan duoc ban tin

			ResultSet result2 = statement.executeQuery("select * from  category where idLibrarian =" + idLibrarian);
			while (result2.next()) {
				String id = result2.getInt("id") + separatorKeyword;
				String categoryCode = result2.getString("categoryCode") + separatorKeyword;
				String categoryName = result2.getString("categoryName") + separatorKeyword;
				String result_queryCategoryTable = id + categoryCode + categoryName + idLibrarian;
				byte[] response_CategoryTable = result_queryCategoryTable.getBytes(StandardCharsets.UTF_8);
				dataOutputStream.writeInt(response_CategoryTable.length);
				out.write(response_CategoryTable);
			}
			dataOutputStream.writeInt(0);

			ResultSet result3 = statement.executeQuery("select * from  book where idLibrarian =" + idLibrarian);
			while (result3.next()) {
				String id = result3.getInt("id") + separatorKeyword;
				String bookCode = result3.getString("bookCode") + separatorKeyword;
				String bookName = result3.getString("bookName") + separatorKeyword;
				String price = result3.getInt("price") + separatorKeyword;
				String idCategory = result3.getInt("idCategory") + separatorKeyword;
				String result_queryBookTable = id + bookCode + bookName + price + idCategory + idLibrarian;
				byte[] response_BookTable = result_queryBookTable.getBytes(StandardCharsets.UTF_8);
				dataOutputStream.writeInt(response_BookTable.length);
				out.write(response_BookTable);
			}
			dataOutputStream.writeInt(0);

			ResultSet result4 = statement.executeQuery("select * from  Member where idLibrarian =" + idLibrarian);
			while (result4.next()) {
				String id = result4.getInt("id") + separatorKeyword;
				String dinhdanhMember = result4.getString("dinhdanh") + separatorKeyword;
				String memberName = result4.getString("name") + separatorKeyword;
				String result_queryTableMember = id + dinhdanhMember + name + idLibrarian;
				byte[] response_tableMember = result_queryTableMember.getBytes(StandardCharsets.UTF_8);
				dataOutputStream.writeInt(response_tableMember.length);
				out.write(response_tableMember);
			}
			dataOutputStream.writeInt(0);

			ResultSet result5 = statement.executeQuery("select * from  loanSlip where idLibrarian =" + idLibrarian);
			while (result5.next()) {
				String id = result5.getInt("id") + separatorKeyword;
				String receiptNumber = result5.getString("receiptNumber") + separatorKeyword;
				String idBook = result5.getInt("idBook") + separatorKeyword;
				String idMember = result5.getInt("idMember") + separatorKeyword;
				String states = result5.getInt("states") + separatorKeyword;
				String rentalDate = result5.getString("idMember") + separatorKeyword;
				String deadline = result5.getString("deadline");
				String result_queryLoanSlipTable = id + receiptNumber + (idLibrarian + separatorKeyword) + idBook
						+ idMember + states + rentalDate + deadline;
				byte[] response_loanSlipTable = result_queryLoanSlipTable.getBytes(StandardCharsets.UTF_8);
				dataOutputStream.writeInt(response_loanSlipTable.length);
				out.write(response_loanSlipTable);
			}
			dataOutputStream.writeInt(0);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

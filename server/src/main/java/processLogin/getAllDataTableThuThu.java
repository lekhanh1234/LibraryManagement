package processLogin;

import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class getAllDataTableThuThu {
    public void getData(HttpServletRequest request, HttpServletResponse response, Statement statement,
			String username, String password) {
    	try {
			String maphancach = "abcxyz987";
			ServletOutputStream out = response.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			    {  
			    ResultSet resuft = statement.executeQuery("select * from accountAdmin where nameUser ='" + username + "' and passwordAdmin='" + password + "'");
			    resuft.next();
				int idAdmin = resuft.getInt("id");
				String usednameResult = resuft.getString("nameUser");
				String passwordResulf = resuft.getString("passwordAdmin");
				String nameAdmin = resuft.getString("nameAdmin");
				String dinhdanh = resuft.getString("dinhdanh");

				String newIdAdmin =idAdmin+ maphancach;
				String new_usedname = usednameResult + maphancach;
				String new_password = passwordResulf + maphancach;
				String new_nameAdmin = nameAdmin + maphancach;
				String new_dinhdanh = dinhdanh;
				String resuft_queryTableAdmin = newIdAdmin + new_usedname + new_password + new_nameAdmin + new_dinhdanh;
				byte[] response_tableAdmin = resuft_queryTableAdmin.getBytes(StandardCharsets.UTF_8);
				dataOutputStream.writeInt(response_tableAdmin.length); // gui ve so luong byte cua phan hoi chua thong									// tin bang thu thu
				out.write(response_tableAdmin);
			    }
					
			ResultSet resuft2 = statement.executeQuery("select * from  accountThuthu");
			while (resuft2.next()) {
				String id = resuft2.getInt("id") + maphancach;
				String nameUser = resuft2.getString("nameUser") + maphancach;
				String passWord = resuft2.getString("matkhau") + maphancach;
				String nameThuthu = resuft2.getString("nameThuThu") + maphancach;
				String dinhdanh = resuft2.getString("dinhdanh");

				String resuft_queryTableThuThu = id + nameUser + passWord + nameThuthu+dinhdanh;

				System.out.println("value:" + resuft_queryTableThuThu);
				byte[] response_tableThuThu = resuft_queryTableThuThu.getBytes(StandardCharsets.UTF_8);
			//	System.out.println("so byte khi chuyen thanh mang byte:" + response_tableLoaisach.length);
			//	System.out.println("chuyen nguoc ve string :" + new String(response_tableLoaisach, StandardCharsets.UTF_8));
				dataOutputStream.writeInt(response_tableThuThu.length);
				out.write(response_tableThuThu);
			}
			dataOutputStream.writeInt(0);
			out.close();
		} catch (Exception e) {
		}
    }
}

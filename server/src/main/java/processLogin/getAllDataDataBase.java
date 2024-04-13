package processLogin;

import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class getAllDataDataBase {
	public void getAllData(HttpServletRequest request, HttpServletResponse response, Statement statement,
			String username, String password) {

		try {
			String maphancach = "abcxyz987";
			ServletOutputStream out = response.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(out);
			ResultSet resuft = statement.executeQuery("select * from accountThuthu where nameUser ='" + username + "' and matkhau='" + password + "'");
			int idthuthu = 0;	
			    resuft.next();
				idthuthu = resuft.getInt("id");
				String usednameResult = resuft.getString("nameUser");
				String passwordResulf = resuft.getString("matkhau");
				String nameThuThu = resuft.getString("nameThuThu");
				String dinhdanh = resuft.getString("dinhdanh");
				String id_ST = idthuthu + maphancach;
				String new_usedname = usednameResult + maphancach;
				String new_password = passwordResulf + maphancach;
				String new_nameThuThu = nameThuThu + maphancach;
				String new_dinhdanh = dinhdanh;
				String resuft_queryTableThuthu = id_ST + new_usedname + new_password + new_nameThuThu + new_dinhdanh;

				byte[] response_tableThuthu = resuft_queryTableThuthu.getBytes(StandardCharsets.UTF_8);
				dataOutputStream.writeInt(response_tableThuthu.length); // gui ve so luong byte cua phan hoi chua thong									// tin bang thu thu
				out.write(response_tableThuthu);  // dư lieu chua duoc gui ve client vi neu dung thead.sleep. client se khong the nhan duoc ban tin
			
			
			
			ResultSet resuft2 = statement.executeQuery("select * from  loaisach where idthuthu =" + idthuthu);
			while (resuft2.next()) {
				String id = resuft2.getInt("id") + maphancach;
				String maloaisach = resuft2.getString("maloaisach") + maphancach;
				String tenloaisach = resuft2.getString("tenloaisach") + maphancach;
				String idthamchieuthuthu =idthuthu+"";
				String resuft_queryTableLoaisach = id + maloaisach + tenloaisach + idthamchieuthuthu;

				System.out.println("value:" + resuft_queryTableLoaisach);
				byte[] response_tableLoaisach = resuft_queryTableLoaisach.getBytes(StandardCharsets.UTF_8);
			//	System.out.println("so byte khi chuyen thanh mang byte:" + response_tableLoaisach.length);
			//	System.out.println("chuyen nguoc ve string :" + new String(response_tableLoaisach, StandardCharsets.UTF_8));
				dataOutputStream.writeInt(response_tableLoaisach.length);
				out.write(response_tableLoaisach);
			}
			dataOutputStream.writeInt(0);
			
			
			ResultSet resuft3 = statement.executeQuery("select * from  book where idthuthu =" + idthuthu);
			while (resuft3.next()) {
				String id = resuft3.getInt("id") + maphancach;
				String masach = resuft3.getString("masach") + maphancach;
				String namesach = resuft3.getString("namesach") + maphancach;
			    String giathue = resuft3.getInt("giathue")+maphancach;
			    String idLoaisach=resuft3.getInt("idLoaisach")+maphancach;
				String idthamchieuthuthu=idthuthu+"";
				String resuft_queryTableSach = id + masach+ namesach +giathue+idLoaisach+ idthamchieuthuthu;
				byte[] response_tableSach = resuft_queryTableSach.getBytes(StandardCharsets.UTF_8);
				dataOutputStream.writeInt(response_tableSach.length);
				out.write(response_tableSach);
			}
			dataOutputStream.writeInt(0);
			
			
			ResultSet resuft4 = statement.executeQuery("select * from  memberThuthu where idthuthu =" + idthuthu);
			while (resuft4.next()) {
				String id = resuft4.getInt("id")+ maphancach;
				String madinhdanh = resuft4.getString("madinhdanh") + maphancach;
				String namemember = resuft4.getString("namemember") + maphancach;
			    String idthamchieuthuthu =idthuthu+"";
				String resuft_queryTableMember = id + madinhdanh+ namemember + idthamchieuthuthu;
			//	System.out.println("table member gui ve client:"+resuft_queryTableMember);

				byte[] response_tableMember =resuft_queryTableMember.getBytes(StandardCharsets.UTF_8);
				dataOutputStream.writeInt(response_tableMember.length);
				out.write(response_tableMember);
			}
			dataOutputStream.writeInt(0);
			
			
			ResultSet resuft5 = statement.executeQuery("select * from  phieumuonmember where idthuthu =" + idthuthu);
			while (resuft5.next()) {
				String id = resuft5.getInt("id")+ maphancach;
				String maphieu = resuft5.getString("maphieu") + maphancach;
			    String idthamchieuthuthu =idthuthu + maphancach;
				String idBook = resuft5.getInt("idBook") + maphancach;
				String id_member = resuft5.getInt("id_member")+maphancach;
				String tinhtrang = resuft5.getInt("tinhtrang")+maphancach;
				String ngaythue = resuft5.getString("ngaythue") + maphancach;
				String thoihan = resuft5.getString("thoihan");
				String resuft_queryTablePhieumuon = id + maphieu+ idthamchieuthuthu +idBook+id_member+tinhtrang+ngaythue+thoihan;

			//	System.out.println("table phieu muon gui ve client:"+resuft_queryTablePhieumuon);

				byte[] response_tablePhieumuon =resuft_queryTablePhieumuon.getBytes(StandardCharsets.UTF_8);
				dataOutputStream.writeInt(response_tablePhieumuon.length);
				out.write(response_tablePhieumuon);
			}
			dataOutputStream.writeInt(0);
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

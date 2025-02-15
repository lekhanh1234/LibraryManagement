package com.example.asm_android2.ServerService;

import java.io.OutputStream;
import java.net.HttpURLConnection;

public class ChangeLibrarianInfo {
    public void changePassWord(String password,String dinhdanh){
        try {
            HttpURLConnection connection = PrepareMethod.createMethodConnection("http://192.168.43.189:8080/sever_messenger/changePassWordAccountThuThu","Post");
            OutputStream outputStream=connection.getOutputStream();
            outputStream.write(("newpassword="+password+"&dinhdanh="+dinhdanh).getBytes());
            outputStream.flush();
            connection.getResponseCode();
        } catch (Exception e){}
    }
}

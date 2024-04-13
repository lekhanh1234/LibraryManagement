package com.example.asm_android2.OperationSever;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class changeInfoThuthu {
    public void changePassWord(String password,String dinhdanh){
        try {
            URL url=new URL("http://192.168.43.189:8080/sever_messenger/changePassWordAccountThuThu");
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent","Mozzila/5.0");
            connection.setDoOutput(true);
            connection.getOutputStream(); // thiet lap ket noi sever tai day
            OutputStream outputStream=connection.getOutputStream();
            outputStream.write(("newpassword="+password+"&dinhdanh="+dinhdanh).getBytes());
            outputStream.flush();
            connection.getResponseCode();
        } catch (Exception e){}

    }
}

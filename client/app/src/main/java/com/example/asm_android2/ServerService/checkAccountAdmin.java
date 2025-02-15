package com.example.asm_android2.ServerService;

import java.io.OutputStream;
import java.net.HttpURLConnection;

public class CheckAccountAdmin {
    public HttpURLConnection PrepareMessage(String userName,String passWord){
        try{
            HttpURLConnection httpURLConnection = PrepareMethod.createMethodConnection("http://192.168.43.189:8080/sever_messenger/checkloginAdmin","Post");
            OutputStream os = httpURLConnection.getOutputStream();
            String PARAMS = "username=" +userName+"&" + "password=" +passWord;
            os.write(PARAMS.getBytes());
            os.flush();
            return httpURLConnection;
        }
        catch (Exception e){
            return null;
        }
    }
}

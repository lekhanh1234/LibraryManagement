package com.example.asm_android2.OperationSever;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class checkAccountAdmin {
    public HttpURLConnection PrepareMessage(String userName,String passWord){
        try{
            String Post_Url = "http://192.168.43.189:8080/sever_messenger/checkloginAdmin";
            URL urlSever_Post = null;
            urlSever_Post = new URL(Post_Url);
            String PARAMS = "username=" +userName+"&" + "password=" +passWord;
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(PARAMS.getBytes());
            os.flush();
            return httpURLConnection;
        }
        catch (Exception e){
            return null;
        }
    }
}

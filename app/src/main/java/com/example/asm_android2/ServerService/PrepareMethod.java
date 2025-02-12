package com.example.asm_android2.ServerService;

import java.net.HttpURLConnection;
import java.net.URL;

public class PrepareMethod {
    public static HttpURLConnection createMethodConnection(String url,String method){
        try {
            URL urlConnect=new URL(url);
            HttpURLConnection connection=(HttpURLConnection) urlConnect.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("User-Agent","Mozzila/5.0");
            connection.setDoOutput(true);
            return connection;
        } catch (Exception e){}
        return null;
    }
}

package com.example.asm_android2.OperationSever;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class prepareMessengerPost {
    public static HttpURLConnection prepareMessenger(String url){
        try {
            URL urlConnect=new URL(url);
            HttpURLConnection connection=(HttpURLConnection) urlConnect.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent","Mozzila/5.0");
            connection.setDoOutput(true);
            return connection;
        } catch (Exception e){}
        return null;
    }
}

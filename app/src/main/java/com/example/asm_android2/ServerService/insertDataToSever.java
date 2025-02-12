package com.example.asm_android2.ServerService;

import com.example.asm_android2.modal.Admin;
import com.example.asm_android2.account.AccountThuthu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class insertDataToSever {
    public boolean insertAccountThuThuToSever(String name,String username,String password,String dinhdanh){
        int idThuThuFromSever[]=new int[1];
        Thread a=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String Post_Url = "http://192.168.43.189:8080/sever_messenger/insertAccountThuThu";
                    URL urlSever_Post = null;
                    urlSever_Post = new URL(Post_Url);
                    String PARAMS = "name=" +name+"&username=" +username+"&password="+password+"&dinhdanh="+dinhdanh;
                    HttpURLConnection httpURLConnection = (HttpURLConnection) urlSever_Post.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    os.write(PARAMS.getBytes());
                    os.flush();
                    int codeResponse= httpURLConnection.getResponseCode();
                    if(codeResponse==403) {
                        idThuThuFromSever[0] = -1;
                        return;
                    }
                    BufferedReader bf=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    idThuThuFromSever[0]=Integer.parseInt(bf.readLine());
                }
                catch (Exception e){
                }
            }
        });
        a.start();
        try{
            a.join();
        } catch (Exception e){}
        if(idThuThuFromSever[0]==-1) return false;
        Admin.getListAccountThuThu().add(new AccountThuthu(idThuThuFromSever[0],username,password,name,dinhdanh));
        return true;
    }

}

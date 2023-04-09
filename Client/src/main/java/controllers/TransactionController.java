package controllers;

import models.Id;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionController {
    private String rootURL = "http://zipcode.rocks:8085";
    private MessageController msgCtrl;
    private IdController idCtrl;

    public TransactionController(MessageController m, IdController j) {
        msgCtrl = m;
        idCtrl = j;
    }

    public List<Id> getIds() {
        return null;
    }

//    public String postId(String idtoRegister, String githubName) {
////        Id tid = new Id(idtoRegister, githubName);
//        tid = idCtrl.postId(tid);
//        return ("Id registered.");
//    }

    public void testIDC(){
        System.out.println(idCtrl.toString());
    }


    public String process(HttpURLConnection con) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder s = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            s.append(inputLine).append("\n");
        }
        in.close();
        return s.toString();
    }

    public boolean isRegistered(String id) {
        return idCtrl.isRegistered(id);
    }


    public String get(String area) throws IOException {
        URL url = new URL("http://zipcode.rocks:8085" + area);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        return process(con);
    }

    public String register(String name, String id) throws IOException{
        URL url = new URL("http://zipcode.rocks:8085/ids");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        if(isRegistered(id)){
            con.setRequestMethod("PUT");
        } else {
            con.setRequestMethod("POST");
        }
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) { //the blank timestamp was messing me up, but it worked if i just gave it a bad one :shrug emoji:
            String jsonInputString = "{\"userid\": \"-\", \"name\": \"" + name+ "\", \"github\": \"" + id + "\"}";
            System.out.println(jsonInputString);
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        return process(con);
    }

    public String send_all(String area, String message) throws IOException {
        URL url = new URL("http://zipcode.rocks:8085" + area);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");


        String id = area.split("/")[2];
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) { //the blank timestamp was messing me up, but it worked if i just gave it a bad one :shrug emoji:
            String jsonInputString = "{\"sequence\": \"-\", \"timestamp\": \"2023-04-08T18:29:05.604264239Z\", \"fromid\": \"" + id + "\", \"toid\": \"\", \"message\": \"" + message + "\"}";
            System.out.println(jsonInputString);
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        return process(con);
    }

    public String send_to(String area, String message, String toId) throws IOException {
        URL url = new URL("http://zipcode.rocks:8085" + area);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");


        String id = area.split("/")[2];
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) { //the blank timestamp was messing me up, but it worked if i just gave it a bad one :shrug emoji:
            String jsonInputString = "{\"sequence\": \"-\", \"timestamp\": \"2023-04-08T18:29:05.604264239Z\", \"fromid\": \"" + id + "\", \"toid\": \"" + toId + "\", \"message\": \"" + message + "\"}";
            System.out.println(jsonInputString);
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        return process(con);
    }
}

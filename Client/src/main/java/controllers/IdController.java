package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import models.Id;
import org.json.JSONArray;
import org.json.JSONObject;

public class IdController {
    private HashMap<String, Id> allIds;

    Id myId;

    public ArrayList<Id> getIds() {
        return new ArrayList<>(allIds.values());
    }

    public void setMyId(Id id){
        myId = id;
    }

    public Id postId(Id id) {
        // create json from id
        // call server, get json result Or error
        // result json to Id obj"

        return null;
    }

    public Id putId(Id id) {
        return null;
    }

    public boolean isRegistered(String id){
        return allIds.containsKey(id);
    }

    public IdController() throws IOException {
        // Get ids from server
        URL url = new URL("http://zipcode.rocks:8085/ids");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        String result = process(con);

        // Parse into JSON
        JSONArray jsonArray = new JSONArray(result);

        // Convert from JSON to map
      allIds = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String githubId = jsonObject.getString("github");
            String uid= jsonObject.getString("userid");
            String name= jsonObject.getString("name");
            allIds.put(githubId,new Id(name,githubId,uid));
        }
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Id id : allIds.values()){
            s.append(id.toString()).append("\n");
        }
        return s.toString();
    }
}
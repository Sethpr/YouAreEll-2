package controllers;

import models.Id;
//import spiffyUrlManipulator;

public class ServerController {
    private String rootURL = "http://zipcode.rocks:8085";

    private ServerController svr = new ServerController();

    private ServerController() {}

    public ServerController shared() {
        return svr;
    }

    public String idGet() { //used to be JsonString, maybe revert?
        // url -> /ids/
        // send the server a get with url
        // return json from server
        return null;
    }
    public String idPost(Id id) {//used to be JsonString, maybe revert?
        // url -> /ids/
        // create json from Id
        // request
        // reply
        // return json
        return null;
    }
    public String idPut(Id id) {//used to be JsonString, maybe revert?
        // url -> /ids/
        return null;
    }


}

// ServerController.shared.doGet()
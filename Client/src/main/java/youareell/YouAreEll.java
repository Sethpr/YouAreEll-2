package youareell;

import controllers.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class YouAreEll {

    TransactionController tt;

    public YouAreEll(TransactionController t) {
        this.tt = t;
    }

    public YouAreEll(MessageController m, IdController i) {
        this(new TransactionController(m, i));
    }

    public static void main(String[] args) throws IOException {
        // hmm: is this Dependency Injection?
        YouAreEll urlhandler = new YouAreEll(
                new TransactionController(
                        new MessageController(), new IdController()
                ));
    }


    public String get_ids() {
        try {
            return tt.get("/ids");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get_messages() {
        try {
            return tt.get("/messages");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String send_all(String id, String message) {
        try {
            return tt.send_all("/ids/" + id + "/messages", message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String send_to(String id, String message, String toId) {
        try {
            return tt.send_to("/ids/" + id + "/messages", message, toId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get_usr_message(String id) {
        try {
            return tt.get("/ids/" + id + "/messages");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String register(String name, String id) {
        try {
            return tt.register(name, id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(String id){
        tt.testIDC();
        //System.out.println("call tt.setMyId");
    }
}

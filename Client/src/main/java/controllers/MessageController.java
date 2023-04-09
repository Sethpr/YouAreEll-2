package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import models.Id;
import models.Message;

public class MessageController {

    private HashSet<Message> messagesSeen;
    // why a HashSet??

    public ArrayList<Message> getMessages() {
        return new ArrayList<>(messagesSeen);
    }
    public List<String> getMessagesForId(Id Id) {
        return messagesSeen.stream().filter((message -> message.getFromId().equals(Id))).map(Message::getMessage).collect(Collectors.toList());
    }
    public Message getMessageForSequence(String seq) {
        return messagesSeen.stream().filter((message -> message.getSeqId().equals(seq))).collect(Collectors.toList()).get(0);
    }
    public ArrayList<Message> getMessagesFromFriend(Id myId, Id friendId) {
        return (ArrayList<Message>) messagesSeen.stream().filter((message -> message.getToId().equals(myId) && message.getFromId().equals(friendId))).collect(Collectors.toList());
    }

    public Message postMessage(Id myId, Id toId, Message msg) {
        return null;
    }
 
}
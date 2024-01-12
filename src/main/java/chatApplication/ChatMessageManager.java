package chatApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatMessageManager {
    private Map<String, List<String>> chatMessages;
    
    public ChatMessageManager() {
        this.chatMessages = new HashMap<>();
    }
    
    public void addMessage(String room, String username, String message) {
        chatMessages.computeIfAbsent(room, k -> new ArrayList<>());
        List<String> roomMessages = chatMessages.get(room);
        roomMessages.add(username + ": " + message);
    }
    
    public List<String> getMessages(String room) {
        return chatMessages.getOrDefault(room, new ArrayList<>());
    }
}

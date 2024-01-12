package chatApplication;

/**
 * This class manages the Chat Rooms, specifically adding, creating, and deleting rooms. 
 * Other important methods such as saving/loading the file that maintains the chat rooms reside here as well.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomManager {
    
    private List<String> chatRooms;
    
    public ChatRoomManager() {
        this.chatRooms = new ArrayList<>();
        loadChatRooms();
    }
    
    public List<String> getChatRooms() {
        return new ArrayList<>(chatRooms);
    }
    
    public void addChatRoom(String roomName) {
        if (!chatRooms.contains(roomName)) {
            chatRooms.add(roomName);
            saveChatRooms();
        }
    }
    
    public void removeChatRoom(String roomName) {
        chatRooms.remove(roomName);
        saveChatRooms();
    }
    
    public void loadChatRooms() {
        File file = new File("chatRooms.txt");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    chatRooms.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveChatRooms() {
        File file = new File("chatRooms.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String room : chatRooms) {
                writer.write(room);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

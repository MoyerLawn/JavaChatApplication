package chatApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserAuthenticator {
    
    private static final String USER_DATA_FILE = "user_data.json";
    private UIManager userInterfaceManager = new UIManager();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public boolean authenticateUser(String enteredUsername, String enteredPassword) {
        List<UserData> userDataList = readUserDataFromFile();

        boolean userExists = userDataList.stream().anyMatch(user -> user.getUsername().equals(enteredUsername));

        System.out.println("User Exists: " + userExists);

        if (!userExists) {
            // Prompt to create a new account
            boolean createAccount = userInterfaceManager.showConfirmationAlert(
                    "Create Account", 
                    "Username not found", 
                    "Do you want to create a new account with the username '" + enteredUsername + "'?");
            
            if (createAccount) {
                addUser(enteredUsername, enteredPassword);
                System.out.println("User Added: " + enteredUsername);
                return true;
            } else {
                return false;
            }
        }

        // Continue with authentication
        for (UserData userData : userDataList) {
            if (userData.getUsername().equals(enteredUsername)) {
                // Use BCrypt to check if the entered password matches the stored hashed password
                boolean passwordMatches = BCrypt.checkpw(enteredPassword, userData.getPasswordHash());
                System.out.println("Password Matches: " + passwordMatches);
                return passwordMatches;
            }
        }

        return false; // User not found
    }

    
    public void addUser(String username, String password) {
        List<UserData> userDataList = readUserDataFromFile();
        
        //Add new user data
        String hashedPassword = hashPassword(password);
        userDataList.add(new UserData(username, hashedPassword));
        
        //Write the updated user data to the file
        writeUserDataToFile(userDataList);
    }
    
    public String hashPassword(String plainPassword) {
        //Generate a salt for bcrypt
        String salt = BCrypt.gensalt();
        
        //Then hash the password with the generated salt
        return BCrypt.hashpw(plainPassword, salt);
    }
    
    private List<UserData> readUserDataFromFile() {
        try {
            Path filePath = Paths.get(USER_DATA_FILE);
            if (Files.exists(filePath)) {
                byte[] jsonData = Files.readAllBytes(filePath);
                String jsonString = new String(jsonData).trim();

                if (!jsonString.isEmpty()) {
                    return objectMapper.readValue(jsonString, new TypeReference<List<UserData>>() {});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    
    private void writeUserDataToFile(List<UserData> userDataList) {
        try {
            Path filePath = Paths.get(USER_DATA_FILE);
            String jsonData = JsonUtils.toJson(userDataList);

            Files.write(filePath, jsonData.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

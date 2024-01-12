package chatApplication;

public class UserAuthenticator {

    public boolean authenticateUser(String username, String password) {
        //For simplicity's sake, we'll just check if the username/password are populated
        return !username.isEmpty() && !password.isEmpty();
    }
}

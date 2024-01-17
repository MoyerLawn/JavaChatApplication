package chatApplication;

public class UserData
{

    private String username;
    private String passwordHash;
    
    public UserData() {
        
    }
    
    public UserData(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername ()
    {
        return username;
    }

    public String getPasswordHash ()
    {
        return passwordHash;
    }
    
}

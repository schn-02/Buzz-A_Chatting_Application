package com.example.buzz.Model;

public class users {
    private String email;
    private String username; // Changed to camelCase
    private String password; // Changed to camelCase
    private String profilePic; // Changed to camelCase
    private String lastMessage; // Changed to camelCase
    private String userId;

    // Constructor with all fields
    public  users(String email, String username, String password, String profilePic, String lastMessage,
                  String userId) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.profilePic = profilePic;
        this.lastMessage = lastMessage;
        this.userId = userId;
    }

 public  users(String email, String username, String password, String profilePic) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.profilePic = profilePic;

    }



    // Default constructor
    public users() {
        super();
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

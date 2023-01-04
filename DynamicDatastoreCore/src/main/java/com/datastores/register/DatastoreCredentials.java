package com.datastores.register;

public class DatastoreCredentials {
    public DatastoreCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    String username;
    String password;


}

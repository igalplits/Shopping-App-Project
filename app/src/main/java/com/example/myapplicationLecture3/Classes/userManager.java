package com.example.myapplicationLecture3.Classes;

import java.util.HashMap;
import java.util.Objects;

public class userManager {
    private static userManager instance;
    private HashMap<String, String> userCredentials;

    private userManager() {
        userCredentials = new HashMap<>();
    }

    public static synchronized userManager getInstance() {
        if (instance == null)
            instance = new userManager();

        return instance;
    }

    public boolean registerUser(String email, String password)
    {
        if(userCredentials.containsKey(email))
            return false;

        userCredentials.put(email, password);
        return true;
    }

    public boolean loginUser(String email, String password)
    {
        return userCredentials.containsKey(email) && Objects.equals(userCredentials.get(email), password);
    }


}

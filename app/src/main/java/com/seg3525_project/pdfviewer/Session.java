package com.seg3525_project.pdfviewer;

/**
 * Created by merek on 07/07/16.
 */
public class Session {

    private static Session instance = null;
    private User user;

    private Session() {
        user = new User("Merek Orlowski", "morlo088@uottawa.ca", "Woodlergh");
    }

    public static Session getInstance() {
        if(instance == null)
            instance = new Session();
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

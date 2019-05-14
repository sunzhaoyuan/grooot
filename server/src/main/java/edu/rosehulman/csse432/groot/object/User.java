package edu.rosehulman.csse432.groot.object;

import org.codehaus.jackson.annotate.JsonIgnore;

public class User {

    @JsonIgnore
    private String key;

    public String id;

    public User(){}

    public User(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "key='" + key + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

}

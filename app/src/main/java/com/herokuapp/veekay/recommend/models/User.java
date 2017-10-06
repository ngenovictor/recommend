package com.herokuapp.veekay.recommend.models;

import java.util.ArrayList;

/**
 * Created by victor on 10/3/17.
 */

public class User {
    private String userId;
    private String firstName;
    private String secondName;
    private String pushId;
    private String userName;
    private ArrayList<String> friends = new ArrayList<>();

    public User(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String firstName, String secondName, String userName){
        this.firstName = firstName;
        this.secondName = secondName;
        this.userName = userName;
    }
    public void setPushId(String pushId){
        this.pushId = pushId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){return userId;}
    public String getFirstName(){return firstName;}
    public String getSecondName(){return secondName;}
    public String getPushId(){return pushId;}
    public String getUserName(){return userName;}
    public String getFullName(){
        return firstName+" "+secondName;
    }
    public ArrayList<String> getFriends(){
        return friends;
    }
    public void addFriends(String friendUid){
        friends.add(friendUid);
    }

}

package com.herokuapp.veekay.recommend.models;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by victor on 10/4/17.
 */
@Parcel
public class Question {
    private String question;
    private String ownerId;
    private String pushId;
    private Date dateCreated;

    public Question(){}
    public Question(String question){
        this.question = question;
        dateCreated = new Date();
    }
    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }
    public void setPushId(String pushId){
        this.pushId = pushId;
    }
    public String getQuestion(){
        return question;
    }
    public String getOwnerId(){
        return ownerId;
    }
    public String getPushId(){
        return pushId;
    }
    public Date getDateCreated(){
        return dateCreated;
    }












}

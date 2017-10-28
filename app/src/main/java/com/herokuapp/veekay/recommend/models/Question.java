package com.herokuapp.veekay.recommend.models;

import org.parceler.Parcel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by victor on 10/4/17.
 */
@Parcel
public class Question {
    private String question;
    private String ownerId;
    private String pushId;
    private String dateCreated;

    public Question(){}
    public Question(String question){
        this.question = question;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dateCreated = dateFormat.format(new Date());
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
    public String getDateCreated(){
        return dateCreated;
    }












}

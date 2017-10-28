package com.herokuapp.veekay.recommend.models;

import org.parceler.Parcel;

/**
 * Created by victor on 10/9/17.
 */
@Parcel
public class Recommendation {
    private String recommendation;
    private int thumbsUp;
    private int thumbsDown;
    private String userPushId;
    private String pushId;
    private String questionPushId;


    public Recommendation(){}
    public Recommendation(String recommendation){
        this.recommendation = recommendation;
        thumbsDown = 0;
        thumbsUp = 0;
    }
    public String getRecommendation(){
        return recommendation;
    }
    public int getThumbsUp(){
        return thumbsUp;
    }
    public int getThumbsDown(){
        return thumbsDown;
    }
    public String getUserPushId(){
        return userPushId;
    }
    public String getPushId(){
        return pushId;
    }
    public String getQuestionPushId(){
        return questionPushId;
    }
    public void setUserPushId(String userPushId){
        this.userPushId = userPushId;
    }
    public void setPushId(String pushId){
        this.pushId = pushId;
    }
    public void setQuestionPushId(String questionPushId){
        this.questionPushId = questionPushId;
    }
    public void addLike(){this.thumbsUp+=1;}
}

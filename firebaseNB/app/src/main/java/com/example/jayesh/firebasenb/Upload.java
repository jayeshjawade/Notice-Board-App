package com.example.jayesh.firebasenb;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Jayesh on 02/01/2017.
 */
@IgnoreExtraProperties
public class Upload{

    public String name;
    public String url;
    public String email;
    private String dateAndTime;
    public String comment;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String url) {
        this.name = name;
        this.url= url;
    }

  /*  public Upload(String name, String url,String dateAndTime,String email) {
        this.dateAndTime = dateAndTime;
        this.name = name;
        this.url= url;
        this.email=email;
    }
*/
    public Upload(String name,String dateAndTime,String email,String comment) {
        this.dateAndTime = dateAndTime;
        this.name = name;
        this.email=email;
        this.comment=comment;
    }

    public Upload(String name, String url,String dateAndTime,String email,String comment) {
        this.dateAndTime = dateAndTime;
        this.name = name;
        this.url= url;
        this.email=email;
        this.comment=comment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateAndTime_file() {
        return dateAndTime;
    }

    public void setDateAndTime_file(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getComment() {
        return comment;
    }


}
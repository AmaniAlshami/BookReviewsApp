package com.example.bookreview;

import com.google.firebase.database.ServerValue;

public class Comment {

    private String content,uid,uname , cid;
    //private Object timestamp;


    public Comment() {
    }

    public Comment(String content, String uid,String uname,String cid) {
        this.content = content;
        this.uid = uid;
        this.uname = uname;
        this.cid = cid ;
       // this.timestamp = ServerValue.TIMESTAMP;

    }
    public Comment(String content,String uname) {
        this.content = content;
        this.uname = uname;
        //this.timestamp = ServerValue.TIMESTAMP;

    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Comment(String content, String uid, String uimg, String uname, Object timestamp) {
        this.content = content;
        this.uid = uid;
        this.uname = uname;
      //  this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

 //   public Object getTimestamp() { return timestamp; }

  //  public void setTimestamp(Object timestamp) {this.timestamp = timestamp;}
}

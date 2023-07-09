package com.example.myquizapp.ui.home;

public class quiz {
String creater;
String quizid;
String quizname;
String totalquestion;
String userid;
String date;

    public quiz(String creater, String quizid, String quizname, String totalquestion, String userid,String date) {
        this.creater = creater;
        this.quizid = quizid;
        this.quizname = quizname;
        this.totalquestion = totalquestion;
        this.userid = userid;
        this.date=date;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getQuizid() {
        return quizid;
    }

    public void setQuizid(String quizid) {
        this.quizid = quizid;
    }

    public String getQuizname() {
        return quizname;
    }

    public void setQuizname(String quizname) {
        this.quizname = quizname;
    }

    public String getTotalquestion() {
        return totalquestion;
    }

    public void setTotalquestion(String totalquestion) {
        this.totalquestion = totalquestion;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package com.example.myquizapp.ui.dashboard;

public class dashQuiz {
    String quizid;
    String quizname;
    String totalquestions;
    String date;
    String score;

    public dashQuiz(String quizid, String quizname, String totalquestions, String date, String score) {
        this.quizid = quizid;
        this.quizname = quizname;
        this.totalquestions = totalquestions;
        this.date = date;
        this.score = score;
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

    public String getTotalquestions() {
        return totalquestions;
    }

    public void setTotalquestions(String totalquestions) {
        this.totalquestions = totalquestions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}

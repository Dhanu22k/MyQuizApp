package com.example.myquizapp.ui.create;

public class Quiz {
    public String quizName;
    public String date;
    public  String quizId;
    public int questionCount;
    Quiz(){

    }

    public Quiz(String quizName, String date, String quizId, int questionCount) {
        this.quizName = quizName;
        this.date = date;
        this.quizId = quizId;
        this.questionCount = questionCount;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }
}

package com.example.myquizapp.ui.create;

public class Quiz {
    public String quizName;
    public String questions;
    public String answer;
    public String date;
    Quiz(){

    }
    public Quiz(String quizName, String questions, String answer, String date) {
        this.quizName = quizName;
        this.questions = questions;
        this.answer = answer;
        this.date = date;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

package ru.lb.fntestdojo.shared.entities;


import java.io.Serializable;
import java.util.List;

/**
 * Created by mihard on 14.03.2016.
 */
public class QuestionDocument implements Serializable{
    private String title;
    private String question;
    private List<String> answerList;
    private Integer questionNumber;
    private String id;
    private Integer correctAnswer;

    public QuestionDocument() {
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }
}

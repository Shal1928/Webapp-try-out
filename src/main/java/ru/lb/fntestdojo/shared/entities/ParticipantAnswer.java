package ru.lb.fntestdojo.shared.entities;

import java.io.Serializable;

/**
 * Created by mihard on 16.03.2016.
 */
public class ParticipantAnswer implements Serializable {
    private Integer answer;
    private String participantName;
    private QuestionDocument question;

    public ParticipantAnswer(){}

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public QuestionDocument getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDocument question) {
        this.question = question;
    }
}

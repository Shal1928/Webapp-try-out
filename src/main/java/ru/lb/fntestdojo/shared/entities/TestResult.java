package ru.lb.fntestdojo.shared.entities;

import java.io.Serializable;

/**
 * Created by mihard on 16.03.2016.
 */
public class TestResult implements Serializable {
    private Integer numberOfQuestions;
    private Integer numberOfCorrectAnswers;

    public TestResult() {
    }

    public TestResult(Integer numberOfQuestions, Integer numberOfCorrectAnswers) {
        this.numberOfQuestions = numberOfQuestions;
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public Integer getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }
}

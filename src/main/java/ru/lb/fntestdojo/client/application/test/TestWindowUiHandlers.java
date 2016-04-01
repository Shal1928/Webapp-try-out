package ru.lb.fntestdojo.client.application.test;


interface TestWindowUiHandlers {
    void doAnswer(String questionNumber, String answerNum);
    void presPreviousButton(Integer previousQuestionNum);
    void presNextButton(Integer nextQuestionNum);
    void presConfirmButton(Integer questionNum);
}

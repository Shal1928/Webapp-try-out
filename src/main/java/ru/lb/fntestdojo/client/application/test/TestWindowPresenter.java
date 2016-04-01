package ru.lb.fntestdojo.client.application.test;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import ru.lb.fntestdojo.client.application.ApplicationPresenter;
import ru.lb.fntestdojo.client.application.test.result.ResultPresenter;
import ru.lb.fntestdojo.client.place.NameTokens;
import ru.lb.fntestdojo.shared.entities.ParticipantAnswer;
import ru.lb.fntestdojo.shared.entities.QuestionDocument;
import ru.lb.fntestdojo.shared.entities.TestResult;
import ru.lb.fntestdojo.shared.services.AuthFilenetAsync;

import java.util.ArrayList;
import java.util.List;

public class TestWindowPresenter extends Presenter<TestWindowPresenter.MyView, TestWindowPresenter.MyProxy> implements TestWindowUiHandlers {
    private AuthFilenetAsync authFilenetService;
    private List<QuestionDocument> questionDocumentList;
    private List<ParticipantAnswer> participantAnswerList = new ArrayList<ParticipantAnswer>();
    private String testNum;
    private ResultPresenter resultPopupPresenter;

    interface MyView extends View, HasUiHandlers<TestWindowUiHandlers> {
        void outlineQuestion(String questionNum, String testNum, String question);
        void outlineAnswer(String questionNum, String aswerNumber, String answerText, Boolean isChecked);
        void outlinePreviosButton(Integer previousQuestionNum);
        void outlineNextButton(Integer nextQuestionNum);
        void outlineConfirmButton(Integer questionNumber);
        void clearAnswersfield();
        void clearLeftHorizontalfield();
        void clearRightHorizontalfield();
    }

    @NameToken(NameTokens.TEST)
    @ProxyStandard
    interface MyProxy extends ProxyPlace<TestWindowPresenter> {
    }

    @Inject
    TestWindowPresenter(
            AuthFilenetAsync authFilenetAsync,
            ResultPresenter resultPresenter,
            EventBus eventBus,
            MyView view,
            MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);

        getView().setUiHandlers(this);

        this.authFilenetService  = authFilenetAsync;
        this.resultPopupPresenter = resultPresenter;
    }

    //этот метод выполняется сразу после конструктора
    @Override
    public void prepareFromRequest(PlaceRequest request) {
        testNum = request.getParameter("testNum",null);
        determineQuestionDocList(testNum);
    }

    @Override
    public void doAnswer(String questionNumber, String answerNum) {
        int questNumInt = Integer.parseInt(questionNumber);
        int answerNumInt = Integer.parseInt(answerNum);
        ParticipantAnswer participantAnswer = new ParticipantAnswer();
        participantAnswer.setAnswer(answerNumInt);
        participantAnswer.setQuestion(questionDocumentList.get(questNumInt));
        if(participantAnswerList.size() > questNumInt) {
            participantAnswerList.set(questNumInt, participantAnswer);
        } else participantAnswerList.add(participantAnswer);
    }

    @Override
    public void presPreviousButton(Integer previousQuestionNum) {
        getView().outlineQuestion(previousQuestionNum.toString(), testNum, questionDocumentList.get(previousQuestionNum).getQuestion());
        outlineAnswerList(previousQuestionNum, questionDocumentList.get(previousQuestionNum).getAnswerList());
    }

    @Override
    public void presNextButton(Integer nextQuestionNum) {
        if(participantAnswerList.size() > nextQuestionNum-1) {
            getView().outlineQuestion(nextQuestionNum.toString(), testNum, questionDocumentList.get(nextQuestionNum).getQuestion());
            outlineAnswerList(nextQuestionNum, questionDocumentList.get(nextQuestionNum).getAnswerList());
        } else {
            Window.alert("Вы не ответили на вопрос!");
        }
    }

    @Override
    public void presConfirmButton(Integer questionNum) {
        if (participantAnswerList.size() > questionNum){
            if (Window.confirm("Вы уверены, что хотите отправить ответы на проверку?")) {
                TestResult testResult = getTestResults(participantAnswerList);
                resultPopupPresenter.presentResult(testResult);
                authFilenetService.writeParticipantAnswers(participantAnswerList, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        resultPopupPresenter.writeServerResponse("Ошибка при записи ответов в Filenet: " + throwable.getLocalizedMessage());
                        addToPopupSlot(resultPopupPresenter);
                    }

                    @Override
                    public void onSuccess(Void aVoid) {
                        resultPopupPresenter.writeServerResponse("Ответы тестируемого успешно записаны в Filenet!");
                        addToPopupSlot(resultPopupPresenter);
                    }
                });
            }
        }else Window.alert("Вы не ответили на последний вопрос!");
    }

    private void determineQuestionDocList(final String testNum){
        authFilenetService.getTestDocsList(testNum, new AsyncCallback<List<QuestionDocument>>() {
            public void onFailure(Throwable caught) {
                Window.alert("Не удалось загрузить вопросы теста " + testNum + ", ошибка: " + caught.getLocalizedMessage());
            }

            public void onSuccess(List<QuestionDocument> result) {
                questionDocumentList = result;
                getView().outlineQuestion("1", testNum, questionDocumentList.get(0).getQuestion());
                outlineAnswerList(0, questionDocumentList.get(0).getAnswerList());
            }
        });
    }

    private void outlineAnswerList(Integer questionNumber, List<String> answers){
        getView().clearAnswersfield();
        getView().clearLeftHorizontalfield();
        getView().clearRightHorizontalfield();

        boolean isAswered;
        for (int i = 0; i < answers.size(); i++) {
            isAswered = (participantAnswerList.size() > questionNumber && participantAnswerList.get(questionNumber).getAnswer().equals(i));
            getView().outlineAnswer(Integer.toString(questionNumber), Integer.toString(i), answers.get(i), isAswered);
        }

        if(questionNumber > 0){
            getView().outlinePreviosButton(questionNumber - 1);
        }

        if (questionNumber < questionDocumentList.size()-1){
            getView().outlineNextButton(questionNumber + 1);
        }

        if(questionNumber == questionDocumentList.size()-1){
            getView().outlineConfirmButton(questionNumber);
        }
    }

    private TestResult getTestResults(List<ParticipantAnswer> participantAnswerList) {
        Integer countCorrectAnswers = 0;
        for (ParticipantAnswer participantAnswer : participantAnswerList) {
            if (participantAnswer.getAnswer().equals(participantAnswer.getQuestion().getCorrectAnswer())) {
                countCorrectAnswers++;
            }
        }
        return new TestResult(participantAnswerList.size(), countCorrectAnswers);
    }
}

package ru.lb.fntestdojo.client.application.test;

import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import javax.inject.Inject;


public class TestWindowView extends ViewWithUiHandlers<TestWindowUiHandlers> implements TestWindowPresenter.MyView {
    interface Binder extends UiBinder<Widget, TestWindowView> {
    }

    @Override
    public void outlineQuestion(String questionNum, String testNum, String question) {
        testNumfield.setInnerText(testNum);
        questionNumfield.setInnerText(questionNum);
        questionTextfield.setInnerText(question);
    }

    @Override
    public void outlineAnswer(final String questionNum, final String aswerNumber, String answerText, Boolean isChecked) {
        RadioButton radioButton = new RadioButton(questionNum, answerText);
        radioButton.setValue(isChecked);
        radioButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getUiHandlers().doAnswer(questionNum, aswerNumber);
            }
        });
        answersfield.add(radioButton);
        answersfield.add(new HTML("<br/>"));
    }

    @Override
    public void outlinePreviosButton(final Integer previousQuestionNum) {
        Button previousButton = new Button("Previous");
        leftHorizontalfield.add(previousButton);
        previousButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getUiHandlers().presPreviousButton(previousQuestionNum);
            }
        });
    }

    @Override
    public void outlineNextButton(final Integer nextQuestionNum) {
        Button nextButton = new Button("Next");
        rightHorizontalfield.add(nextButton);
        nextButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getUiHandlers().presNextButton(nextQuestionNum);
            }
        });
    }

    @Override
    public void outlineConfirmButton(final Integer questionNumber) {
        Button confirmButton = new Button("Confirm Responses");
        rightHorizontalfield.add(confirmButton);
        confirmButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getUiHandlers().presConfirmButton(questionNumber);
            }
        });
    }

    @Override
    public void clearAnswersfield() {
        answersfield.clear();
    }

    @Override
    public void clearLeftHorizontalfield() {
        leftHorizontalfield.clear();
    }

    @Override
    public void clearRightHorizontalfield() {
        rightHorizontalfield.clear();
    }

    @UiField
    SpanElement testNumfield;

    @UiField
    SpanElement questionNumfield;

    @UiField
    SpanElement questionTextfield;

    @UiField
    VerticalPanel answersfield;

    @UiField
    HorizontalPanel leftHorizontalfield;

    @UiField
    HorizontalPanel rightHorizontalfield;

    @Inject
    TestWindowView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

}

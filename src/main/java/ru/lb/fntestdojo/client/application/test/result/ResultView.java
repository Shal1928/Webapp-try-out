
package ru.lb.fntestdojo.client.application.test.result;

import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class ResultView extends PopupViewWithUiHandlers<ResultUiHandlers> implements ResultPresenter.MyView {
    interface Binder extends UiBinder<PopupPanel, ResultView> {
    }

    @Inject
    ResultView(Binder uiBinder, EventBus eventBus) {
        super(eventBus);

        initWidget(uiBinder.createAndBindUi(this));

        closeResultButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getUiHandlers().closeResultButton();
            }
        });
        center();
    }

    @Override
    public void outlineResults(Integer correctAnswers, Integer totalQuestions, Integer percentCorrectAnswers) {
        numCorrectAnswers.setInnerText(correctAnswers.toString());
        numOfQuestions.setInnerText(totalQuestions.toString());
        percentOfCorrectAnswers.setInnerText(percentCorrectAnswers.toString());
    }

    @Override
    public void outlineServerResponse(String response) {
        serverResponse.setText(response);
    }

    @UiField
    SpanElement numCorrectAnswers;

    @UiField
    SpanElement numOfQuestions;

    @UiField
    SpanElement percentOfCorrectAnswers;

    @UiField
    Button closeResultButton;

    @UiField
    HTML serverResponse;
}

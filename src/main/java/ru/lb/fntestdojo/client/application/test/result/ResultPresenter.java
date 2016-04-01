
package ru.lb.fntestdojo.client.application.test.result;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import ru.lb.fntestdojo.client.place.NameTokens;
import ru.lb.fntestdojo.shared.entities.TestResult;

public class ResultPresenter extends PresenterWidget<ResultPresenter.MyView> implements ResultUiHandlers {
    private PlaceManager placeManager;

    interface MyView extends PopupView, HasUiHandlers<ResultUiHandlers> {
        void outlineResults(Integer correctAnswers, Integer totalQuestions, Integer percentCorrectAnswers);
        void outlineServerResponse(String response);
    }

    @Inject
    ResultPresenter(final EventBus eventBus, final MyView view, PlaceManager placeManager) {
        super(eventBus, view);

        getView().setUiHandlers(this);
        this.placeManager = placeManager;
    }

    public void show(){
        getView().show();
    }

    public void hide(){
        getView().hide();
    }

    public void presentResult(TestResult testResult){
        getView().outlineResults(testResult.getNumberOfCorrectAnswers(), testResult.getNumberOfQuestions(), testResult.getNumberOfCorrectAnswers() * 100 / testResult.getNumberOfQuestions());
    }

    public void writeServerResponse(String response){
        getView().outlineServerResponse(response);
    }

    @Override
    public void closeResultButton() {
        hide();
        PlaceRequest placeRequest = new PlaceRequest.Builder()
                .nameToken(NameTokens.CHOICE)
                .build();
        placeManager.revealPlace(placeRequest);
    }
}

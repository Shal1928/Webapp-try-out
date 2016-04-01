package ru.lb.fntestdojo.client.application.choice;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import ru.lb.fntestdojo.client.application.ApplicationPresenter;
import ru.lb.fntestdojo.client.place.NameTokens;
import ru.lb.fntestdojo.shared.services.AuthFilenetAsync;

import java.util.List;

public class TestChoicePresenter extends Presenter<TestChoicePresenter.MyView, TestChoicePresenter.MyProxy> implements TestChoiceUiHandlers {
    final AuthFilenetAsync authFilenetService;
    private PlaceManager placeManager;

    interface MyView extends View, HasUiHandlers<TestChoiceUiHandlers> {
        void addTestButton(String test);
    }
    @NameToken(NameTokens.CHOICE)
    @ProxyStandard
    interface MyProxy extends ProxyPlace<TestChoicePresenter> {

    }

    @Inject
    TestChoicePresenter(
            AuthFilenetAsync authFilenetAsync,
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            PlaceManager placeManager) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);

        this.placeManager = placeManager;
        this.authFilenetService = authFilenetAsync;
        getView().setUiHandlers(this);
        addTests();
    }

    @Override
    public void exitTestChoice() {
        authFilenetService.logOut(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }

    @Override
    public void onChooseTest(String test) {
        PlaceRequest placeRequest = new PlaceRequest.Builder()
                .nameToken(NameTokens.TEST)
                .with("testNum", test)
                .build();
        placeManager.revealPlace(placeRequest);
    }

    private void addTests(){
        authFilenetService.getTestsList(new AsyncCallback<List<String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                Window.alert("Список тестов Filenet не получен, причина: " + throwable.getLocalizedMessage());
            }

            @Override
            public void onSuccess(List<String> strings) {
                for (String test : strings) {
                    getView().addTestButton(test);
                }
            }
        });

    }
}

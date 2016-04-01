package ru.lb.fntestdojo.client.application.choice;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import javax.inject.Inject;


public class TestChoiceView extends ViewWithUiHandlers<TestChoiceUiHandlers> implements TestChoicePresenter.MyView {
    interface Binder extends UiBinder<Widget, TestChoiceView> {
    }

    @Override
    public void addTestButton(final String test) {
        final Button testButton = new Button(test);
        testButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                getUiHandlers().onChooseTest(test);
            }
        });
        vPanelTests.add(testButton);
        vPanelTests.add(new HTML("<br/>"));
    }

    @UiField
    VerticalPanel vPanelTests;

    @UiField
    Button exitButton;

    @UiHandler("exitButton")
    void onClose(ClickEvent event){
        getUiHandlers().exitTestChoice();
    }

    @Inject
    TestChoiceView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}

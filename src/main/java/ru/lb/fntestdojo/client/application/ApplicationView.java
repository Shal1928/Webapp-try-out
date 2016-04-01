package ru.lb.fntestdojo.client.application;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

import javax.inject.Inject;

public class ApplicationView extends ViewWithUiHandlers<ApplicationPresenter> implements ApplicationPresenter.MyView {
    interface Binder extends UiBinder<Widget, ApplicationView> {
    }

    @UiField
    SimplePanel main;

    @Inject
    ApplicationView(
            Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));

        bindSlot(ApplicationPresenter.SLOT_MAIN, main);
    }

}
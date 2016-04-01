package ru.lb.fntestdojo.client.application.test.result;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ResultModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenterWidget(ResultPresenter.class, ResultPresenter.MyView.class, ResultView.class);
    }
}

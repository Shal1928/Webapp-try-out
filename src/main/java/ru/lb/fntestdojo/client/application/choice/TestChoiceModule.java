package ru.lb.fntestdojo.client.application.choice;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class TestChoiceModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(TestChoicePresenter.class, TestChoicePresenter.MyView.class, TestChoiceView.class, TestChoicePresenter.MyProxy.class);
    }
}

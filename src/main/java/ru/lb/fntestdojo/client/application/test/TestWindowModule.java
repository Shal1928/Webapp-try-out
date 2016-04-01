package ru.lb.fntestdojo.client.application.test;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import ru.lb.fntestdojo.client.application.test.result.ResultModule;

public class TestWindowModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new ResultModule());

        bindPresenter(TestWindowPresenter.class, TestWindowPresenter.MyView.class, TestWindowView.class, TestWindowPresenter.MyProxy.class);
    }
}

package ru.lb.fntestdojo.client.gin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import ru.lb.fntestdojo.client.application.ApplicationModule;
import ru.lb.fntestdojo.client.place.NameTokens;
import ru.lb.fntestdojo.client.resources.ResourceLoader;

public class ClientModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new DefaultModule
                .Builder()
                .defaultPlace(NameTokens.CHOICE)
                // TODO: 23.03.2016 errorPlace
                .errorPlace(NameTokens.CHOICE)
                // TODO: 23.03.2016 unauthorizedPlace
                .unauthorizedPlace(NameTokens.CHOICE)
                .build());
        install(new ApplicationModule());

        bind(ResourceLoader.class).asEagerSingleton();
    }
}

package ru.lb.fntestdojo.client.application.choice;

import com.gwtplatform.mvp.client.UiHandlers;

interface TestChoiceUiHandlers extends UiHandlers {
    void exitTestChoice();
    void onChooseTest(String test);
}

package ru.lb.fntestdojo.shared.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.lb.fntestdojo.shared.entities.ParticipantAnswer;
import ru.lb.fntestdojo.shared.entities.QuestionDocument;

import java.util.List;

public interface AuthFilenetAsync {
    void getTestDocsList(String testNum, AsyncCallback<List<QuestionDocument>> async);

    void writeParticipantAnswers(List<ParticipantAnswer> participantAnswerList, AsyncCallback<Void> async);

    void logOut(AsyncCallback<Void> async);

    void getTestsList(AsyncCallback<List<String>> async);
}

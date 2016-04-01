package ru.lb.fntestdojo.shared.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import ru.lb.fntestdojo.shared.entities.ParticipantAnswer;
import ru.lb.fntestdojo.shared.entities.QuestionDocument;

import java.util.List;

/**
 * Created by mihard on 10.03.2016.
 */

@RemoteServiceRelativePath("AuthFilenet")
public interface AuthFilenet extends RemoteService {
    List<QuestionDocument> getTestDocsList(String testNum) throws IllegalArgumentException;

    void writeParticipantAnswers(List<ParticipantAnswer> participantAnswerList) throws IllegalArgumentException;

    void logOut();

    List<String> getTestsList();
}

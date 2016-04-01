package ru.lb.fntestdojo.server;

import com.filenet.api.collection.ObjectStoreSet;
import com.filenet.api.constants.*;
import com.filenet.api.core.*;
import ru.lb.fntestdojo.server.dao.ParticipantAnswerDao;
import ru.lb.fntestdojo.server.dao.QuestionDocumentDao;
import ru.lb.fntestdojo.server.dao.TestsListDao;
import ru.lb.fntestdojo.shared.entities.ParticipantAnswer;
import ru.lb.fntestdojo.shared.entities.QuestionDocument;
import ru.lb.fntestdojo.shared.services.AuthFilenet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mihard on 10.03.2016.
 */

@SuppressWarnings("serial")
@WebServlet(name="AuthFilenetServlet", urlPatterns={"/AuthFilenetServlet"})
public class AuthFilenetImpl extends HttpServlet implements AuthFilenet {

//    private String

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

//        req.getRemoteUser()
    }

    @Override
    public void logOut(){
//        getThreadLocalRequest().getSession().invalidate();
        //// TODO: 24.03.2016 переадресовать на логин jsp
    }

    @Override
    public List<String> getTestsList() {
        TestsListDao testsListDao = new TestsListDao(getObjectStore());
        return testsListDao.getList();
    }

    @Override
    public List<QuestionDocument> getTestDocsList(String testNum) throws IllegalArgumentException {
        if(testNum == null || testNum.equals("")){
            throw new IllegalArgumentException("Недопустимое значение номера теста = " + testNum + "!");
        }

        QuestionDocumentDao questionDocumentDao = new QuestionDocumentDao(getObjectStore());

        return questionDocumentDao.getQuestionDocumentList(testNum);
    }

    @Override
    public void writeParticipantAnswers(List<ParticipantAnswer> participantAnswerList) throws IllegalArgumentException{
        if(participantAnswerList == null || participantAnswerList.size() < 1){
            throw new IllegalArgumentException("Недопустимое значение списк ответов List<ParticipantAnswer>");
        }
        //Записываем в ответы имя отвечающего из значения в сессии, которое было определено фильтром
        String participantName = getThreadLocalRequest().getRemoteUser();
        for (ParticipantAnswer answer : participantAnswerList) {
            answer.setParticipantName(participantName);
        }

        ParticipantAnswerDao participantAnswerDao = new ParticipantAnswerDao(getObjectStore());
        participantAnswerDao.writeAnswersToFN(participantAnswerList);
    }

    private ObjectStore getObjectStore(){
        Connection connection = Factory.Connection.getConnection(FilenetProperties.URI);
        Domain domain = Factory.Domain.fetchInstance(connection, FilenetProperties.DOMAIN_NAME, null);
        ObjectStore objectStore = null;

        ObjectStoreSet osSet = domain.get_ObjectStores();
        Iterator<ObjectStore> osIterator = osSet.iterator();
        while (osIterator.hasNext()) {
            objectStore = (ObjectStore) osIterator.next();
            if ((objectStore.getAccessAllowed().intValue() & AccessLevel.USE_OBJECT_STORE.getValue()) > 0) {
                if (objectStore.get_Name().equals(FilenetProperties.OBJECT_STORE_NAME)) {
                    break;
                }
            }
        }

        if (objectStore == null || !objectStore.get_Name().equals(FilenetProperties.OBJECT_STORE_NAME)) {
            throw new IllegalArgumentException("objectStore 'WXT' - не найден!");
        }
        return objectStore;
    }
}

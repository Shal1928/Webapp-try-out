package ru.lb.fntestdojo.server.dao;

import com.filenet.api.collection.DocumentSet;
import com.filenet.api.collection.FolderSet;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.property.Properties;
import ru.lb.fntestdojo.server.FilenetProperties;
import ru.lb.fntestdojo.shared.entities.QuestionDocument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mihard on 18.03.2016.
 */
public class QuestionDocumentDao {
    private final ObjectStore objectStore;

    public QuestionDocumentDao(ObjectStore objectStore) {
        this.objectStore = objectStore;
    }

    public List<QuestionDocument> getQuestionDocumentList(String testNum){
        Folder testsFolder = Factory.Folder.fetchInstance(objectStore, FilenetProperties.TESTS_FOLDER, null);
        FolderSet subFolders = testsFolder.get_SubFolders();
        Iterator subFolderIter = subFolders.iterator();
        Folder subFolder = null;
        while(subFolderIter.hasNext()) {
            subFolder = (Folder)subFolderIter.next();
            if(subFolder.get_FolderName().equals(testNum)){
                break;
            }
        }
        if(subFolder == null || !subFolder.get_FolderName().equals(testNum)){
            throw new IllegalArgumentException("Тест с номером " + testNum + " не найден!");
        }

        List<QuestionDocument> questionDocumentList = new ArrayList<>();
        DocumentSet documents = subFolder.get_ContainedDocuments();
        Iterator documentsIter = documents.iterator();
        Document retrieveDoc = null;
        Properties properties = null;
        while (documentsIter.hasNext()){
            QuestionDocument questionDocument = new QuestionDocument();
            retrieveDoc = (Document)documentsIter.next();
            properties = retrieveDoc.getProperties();
            try {
                questionDocument.setId(retrieveDoc.get_Id().toString());
                questionDocument.setTitle(retrieveDoc.get_Name());
                questionDocument.setQuestion(properties.getStringValue("Question"));
                questionDocument.setQuestionNumber(properties.getInteger32Value("QuestionNumber"));
                questionDocument.setAnswerList(new ArrayList<>(properties.getStringListValue("Answers")));
                questionDocument.setCorrectAnswer(properties.getInteger32Value("CorrectAnswer"));
            } catch (Exception e){
                throw new IllegalArgumentException("ошибка в мапинге полей Document в QuestionDocument: " + Arrays.toString(Thread.currentThread().getStackTrace()));
            }
            questionDocumentList.add(questionDocument);
        }
        return questionDocumentList;
    }
}

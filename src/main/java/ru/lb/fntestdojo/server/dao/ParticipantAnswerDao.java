package ru.lb.fntestdojo.server.dao;

import com.filenet.api.collection.DocumentSet;
import com.filenet.api.collection.FolderSet;
import com.filenet.api.constants.*;
import com.filenet.api.core.*;
import com.filenet.api.util.Id;
import ru.lb.fntestdojo.server.FilenetProperties;
import ru.lb.fntestdojo.shared.entities.ParticipantAnswer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mihard on 18.03.2016.
 */
public class ParticipantAnswerDao {
    private final ObjectStore objectStore;

    public ParticipantAnswerDao(ObjectStore objectStore) {
        this.objectStore = objectStore;
    }

    public void writeAnswersToFN(List<ParticipantAnswer> participantAnswerList){
        String participantName = participantAnswerList.get(0).getParticipantName();

        Folder answersFolder = Factory.Folder.fetchInstance(objectStore, FilenetProperties.TESTS_FOLDER + "Answers", null);
        Folder participantFolder = getChildFolder(objectStore, answersFolder, participantName);

        String participantTestFolderName = participantAnswerList.get(0).getQuestion().getTitle().split("[.]")[0] + "_" + participantName;
        Folder participantTestFolder = getChildFolder(objectStore, participantFolder, participantTestFolderName);

        List<Document> existingAnswersList = new ArrayList<>();
        DocumentSet documentSet = participantTestFolder.get_ContainedDocuments();
        Iterator docIter = documentSet.iterator();
        while (docIter.hasNext()){
            existingAnswersList.add((Document)docIter.next());
        }

        Document questionDocFromFilenet = null;
        Boolean isExistOnFilenet;
        for (ParticipantAnswer pa : participantAnswerList) {
            isExistOnFilenet = false;
            if(existingAnswersList.size() > 0){
                for (Document doc : existingAnswersList) {
                    questionDocFromFilenet = (Document)doc.getProperties().getObjectValue("ParticipantQuestion");
                    if(questionDocFromFilenet != null && pa.getQuestion().getTitle().equals(questionDocFromFilenet.get_Name())){
                        updateParticipantAnswer(objectStore, doc, pa);
                        isExistOnFilenet = true;
                        break;
                    }
                }
            }
            if(!isExistOnFilenet){
                createAndSaveParticipantAnswer(objectStore, participantTestFolder, pa);
            }
        }
    }

    private void updateParticipantAnswer(ObjectStore objectStore, Document existParticipantAnswer, ParticipantAnswer participantAnswer){
        if(existParticipantAnswer.get_IsCurrentVersion().booleanValue() == false){
            existParticipantAnswer = (Document)existParticipantAnswer.get_CurrentVersion();
        }
        if(existParticipantAnswer.get_IsReserved().booleanValue() == false) {
            existParticipantAnswer.checkout(ReservationType.COLLABORATIVE, null, null, null);
            existParticipantAnswer.save(RefreshMode.REFRESH);
            existParticipantAnswer = (Document) existParticipantAnswer.get_Reservation();
        }
        existParticipantAnswer.getProperties().putValue("ParticipantAnswer", participantAnswer.getAnswer());
        existParticipantAnswer.getProperties().putValue("ParticipantName", participantAnswer.getParticipantName());
        existParticipantAnswer.getProperties().putObjectValue("ParticipantQuestion", Factory.Document.getInstance(objectStore, "FilenetTestQuestion", new Id(participantAnswer.getQuestion().getId())));
        existParticipantAnswer.checkin(AutoClassify.AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
        existParticipantAnswer.save(RefreshMode.REFRESH);
    }

    private void createAndSaveParticipantAnswer(ObjectStore objectStore, Folder folderToSave, ParticipantAnswer participantAnswer){
        Document newAnswerDoc = Factory.Document.createInstance(objectStore, "FilenetTestParticipantAnswer");
        newAnswerDoc.checkin(AutoClassify.AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
        newAnswerDoc.getProperties().putValue("DocumentTitle", participantAnswer.getQuestion().getTitle() + "_" + participantAnswer.getParticipantName());
        newAnswerDoc.getProperties().putValue("ParticipantAnswer", participantAnswer.getAnswer());
        newAnswerDoc.getProperties().putValue("ParticipantName", participantAnswer.getParticipantName());
        newAnswerDoc.getProperties().putObjectValue("ParticipantQuestion", Factory.Document.getInstance(objectStore, "FilenetTestQuestion", new Id(participantAnswer.getQuestion().getId())));
        newAnswerDoc.save(RefreshMode.REFRESH);
        ReferentialContainmentRelationship rel = folderToSave.file(newAnswerDoc, AutoUniqueName.AUTO_UNIQUE, participantAnswer.getQuestion().getTitle() + "_" + participantAnswer.getParticipantName(), DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
        rel.save(RefreshMode.NO_REFRESH);
    }

    private Folder getChildFolder (ObjectStore objectStore, Folder parentFolder, String childFolderName){
        FolderSet subFolders = parentFolder.get_SubFolders();
        Iterator subFolderIter = subFolders.iterator();
        Folder subFolder = null;
        while(subFolderIter.hasNext()) {
            subFolder = (Folder)subFolderIter.next();
            if(subFolder.get_FolderName().equals(childFolderName)){
                break;
            }
        }
        if(subFolder == null || !subFolder.get_FolderName().equals(childFolderName)){
            subFolder = Factory.Folder.createInstance(objectStore, null);
            subFolder.set_Parent(parentFolder);
            subFolder.set_FolderName(childFolderName);
            subFolder.save(RefreshMode.REFRESH);
        }
        return subFolder;
    }
}

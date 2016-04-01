package ru.lb.fntestdojo.server.dao;

import com.filenet.api.collection.FolderSet;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import ru.lb.fntestdojo.server.FilenetProperties;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mihard on 24.03.2016.
 */
public class TestsListDao {
    private final ObjectStore objectStore;

    public TestsListDao(ObjectStore objectStore) {
        this.objectStore = objectStore;
    }

    public List<String> getList(){
        List<String> testsList = new ArrayList<String>();
        Folder testsFolder = Factory.Folder.fetchInstance(objectStore, FilenetProperties.TESTS_FOLDER, null);
        FolderSet subFolders = testsFolder.get_SubFolders();
        Iterator subFolderIter = subFolders.iterator();
        Folder subFolder = null;
        while(subFolderIter.hasNext()) {
            subFolder = (Folder)subFolderIter.next();
            //добавляем в список все кроме папки с ответами
            if(!subFolder.get_FolderName().equals(FilenetProperties.ANSWER_FOLDER)){
                testsList.add(subFolder.get_FolderName());
            }
        }
        if(testsList.size() < 1){
            throw new IllegalArgumentException("В папке " + FilenetProperties.TESTS_FOLDER + " нет тестов!");
        }

        return testsList;
    }
}

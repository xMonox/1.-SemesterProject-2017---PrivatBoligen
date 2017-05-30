package logic.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.util.Date;

/**
 * Created by lupus on 5/6/17.
 */
public class ObservableDocument {
    //Alle
    private IntegerProperty fileId;
    private StringProperty fileName;
    private IntegerProperty owner;
    private StringProperty uploadDate;

    public ObservableDocument(int fileId, String fileName, int owner, String uploadDate) {
        this.fileId = new SimpleIntegerProperty();
        this.fileName = new SimpleStringProperty();
        this.owner = new SimpleIntegerProperty();
        this.uploadDate = new SimpleStringProperty();
        setFileId(fileId);
        setFileName(fileName);
        setOwner(owner);
        setUploadDate(uploadDate);
    }

    public void setFileId(int fileId) {
        this.fileId.set(fileId);
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }

    public void setOwner(int owner) {
        this.owner.set(owner);
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate.set(uploadDate);
    }

    public int getFileId() {
        return fileId.get();
    }

    public IntegerProperty fileIdProperty() {
        return fileId;
    }

    public String getFileName() {
        return fileName.get();
    }

    public StringProperty fileNameProperty() {
        return fileName;
    }

    public int getOwner() {
        return owner.get();
    }

    public IntegerProperty ownerProperty() {
        return owner;
    }

    public String getUploadDate() {
        return uploadDate.get();
    }

    public StringProperty uploadDateProperty() {
        return uploadDate;
    }
}



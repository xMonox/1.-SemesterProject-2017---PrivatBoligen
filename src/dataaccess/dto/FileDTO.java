package dataaccess.dto;

import java.sql.Blob;

//Create a File Data Transfer Object used to download and upload files and transfer files between layers.
//Bertram, Jesper
public class FileDTO {
    private int fileId;
    private String fileName;
    private Blob blob;

    public FileDTO() {

    }

    public FileDTO(int fileId, String fileName) {
        this.fileId = fileId;
        this.fileName = fileName;
    }

    public FileDTO(int fileId, String fileName, Blob blob) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.blob = blob;
    }

    public int getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public Blob getBlob() {
        return blob;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }
}

package logic.repositories;

import dataaccess.IDataAccessFacade;
import dataaccess.dto.FileDTO;
import logic.services.FileService;

import java.io.File;

//Used to create File Data Transfer Objects. fileService and dataAccess dependencies are injected so we can get the file from the DB,
//and assemble the file in FileService, for use when the object is created.
public class FileRepository {
    private FileService fileService;
    private IDataAccessFacade dataAccess;

    //Jesper, Bertram
    //dataaccess and fileservice are injected here.
    public FileRepository(IDataAccessFacade dataAccess, FileService fileService) {
        this.dataAccess = dataAccess;
        this.fileService = fileService;
    }

    //Jesper, Bertram
    //Used when saving a file from the database to disc. Creates Data Transfer Object via database query, sends the filestream to FileService
    //Fileservice returns an assembled file for saving via "Download" in the "Faktura" section in the GUI.
    public File saveFileToDisc(int fileId, File destinationFile) {
        FileDTO fileObject = dataAccess.getFile(fileId);
        File downloadedFile = fileService.assembleFile(fileObject, destinationFile);
        return downloadedFile;
    }
}



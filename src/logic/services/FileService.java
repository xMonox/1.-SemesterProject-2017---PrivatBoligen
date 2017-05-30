package logic.services;

import dataaccess.dto.FileDTO;

import java.io.*;
import java.sql.SQLException;

//Used to assemble Files from InputStream to file
public class FileService {

    //Bertram
    //Used to Convert a File to an InputStream. Takes a filepath from the file selected in GUI, creates a file Object, converts
    //it to an InputStream and returns it.
    public InputStream getFileStream(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(file);
        return inputStream;
    }

    //Jesper, Bertram
    //Creates a file from an InputStream and returns it. Takes a FileDTO data transfer object, a destination set in FileChooser, and streams
    // the bytes to the object at that destination.
    public File assembleFile(FileDTO fileDTO, File destination) {
        File file = destination;
        try {
            byte[] fileArray = fileDTO.getBlob().getBytes(1L, (int) fileDTO.getBlob().length());
            try {
                FileOutputStream out = new FileOutputStream(file);
                out.write(fileArray);
                out.close();
            } catch (IOException var8) {
                var8.printStackTrace();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return file;
    }
}

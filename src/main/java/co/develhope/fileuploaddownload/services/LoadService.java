package co.develhope.fileuploaddownload.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class LoadService {

    @Value("${fileRepositoryFolder}")
    private String fileRepositoryFolder;

    public String upload(MultipartFile file) throws IOException {
        String extensionFile = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + "." + extensionFile;
        File fileRepository = new File(fileRepositoryFolder);
        if (!fileRepository.exists()) throw new IOException("FileRepository does not exist");
        if (!fileRepository.isDirectory()) throw new IOException("FileRepository isn't a directory.");

        File finalDestination = new File(fileRepositoryFolder + "\\" + newFileName);
        if(finalDestination.exists()) throw new IOException("File already exists.");

        file.transferTo(finalDestination);
        return newFileName + " uploaded in: " + fileRepository;
    }

    public byte[] download(String fileName) throws IOException {
        File fileToDownload = new File(fileRepositoryFolder + "\\" + fileName);
        if (!fileToDownload.exists()) throw new IOException("File does not exist.");
        return IOUtils.toByteArray(new FileInputStream(fileToDownload));

    }






}

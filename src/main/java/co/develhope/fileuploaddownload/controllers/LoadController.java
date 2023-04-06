package co.develhope.fileuploaddownload.controllers;

import co.develhope.fileuploaddownload.services.LoadService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/file")
public class LoadController {

    @Autowired
    private LoadService loadService;

    @PostMapping("/upload")
    public List<String> upload(@RequestParam MultipartFile[] files) throws IOException, IOException {
        List<String> filesNames = new ArrayList<>();
        for (MultipartFile file: files) {
            String fileName = loadService.upload(file);
            filesNames.add(fileName);
        }
        return filesNames;
    }

    @GetMapping("/download")
    public @ResponseBody byte[] download(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        System.out.println("Downloading " + fileName);
        String extensionFile = FilenameUtils.getExtension(fileName);
        switch (extensionFile) {
            case "jpg", "jpeg" -> response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            case "png" -> response.setContentType(MediaType.IMAGE_PNG_VALUE);
            case "gif" -> response.setContentType(MediaType.IMAGE_GIF_VALUE);
            default -> throw new IOException("Unknown file extensionFile.");
        }
        response.setHeader("Content-Disposition", "attachment; filename = \""+fileName+"\"");
        return loadService.download(fileName);
    }





}

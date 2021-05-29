package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public void saveImage(MultipartFile multipartFile, String fileName) throws IOException {
        if (multipartFile != null && !fileName.equals("")) {
            File targetFile = new File("img_db/" + fileName);
            OutputStream outputStream = new FileOutputStream(targetFile);
            outputStream.write(multipartFile.getBytes());
            outputStream.close();
        }
    }

    @Override
    public boolean deleteImage(String fileName) {
        if (!fileName.equals("")) {
            File targetFile = new File("img_db/" + fileName);
            return targetFile.delete();
        }
        return false;
    }

    @Override
    public Path getImage(String fileName) throws FileNotFoundException {
        if (fileName.equals("")){
            throw new FileNotFoundException("File Not found");
        }
        Path path = Paths.get("img_db/" + fileName);
        if (path == null || !Files.exists(path)){
            throw new FileNotFoundException("File Not found");
        }
        return path;
    }
}

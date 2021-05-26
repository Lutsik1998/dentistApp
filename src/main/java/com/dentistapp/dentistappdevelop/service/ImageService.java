package com.dentistapp.dentistappdevelop.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface ImageService {
    public void saveImage(MultipartFile multipartFile, String fileName) throws IOException;
    public boolean deleteImage(String fileName);
    public Path getImage(String fileName) throws FileNotFoundException;
}

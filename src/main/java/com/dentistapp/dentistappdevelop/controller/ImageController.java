package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.security.jwt.JwtUtils;
import com.dentistapp.dentistappdevelop.service.ImageService;
import com.dentistapp.dentistappdevelop.service.impl.RecipeServiceImpl;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/image/")
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    ImageService imageService;

//    @PostMapping(value = "upload")
//    public ResponseEntity<String> saveImage(@RequestParam(value = "file") MultipartFile multipartFile, @RequestParam(value = "token") String token) throws IOException {
//        if (!jwtUtils.validateJwtToken(token)){
//            return new ResponseEntity("401 - Unauthorized".getBytes(), HttpStatus.UNAUTHORIZED);
//        }
//        String fileName = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().length()-4, multipartFile.getOriginalFilename().length());
//        fileName = new ObjectId().toString() + fileName;
//        File targetFile = new File("img_db/" + fileName);
//        OutputStream outputStream= new FileOutputStream(targetFile);
//        outputStream.write(multipartFile.getBytes());
//        outputStream.close();
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/image/download/")
//                .path(fileName)
//                .toUriString();
//        return new ResponseEntity<>(fileDownloadUri, HttpStatus.OK);
//    }

    @GetMapping(value = "download/{fileName:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName, @RequestParam(value = "token") String token) {
        if (!jwtUtils.validateJwtToken(token)){
            return new ResponseEntity("401 - Unauthorized".getBytes(), HttpStatus.UNAUTHORIZED);
        }
        Resource resource = null;
        try {
            Path path = imageService.getImage(fileName);
            resource = new UrlResource(path.toUri());
            System.out.println("wtf");
        }catch (FileNotFoundException e){
            logger.warn(e.getMessage());
            return new ResponseEntity("File not found", HttpStatus.NOT_FOUND);
        }catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity(resource, headers, HttpStatus.OK);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
    }

}

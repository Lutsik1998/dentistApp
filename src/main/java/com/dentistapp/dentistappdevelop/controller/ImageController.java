package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.security.jwt.JwtUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/image/")
public class ImageController {
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping(value = "upload")
    public ResponseEntity<String> saveImage(@RequestParam(value = "file") MultipartFile multipartFile, @RequestParam(value = "token") String token) throws IOException {
        if (!jwtUtils.validateJwtToken(token)){
            return new ResponseEntity("401 - Unauthorized".getBytes(), HttpStatus.UNAUTHORIZED);
        }
        String fileName = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().length()-4, multipartFile.getOriginalFilename().length());
        fileName = new ObjectId().toString() + fileName;
        File targetFile = new File("img_db/" + fileName);
        OutputStream outputStream= new FileOutputStream(targetFile);
        outputStream.write(multipartFile.getBytes());
        outputStream.close();
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/download/")
                .path(fileName)
                .toUriString();
        return new ResponseEntity<>(fileDownloadUri, HttpStatus.OK);
    }

    @GetMapping(value = "download/{fileName:.+}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName, @RequestParam(value = "token") String token) {
        if (!jwtUtils.validateJwtToken(token)){
            return new ResponseEntity("401 - Unauthorized".getBytes(), HttpStatus.UNAUTHORIZED);
        }
        Path path = Paths.get("img_db/" + fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity(resource, headers, HttpStatus.OK);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
    }

}

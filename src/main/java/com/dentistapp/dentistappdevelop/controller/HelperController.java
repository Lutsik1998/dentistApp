package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.model.Patient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/helper")
public class HelperController {

    @GetMapping(value = "/test")
    public ResponseEntity<?> test() {
        return new ResponseEntity("ok", HttpStatus.OK);
    }

}

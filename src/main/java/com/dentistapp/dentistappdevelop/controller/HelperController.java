package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.model.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/")
public class HelperController {

    @GetMapping(value = "/api/helper/test")
    public ResponseEntity<?> test() {
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    @GetMapping(value = "")
    public RedirectView mainPage() {
        return new RedirectView("/swagger-ui.html");
    }

}

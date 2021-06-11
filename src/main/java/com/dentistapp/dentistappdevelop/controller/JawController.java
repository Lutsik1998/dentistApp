package com.dentistapp.dentistappdevelop.controller;


import com.dentistapp.dentistappdevelop.model.Jaw;
import com.dentistapp.dentistappdevelop.model.Recipe;
import com.dentistapp.dentistappdevelop.model.Tooth;
import com.dentistapp.dentistappdevelop.service.JawService;
import com.dentistapp.dentistappdevelop.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/patient/")
public class JawController {

    @Autowired
    JawService jawService;


    @GetMapping(value = "{patientId}/jaw")
    public ResponseEntity findJawByPatientId(@PathVariable(value = "patientId") String patientId) {
        try {
            Jaw jaw = jawService.findJawByPatientId(patientId);
            if (jaw == null) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(jaw, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }


    @PutMapping(value = "{patientId}/jaw")
    public ResponseEntity<Jaw> updateJaw(@PathVariable(value = "patientId") String patientId, @RequestBody @Valid Jaw jawDetails) {
        try {

            Jaw jaw = jawService.update(patientId, jawDetails);
            if (jaw == null) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Jaw>(jaw, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping(value = "{patientId}/jaw/{toothId}")
    public ResponseEntity<Tooth> updateTooth(@PathVariable(value = "patientId") String patientId,@PathVariable(value = "toothId") String toothId, @RequestBody @Valid Tooth toothDetails) {
        try {

            Tooth tooth = jawService.updateTooth(patientId, toothId, toothDetails);
            if (tooth == null) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Tooth>(tooth, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }


}

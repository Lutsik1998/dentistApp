package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.model.Visit;
import com.dentistapp.dentistappdevelop.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/visit")
public class VisitController {
    @Autowired
    VisitService visitService;

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/save")
    public ResponseEntity<?> saveOffice(@RequestBody @Valid Visit visit) {
        try {
            visit = visitService.save(visit);
            return new ResponseEntity(visit, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (Exception e1) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Visit> updateOffice(@PathVariable(value = "id") String id, @RequestBody @Valid Visit visitDetails) throws ResourceNotFoundException {
        try {
            visitDetails.setId(id);
            Visit visit = visitService.update(visitDetails);
            return new ResponseEntity<Visit>(visit, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        } catch (Exception e1) {
            return new ResponseEntity("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Visit>> getAllOffices() {
        try {
            List<Visit> visitList = visitService.findAll();
            return new ResponseEntity<List<Visit>>(visitList, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        } catch (Exception e1) {
            return new ResponseEntity("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Visit> getOfficeById(@PathVariable String id) {
        try {
            Visit visit = visitService.findById(id);
            return new ResponseEntity<Visit>(visit, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        } catch (Exception e1) {
            return new ResponseEntity("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Visit> deleteOfficeByID(@PathVariable(value = "id") String id) throws ResourceNotFoundException {
        try {
            visitService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity("Patient not found for this id: " + id, HttpStatus.NOT_FOUND);
        } catch (ServerErrorException e1) {
            return new ResponseEntity("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ResponseStatusException e2) {
            return new ResponseEntity(e2.getMessage(), e2.getStatus());
        }
        return new ResponseEntity("Deleted successfully", HttpStatus.OK);

    }

}

package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.model.Office;
import com.dentistapp.dentistappdevelop.service.OfficeService;
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
@RequestMapping("/api/office")
public class OfficeController {
    @Autowired
    OfficeService officeService;

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/save")
    public ResponseEntity<?> saveOffice(@RequestBody @Valid Office office) {
        try {
            office = officeService.save(office);
            return new ResponseEntity(office, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Office> updateOffice(@PathVariable(value = "id") String id, @RequestBody @Valid Office officeDetails) throws ResourceNotFoundException {
        try {
            officeDetails.setId(id);
            Office office = officeService.update(officeDetails);
            return new ResponseEntity<Office>(office, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Office>> getAllOffices() {
        try {
            List<Office> officeList = officeService.findAll();
            return new ResponseEntity<List<Office>>(officeList, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Office> getOfficeById(@PathVariable String id) {
        try {
            Office office = officeService.findById(id);
            return new ResponseEntity<Office>(office, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteOfficeByID(@PathVariable(value = "id") String id) throws ResourceNotFoundException {
        try {
            officeService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity("Patient not found for this id: " + id, HttpStatus.NOT_FOUND);
        } catch (ResponseStatusException e2) {
            return new ResponseEntity<>(e2.getMessage(), e2.getStatus());
        }
        return new ResponseEntity("Deleted successfully", HttpStatus.OK);

    }

}

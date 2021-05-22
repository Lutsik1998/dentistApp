package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.model.Visit;
import com.dentistapp.dentistappdevelop.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/visit")
public class VisitController {
    @Autowired
    VisitService visitService;

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/save")
    public ResponseEntity<?> saveVisit(@RequestBody @Valid Visit visit) {
        try {
            visit = visitService.save(visit);
            return new ResponseEntity(visit, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Visit> updateVisit(@PathVariable(value = "id") String id, @RequestBody @Valid Visit visitDetails) throws ResourceNotFoundException {
        try {
            visitDetails.setId(id);
            Visit visit = visitService.update(visitDetails);
            return new ResponseEntity<Visit>(visit, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Visit>> getAllVisits() {
        try {
            List<Visit> visitList = visitService.findAll();
            return new ResponseEntity<List<Visit>>(visitList, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Visit> getVisitById(@PathVariable String id) {
        try {
            Visit visit = visitService.findById(id);
            return new ResponseEntity<Visit>(visit, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Visit> deleteVisitByID(@PathVariable(value = "id") String id) throws ResourceNotFoundException {
        try {
            visitService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity("Patient not found for this id: " + id, HttpStatus.NOT_FOUND);
        }catch (ResponseStatusException e2) {
            return new ResponseEntity(e2.getMessage(), e2.getStatus());
        }
        return new ResponseEntity("Deleted successfully", HttpStatus.OK);
    }

//    @GetMapping(value = "/visits")
//    public ResponseEntity<List<Visit>> getFreeTimeDates(@RequestParam String doctor, @RequestParam Date startDate, @RequestParam Duration durationDate, @RequestParam Duration duration) {
//        try {
//            List<Visit> visitList = visitService.findAll();
//            return new ResponseEntity<List<Visit>>(visitList, HttpStatus.OK);
//        } catch (ResponseStatusException e) {
//            return new ResponseEntity(e.getMessage(), e.getStatus());
//        }
//    }

    @GetMapping(value = "/freeDays")
    public ResponseEntity<List<LocalDate>> getFreeTimeDates(@RequestParam("doctorId") String doctorId,
                                                          @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                          @RequestParam("durationDate")  long durationDate,
                                                          @RequestParam("durationTime") long durationTime) {
        try {
            List<LocalDate> visitList = visitService.findFreeDatesByDateTimeStartAndDateTimeEndAndDoctorIdTaskDurationSortedByDateStart(startDate, startDate.plusDays(durationDate), durationTime, doctorId);
            return new ResponseEntity<List<LocalDate>>(visitList, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "/filter1")
    public ResponseEntity<List<Visit>> FilterVisits(@RequestParam("doctorId") String doctorId,
                                                            @RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
                                                            @RequestParam("endDateTime")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        try {
            List<Visit> visitList = visitService.findByDateTimeStartAndDateTimeEndAndDoctorIdSortedByDateTimeStart(startDateTime, endDateTime,  doctorId);
            return new ResponseEntity<List<Visit>>(visitList, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

}

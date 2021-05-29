package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.model.Review;
import com.dentistapp.dentistappdevelop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "visit/{visitId}/review")
    public ResponseEntity<?> saveReview(@PathVariable(value = "visitId") String visitId,@RequestBody @Valid Review review) {
        try {
            review = reviewService.save(visitId, review);
            return new ResponseEntity(review, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("visit/{visitId}/review")
    public ResponseEntity<Review> updateReview(@PathVariable(value = "visitId") String visitId, @RequestBody @Valid Review reviewDetails) {
        try {
            Review review = reviewService.update(visitId, reviewDetails);
            return new ResponseEntity<Review>(review, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "doctor/{doctorId}/review/all")
    public ResponseEntity<List<Review>> getAllReviewsByDoctorId(@PathVariable(value = "doctorId") String doctorId) {
        try {
            List<Review> visitList = reviewService.findAllByDoctorId(doctorId);
            return new ResponseEntity<List<Review>>(visitList, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "visit/{visitId}/review", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> getReviewByVisitId(@PathVariable(value = "visitId") String visitId) {
        try {
            Review review = reviewService.findByVisitId(visitId);
            return new ResponseEntity<Review>(review, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "visit/{visitId}/review")
    public ResponseEntity<Review> deleteReviewByVisitId(@PathVariable(value = "visitId") String visitId) {
        try {
            reviewService.deleteByVisitId(visitId);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity("Patient not found for this id: " + visitId, HttpStatus.NOT_FOUND);
        }catch (ResponseStatusException e2) {
            return new ResponseEntity(e2.getMessage(), e2.getStatus());
        }
        return new ResponseEntity("Deleted successfully", HttpStatus.OK);

    }
}

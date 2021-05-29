package com.dentistapp.dentistappdevelop.service;


import com.dentistapp.dentistappdevelop.model.Review;

import java.util.List;

public interface ReviewService {

    public Review save(String visitId, Review review);
    public Review update(String visitId, Review review);
    public List<Review> findAllByDoctorId(String doctorId);
    public Review findByVisitId(String visitId);
    public void deleteByVisitId(String visitId);
}

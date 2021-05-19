package com.dentistapp.dentistappdevelop.service;

import com.dentistapp.dentistappdevelop.model.Review;
import com.dentistapp.dentistappdevelop.model.Visit;
import com.dentistapp.dentistappdevelop.repository.VisitRepository;
import com.dentistapp.dentistappdevelop.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootTest
public class ReviewServiceTest {
//    private Validator validator;
//    @Autowired
//    private ReviewService reviewService;
//    @MockBean
//    private VisitRepository visitRepository;
//    @MockBean
//    private VisitService visitService;
//    @MockBean
//    private MongoTemplate mongoTemplate;
//
//    @BeforeEach
//    public void setup() {
//        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//        validator = validatorFactory.getValidator();
//    }
//
//    @Test
//    public void save() {
//        Visit visit = new Visit();
//        visit = visitRepository.save(visit);
//        Review review = new Review();
//        review.setText("unit_test");
//        review.setRating(3);
//        Review newReview = reviewService.save(visit.getId(), review);
//        Assertions.assertEquals(review, newReview);
//    }
}

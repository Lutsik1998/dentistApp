package com.dentistapp.dentistappdevelop.service;

import com.dentistapp.dentistappdevelop.model.Visit;
import com.dentistapp.dentistappdevelop.repository.VisitRepository;

import java.util.List;

public interface VisitService {
    public VisitRepository getVisitRepository();

    public Visit save(Visit visit);
    public Visit update(Visit visit);
    public Visit findById(String id);
    public List<Visit> findAll();
    public void deleteById(String id);
    public boolean existsOfficeById(String id);
}

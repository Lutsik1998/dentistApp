package com.dentistapp.dentistappdevelop.service;

import com.dentistapp.dentistappdevelop.model.Office;
import com.dentistapp.dentistappdevelop.repository.OfficeRepository;

import java.util.List;

public interface OfficeService {
    public OfficeRepository getOfficeRepository();

    public Office save(Office office);
    public Office update(Office office);
    public List<Office> findAll();
    public Office findById(String id);
    public void deleteById(String id);
    boolean existsOfficeById(String id);
    boolean existsByNIP(int nip);
}

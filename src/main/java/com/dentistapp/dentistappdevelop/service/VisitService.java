package com.dentistapp.dentistappdevelop.service;

import com.dentistapp.dentistappdevelop.model.Visit;
import com.dentistapp.dentistappdevelop.repository.VisitRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface VisitService {
    public VisitRepository getVisitRepository();

    public Visit save(Visit visit);
    public Visit update(Visit visit);
    public Visit findById(String id);
    public List<Visit> findAll();
    public void deleteById(String id);
    public boolean existsById(String id);
    public List<LocalDate> findFreeDatesByDateTimeStartAndDateTimeEndAndDoctorIdTaskDurationSortedByDateStart(LocalDate dateStart, LocalDate dateEnd, long taskDuration, String doctorId);
    public List<Visit> findByDateTimeStartAndDateTimeEndAndDoctorIdSortedByDateTimeStart(LocalDateTime dateTimeStart,LocalDateTime dateTimeEnd, String doctorId);
    public boolean existsInIntervalByDateTimeStartAndDateTimeEndAndDoctorId(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String doctorId);
}

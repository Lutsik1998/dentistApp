package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.Visit;
import com.dentistapp.dentistappdevelop.repository.VisitRepository;
import com.dentistapp.dentistappdevelop.service.DoctorService;
import com.dentistapp.dentistappdevelop.service.OfficeService;
import com.dentistapp.dentistappdevelop.service.PatientService;
import com.dentistapp.dentistappdevelop.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {
    ZoneOffset zoneOffset = ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now());
    @Autowired
    VisitRepository visitRepository;
    @Autowired
    DoctorService doctorService;
    @Autowired
    PatientService patientService;
    @Autowired
    OfficeService officeService;

    @Override
    public VisitRepository getVisitRepository() {
        return visitRepository;
    }

    @Override
    public Visit save(Visit visit) {
        if (visit == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad data");
        }
        if (!doctorService.existsById(visit.getDoctorId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong doctor id \"" + visit.getDoctorId() + "\"");
        }
        if (!officeService.existsOfficeById(visit.getOfficeId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong office id \"" + visit.getOfficeId() + "\"");
        }
        if (!patientService.existsById(visit.getPatientId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong patient id \"" + visit.getPatientId() + "\"");
        }
        if (visit.getDateTimeStart().getHour() < 8 || visit.getDateTimeEnd().getHour() >= 20
                || visit.getDateTimeEnd().toEpochSecond(zoneOffset) - visit.getDateTimeStart().toEpochSecond(zoneOffset) < 60){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Time");
        }
        if (this.existsInIntervalByDateTimeStartAndDateTimeEndAndDoctorId(visit.getDateTimeStart(), visit.getDateTimeEnd(), visit.getDoctorId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This time is already taken");
        }
        return visitRepository.save(visit);
    }

    @Override
    public Visit update(Visit visit) {
        if (visit == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad data");
        }
        if (!existsById(visit.getId())) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Visit with id \"" + visit.getId() + "\" not found");
        }
        return this.save(visit);
    }

    @Override
    public Visit findById(String id) {
        if (id == null || id.equals("") || id.length() != 24) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad id");
        }
        Optional<Visit> optionalOffice = visitRepository.findById(id);
        if (optionalOffice.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Office whit id \"" + id + "\" not found");
        }
        return optionalOffice.get();
    }

    @Override
    public List<Visit> findAll() {
        List<Visit> visitList = visitRepository.findAll();
        if (visitList == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return visitList;
    }

    @Override
    public void deleteById(String id) {
        if (id == null || id.equals("") || id.length() != 24) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad id");
        }
        visitRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        if (id == null || id.equals("") || id.length() != 24) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Visit id:" + id);
        }
        return visitRepository.existsById(id);
    }

    @Override
    public List<LocalDate> findFreeDatesByDateTimeStartAndDateTimeEndAndDoctorIdTaskDurationSortedByDateStart(LocalDate dateStart, LocalDate dateEnd, long taskDuration, String doctorId) {
        taskDuration = taskDuration * 60;
        long timeDayStart = dateStart.toEpochSecond(LocalTime.of(8, 0),zoneOffset ); // change time start
        long timeDayEnd = dateStart.toEpochSecond(LocalTime.of(20, 0), zoneOffset); // change time start
        long currentTimeEnd = dateStart.toEpochSecond(LocalTime.of(8, 0), zoneOffset);
        if (taskDuration > timeDayEnd - timeDayStart) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Task Duration");
        }
        List<Visit> visits = visitRepository.findByDateTimeStartAndDateTimeEndAndDoctorIdSortedByDateTimeStart(dateStart.atStartOfDay(), dateEnd.atStartOfDay(), doctorId);
        Set<LocalDate> dateList = new HashSet<>();
//        List<LocalDate> dateList = dateStart.datesUntil(dateEnd).collect(Collectors.toList());
        for (Visit visit : visits) {
            while (visit.getDateTimeStart().toEpochSecond(zoneOffset) > timeDayEnd) {
                if (currentTimeEnd + taskDuration < timeDayEnd) {
                    System.out.println(LocalDateTime.ofInstant(Instant.ofEpochSecond(currentTimeEnd), ZoneId.systemDefault()).toLocalDate());
            System.out.println("if  w");
                    dateList.add(LocalDateTime.ofInstant(Instant.ofEpochSecond(currentTimeEnd), ZoneId.systemDefault()).toLocalDate());
                }
                timeDayStart += 86400;
                timeDayEnd += 86400;
                currentTimeEnd = timeDayStart;
            }
            if (currentTimeEnd + taskDuration > visit.getDateTimeStart().toEpochSecond(zoneOffset) || currentTimeEnd + taskDuration > timeDayEnd) {
                currentTimeEnd = visit.getDateTimeEnd().toEpochSecond(zoneOffset);
                continue;
            }
            if (currentTimeEnd + taskDuration < visit.getDateTimeStart().toEpochSecond(zoneOffset)) {
            System.out.println("if");
                    System.out.println(LocalDateTime.ofInstant(Instant.ofEpochSecond(currentTimeEnd), ZoneId.systemDefault()).toLocalDate());
                dateList.add(LocalDateTime.ofInstant(Instant.ofEpochSecond(currentTimeEnd), ZoneId.systemDefault()).toLocalDate());
                currentTimeEnd = visit.getDateTimeEnd().toEpochSecond(zoneOffset);
            }
        }
        while (dateEnd.toEpochSecond(LocalTime.of(20, 0), OffsetTime.now().getOffset()) > timeDayEnd) {
            if (currentTimeEnd + taskDuration < timeDayEnd) {
                System.out.println("w");
                System.out.println(LocalDateTime.ofInstant(Instant.ofEpochSecond(currentTimeEnd), ZoneId.systemDefault()).toLocalDate());
                dateList.add(LocalDateTime.ofInstant(Instant.ofEpochSecond(currentTimeEnd), ZoneId.systemDefault()).toLocalDate());
            }
            timeDayStart += 86400;
            timeDayEnd += 86400;
            currentTimeEnd = timeDayStart;
        }
        return new ArrayList<>(dateList);
    }

    @Override
    public List<Visit> findByDateTimeStartAndDateTimeEndAndDoctorIdSortedByDateTimeStart(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String doctorId) {
        return visitRepository.findByDateTimeStartAndDateTimeEndAndDoctorIdSortedByDateTimeStart(dateTimeStart, dateTimeEnd, doctorId);
    }

    @Override
    public boolean existsInIntervalByDateTimeStartAndDateTimeEndAndDoctorId(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String doctorId) {
        return visitRepository.existsByDateTimeStartAndDateTimeEndAndDoctorId(dateTimeStart, dateTimeEnd, doctorId);
    }


}

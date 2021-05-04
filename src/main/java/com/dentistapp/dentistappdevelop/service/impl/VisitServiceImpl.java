package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.Visit;
import com.dentistapp.dentistappdevelop.repository.VisitRepository;
import com.dentistapp.dentistappdevelop.service.DoctorService;
import com.dentistapp.dentistappdevelop.service.OfficeService;
import com.dentistapp.dentistappdevelop.service.PatientService;
import com.dentistapp.dentistappdevelop.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class VisitServiceImpl implements VisitService {
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
        return visitRepository.save(visit);
    }

    @Override
    public Visit update(Visit visit) {
        if (visit == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad data");
        }
        if (!existsById(visit.getId())) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Bad id");
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
        return visitRepository.save(visit);
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad id");
        }
        return visitRepository.existsById(id);
    }
}

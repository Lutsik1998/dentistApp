package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.Office;
import com.dentistapp.dentistappdevelop.repository.OfficeRepository;
import com.dentistapp.dentistappdevelop.service.DoctorService;
import com.dentistapp.dentistappdevelop.service.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeServiceImpl implements OfficeService {
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    DoctorService doctorService;

    @Override
    public OfficeRepository getOfficeRepository() {
        return officeRepository;
    }

    @Override
    public Office save(Office office) {
        if (office == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad data");
        }
        if (existsByNIP(office.getNIP())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Office with NIP:" + Integer.toString(office.getNIP()) + " is already exists");
        }
        if (office.getListDoctorsId() != null) {
            for (String doctorId : office.getListDoctorsId()) {
                if (!doctorService.existsById(doctorId)) {
                    office.getListDoctorsId().remove(doctorId);
                }
            }
        }
        return officeRepository.save(office);
    }

    @Override
    public Office update(Office office) {
        if (office == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad data");
        }
        Office oldOffice = findById(office.getId());
        if (oldOffice == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Office with id \"" + office.getId() + "\" not found");
        }
        if (office.getNIP() != oldOffice.getNIP()){
            if (existsByNIP(office.getNIP())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Office with NIP:" + Integer.toString(office.getNIP()) + " is already exists");
            }
        }
        if (office.getListDoctorsId() != null) {
            for (String doctorId : office.getListDoctorsId()) {
                if (!doctorService.existsById(doctorId)) {
                    office.getListDoctorsId().remove(doctorId);
                }
            }
        }
        return officeRepository.save(office);
    }

    @Override
    public List<Office> findAll() {
        List<Office> officeList = officeRepository.findAll();
        if (officeList == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return officeList;
    }

    @Override
    public Office findById(String id) {
        if (id == null || id.equals("") || id.length() != 24) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad id");
        }
        Optional<Office> optionalOffice = officeRepository.findById(id);
        if (optionalOffice.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Office whit id \"" + id + "\" not found");
        }
        return optionalOffice.get();
    }

    @Override
    public void deleteById(String id) {
        if (id == null || id.equals("") || id.length() != 24) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad id");
        }
        officeRepository.deleteById(id);
    }

    @Override
    public boolean existsOfficeById(String id) {
        if (id == null || id.equals("") || id.length() != 24) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong office id \"" + id + "\"");
        }
        return officeRepository.existsOfficeById(id);
    }

    @Override
    public boolean existsByNIP(int nip) {
        return officeRepository.existsByNIP(nip);
    }
}

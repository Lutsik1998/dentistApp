package com.dentistapp.dentistappdevelop.service;


import com.dentistapp.dentistappdevelop.model.Jaw;
import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.model.Recipe;
import com.dentistapp.dentistappdevelop.model.Tooth;

import java.util.List;


public interface JawService {

    public Jaw findJawByPatientId(String patientId);
    public Jaw update(String patientId, Jaw jaw);
    public Tooth updateTooth(String patientId, String toothId, Tooth tooth);

}

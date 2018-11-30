package malaria.com.malaria.services;

import malaria.com.malaria.interfaces.IMalariaKBSService;
import malaria.com.malaria.models.Analysis;
import malaria.com.malaria.models.Microscopist;
import malaria.com.malaria.models.Nurse;
import malaria.com.malaria.models.Patient;
import malaria.com.malaria.models.Test;

public class MalariaKBSService implements IMalariaKBSService {
    @Override
    public Test createTest(Nurse nurse, Microscopist microscopist, Patient patient) {
        return null;
    }

    @Override
    public Test getTest() {
        return null;
    }

    @Override
    public Analysis getAnalysis() {
        return null;
    }
}

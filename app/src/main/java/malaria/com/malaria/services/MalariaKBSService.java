package malaria.com.malaria.services;

import malaria.com.malaria.interfaces.IMalariaKBSService;
import malaria.com.malaria.models.Analysis;
import malaria.com.malaria.models.Microscopist;
import malaria.com.malaria.models.Nurse;
import malaria.com.malaria.models.Patient;
import malaria.com.malaria.models.Result;
import malaria.com.malaria.models.Test;

public class MalariaKBSService implements IMalariaKBSService {
    private Test t;

    @Override
    public Test createTest(Nurse nurse, Microscopist microscopist, Patient patient) {
        Test t = new Test();
        t.setNurse(nurse);
        t.setMicroscopist(microscopist);
        t.setPatient(patient);
        this.t = t;
        return t;
    }

    @Override
    public Test getTest() {
        return t;
    }

    @Override
    public Analysis getAnalysis() {
        return this.getTest().getAnalysis();
    }

    @Override
    public Result getResult() {
        return this.getAnalysis().getResult();
    }
}

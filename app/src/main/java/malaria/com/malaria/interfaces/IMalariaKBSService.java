package malaria.com.malaria.interfaces;

import malaria.com.malaria.models.Analysis;
import malaria.com.malaria.models.Microscopist;
import malaria.com.malaria.models.Nurse;
import malaria.com.malaria.models.Patient;
import malaria.com.malaria.models.Test;

public interface IMalariaKBSService {


    Test createTest(Nurse nurse, Microscopist microscopist, Patient patient);

    Test getTest();

    Analysis getAnalysis();


}

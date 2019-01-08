package malaria.com.malaria.interfaces;

import malaria.com.malaria.models.Analysis;
import malaria.com.malaria.models.Result;
import malaria.com.malaria.models.Test;

public interface IMalariaKBSService {


    Test createTest();

    Test getTest();

    Analysis getAnalysis();


    Result getResult();
}

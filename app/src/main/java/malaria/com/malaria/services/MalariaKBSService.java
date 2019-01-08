package malaria.com.malaria.services;

import malaria.com.malaria.interfaces.IMalariaKBSService;
import malaria.com.malaria.models.Analysis;
import malaria.com.malaria.models.Result;
import malaria.com.malaria.models.Test;

public class MalariaKBSService implements IMalariaKBSService {
    private Test t;

    @Override
    public Test createTest() {
        Test t = new Test();
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

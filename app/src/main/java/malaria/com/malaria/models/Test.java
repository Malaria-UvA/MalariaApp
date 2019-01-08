package malaria.com.malaria.models;

import java.util.Date;

/**
 *
 */
public class Test {

    private Analysis analysis;
    /**
     *
     */
    private Date date;

    /**
     * Default constructor
     */
    public Test() {
        this.date = new Date();
        this.analysis = new Analysis();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }
}
package malaria.com.malaria.models;

import java.util.Date;

/**
 *
 */
public class Test {
    private Nurse nurse;
    private Microscopist microscopist;
    private Patient patient;

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

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Microscopist getMicroscopist() {
        return microscopist;
    }

    public void setMicroscopist(Microscopist microscopist) {
        this.microscopist = microscopist;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }
}
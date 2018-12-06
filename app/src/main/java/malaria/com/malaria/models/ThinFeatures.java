package malaria.com.malaria.models;

import java.util.List;

import malaria.com.malaria.errors.InvalidFeatureValuesException;

/**
 *
 */
public class ThinFeatures extends Features {

    /**
     *
     */
    private int n_redBloodCells;
    /**
     *
     */
    private int n_infected_rbc;

    /**
     * Default constructor
     */
    public ThinFeatures(int rbc, int irbc) {
        if(rbc < irbc){
            throw new InvalidFeatureValuesException("The number of rbc should be equal or higher than the infected rbc");
        }

        this.n_redBloodCells = rbc;
        this.n_infected_rbc = irbc;
    }

    public int getN_redBloodCells() {
        return n_redBloodCells;
    }

    public void setN_redBloodCells(int n_redBloodCells) {
        this.n_redBloodCells = n_redBloodCells;
    }

    public int getN_infected_rbc() {
        return n_infected_rbc;
    }

    public void setN_infected_rbc(int n_infected_rbc) {
        this.n_infected_rbc = n_infected_rbc;
    }

    /**
     * This method is part of the extracted knowledge. It returns 'true'
     * if it needs to stop counting, 'false' otherwise.
     *
     * @param fs features that have been added to the system
     * @return boolean
     */
    static boolean stopConditionMet(List<Features> fs) {
        return fs.size() == 20;
    }

    /**
     * This method is part of the extracted knowledge. It calculates the parasites
     * per microlitre of blood using the formula for the Thin film.
     *
     * @param nInfRBC number of infected red blood cells
     * @param nRBC number of total red blood cells
     * @return int
     */
    static int calculate(int nInfRBC, int nRBC) {
        return (int) Math.ceil(5_000_000f * nInfRBC / nRBC);
    }
}
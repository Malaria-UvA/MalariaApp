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
     * Rule instance 3 under Rule type: Analysis-influence
     * @param fs
     * @return
     */
    public static boolean stopConditionMet(List<Features> fs) {
        return fs.size() == 20;
    }

    /**
     * Rule instance 3 under Rule type: Analysis-influence
     * @param nInfRBC
     * @param nRBC
     * @return
     */
    public static int calculate(int nInfRBC, int nRBC) {
        return (int) Math.ceil(5_000_000f * nInfRBC / nRBC);
    }
}
package malaria.com.malaria.models;

import java.util.List;

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

    public static boolean stopConditionMet(List<Features> fs) {
        return fs.size() == 20;
    }
}
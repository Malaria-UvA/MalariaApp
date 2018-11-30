package malaria.com.malaria.models;

import java.util.List;

/**
 *
 */
public class ThickFeatures extends Features {

    /**
     *
     */
    private int n_whiteBloodCells;
    /**
     *
     */
    private int n_parasites;

    /**
     * Default constructor
     */
    public ThickFeatures(int wbc, int parasites) {
        this.n_whiteBloodCells = wbc;
        this.n_parasites = parasites;
    }

    public int getN_whiteBloodCells() {
        return n_whiteBloodCells;
    }

    public void setN_whiteBloodCells(int n_whiteBloodCells) {
        this.n_whiteBloodCells = n_whiteBloodCells;
    }

    public int getN_parasites() {
        return n_parasites;
    }

    public void setN_parasites(int n_parasites) {
        this.n_parasites = n_parasites;
    }

    public static boolean stopConditionMet(List<Features> fs) {
        int nParasitesTotal = 0;
        int nWBC = 0;
        for (Features f : fs) {
            ThickFeatures tf = (ThickFeatures) f;

            nParasitesTotal += tf.getN_parasites();
            nWBC += tf.getN_whiteBloodCells();

        }
        return nParasitesTotal >= 100 && nWBC <= 200 || nParasitesTotal <= 99 && nWBC <= 500;
    }
}
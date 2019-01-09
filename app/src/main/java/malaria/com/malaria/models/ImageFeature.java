package malaria.com.malaria.models;

import android.media.Image;

import java.util.List;

/**
 *
 */
public class ImageFeature {

    /**
     *
     */
    private int nWhiteBloodCells;
    /**
     *
     */
    private int nParasites;

    /**
     * Default constructor
     */
    public ImageFeature(int wbc, int parasites) {
        this.nWhiteBloodCells = wbc;
        this.nParasites = parasites;
    }

    public int getnWhiteBloodCells() {
        return nWhiteBloodCells;
    }

    public void setnWhiteBloodCells(int nWhiteBloodCells) {
        this.nWhiteBloodCells = nWhiteBloodCells;
    }

    public int getnParasites() {
        return nParasites;
    }

    public void setnParasites(int nParasites) {
        this.nParasites = nParasites;
    }

    /**
     * This method is part of the extracted knowledge. It returns 'true'
     * if it needs to stop counting, 'false' otherwise.
     *
     * @param fs features that have been added to the system
     * @return boolean
     */
    static boolean stopConditionMet(List<ImageFeature> fs) {
        int nParasitesTotal = 0;
        int nWBC = 0;
        for (ImageFeature tf : fs) {
            nParasitesTotal += tf.getnParasites();
            nWBC += tf.getnWhiteBloodCells();
        }
        return nParasitesTotal >= 100 && nWBC >= 200 || nParasitesTotal <= 99 && nWBC >= 500;
    }

    /**
     * This method is part of the extracted knowledge. It calculates the parasites
     * per microlitre of blood using the formula for the Thick film.
     *
     * @param nParasitesTotal number of total parasites
     * @param nWBC number of total white blood cells
     * @return int
     */
    static int calculate(int nParasitesTotal, int nWBC) {
        return (int) Math.ceil(8000f * nParasitesTotal / nWBC);
    }
}
package malaria.com.malaria.models;

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
    public ThickFeatures() {
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
}
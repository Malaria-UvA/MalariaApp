package malaria.com.malaria.models;

import java.util.ArrayList;
import java.util.List;

import malaria.com.malaria.errors.InvalidTypeException;
import malaria.com.malaria.errors.NotEnoughFeatureException;

/**
 *
 */
public class Analysis {
    private List<Features> featuresList;
    private Result result;
    /**
     *
     */
    private boolean performed;
    /**
     *
     */
    private TypeEnum type;

    /**
     * Default constructor
     */
    public Analysis() {
        this.featuresList = new ArrayList<>();
        this.type = TypeEnum.THICK;
    }

    public boolean isPerformed() {
        return performed;
    }

    public void setPerformed(boolean performed) {
        this.performed = performed;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;

        this.featuresList.clear();
        this.result = null;
    }

    public Result getResult() {
        return result;
    }

    public void addFeature(Features f) {
        if (
                (f instanceof ThickFeatures && this.type != TypeEnum.THICK)
                        ||
                        (f instanceof ThinFeatures && this.type != TypeEnum.THIN)
                ) {
            throw new InvalidTypeException("Type does not match");
        }
        this.featuresList.add(f);
    }

    public boolean stopConditionMet() {
        return this.type == TypeEnum.THICK ? ThickFeatures.stopConditionMet(this.featuresList) : ThinFeatures.stopConditionMet(this.featuresList);
    }

    public boolean thickAnalysisValid() {
        if (this.type == TypeEnum.THICK) {
            int nParasitesTotal = 0;
            int nWBC = 0;
            for (Features f : this.featuresList) {
                ThickFeatures tf = (ThickFeatures) f;

                nParasitesTotal += tf.getN_parasites();
                if (nParasitesTotal >= 100) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    public void calculateResult() {
        if (this.featuresList.isEmpty()) {
            throw new NotEnoughFeatureException("At least one feature is necessary");
        }
        if (type == TypeEnum.THICK) {
            int nParasitesTotal = 0;
            int nWBC = 0;
            for (Features f : this.featuresList) {
                ThickFeatures tf = (ThickFeatures) f;

                nParasitesTotal += tf.getN_parasites();
                nWBC += tf.getN_whiteBloodCells();
            }

            Result r = new Result();
            r.setParasites_per_microlitre(8000 * nParasitesTotal / nWBC);
            this.result = r;
        } else if (type == TypeEnum.THIN) {
            if (this.featuresList.size() < 20) {
                throw new NotEnoughFeatureException("At least 20 features are needed for the thin analysis.");
            }
            int nInfRBC = 0;
            int nRBC = 0;
            for (Features f : this.featuresList) {
                ThinFeatures tf = (ThinFeatures) f;

                nInfRBC += tf.getN_infected_rbc();
                nRBC += tf.getN_redBloodCells();
            }

            Result r = new Result();
            r.setParasites_per_microlitre(5_000_000 * nInfRBC / nRBC);
            this.result = r;
        } else {
            throw new InvalidTypeException("Type is unknown");
        }
    }
    public int getNumberOfFeatures(){
        return this.featuresList.size();
    }

    public List<Features> getFeaturesList() {
        return featuresList;
    }

    /**
     *
     */
    public enum TypeEnum {
        THICK,
        THIN
    }
}
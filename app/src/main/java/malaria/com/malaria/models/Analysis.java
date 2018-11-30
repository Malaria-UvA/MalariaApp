package malaria.com.malaria.models;

/**
 *
 */
public class Analysis {

    /**
     *
     */
    private boolean performed;
    /**
     *
     */
    private Test.TypeEnum type;

    /**
     * Default constructor
     */
    public Analysis() {
    }

    public boolean isPerformed() {
        return performed;
    }

    public void setPerformed(boolean performed) {
        this.performed = performed;
    }

    public Test.TypeEnum getType() {
        return type;
    }

    public void setType(Test.TypeEnum type) {
        this.type = type;
    }
}
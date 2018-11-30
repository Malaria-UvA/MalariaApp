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
    private TypeEnum type;

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

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    /**
     *
     */
    public enum TypeEnum {
        THICK,
        THIN
    }
}
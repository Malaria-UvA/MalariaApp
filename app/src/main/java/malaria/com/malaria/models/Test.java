package malaria.com.malaria.models;

import java.util.Date;

/**
 *
 */
public class Test {

    /**
     *
     */
    private Date date;

    /**
     * Default constructor
     */
    public Test() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     */
    public enum TypeEnum {
        THICK,
        THIN
    }
}
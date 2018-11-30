package malaria.com.malaria.models;


/**
 *
 */
public abstract class Person {

    /**
     *
     */
    protected String name;

    /**
     * Default constructor
     */
    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
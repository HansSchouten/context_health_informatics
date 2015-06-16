package analyze.pattern;

/**
 * This class represents an object that contains within how many days the next label should appear.
 * @author Hans Schouten
 *
 */
public class Within {

    /**
     * Number of days within the next label should appear.
     */
    protected int numberOfDays;

    /**
     * Within constructor.
     * @param number        - The number of days.
     */
    public Within(int number) {
        this.numberOfDays = number;
    }

    /**
     * Return the number of days within the next label should appear.
     * @return              - The number of days.
     */
    public int getNumberOfDays() {
        return this.numberOfDays;
    }

}

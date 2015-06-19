package analyze.pattern;

/**
 * This class represents an object that contains after how many days the next label should appear.
 * @author Hans Schouten
 *
 */
public class After {

    /**
     * Number of days after which the next label should appear.
     */
    protected int numberOfDays;

    /**
     * After constructor.
     * @param number        - The number of days.
     */
    public After(int number) {
        this.numberOfDays = number;
    }

    /**
     * Return the number of days after which the next label should appear.
     * @return              - The number of days.
     */
    public int getNumberOfDays() {
        return this.numberOfDays;
    }

}

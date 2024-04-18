package model;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Aarsh Patel
 */
public class ElectiveCategory {
    private String type;
    private int creditsRequired;
    private HashMap<Course, Integer> courseChoices;

    public ElectiveCategory(String type, int creditsRequired, HashMap<Course, Integer> courseChoices) {
        setType(type);
        setCreditRequired(creditsRequired);
        setCourseChoices(courseChoices);
    }

    // ----- Accessor -----
    public HashMap<Course, Integer> getCourseChoices() {
        return this.courseChoices;
    }

    public String getType() {
        return this.type;
    }

    public int getCreditsRequired() {
        return this.creditsRequired;
    }

    // ----- Mutator -----
    public void setCourseChoices(HashMap<Course, Integer> courses) {
        this.courseChoices = courses;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCreditRequired(int credit) {
        this.creditsRequired = credit;
    }

    public String toString() {
        StringBuilder retString = new StringBuilder();
        retString.append(this.type + ": ");
        for (Map.Entry<Course, Integer> choice : courseChoices.entrySet()) {
            retString.append(choice.getKey() + ", ");
        }
        return retString.toString();
    }
}

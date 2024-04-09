package model;
import java.util.ArrayList;

public class Prerequisites {
    private int choices;
    private String minGrade;
    private ArrayList<Course> courseOptions;

    public Prerequisites() {
        setChoices(0);
        setMinGrade("F");
        setCourseOptions(null);
    }

    public Prerequisites(int choices, String minGrade, ArrayList<Course> courseOptions) {
        setChoices(choices);
        setMinGrade(minGrade);
        setCourseOptions(courseOptions);
    }

    // ----- Accessor -----
    public int getChoices() { return this.choices; }
    public String getMinGrade() { return this.minGrade; }
    public ArrayList<Course> getCourseOptions() { return this.courseOptions; }

    // ----- Mutator -----
    public void setChoices(int choices) {
        this.choices = choices;
    }

    public void setMinGrade(String minGrade) {
        this.minGrade = minGrade;
    }

    public void setCourseOptions(ArrayList<Course> courseOptions) {
        if (courseOptions == null) {
            this.courseOptions = new ArrayList<Course>();
            return;
        }
        this.courseOptions = courseOptions;
    }

    // ----- Method -----
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\n-- Min Grade: " + this.minGrade);
        result.append("\n-- Course Options: ");
        if(this.courseOptions != null) {
            for(int i = 0; i < this.courseOptions.size(); i++) {
                Course course = this.courseOptions.get(i);
                result.append(course.toStringCourseAbbr());
                result.append(i != this.courseOptions.size() - 1 ? " or " : "\n");
            }
        }
        return result.toString();
    }
}

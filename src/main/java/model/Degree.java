package model;
import java.util.*;
/**
 * 
 * @author Aarsh Patel
 */
public class Degree {
    private UUID id;
    private String degreeType;
    private String subjectName;
    private int totalCreditRequired;
    private HashMap<Course, Integer> majorCourses;
    private ArrayList<ElectiveCategory> electiveList;

    public Degree() {
        this.id = UUID.randomUUID();
        this.degreeType = "Bachelor of Undeclared";
        this.subjectName = "Undeclared";
        this.totalCreditRequired = 100;
        this.majorCourses = new HashMap<>();
        this.electiveList = new ArrayList<>();
    }

    public Degree(String degreeType, String subjectName, int totalCreditRequired, 
        HashMap<Course, Integer> majorCourses, ArrayList<ElectiveCategory> electiveList) 
    {
        this.id = UUID.randomUUID();
        setSubject(subjectName);
        setDegreeType(degreeType);
        setTotalCreditRequired(totalCreditRequired);
        setMajorCourses(majorCourses);
        setElectiveList(electiveList);
    }

    public Degree(UUID id, String degreeType, String subjectName, int totalCreditRequired, 
    HashMap<Course, Integer> majorCourses, ArrayList<ElectiveCategory> electiveList) 
    {
        this.id = id;
        setSubject(subjectName);
        setDegreeType(degreeType);
        setTotalCreditRequired(totalCreditRequired);
        setMajorCourses(majorCourses);
        setElectiveList(electiveList);
    }

    // ----- Accessor -----
    public UUID getID() {
        return this.id;
    }

    public String getSubject() {
        return this.subjectName;
    }

    public String getDegreeType() {
        return this.degreeType;
    }
   
    public int getTotalCreditRequired() {
        return this.totalCreditRequired;
    }

    public HashMap<Course, Integer> getMajorCourses() {
        return this.majorCourses;
    }

    public ArrayList<ElectiveCategory> getElectiveList() {
        return this.electiveList;
    }

    // ----- Mutator -----
    public void setDegreeType(String type) {
        this.degreeType = type;
    }

    public void setSubject(String subject) {
        this.subjectName = subject;
    }
   
    public void setTotalCreditRequired(int credit) {
        this.totalCreditRequired = credit;
    }

    public void setMajorCourses(HashMap<Course, Integer> courses) {
        if (courses == null) {
            this.majorCourses = new HashMap<Course, Integer>();
            return;
        }
        this.majorCourses = courses;
    }

    public void setElectiveList(ArrayList<ElectiveCategory> electiveList) {
        this.electiveList = electiveList;
    }


    public boolean addMajorCourse(Course course, int preferredSemester) {
        if (course != null && preferredSemester > 0 && preferredSemester < 9) {
            majorCourses.put(course, preferredSemester);
            return true;
        }
        return false;
    }

    public boolean removeMajorCourse(Course course) {
        if (course != null) {
            majorCourses.remove(course);
            return true;
        }
        return false;
    }
    
    public boolean equals(Degree degree) {
        return this == degree;
    }

    public String toString() {
        StringBuilder retString = new StringBuilder();
        retString.append("Degree: " + this.degreeType + " in " + this.subjectName + "\n");
        retString.append("Total Credit Required: " + this.totalCreditRequired + "\n");
        retString.append("Major Courses\n");
        for (Map.Entry<Course, Integer> entry : majorCourses.entrySet()) {
            retString.append(entry.toString()+ "\n");
        }
        
        for (ElectiveCategory element : electiveList) {
            retString.append("\n" + element.getType() + "\n");
            retString.append("Total Credits Required: " + element.getCreditsRequired() + "\n");
            retString.append(element + "\n");
        }
        return retString.toString();
    }
    // public static void main(String[] args) {
    //     String type = "Bachelors";
    //     String name = "Computer Science";
    //     int totalCredits = 125;
    //     HashMap<Course, Integer> courses = new HashMap<>();
    //     courses.put(new Course("Vector Calculus", "MATH", "241", "", 3, new ArrayList<Season>(), new ArrayList<Prerequisites>()), 1);
    //     ArrayList<ElectiveCategory> electives = new ArrayList<>();
    //     electives.add(new ElectiveCategory("Application Area", 16, courses));
    //     Degree testDegree = new Degree(type, name, totalCredits, courses, electives);
    //     System.out.println(testDegree);
    // }
}

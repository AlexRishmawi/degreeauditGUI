package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Student extends User {
    private ClassLevel classification;
    private Advisor advisor;
    private ArrayList<String> notes;
    private Degree degree;
    private double instituteGPA;
    private double programGPA;
    private String status;
    private Semester currentSemester;
    private ArrayList<Semester> allSemester; // All Completed Semester
    private HashMap<Course, String> completeCourses; // only for completed course with graded
    private ArrayList<Semester> semestersPlan = new ArrayList<>(); // Dummy 8 semester plan
    private String studentID;


    public Student(String firstName, String lastName, String email, String password, String studentID) 
    {
        super(firstName, lastName, email, password);
        super.setUserType(UserType.STUDENT);
        classification = ClassLevel.FRESHMAN;
        advisor = null;
        this.notes = new ArrayList<>();
        this.degree = new Degree();
        this.instituteGPA = 4.0;
        this.programGPA = 4.0;
        this.status = "Pending";
        this.currentSemester = null;
        this.allSemester = new ArrayList<>();
        this.completeCourses = new HashMap<>();
        this.studentID = studentID;
    }

    public Student(String firstName, String lastName, String email, String password, String studentID,
        String level, Advisor advisor, ArrayList<String> notes, Degree degree,
        double instituteGPA, double programGPA, String status, HashMap<Course, String> completeCourses,
        Semester currentSemester, ArrayList<Semester> allSemester) 
    {
        super(firstName, lastName, email, password);
        super.setUserType(UserType.STUDENT);
        setLevel(level);
        setAdvisor(advisor);
        setNotes(notes);
        setDegree(degree);
        setInstituteGPA(instituteGPA);
        setProgramGPA(programGPA);
        setStatus(status);
        setCurrentSemester(currentSemester);
        setAllSemester(allSemester);
        setCompleteCourses(completeCourses);
        this.studentID = studentID;
        initializeSemesterPlan();
    }

    public Student(UUID id, String firstName, String lastName, String email, String password, String studentID,
            String level, Advisor advisor, ArrayList<String> notes, Degree degree,
            double instituteGPA, double programGPA, String status, HashMap<Course, String> completeCourses,
            Semester currentSemester, ArrayList<Semester> allSemester) 
    {
        super(id, firstName, lastName, email, password);
        super.setUserType(UserType.STUDENT);
        setLevel(level);
        setAdvisor(advisor);
        setNotes(notes);
        setDegree(degree);
        setInstituteGPA(instituteGPA);
        setProgramGPA(programGPA);
        setStatus(status);
        setCurrentSemester(currentSemester);
        setAllSemester(allSemester);
        setCompleteCourses(completeCourses);
        this.studentID = studentID;
        initializeSemesterPlan();
    }

    // ----- Mutator -----
    public void setLevel(String level) {
        if (level.equalsIgnoreCase("sophomore")) {
            this.classification = ClassLevel.SOPHOMORE;
        } else if (level.equalsIgnoreCase("junior")) {
            this.classification = ClassLevel.JUNIOR;
        } else if (level.equalsIgnoreCase("senior")) {
            this.classification = ClassLevel.SENIOR;
        } else {
            this.classification = ClassLevel.FRESHMAN;
        }
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public void setInstituteGPA(double gpa) {
        this.instituteGPA = gpa;
    }

    public void setProgramGPA(double gpa) {
        this.programGPA = gpa;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCurrentSemester(Semester semester) {
        this.currentSemester = semester;
        // this.allSemester.add(semester);
    }

    public void setAllSemester(ArrayList<Semester> allSemester) {
        this.allSemester = allSemester;
    }

    public void setCompleteCourses(HashMap<Course, String> completeCourses) {
        this.completeCourses = completeCourses;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    // ----- Accessor -----

    // Alex Mesa Additions
    public String getStatus() {
        return this.status;
    }

    // Alex Mesa Additions
    public ClassLevel getLevel() {
        return this.classification;
    }

    public Advisor getAdvisor() {
        return this.advisor;
    }

    public ArrayList<String> getNotes() {
        return this.notes;
    }

    public Degree getDegree() {
        return this.degree;
    }

    public Double getInstituteGPA() {
        return this.instituteGPA;
    }

    public Double getProgramGPA() {
        return this.programGPA;
    }

    public HashMap<Course, String> getCompletedCourse() {
        return this.completeCourses;
    }

    // Changed by Alex Mesa
    public Semester getCurrentSemester() {
        return this.currentSemester;
    }

    // Changed by Alex Mesa
    public ArrayList<Semester> getAllSemester() {
        return this.allSemester;
    }

    public String getStudentID() {
        return this.studentID;
    }

    public ArrayList<Semester> getSemesterPlans() {
        initializeSemesterPlan();
        return this.semestersPlan;
    }

    // ----- Others method -----
    public void addCourseCompleted(Course course, String grade) {
        if (this.completeCourses == null) {
            this.completeCourses = new HashMap<>();
        }
        this.completeCourses.put(course, grade);
        initializeSemesterPlan();
    }

    public void addSemester(Semester semester) {
        this.allSemester.add(semester);
    }

    public void doneSemester() {
        this.allSemester.add(this.currentSemester);
        this.currentSemester = null;
        initializeSemesterPlan();
    }

    public void addNotes(String note) {
        this.notes.add(note);
    }

    public void initializeSemesterPlan() {
        // Asign completed semesters to 8 semester plan
        this.semestersPlan.clear();

        int totalCredit = this.degree.getTotalCreditRequired();

        // Add all completed semester
        for(Semester completedSemester: this.allSemester) {
            this.semestersPlan.add(completedSemester);
            totalCredit -= completedSemester.getCreditLimit();
        }

        HashMap<UUID, Integer> allCourseNotTaken = new HashMap<>();
        ArrayList<Course> queueCourse = new ArrayList<>();

        // Get all the course need to take
        HashMap<Course, Integer> majorCourses = this.degree.getMajorCourses();
        for (Course course : majorCourses.keySet()) {
            if (this.completeCourses.keySet().stream().anyMatch(c -> !c.equals(course))) {
                allCourseNotTaken.put(course.getID(), majorCourses.get(course));
                queueCourse.add(course);
            }
        }

        ArrayList<ElectiveCategory> electiveList = this.degree.getElectiveList();
        for (ElectiveCategory category : electiveList) {
            for (Course course : category.getCourseChoices().keySet()) {
                if (!this.completeCourses.keySet().contains(course) && 
                        !allCourseNotTaken.keySet().contains(course.getID())
                    )
                {
                    allCourseNotTaken.put(course.getID(), category.getCourseChoices().get(course));
                    queueCourse.add(course);
                }
            }
            totalCredit += category.getCreditsRequired();
        }

        // Generate the course need to take
        ArrayList<Course> semesterCourse = new ArrayList<>();

        // Set the season and year of semester
        String currSeason;
        int currYear;
        if(this.allSemester.size() != 0) {
            Semester lastSemester = this.allSemester.get(this.allSemester.size() - 1);
            currSeason = lastSemester.getSeason().toString();
            currYear = lastSemester.getYear();
        } else {
            currSeason = "Fall";
            currYear = 2024;
        }

        int creditLimitLeft = 18;
        for (int level = 1; level <= 8; level++) {
            if(totalCredit < 0) {
                break;
            }
            for (int i = queueCourse.size() - 1; i >= 0; i--) {
                Course course = queueCourse.get(i);
                int semesterPrefer = allCourseNotTaken.get(course.getID());
                if (semesterPrefer != level) {
                    continue;
                }

                int courseLimit = course.getCreditHours();
                if(totalCredit < courseLimit) {
                    continue;
                } else if (totalCredit == courseLimit) {
                    semesterCourse.add(course);
                    queueCourse.remove(course);
                    Semester tempSemester = new Semester(currSeason, currYear, courseLimit, new ArrayList<>(semesterCourse));
                    this.semestersPlan.add(tempSemester);
                }

                if (courseLimit <= creditLimitLeft) {
                    creditLimitLeft -= courseLimit;
                    semesterCourse.add(course);
                    queueCourse.remove(course);
                } else {
                    if (currSeason.equalsIgnoreCase("fall")) {
                        currSeason = "Spring";
                        currYear += 1;
                    } else {
                        currSeason = "Fall";
                    }
                    
                    int currentCredit = 18 - creditLimitLeft;
                    Semester tempSemester = new Semester(currSeason, currYear, currentCredit, new ArrayList<>(semesterCourse));
                    this.semestersPlan.add(tempSemester);
                    totalCredit -= currentCredit;
       
                    semesterCourse.clear();
                    creditLimitLeft = 18;
                    i++;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("---------------------------------Student---------------------------------\n");
        result.append(super.toString());
        result.append("\n-- Student ID: " + this.studentID);
        result.append("\n-- Classification: " + this.classification.toString());
        result.append("\n-- Institute GPA: " + this.instituteGPA);
        result.append("\n-- Program GPA: " + this.programGPA);
        result.append("\n-- Status: " + this.status);
        if (this.advisor != null) {
            result.append("\n-- Advisor: " + advisor.getFirstName() + " " + advisor.getLastName());
        } else {
            result.append("\n-- Advisor: None");
        }
        result.append("\n" + printNotes() + "\n");
        result.append("\n-------------------------------------------------------------------\n");

        result.append("\n-- Degree: Bachelors in " + this.degree.getSubject() + "\n");
        
        result.append("\n-- Completed Course:\n");
        for (Course course : completeCourses.keySet()) {
            String graded = completeCourses.get(course);
                result.append(course.toStringCourseAbbr() + "\n --Graded: " + graded + "\n");
        }

        result.append("\n-- 8 Semester Plan:\n" + this.toStringSemesterPlanBrief());
        
        return result.toString();
    }

    public String toStringAccount() {
        return super.toString();
    }

    public String printNotes() {
        return "-- Notes: " + notes.toString();
    }

    public String toStringDegree() {
        return this.degree.toString();
    }

    public String toStringCompletedSemester() {
        StringBuilder result = new StringBuilder();
        for (Semester semester: this.allSemester) {
            result.append("===================================================\n");
            result.append("Semester: " + semester.getSeason() + " " + semester.getYear() + "\t");
            result.append("Credit Limit: " + semester.getCreditLimit() + "\n");
            result.append("===================================================\n");
            for (Course course : semester.getCourses()) {
                result.append("Courses:" + course.toStringCourseAbbr());  
                result.append("\t" + "\n --Graded: " + this.completeCourses.get(course) +"\n");
                result.append("--------------------------------------------------\n");
            }
        }
        return result.toString();
    }

    public String toStringSemesterPlan() {
        StringBuilder result = new StringBuilder();
        for(Semester semester: this.semestersPlan) {
            result.append(semester.toString());
        }
        return result.toString();
    }

    public String toStringSemesterPlanBrief() {
        StringBuilder result = new StringBuilder();
        for(Semester semester: this.semestersPlan) {
            result.append(semester.toStringBrief());
        }
        return result.toString();
    }
}
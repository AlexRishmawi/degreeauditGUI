package model;
/**
 * 
 * @author Aarsh Patel
 */


public abstract class DataConstants {

    // Degree Constants
    protected static final String DEGREE_FILE_NAME = "./java/data/json/degree.json";
    protected static final String DEGREE_ID = "id";
    protected static final String DEGREE_TOTAL_CREDIT_REQUIRED = "totalCreditRequired";
    protected static final String DEGREE_MAJOR_COURSES = "majorCourses";
    protected static final String DEGREE_SUBJECT_NAME = "subjectName";
    protected static final String DEGREE_ELECTIVE_LIST = "electiveList";

    // SEMESTER Constants
    protected static final String SEMESTER_SEASON = "season";
    protected static final String SESMESTER_YEAR = "year";
    protected static final String SEMSESTER_LIMIT = "creditLimit";
    protected static final String SESMESTER_COURSES = "courses";

    // Elective Constants
    protected static final String ELECTIVE_TYPE = "type";
    protected static final String ELECTIVE_CREDIT_REQUIRED = "creditRequired";
    protected static final String ELECTIVE_COURSE_CHOICES = "courseChoices";

    // Course Constants
    protected static final String COURSE_FILE_NAME = "./java/data/json/course.json";
    protected static final String COURSE_ID = "uuid";
    protected static final String COURSE_NAME = "name";
    protected static final String COURSE_SUBJECT = "subject";
    protected static final String COURSE_CODE = "number";
    protected static final String COURSE_CREDIT_HOURS = "credit_hours";
    protected static final String COURSE_SEMESTER_OFFER = "semesters_offered";
    protected static final String COURSE_DESCRIPTION = "description";
    protected static final String COURSE_PREREQUISITE = "prerequisites";
    protected static final String COURSE_PREREQUISITE_CHOICES = "choices";
    protected static final String COURSE_PREREQUISITE_MIN_GRADE = "min_grade";
    protected static final String COURSE_PREREQUISITE_COURSE_OPTION = "course_options";

    // User Constants share among the user
    protected static final String USER_ID = "id";
    protected static final String USER_TYPE = "type";
    protected static final String USER_FIRST_NAME = "firstName";
    protected static final String USER_LAST_NAME = "lastName";
    protected static final String USER_EMAIL = "email";
    protected static final String USER_PASSWORD = "password";

    // Student Constants
    protected static final String STUDENT_FILE_NAME = "./java/data/json/student.json";
    protected static final String STUDENT_CLASSIFICATION = "classification";
    protected static final String STUDENT_ID = "studentID";
    protected static final String STUDENT_ADVISOR_ID = "advisorID";
    protected static final String STUDENT_NOTES = "notes";
    protected static final String STUDENT_STATUS = "status";
    protected static final String STUDENT_DEGREE_ID = "degreeID";
    protected static final String STUDENT_INSTITUTE_GPA = "instituteGPA";
    protected static final String STUDENT_PROGRAM_GPA = "programGPA";
    protected static final String STUDENT_COMPLETED_COURSES = "completeCourses";
    protected static final String STUDENT_ALL_SEMESTERS = "allSemesters";
    protected static final String STUDENT_CURRENT_SEMESTER = "currentSemester";

    // Advisor Constants
    protected static final String ADVISOR_FILE_NAME = "./java/data/json/advisor.json";
    protected static final String ADVISOR_STUDENT_LIST = "studentList";
    protected static final String ADVISOR_IS_ADMIN = "isAdmin";
}
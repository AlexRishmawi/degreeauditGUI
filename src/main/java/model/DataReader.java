package model;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.UUID;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The DataReader class is responsible for loading data from JSON files
 * and converting them into corresponding Java objects.
 */
public class DataReader extends DataConstants {

    /**
     * Loads user data from JSON files and returns an ArrayList of User objects.
     * @return An ArrayList containing User objects loaded from JSON files.
     */
    public static ArrayList<User> loadUser() {
        CourseList courseList = CourseList.getInstance();
        DegreeList degreeList = DegreeList.getInstance();

        ArrayList<User> loadedUsers = new ArrayList<>();
        HashMap<UUID, Integer> mappingAdvisorToStudent = new HashMap<>();
        // Queue<Pair<Advisor, Object>> queueAdvisor = new LinkedList<>();

        String[] FILES = {ADVISOR_FILE_NAME, STUDENT_FILE_NAME};
        for(String file: FILES) 
        {
            try {
                FileReader reader = new FileReader(file);
                JSONArray readerJSON = (JSONArray) new JSONParser().parse(reader);
                for(int i = 0; i < readerJSON.size(); i++) {
                    // Get default user information
                    JSONObject userJSON = (JSONObject) readerJSON.get(i);
                    UUID id = UUID.fromString((String) userJSON.get(USER_ID));
                    String firstName = (String) userJSON.get(USER_FIRST_NAME);
                    String lastName = (String) userJSON.get(USER_LAST_NAME);
                    String email = (String) userJSON.get(USER_EMAIL);
                    String password = (String) userJSON.get(USER_PASSWORD);
                    String type = (String) userJSON.get(USER_TYPE);

                    if(type.equalsIgnoreCase("student")) {
                        // Student information
                        String level = (String) userJSON.get(STUDENT_CLASSIFICATION);
                        UUID advisorID = UUID.fromString((String) userJSON.get(STUDENT_ADVISOR_ID));
                        String studentID = (String) userJSON.get(STUDENT_ID);
                        int advisorIndex = mappingAdvisorToStudent.get(advisorID);
                        Advisor advisor = (Advisor) loadedUsers.get(advisorIndex);

                        ArrayList<String> notes = new ArrayList<>();
                        JSONArray noteJsonArray = (JSONArray) userJSON.get(STUDENT_NOTES);
                        for(int j = 0; j < noteJsonArray.size(); j++) {
                            notes.add((String) noteJsonArray.get(j));
                        }

                        double instituteGPA = (double) userJSON.get(STUDENT_INSTITUTE_GPA);
                        double programGPA = (double) userJSON.get(STUDENT_PROGRAM_GPA);

                        String status = (String) userJSON.get(STUDENT_STATUS);
                        UUID degreeID = UUID.fromString((String) userJSON.get(STUDENT_DEGREE_ID));
                        Degree degree = degreeList.getDegree(degreeID);

                        HashMap<Course, String> completedCourse = new HashMap<>();
                        JSONObject completedCourseObject = (JSONObject) userJSON.get(STUDENT_COMPLETED_COURSES);
                        for(Object keyID: completedCourseObject.keySet()) {
                            UUID courseID = UUID.fromString((String) keyID);
                            Course course = courseList.getCourse(courseID);
                            String graded = (String) completedCourseObject.get(keyID);
                            completedCourse.put(course, graded);
                        }

                        JSONObject currentSemesterObject = (JSONObject) userJSON.get(STUDENT_CURRENT_SEMESTER);
                        String season = (String) currentSemesterObject.get(SEMESTER_SEASON);
                        int year = ((Long) currentSemesterObject.get(SESMESTER_YEAR)).intValue();
                        int limit = ((Long) currentSemesterObject.get(SEMSESTER_LIMIT)).intValue();
                        ArrayList<Course> courses = new ArrayList<>();
                        JSONArray coursesJsonArray = (JSONArray) currentSemesterObject.get(SESMESTER_COURSES);
                        for(int j = 0; j < coursesJsonArray.size(); j++) {
                            Course course = courseList.getCourse(UUID.fromString((String) coursesJsonArray.get(j)));
                            courses.add(course);
                        }
                        Semester currentSemester = new Semester(season, year, limit, courses);

                        ArrayList<Semester> allSemesters = new ArrayList<>();
                        JSONArray allSemesterJsonArray = (JSONArray) userJSON.get(STUDENT_ALL_SEMESTERS);
                        for(int j = 0; j < allSemesterJsonArray.size(); j++) {
                            JSONObject allSemJsonObject = (JSONObject) allSemesterJsonArray.get(j);
                            String temp_season = (String) allSemJsonObject.get(SEMESTER_SEASON);
                            int temp_year = ((Long) allSemJsonObject.get(SESMESTER_YEAR)).intValue();
                            int temp_limit = ((Long) allSemJsonObject.get(SEMSESTER_LIMIT)).intValue();
                            ArrayList<Course> temp_courses = new ArrayList<>();
                            JSONArray temp_coursesJsonArray = (JSONArray) allSemJsonObject.get(SESMESTER_COURSES);
                            for(int k = 0; k < temp_coursesJsonArray.size(); k++) {
                                Course course = courseList.getCourse(UUID.fromString((String) temp_coursesJsonArray.get(j)));
                                temp_courses.add(course);
                            }
                            Semester tempSemester = new Semester(temp_season, temp_year, temp_limit, temp_courses);
                            allSemesters.add(tempSemester);
                        }
                        
                        Student student = new Student(id, firstName, lastName, email, password, studentID, level, advisor, notes, degree, instituteGPA, programGPA, status, completedCourse, currentSemester, allSemesters);
                        loadedUsers.add(student);
                        advisor.addStudent(student);

                    } else if (type.equalsIgnoreCase("advisor")) {
                        // Advisor information
                        String isAdminStr = (String) userJSON.get(ADVISOR_IS_ADMIN);
                        boolean isAdmin = Boolean.parseBoolean(isAdminStr);

                        ArrayList<Student> studentList = new ArrayList<Student>();
                        // JSONArray studentListJSON = (JSONArray) userJSON.get(ADVISOR_STUDENT_LIST);

                        Advisor advisor = new Advisor(id, firstName, lastName, email, password, studentList, isAdmin);

                        loadedUsers.add(advisor);
                        mappingAdvisorToStudent.put(advisor.getID(), loadedUsers.indexOf(advisor));
                        // queueAdvisor.add(new Pair<Advisor, Object>(advisor, studentListJSON));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("ERROR --- Couldn't load User file");
            }
        }
        
        return loadedUsers;
    }

    /**
     * Loads course data from a JSON file and returns an ArrayList of Course objects.
     * @return An ArrayList containing Course objects loaded from a JSON file.
     */
    public static ArrayList<Course> loadCourse() {
        ArrayList<Course> loadedCourse = new ArrayList<>();
        HashMap<UUID, Integer> mappingCourse = new HashMap<>();
        Queue<Pair<Course, Object>> queueCourse = new LinkedList<>();

        // Read a file and load the course with no prerequisite first
        try {
            FileReader reader = new FileReader(COURSE_FILE_NAME);
            JSONArray readerJSON = (JSONArray) new JSONParser().parse(reader);
            for (int i = 0; i < readerJSON.size(); i++) {
                JSONObject courseJSON = (JSONObject) readerJSON.get(i);

                UUID id = UUID.fromString((String) courseJSON.get(COURSE_ID));
                String subject = (String) courseJSON.get(COURSE_SUBJECT);
                String code = (String) courseJSON.get(COURSE_CODE);

                String name = (String) courseJSON.get(COURSE_NAME);
                String description = (String) courseJSON.get(COURSE_DESCRIPTION);

                int credit = ((Long) courseJSON.get(COURSE_CREDIT_HOURS)).intValue();

                ArrayList<Season> semesterOffer = new ArrayList<>();
                JSONArray semesterJSON = (JSONArray) courseJSON.get(COURSE_SEMESTER_OFFER);
                for (int j = 0; j < semesterJSON.size(); j++) {
                    semesterOffer.add(Season.valueOf(((String) semesterJSON.get(j)).toUpperCase()));
                }

                ArrayList<Prerequisites> prerequisites = new ArrayList<Prerequisites>();
                JSONArray prerequisiteJsonObject = (JSONArray) courseJSON.get(COURSE_PREREQUISITE);

                Course course = new Course(id, subject, code, name, description, credit, semesterOffer, prerequisites);
                if (prerequisiteJsonObject.size() == 0) {
                    loadedCourse.add(course);
                    int index = loadedCourse.indexOf(course);
                    mappingCourse.put(course.getID(), index);
                } else {
                    queueCourse.add(new Pair<Course, Object>(course, prerequisiteJsonObject));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR --- Couldn't load Course file");
        }

        while (!queueCourse.isEmpty()) {
            Pair<Course, Object> item = queueCourse.poll();
            Course course = item.getKey();
            JSONArray prereJson = (JSONArray) item.getValue();

            ArrayList<Prerequisites> allPrerequisites = new ArrayList<>();
            boolean isGoodToLoad = true;

            for (int i = 0; i < prereJson.size(); i++) {
                JSONObject prereJSON = (JSONObject) prereJson.get(i);
                int choices = ((Long) prereJSON.get(COURSE_PREREQUISITE_CHOICES)).intValue();
                String minGrade = (String) prereJSON.get(COURSE_PREREQUISITE_MIN_GRADE);

                ArrayList<Course> courseOptions = new ArrayList<>();
                JSONArray courseOptionJSON = (JSONArray) prereJSON.get(COURSE_PREREQUISITE_COURSE_OPTION);
                for (int j = 0; j < courseOptionJSON.size(); j++) {
                    UUID courseID = UUID.fromString((String) courseOptionJSON.get(j));
                    if (mappingCourse.containsKey(courseID)) {
                        courseOptions.add(loadedCourse.get(mappingCourse.get(courseID)));
                    }
                }

                // check wherether if all options is loaded
                if (courseOptions.size() == courseOptionJSON.size()) {
                    allPrerequisites.add(new Prerequisites(choices, minGrade, courseOptions));
                } else {
                    isGoodToLoad = false;
                    break;
                }
            }

            // if it good condition then load, otherwise load back to queue
            if (isGoodToLoad) {
                course.setPrerequisites(allPrerequisites);
                loadedCourse.add(course);
                mappingCourse.put(course.getID(), loadedCourse.indexOf(course));
            } else {
                queueCourse.add(item);
            }
        }

        return loadedCourse;
    }

    /**
     * Loads degree information from a JSON file and returns an ArrayList of Degree objects.
     * Each Degree object contains details such as degree ID, subject name, total credit requirement,
     * major courses, and elective categories.
     * @return ArrayList of Degree objects loaded from the JSON file.
     */
    public static ArrayList<Degree> loadDegree() {

        CourseList courseList = CourseList.getInstance();
        ArrayList<Degree> loadedDegree = new ArrayList<>();

        try {
            FileReader reader = new FileReader(DEGREE_FILE_NAME);
            JSONArray readerJSON = (JSONArray) new JSONParser().parse(reader);
            for(int i = 0; i < readerJSON.size(); i++) {
                JSONObject degreeObject = (JSONObject) readerJSON.get(i);
                UUID degreeID = UUID.fromString((String) degreeObject.get(DEGREE_ID));
                int creditRequire = (int) ((long) degreeObject.get(DEGREE_TOTAL_CREDIT_REQUIRED));
                String subject = (String) degreeObject.get(DEGREE_SUBJECT_NAME);
                
                HashMap<Course, Integer> majorCourses = new HashMap<>();
                JSONObject majorCourseObject = (JSONObject) degreeObject.get(DEGREE_MAJOR_COURSES);
                for(Object courseID: majorCourseObject.keySet()) {
                    UUID id = UUID.fromString((String) courseID);
                    Course temp_course = courseList.getCourse(id);

                    int preferredSemester = (int) ((long) majorCourseObject.get(courseID));
                    majorCourses.put(temp_course, preferredSemester);
                }

                ArrayList<ElectiveCategory> electiveList = new ArrayList<>();
                JSONArray electiveArray = (JSONArray) degreeObject.get(DEGREE_ELECTIVE_LIST);
                for(int j = 0; j < electiveArray.size(); j++) {
                    JSONObject electivObject = (JSONObject) electiveArray.get(j);
                    String type = (String) electivObject.get(ELECTIVE_TYPE);
                    int electivecreditRequired = (int) ((long) electivObject.get(ELECTIVE_CREDIT_REQUIRED));

                    JSONObject courseJsonObject = (JSONObject) electivObject.get(ELECTIVE_COURSE_CHOICES);
                    HashMap<Course, Integer> coursesChoices = new HashMap<>();
                    for(Object courseID: courseJsonObject.keySet()) {
                        UUID id = UUID.fromString((String) courseID);
                        Course temp_course = courseList.getCourse(id);

                        int preferredSemester = (int) ((long) courseJsonObject.get(courseID));
                        coursesChoices.put(temp_course, preferredSemester);
                    }

                    electiveList.add(new ElectiveCategory(type, electivecreditRequired, coursesChoices));
                }

                Degree degree = new Degree(degreeID, subject, subject, creditRequire, majorCourses, electiveList);
                loadedDegree.add(degree);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR --- Couldn't Degree file");
        }

        return loadedDegree;
    }

    // ----- Private Data Structure -----
    /**
    * A generic Pair class representing a key-value pair.
    * @param <K> the type of the key
    * @param <V> the type of the value
    */
    private static class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public String toString() {
            return "(" + key + ", " + value + ")";
        }
    }
}
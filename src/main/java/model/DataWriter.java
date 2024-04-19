package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataWriter extends DataConstants {

    @SuppressWarnings("unchecked")
    public static void writeDegree(ArrayList<Degree> allDegrees) {
        JSONArray allDegreeObject = new JSONArray();
        for (Degree degree : allDegrees) {
            HashMap<String, Object> degreeObject = new HashMap<>();
            degreeObject.put(DEGREE_ID, degree.getID().toString());
            degreeObject.put(DEGREE_TOTAL_CREDIT_REQUIRED, degree.getTotalCreditRequired());

            degreeObject.put(DEGREE_MAJOR_COURSES, serializeCoursePrefer(degree.getMajorCourses()));
            degreeObject.put(DEGREE_SUBJECT_NAME, degree.getSubject());

            JSONArray electiveListArrayObject = new JSONArray();
            degree.getElectiveList().forEach(elective -> electiveListArrayObject.add(serializeElective(elective)));
            degreeObject.put(DEGREE_ELECTIVE_LIST, electiveListArrayObject);

            allDegreeObject.add(degreeObject);
        }

        writeToFile(DEGREE_FILE_NAME, allDegreeObject);
    }

    @SuppressWarnings("unchecked")
    public static void writeCourse(ArrayList<Course> allCourses) {
        JSONArray allCourseObject = new JSONArray();
        for (Course course : allCourses) {
            HashMap<String, Object> courseObject = new HashMap<>();
            courseObject.put(COURSE_ID, course.getID().toString());
            courseObject.put(COURSE_SUBJECT, course.getSubject());
            courseObject.put(COURSE_CODE, course.getCode());
            courseObject.put(COURSE_DESCRIPTION, course.getDescription());
            courseObject.put(COURSE_CREDIT_HOURS, course.getCreditHours());

            JSONArray semesterOfferArray = new JSONArray();
            course.getSemesterOffer().forEach(season -> semesterOfferArray.add(season.toString()));
            courseObject.put(COURSE_SEMESTER_OFFER, semesterOfferArray);

            JSONArray prereJsonArray = new JSONArray();
            course.getPrerequisites()
                    .forEach(prerequisites -> prereJsonArray.add(serializePrerequisite(prerequisites)));
            courseObject.put(COURSE_PREREQUISITE, prereJsonArray);

            allCourseObject.add(new JSONObject(courseObject));
        }

        writeToFile(COURSE_FILE_NAME, allCourseObject);
    }

    @SuppressWarnings("unchecked")
    public static void writeUser(ArrayList<User> allUsers) {
        JSONArray allStudentObject = new JSONArray();
        JSONArray allAdvisorObject = new JSONArray();

        for (User user : allUsers) {
            HashMap<String, Object> userObject = new HashMap<>();
            userObject.put(USER_ID, user.getID().toString());
            userObject.put(USER_TYPE, user.getUserType().toString());
            userObject.put(USER_FIRST_NAME, user.getFirstName());
            userObject.put(USER_LAST_NAME, user.getLastName());
            userObject.put(USER_EMAIL, user.getEmail());
            userObject.put(USER_PASSWORD, user.getPassword());
            if (user.getUserType().equals(UserType.STUDENT)) {
                fillStudentDetails(userObject, (Student) user);
                allStudentObject.add(new JSONObject(userObject));
            } else if (user.getUserType().equals(UserType.ADVISOR)) {
                fillAdvisorDetails(userObject, (Advisor) user);
                allAdvisorObject.add(new JSONObject(userObject));
            }
        }

        writeToFile(STUDENT_FILE_NAME, allStudentObject);
        writeToFile(ADVISOR_FILE_NAME, allAdvisorObject);
    }

    @SuppressWarnings("unchecked")
    private static void fillAdvisorDetails(HashMap<String, Object> map, Advisor advisor) {
        map.put(ADVISOR_IS_ADMIN, advisor.getIsAdmin().toString());
        JSONArray studentListJson = new JSONArray();
        advisor.getStudentList().forEach(student -> studentListJson.add(student.getID().toString()));
        map.put(ADVISOR_STUDENT_LIST, studentListJson);
    }

    @SuppressWarnings("unchecked")
    private static void fillStudentDetails(HashMap<String, Object> map, Student student) {
        map.put("type", "Student");
        map.put(STUDENT_ID, student.getStudentID());
        map.put(STUDENT_CLASSIFICATION, student.getLevel().toString());

        if (student.getAdvisor() != null) {
            map.put(STUDENT_ADVISOR_ID, student.getAdvisor().getID().toString());
        } else {
            map.put(STUDENT_ADVISOR_ID, "");
        }
        JSONArray notesJson = new JSONArray();
        for (String note : student.getNotes()) {
            notesJson.add(note);
        }
        map.put(STUDENT_NOTES, notesJson);
        map.put(STUDENT_DEGREE_ID, student.getDegree().getID().toString());
        map.put(STUDENT_INSTITUTE_GPA, student.getInstituteGPA());
        map.put(STUDENT_PROGRAM_GPA, student.getProgramGPA());
        map.put(STUDENT_STATUS, student.getStatus());
        map.put(STUDENT_COMPLETED_COURSES, serializeCompleteCourses(student.getCompletedCourse()));
        map.put(STUDENT_ALL_SEMESTERS, serializeAllSemesters(student.getAllSemester()));
        map.put(STUDENT_CURRENT_SEMESTER, serializeSemester(student.getCurrentSemester()));
    }

    @SuppressWarnings("unchecked")
    private static JSONObject serializeCoursePrefer(HashMap<Course, Integer> coursePrefer) {
        JSONObject coursePreferObject = new JSONObject();
        coursePrefer.forEach((Course, preferredSemester) -> {
            coursePreferObject.put(Course.getID().toString(), preferredSemester);
        });
        return coursePreferObject;
    }

    private static JSONObject serializeElective(ElectiveCategory elective) {
        HashMap<String, Object> electiveObject = new HashMap<>();
        electiveObject.put(ELECTIVE_TYPE, elective.getType());
        electiveObject.put(ELECTIVE_CREDIT_REQUIRED, elective.getCreditsRequired());
        electiveObject.put(ELECTIVE_COURSE_CHOICES, serializeCoursePrefer(elective.getCourseChoices()));
        return new JSONObject(electiveObject);
    }

    @SuppressWarnings("unchecked")
    private static JSONObject serializePrerequisite(Prerequisites prerequisites) {
        HashMap<String, Object> prerequisitesObject = new HashMap<>();
        prerequisitesObject.put(COURSE_PREREQUISITE_CHOICES, prerequisites.getChoices());
        prerequisitesObject.put(COURSE_PREREQUISITE_MIN_GRADE, prerequisites.getMinGrade());

        JSONArray courseOptionArray = new JSONArray();
        prerequisites.getCourseOptions().forEach(course -> courseOptionArray.add(course.getID().toString()));
        prerequisitesObject.put(COURSE_PREREQUISITE_COURSE_OPTION, courseOptionArray);

        return new JSONObject(prerequisitesObject);
    }

    private static JSONObject serializeCompleteCourses(HashMap<Course, String> completedCourse) {
        HashMap<String, String> completedCourseByID = new HashMap<>();
        for (Map.Entry<Course, String> entry : completedCourse.entrySet()) {
            String courseID = entry.getKey().getID().toString();
            String graded = entry.getValue();
            completedCourseByID.put(courseID, graded);
        }

        return new JSONObject(completedCourseByID);
    }

    @SuppressWarnings("unchecked")
    private static JSONArray serializeAllSemesters(ArrayList<Semester> semesters) {
        JSONArray semestersJson = new JSONArray();
        semesters.forEach(semester -> semestersJson.add(serializeSemester(semester)));
        return semestersJson;
    }

    @SuppressWarnings("unchecked")
    private static JSONObject serializeSemester(Semester semester) {
        if (semester == null)
            return new JSONObject();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put(SEMESTER_SEASON, semester.getSeason().toString());
        map.put(SESMESTER_YEAR, semester.getYear());
        map.put(SEMSESTER_LIMIT, semester.getCreditLimit());
        JSONArray coursesJson = new JSONArray();
        semester.getCourses().forEach(course -> coursesJson.add(course.getID().toString()));
        map.put(SESMESTER_COURSES, coursesJson);
        return new JSONObject(map);
    }

    private static void writeToFile(String filePath, JSONArray jsonArray) {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
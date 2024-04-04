import java.util.ArrayList;
import java.util.UUID;

/**
 * The CourseList class represents a list of courses and provides methods to manage them.
 */
public class CourseList {
    private static CourseList courseList;
    private ArrayList<Course> courses;
    
    // Private constructor to prevent direct instantiation
    private CourseList() {
        this.courses = DataReader.loadCourse();
    }

    /**
     * Returns the singleton instance of the CourseList.
     * @return The singleton instance of the CourseList.
     */
    public static CourseList getInstance() {
        return courseList != null ? courseList : new CourseList();
    }

    /**
     * Returns the singleton instance of the CourseList.
     * @return The singleton instance of the CourseList.
     */
    public Course getCourse(UUID id) {
        for (Course course : this.courses) {
            if (course.getID().equals(id))
                return course;
        }
        return null;
    }

    /**
     * Adds a new course to the list.
     * @param course The course to add.
     * @return True if the course was successfully added, otherwise false.
     */
    public boolean addCourse(Course course) {
        this.courses.add(course);
        return this.courses.contains(course);
    }

    /**
     * Deletes a course from the list.
     * @param course The course to delete.
     * @return True if the course was successfully deleted, otherwise false.
     */
    public boolean deleteCourse(UUID id) {
        for (Course course : this.courses) {
            if (course.getID().equals(id)) {
                this.courses.remove(course);
                return true;
            }
        }
        return false;
    }

    /**
     * Updates a course in the list based on its unique ID.
     * @param id The unique ID of the course to update.
     * @param course The updated course object.
     * @return True if the course was successfully updated, otherwise false.
     */
    public boolean updateCourse(UUID id, Course course) {
        for(int i = 0; i < this.courses.size(); i++) {
            if (this.courses.get(i).getID().equals(course.getID())) {
                this.courses.set(i, course);
                return true;
            }
        }
        return false;
    }

    public boolean writeCourse() {
        return true;
    }

    /**
     * Retrieves all courses stored in the list.
     * @return An ArrayList containing all courses stored in the list.
     */
    public ArrayList<Course> getAllCourse() {
        return this.courses;
    }
    
}

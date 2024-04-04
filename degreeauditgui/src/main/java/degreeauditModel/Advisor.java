import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Advisor class represents a user with advisor privileges.
 * It extends the User class and contains methods to manage students and perform advisor-specific actions.
 */

public class Advisor extends User{
    private ArrayList<Student> studentList;
    private Student currentStudent;
    private Boolean isAdmin;

    /**
     * Constructor for creating an Advisor instance.
     * @param firstName First name of the advisor.
     * @param lastName Last name of the advisor.
     * @param email Email address of the advisor.
     * @param password Password of the advisor.
     * @param isAdmin Specifies whether the advisor is an administrator.
     */
    public Advisor(String firstName, String lastName, String email, String password, Boolean isAdmin) 
    {
        super(firstName, lastName, email, password);
        super.setUserType(UserType.ADVISOR);
        this.studentList = new ArrayList<Student>();
        setAdmin(isAdmin);
    }

    /**
     * Constructor for creating an Advisor instance with an existing list of students.
     * @param firstName First name of the advisor.
     * @param lastName Last name of the advisor.
     * @param email Email address of the advisor.
     * @param password Password of the advisor.
     * @param studentList List of students managed by the advisor.
     * @param isAdmin Specifies whether the advisor is an administrator.
     */
    public Advisor(String firstName, String lastName, String email, String password, ArrayList<Student> studentList, Boolean isAdmin)
    {
        super(firstName, lastName, email, password);
        super.setUserType(UserType.ADVISOR);
        setStudentList(studentList);
        setAdmin(isAdmin);
    }

    /**
     * Constructor for creating an Advisor instance with a specific ID and an existing list of students.
     * @param id Unique identifier of the advisor.
     * @param firstName First name of the advisor.
     * @param lastName Last name of the advisor.
     * @param email Email address of the advisor.
     * @param password Password of the advisor.
     * @param studentList List of students managed by the advisor.
     * @param isAdmin Specifies whether the advisor is an administrator.
     */
    public Advisor(UUID id, String firstName, String lastName, String email, String password, ArrayList<Student> studentList, Boolean isAdmin) 
    {
        super(id, firstName, lastName, email, password);
        super.setUserType(UserType.ADVISOR);
        setStudentList(studentList);
        setAdmin(isAdmin);
    }
    
    // ----- Accessor -----
    /**
     * Retrieves the list of students managed by the advisor.
     * @return ArrayList containing the students managed by the advisor.
     */
    public ArrayList<Student> getStudentList() 
    {
        return this.studentList;
    }

    /**
     * Retrieves the currently selected student.
     * @return The currently selected student.
     */
    public Student getCurrentStudent() 
    {
        return this.currentStudent;
    }

    /**
     * Checks if the advisor is an administrator.
     * @return True if the advisor is an administrator, otherwise false.
     */
    public Boolean getIsAdmin() 
    {
        return this.isAdmin;
    }

    // ----- Mutator -----
    /**
     * Sets the currently selected student based on their unique ID.
     * @param id The unique ID of the student.
     */
    public boolean setCurrentStudent(UUID id) 
    {
        this.currentStudent = this.findStudent(id);
        return this.currentStudent != null;
    }

    /**
     * Sets the list of students managed by the advisor.
     * @param studentList ArrayList containing the students to be managed by the advisor.
     */
    public void setStudentList(ArrayList<Student> studentList) 
    {
        this.studentList = studentList;
    }

    /**
     * Sets whether the advisor is an administrator.
     * @param isAdmin True if the advisor is an administrator, otherwise false.
     */
    public void setAdmin(Boolean isAdmin) 
    {
        this.isAdmin = isAdmin;
    }

    // ----- Advisor method -----
    /**
     * Adds a new student to the advisor's list of managed students.
     * @param student The student to be added.
     */
    public void addStudent(Student student) 
    {
        this.studentList.add(student);
        ArrayList<Student> students = this.getStudentList();
        this.currentStudent = students.get(students.size() - 1);
    }

    /**
     * Removes a student from the advisor's list of managed students.
     * @param UUID The student id to be removed.
     * @return True if the student was successfully removed, otherwise false.
     */
    public boolean removeStudent(UUID id) 
    {
        Student student = this.findStudent(id);
        this.studentList.remove(student);
        
        if(currentStudent.equals(student)) {
            currentStudent = null;
        }

        return this.studentList.contains(student);
    }
    
    /**
     * Finds a student in the advisor's list of managed students based on their unique ID.
     * @param id The unique ID of the student to be found.
     * @return The student if found, otherwise null.
     */
    public Student findStudent(UUID id) 
    {
        if(this.studentList.size() == 0) {
            return null;
        }

        for(int i = 0; i < this.studentList.size(); i++) {
            if(this.studentList.get(i).getID().equals(id)) {
                return this.studentList.get(i);
            }
        }
        return null;
    }
    public Student findStudent(String studentID)
    {
        if(this.studentList.size() == 0) {
            System.out.println("Student list is empty");
            return null;
        }
        System.out.println("Running findStudent");
        for(int i = 0; i < this.studentList.size(); i++) {
            if(this.studentList.get(i).getStudentID().equals(studentID)) {
                return this.studentList.get(i);
            }
        }
        return null;
    }

    /**
     * Edits the first name of the currently selected student.
     * @param fname The new first name.
     * @return True if the first name was successfully updated, otherwise false.
     */
    public boolean editStudentFirstName(String fname)
    {
        this.currentStudent.setFirstName(fname);
        return true;
    }

    /**
     * Edits the last name of the currently selected student.
     * @param lname The new last name.
     * @return True if the last name was successfully updated, otherwise false.
     */
    public boolean editStudentLastName(String lname)
    {
        this.currentStudent.setLastName(lname);
        return true;
    }

    /**
     * Edits the email of the currently selected student.
     * @param email The new email address.
     * @return True if the email was successfully updated, otherwise false.
     */
    public boolean editStudentEmail(String email) {
        this.currentStudent.setEmail(email);
        return true;
    }

    /**
     * Edits the password of the currently selected student.
     * @param newPassword The new password.
     * @return True if the password was successfully updated, otherwise false.
     */
    public boolean editStudentPassword(String newPassword) {
        this.currentStudent.setPassword(newPassword);
        // return updateStudentInStudentList();
        return true;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("-- Admin: "+ this.isAdmin);
        result.append("\n" + super.toString());
        result.append("\n-- Student List: \n" + this.studentList.toString());
        // for (int i = 0; i < this.studentList.size(); i++) {
        //     Student student = this.studentList.get(i);
        //     result.append("[ Student " + (i+1) + ": \n" + student.toStringAccount() + "\n]");
        //     result.append(i != this.studentList.size() - 1 ? " ,\n" : "\n");
        // }
        return result.toString();
    }

    public String toStringAccount() {
        return super.toString();
    }

    public static void main(String[] args) {
        // Test Advisor Overloader
        Advisor newAdvisor = new Advisor("Aarsh", "Patel", "aarsh@email.sc.edu", "test1", false);

        //Test Advisor Constructor
        Advisor newAdvisor2 = new Advisor("Aarsh", "Patel", "johndoe@email.sc.edu", "test2", new ArrayList<Student>(), true);

        //Test getStudentList
        System.out.println(newAdvisor2.getStudentList());
        System.out.println(newAdvisor.getStudentList());

        //Test getCurrentStudent
        System.out.println(newAdvisor2.getCurrentStudent());
        System.out.println(newAdvisor.getCurrentStudent());

        //Test getIsAdmin
        System.out.println(newAdvisor2.getIsAdmin());
        System.out.println(newAdvisor.getIsAdmin());

        //Test setStudentList
        ArrayList<Student> studentList = new ArrayList<Student>();
        studentList.add(new Student("John", "Doe", "john@email.sc.edu", "test", "X67283832"));
        newAdvisor.setStudentList(studentList);
        System.out.println(newAdvisor.getStudentList());
        System.out.println(newAdvisor2.getStudentList());

        //Test setCurrentStudent
        newAdvisor.setCurrentStudent(studentList.get(0).getID());
        System.out.println(newAdvisor.getCurrentStudent());

        //Test setAdmin
        newAdvisor.setAdmin(true);
        System.out.println(newAdvisor.getIsAdmin());

        //Test addStudent
        String type = "Bachelors";
        String name = "Computer Science";
        int totalCredits = 125;
        HashMap<Course, Integer> courses = new HashMap<>();
        courses.put(new Course("Vector Calculus", "MATH", "241", "", 3, new ArrayList<Season>(), new ArrayList<Prerequisites>()), 1);
        ArrayList<ElectiveCategory> electives = new ArrayList<>();
        //newAdvisor.addStudent(new Student("Jane", "Doe", "", "", "X72838291", "SOPHOMORE", newAdvisor, new ArrayList<String>(), new Degree(type, name, totalCredits, courses, electives), 3.5, 3.5, "ACTIVE"));
        System.out.println(newAdvisor.getStudentList());

        //Test removeStudent
        newAdvisor.removeStudent(newAdvisor.getStudentList().get(0).getID());
        System.out.println(newAdvisor.getStudentList());

        //Test findStudent
        System.out.println(newAdvisor.findStudent(newAdvisor.getStudentList().get(0).getID()).toString());

        //Test editStudentFirstName
        newAdvisor.setCurrentStudent(newAdvisor.getStudentList().get(0).getID());
        newAdvisor.editStudentFirstName("John");
        System.out.println(newAdvisor.getStudentList().get(0).getFirstName());
    }
}

package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DegreeWork {
    private UserList userList;
    private CourseList courseList;
    private DegreeList degreeList;
    private User currentUser;

    public DegreeWork() {
        this.userList = UserList.getInstance();
        this.courseList = CourseList.getInstance();
        this.degreeList = DegreeList.getInstance();
    }

    public UserList getUserList() {
        return this.userList;
    }

    public CourseList getCourseList() {
        return this.courseList;
    }

    public DegreeList getDegreeList() {
        return this.degreeList;
    }


    public User getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // -------- User method --------
    public boolean login(String email, String password) {
        this.currentUser = this.userList.getUser(email, password);
        //System.out.println(this.currentUser);
        return this.currentUser != null;
    }

    public boolean login(String firstName, String lastName, String password) {
        return (this.currentUser = this.userList.getUser(firstName, lastName, password)) != null;
    }

    public boolean userExist(String email) {
        return this.userList.checkUser(email);
    }

    public boolean signup(String firstName, String lastName, String email, String password, String studentID, boolean isAdvisor) {
        if(this.userExist(email)) 
            return false;
        if(isAdvisor) {
            this.createAdvisor(firstName, lastName, email, password, new ArrayList<Student>(), false);
            return this.login(email, password);
        } else {
            this.createStudent(firstName, lastName, email, password, studentID, "FRESHMEN", null, new ArrayList<String>(), null, 4.0, 4.0, "active"); // Check if nulls work
            return this.login(email, password);
        }
    }

    public boolean logout() {
        return (this.currentUser = null) == null &&
            this.userList.writeToFile() &&
            this.degreeList.writeToFile() &&
            this.courseList.writeToFile();
    }

    public boolean createUser(String type, String firstName, String lastName, String password, String studentID, String email) {
        return this.userList.createUser(type, firstName, lastName, password, studentID, email);
    }

    public Student createStudent(String firstName, String lastName, String email, String password, String studentID,
            String level, Advisor advisor, ArrayList<String> notes, Degree degree,
            double instituteGPA, double programGPA, String status) {
        Student tempStudent = new Student(firstName, lastName, email, password, studentID, level, advisor, notes, degree, instituteGPA, programGPA, status, new HashMap<Course, String>(), null, new ArrayList<>());
        this.userList.addUser(tempStudent);
        this.degreeList.addDegree(degree);
        setCurrentUser(tempStudent);
        return tempStudent;
    }

    public Advisor createAdvisor(String firstName, String lastName, String email, String password, ArrayList<Student> studentList, Boolean isAdmin) {
        
        Advisor tempAdvisor = new Advisor(firstName, firstName, email, password, studentList, isAdmin);
        this.userList.addUser(tempAdvisor);

        return tempAdvisor;
    }

    public boolean removeUser(String id) {
        return this.userList.removeUser(UUID.fromString(id));
    }



    // -------- Student and Advisor Method --------

    public String displayDegreeProgress() {
        if (this.currentUser instanceof Student) {
            return this.currentUser.toString();
        } else if (this.currentUser instanceof Advisor) {
            return ((Advisor) this.currentUser).getCurrentStudent().toString();
        }
        return "No information to display";
    }

    public String displayEightSemesterPlan() {
        if (this.currentUser instanceof Student) {
            return ((Student) this.currentUser).toStringSemesterPlan();
        } else if (this.currentUser instanceof Advisor) {
            return ((Advisor) this.currentUser).getCurrentStudent().toStringSemesterPlan();
        }
        return "No information to display";
    }
    
    // public boolean displayMajorMap() {
    //     if (this.currentUser.getUserType() == UserType.STUDENT) {
    //         return ((Student) this.currentUser).getDegree().majorMapToString();
    //     } else if (this.currentUser.getUserType() == UserType.ADVISOR) {
    //         return ((Advisor) this.currentUser).getCurrentStudent().getCurrentCourse().majorMapToString();
    //     }
    // }

    public boolean addNotes(String note) {
        if (this.currentUser.getUserType() == UserType.STUDENT) {
            ((Student) this.currentUser).addNotes(note);
        } else if (this.currentUser.getUserType() == UserType.ADVISOR) {
            ((Advisor) this.currentUser).getCurrentStudent().addNotes(note);
        } else {
            return false;
        }
        return true;
    }

    public ArrayList<Course> getCurrentCourse() {
        if (this.currentUser.getUserType() == UserType.STUDENT) {
            return ((Student) this.currentUser).getCurrentSemester().getCourses();
        } else if (this.currentUser.getUserType() == UserType.ADVISOR) {
            return ((Advisor) this.currentUser).getCurrentStudent().getCurrentSemester().getCourses();
        }
        return null;
    }

    // ----- User Account Method -----
    public boolean editUserFirstName(UUID id, String name) 
    {
        if (this.currentUser.getUserType() == UserType.ADVISOR) {
            if(((Advisor) this.currentUser).getIsAdmin()) {
                User tempUser = this.userList.getUser(id);
                tempUser.setFirstName(name);
            } else {
                ((Advisor) this.currentUser).setCurrentStudent(id);
                User tempUser = ((Advisor) this.currentUser).getCurrentStudent();
                tempUser.setFirstName(name);
            }    
            return true;
        } else if(this.currentUser.getUserType() == UserType.STUDENT) {
            this.currentUser.setFirstName(name);
            return true;
        }
        return false;
    }

    public boolean editUserLastName(UUID id, String name) {
        if (this.currentUser.getUserType() == UserType.ADVISOR) {
            if(((Advisor) this.currentUser).getIsAdmin()) {
                User tempUser = this.userList.getUser(id);
                tempUser.setLastName(name);
            } else {
                ((Advisor) this.currentUser).setCurrentStudent(id);
                User tempUser = ((Advisor) this.currentUser).getCurrentStudent();
                tempUser.setLastName(name);
            }    
            return true;
        } else if(this.currentUser.getUserType() == UserType.STUDENT) {
            this.currentUser.setLastName(name);
            return true;
        }
        return false;
    }

    public boolean editUserEmail(UUID id, String email) {
        if (this.currentUser.getUserType() == UserType.ADVISOR) {
            if(((Advisor) this.currentUser).getIsAdmin()) {
                User tempUser = this.userList.getUser(id);
                tempUser.setEmail(email);;
            } else {
                ((Advisor) this.currentUser).setCurrentStudent(id);
                User tempUser = ((Advisor) this.currentUser).getCurrentStudent();
                tempUser.setEmail(email);;
            }    
            return true;
        } else if(this.currentUser.getUserType() == UserType.STUDENT) {
            this.currentUser.setEmail(email);
            return true;
        }
        return false;
    }

    public boolean editUserPassword(UUID id, String password) {
        if (this.currentUser.getUserType() == UserType.ADVISOR) {
            if(((Advisor) this.currentUser).getIsAdmin()) {
                User tempUser = this.userList.getUser(id);
                tempUser.setPassword(password);
            } else {
                ((Advisor) this.currentUser).setCurrentStudent(id);
                User tempUser = ((Advisor) this.currentUser).getCurrentStudent();
                tempUser.setPassword(password);
            }    
            return true;
        } else if(this.currentUser.getUserType() == UserType.STUDENT) {
            this.currentUser.setPassword(password);
            return true;
        }
        return false;
    }

    public boolean deleteUser(UUID id) {
        if (this.currentUser.getUserType() == UserType.ADVISOR) {
            if(((Advisor) this.currentUser).getIsAdmin()) {
                this.userList.removeUser(id);
            } else {
                ((Advisor) this.currentUser).removeStudent(id);
            }    
            return true;
        }
        return false;
    }

    // -------- Advisor --------
    public boolean setCurrentStudent(UUID id) {
        if (this.currentUser instanceof Advisor) {
            ((Advisor) this.currentUser).setCurrentStudent(id);
            return true;
        }
        return false;
    }

    public boolean isStudent() {
        return this.currentUser instanceof Student;
    }

    public Student findStudent(String studentID) {
        if (this.currentUser instanceof Advisor) {
            Student tempStudent = (Student) this.userList.getUser(studentID);
            this.setCurrentStudent(((User) tempStudent).getID());
            return tempStudent;
        }
        return null;
    }
    

    public Student getCurrentStudent() {
        if (this.currentUser.getUserType() == UserType.ADVISOR) {
            return ((Advisor) this.currentUser).getCurrentStudent();
        }
        return (Student) this.currentUser;
    }

    public String getStudentInfo(UUID id) {
        if (this.currentUser.getUserType() == UserType.ADVISOR) {
            if(((Advisor) this.currentUser).getIsAdmin()) {
                return this.userList.getUser(id).toString();
            } else {
                ((Advisor) this.currentUser).setCurrentStudent(id);
                return ((Advisor) this.currentUser).getCurrentStudent().toString();
            }    
        }
        return ((Student) currentUser).toString();
    }

    // ----- Admin Method -----
    public boolean addCourse(String subject, String code, String name, String description, int credit,
        ArrayList<Season> semester, ArrayList<Prerequisites> prerequisites) 
    {
        if(!this.currentUser.getUserType().equals(UserType.ADVISOR) && !((Advisor) this.currentUser).getIsAdmin()) 
        {
            return false;
        }
    
        Course newCourse = new Course(subject, code, name, description, credit, semester, prerequisites);
        this.courseList.addCourse(newCourse);
        return (this.courseList.getCourse(newCourse.getID())) != null;
    }

    public boolean editCourseName(UUID id, String name) {
        if(!this.currentUser.getUserType().equals(UserType.ADVISOR) && !((Advisor) this.currentUser).getIsAdmin()) 
        {
            return false;
        }

        Course course = this.courseList.getCourse(id);
        course.setCourseName(name);
        return true;
    }

    public boolean editCourseSubject(UUID id, String subject) {
        if(!this.currentUser.getUserType().equals(UserType.ADVISOR) && !((Advisor) this.currentUser).getIsAdmin()) 
        {
            return false;
        }

        Course course = this.courseList.getCourse(id);
        course.setSubject(subject);
        return true;
    }

    public boolean editCourseCode(UUID id, String code) {
        if(!this.currentUser.getUserType().equals(UserType.ADVISOR) && !((Advisor) this.currentUser).getIsAdmin()) 
        {
            return false;
        }

        Course course = this.courseList.getCourse(id);
        course.setCode(code);
        return true;
    }

    public boolean editCourseCredit(UUID id, int credit) {
        if(!this.currentUser.getUserType().equals(UserType.ADVISOR) && !((Advisor) this.currentUser).getIsAdmin()) 
        {
            return false;
        }

        Course course = this.courseList.getCourse(id);
        course.setCreditHours(credit);
        return true;
    }

    public boolean editCourseSemesterOffer(UUID id, ArrayList<Season> seasons) {
        if(!this.currentUser.getUserType().equals(UserType.ADVISOR) && !((Advisor) this.currentUser).getIsAdmin()) 
        {
            return false;
        }

        Course course = this.courseList.getCourse(id);
        course.setSemesterOffer(seasons);
        return true;
    }

    public boolean editCoursePrerequisites(UUID id, ArrayList<Prerequisites> Prerequisites) {
        if(!this.currentUser.getUserType().equals(UserType.ADVISOR) && !((Advisor) this.currentUser).getIsAdmin()) 
        {
            return false;
        }

        Course course = this.courseList.getCourse(id);
        course.setPrerequisites(Prerequisites);
        return true;
    }

    public boolean editCourseDescription(UUID id, String description) {
        if(!this.currentUser.getUserType().equals(UserType.ADVISOR) && !((Advisor) this.currentUser).getIsAdmin()) 
        {
            return false;
        }

        Course course = this.courseList.getCourse(id);
        course.setDescription(description);;
        return true;
    }
}
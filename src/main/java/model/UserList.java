package model;
/**
 * @author Paksh Patel
 */
import java.util.ArrayList;
import java.util.UUID;

/**
 * The UserList class represents a list of users and provides methods to manage them.
 * This class implements the Singleton pattern to ensure only one instance exists throughout the application.
 */
public class UserList {
    private static UserList userList;
    private ArrayList<User> users;

    // Private constructor to prevent direct instantiation
    private UserList() {
        this.users = DataReader.loadUser();
    }

    /**
     * Returns the singleton instance of the UserList.
     * @return The singleton instance of the UserList.
     */
    public static UserList getInstance() {
            return userList != null ? userList : new UserList();
    }

    // ----- Accessor -----
    /**
     * Retrieves a user from the list based on their email and password.
     * @param email The email address of the user.
     * @param password The password of the user.
     * @return The user object if found, otherwise null.
     */
    public User getUser(String email, String password) {
        for (User user : this.users) {
            if(user instanceof Advisor) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equalsIgnoreCase(password)) {
                    return user;
                } else {
                    for (Student student : ((Advisor) user).getStudentList()) {
                        if (student.getEmail().equalsIgnoreCase(email) && student.getPassword().equalsIgnoreCase(password)) {
                            return student;
                        }
                    }
                }
            }
        }
        return null;
    }
    public User getUser(String studentID) {
        for (User user : this.users) {
            if(user instanceof Advisor) {
                for (Student student : ((Advisor) user).getStudentList()) {
                    if (student.getStudentID().equalsIgnoreCase(studentID)) {
                        return student;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Retrieves a user from the list based on their first name, last name, and password.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param password The password of the user.
     * @return The user object if found, otherwise null.
     */
    public User getUser(String firstName, String lastName, String password) {
        for (User user : this.users) {
            if (user.getFirstName().equalsIgnoreCase(firstName) &&
                user.getLastName().equalsIgnoreCase(lastName) &&
                user.getPassword().equalsIgnoreCase(password)) 
            {
                return user;
            }
        }
        return null;
    }

    public boolean checkUser(String email) {
        for (User user : this.users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Retrieves a user from the list based on their unique ID.
     * @param id The unique ID of the user.
     * @return The user object if found, otherwise null.
     */
    public User getUser(UUID id) {
        for(User user : this.users) {
            if(user.getID().equals(id)) {
                return user;
            }
        }
        return null;
    }

    // ----- Other user mthod -----
    /**
     * Creates a new user of the specified type and adds it to the list.
     * @param type The type of user to create (e.g., "student" or "advisor").
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param password The password of the user.
     * @param email The email address of the user.
     * @return True if the user was successfully created and added, otherwise false.
     */
    public boolean createUser(String type, String firstName, String lastName, String password, String email, String studentID) {
        User user;
        if(type.equalsIgnoreCase("student")) {
            user = new Student(firstName, lastName, email, password, studentID);
        } else if(type.equalsIgnoreCase("advisor")) {
            user = new Advisor(firstName, lastName, email, password, null);
        } else {
            return false;
        }
        this.users.add(user);
        return true;
    }

    /**
     * Adds a user to the list.
     * @param user The user to add.
     * @return True if the user was successfully added, otherwise false.
     */
    public boolean addUser(User user) {
        this.users.add(user);
        return this.users.contains(user);
    }

    /**
     * Updates a user in the list based on their unique ID.
     * @param id The unique ID of the user to update.
     * @param changed_user The updated user object.
     * @return True if the user was successfully updated, otherwise false.
     */
    public boolean updatedUser(UUID id, User changed_user) {
        for(int i = 0; i < this.users.size(); i++) {
            if(this.users.get(i).getID().equals(changed_user.getID())) {
                this.users.set(i, changed_user);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a user from the list based on their unique ID.
     * @param id The unique ID of the user to remove.
     * @return True if the user was successfully removed, otherwise false.
     */
    public boolean removeUser(UUID id) {
        for (User user : users) {
            if (user.getID().equals(id)) {
                this.users.remove(user);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a user with the specified unique ID exists in the list.
     * @param id The unique ID of the user to check.
     * @return True if the user exists, otherwise false.
     */
    public boolean findUser(UUID id) {
        for (User user : users) {
            if (user.getID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves all users stored in the list.
     * @return An ArrayList containing all users stored in the list.
     */
    public ArrayList<User> getAllUsers() {
        return this.users;
    }

    public boolean writeToFile() {
        DataWriter.writeUser(this.users);
        return true;
    }
}
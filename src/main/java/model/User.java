package model;
import java.util.UUID;
public class User {
    private UUID id;
    private UserType type;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    /**
     * Constructor for User to be created by an admin
     * @return
     */
    public User(String firstName, String lastName, String email, String password, UserType type) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    /**
     * Constructor for User importing data from the database
     * @return
     */
    public User(UUID id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    //added
    public User(String firstName, String lastName, String email, String password) {
        this(UUID.randomUUID(), firstName, lastName, email, password);
    }

    /**
     * Gets the ID of the user
     * @return
     */
    public UUID getID() {
        return id;
    }

    /**
     * Sets the ID of the user
     * @return
     */
    public void setID(UUID id) {
        this.id = id;
    }

    /**
     * Sets the type of user
     * @return
     */
    public void setUserType(UserType type) {
        this.type = type;
    }
    
    /**
     * Returns the type of user
     * @return
     */
    public UserType getUserType() {
        return type;
    }

    /**
     * Gets the first name of the user
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email of the user
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the string representation of the user
     * @return
     */
    public String toString() {
        return "Name: " + this.firstName + " " + this.lastName + 
            "\tEmail: " + this.email;
    }

    /**
     * Returns true if the user is a student
     * @return
     */
    public boolean isStudent() {
        if(this.type == UserType.STUDENT) {
            return true;
        }
        return false;
    }

    /**
     * Compare 2 User object
     * @param User
     * @return boolean
     */
    public boolean equals(User user) {
        return user.getID().equals(this.id);
    }
}
import java.util.ArrayList;
import java.util.List;

/**
 * The User class represents a user with a username and password, and maintains a list of tasks.
 * Provides methods to manage tasks including adding and removing tasks.
 */
public class User {

    private String username;
    private String password;
    private List<Task> tasks;

    /**
     * Constructs a User with a specified username and password, initializing an empty task list.
     *
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the user's task list.
     *
     * @param task the task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the user's task list.
     *
     * @param task the task to be removed
     */
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    /**
     * Retrieves the list of tasks assigned to the user.
     *
     * @return the list of tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    // Getters and setters for username and password can be added here as needed
}

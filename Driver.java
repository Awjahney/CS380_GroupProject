import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalTime;


public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/scheduler_db";
        String username = "root";
        String password = "password";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to the database!");

            // Example usage
            User user = new User(1, "JohnDoe", "securepassword");
            Task newTask = new Task(0, "Finish project", Task.Priority.HIGH, LocalDate.now(), LocalTime.of(15, 0), "Complete phase 3");

            // Save a task
            user.saveTask(connection, newTask);

            // Fetch tasks
            System.out.println("Tasks for user:");
            user.fetchTasks(connection).forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

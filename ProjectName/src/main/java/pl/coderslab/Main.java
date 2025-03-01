package pl.coderslab;

import java.io.*;
import java.util.*;
import org.apache.commons.lang3.ArrayUtils;

public class Main {
    static String[][] tasks;
    static final String FILE_PATH = "/home/patrykw/Java/Zasoby_do_projektu/05_attachment_Zasoby do projektu.pl/ProjectName/src/main/java/pl/coderslab/tasks.csv";

    public static void showMenu() {
        System.out.println(ConsoleColors.BLUE_BOLD + "Please select an option" + ConsoleColors.RESET);
        String[] options = {"add", "remove", "list", "exit"};
        for (String option : options) {
            System.out.println(ConsoleColors.CYAN + option + ConsoleColors.RESET);
        }
    }

    public static void loadTasksFromFile() {
        List<String[]> taskList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                taskList.add(line.split(","));
            }
            tasks = taskList.toArray(new String[0][]);
            System.out.println(ConsoleColors.GREEN_BOLD + "Tasks loaded successfully!" + ConsoleColors.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED_BOLD + "Error reading file: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    public static void saveTasksToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] task : tasks) {
                bw.write(String.join(",", task));
                bw.newLine();
            }
            System.out.println(ConsoleColors.GREEN_BOLD + "Tasks saved successfully!" + ConsoleColors.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED_BOLD + "Error writing file: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    public static void listTasks() {
        if (tasks == null || tasks.length == 0) {
            System.out.println("No tasks available.");
            return;
        }
        for (int i = 0; i < tasks.length; i++) {
            System.out.println(i + ": " + String.join(" ", tasks[i]));
        }
    }

    public static void addTask(Scanner scanner) {
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String date = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        String important = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[]{description, date, important};
        System.out.println(ConsoleColors.GREEN_BOLD + "Task added successfully!" + ConsoleColors.RESET);
    }

    public static void removeTask(Scanner scanner) {
        System.out.println("Please select number to remove.");
        while (true) {
            String input = scanner.nextLine();
            try {
                int index = Integer.parseInt(input);
                if (index < 0 || index >= tasks.length) {
                    throw new NumberFormatException();
                }
                tasks = ArrayUtils.remove(tasks, index);
                System.out.println(ConsoleColors.GREEN_BOLD + "Value was successfully deleted." + ConsoleColors.RESET);
                break;
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED_BOLD + "Incorrect argument. Please enter a valid number." + ConsoleColors.RESET);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadTasksFromFile();

        while (true) {
            showMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "add":
                    addTask(scanner);
                    break;
                case "remove":
                    removeTask(scanner);
                    break;
                case "list":
                    listTasks();
                    break;
                case "exit":
                    saveTasksToFile();
                    System.out.println(ConsoleColors.RED_BOLD + "Bye, bye." + ConsoleColors.RESET);
                    scanner.close();
                    return;
                default:
                    System.out.println(ConsoleColors.YELLOW_BOLD + "Please select a correct option." + ConsoleColors.RESET);
            }
        }
    }
}

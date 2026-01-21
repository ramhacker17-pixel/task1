import java.util.Scanner;

public class StudentGradeTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Input number of students
        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        // Arrays to store data
        String[] names = new String[n];
        double[] grades = new double[n];

        // Input student details
        for (int i = 0; i < n; i++) {
            System.out.println("\nStudent " + (i + 1));
            System.out.print("Enter name: ");
            names[i] = sc.nextLine();

            System.out.print("Enter grade: ");
            grades[i] = sc.nextDouble();
            sc.nextLine(); // consume newline
        }

        // Initialize calculations
        double sum = 0;
        double highest = grades[0];
        double lowest = grades[0];

        // Calculate sum, highest, lowest
        for (int i = 0; i < n; i++) {
            sum += grades[i];

            if (grades[i] > highest) {
                highest = grades[i];
            }

            if (grades[i] < lowest) {
                lowest = grades[i];
            }
        }

        double average = sum / n;

        // Display summary report
        System.out.println("\n===== STUDENT GRADE REPORT =====");
        System.out.println("Name\t\tGrade");

        for (int i = 0; i < n; i++) {
            System.out.println(names[i] + "\t\t" + grades[i]);
        }

        System.out.println("\nAverage Score: " + average);
        System.out.println("Highest Score: " + highest);
        System.out.println("Lowest Score : " + lowest);

        sc.close();
    }
}

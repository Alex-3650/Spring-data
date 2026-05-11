package java_orm.studentManager.ui;

import java_orm.studentManager.entity.Student;
import java_orm.studentManager.repository.StudentRepository;
import java_orm.studentManager.service.StudentService;
import org.w3c.dom.Element;
import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;

//UI layer
public class ConsoleMenu {

    private final Scanner scanner ;
    private StudentService studentService;

    public ConsoleMenu() {
       this.scanner = new Scanner(System.in);
    }

    public void start(){
        try {
            this.studentService = new StudentService();
            showMenu();

            int choice = getInt("Enter your choice: ");
            while (choice != 0) {

                switch (choice){
                   case 1 -> addStudent();
                   case 2 -> findStudentById();
                   case 3 -> listAllStudents();
                   case 4 -> updateStudent();
                   case 5 -> deleteStudent();
                   case 6 -> countAllStudents();
                   case 7 -> searchStudentByPattern();
                   default ->{

                       System.out.println("Invalid choice");
                   }

                }
                choice = getInt("Enter your choice: ");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }finally {
            scanner.close();
        }
    }

    private void addStudent() {
        String name = getString("Enter student name: ");

        int age = getInt("Enter student age: ");

        Student student = new Student(name, age);
        try {
            studentService.addNewStudent(name,age);
            System.out.println("Student with name "+student.getName()+"was added successfully.");
        }catch (IllegalArgumentException e) {
            System.out.println("Invalid input. "+ e.getMessage());
        }catch (Exception e) {
            System.out.println("An error occurred while adding student with name " + name + ".");
        }

    }

    private void findStudentById() {
        int id = getInt("Enter Student ID: ");
        Student student = studentService.findStudentById(id);
    }

    private void listAllStudents() {
        System.out.println("All students:");
        try{
            studentService.getAllStudents().forEach(System.out::println);
        }catch (Exception e){
            System.out.println("An error occurred while fetching all students!.");
        }
    }

    private void updateStudent() {
        int idOfStudentToUpdate = getInt("Enter id of student to update: ");
        String name = getString("Enter the new name: ");
        int age = getInt("Enter the new age: ");

        try {
            studentService.updateStudentById(idOfStudentToUpdate,name,age);
        } catch (RuntimeException e) {
            System.out.println("A problem occurred while updating student with id " + idOfStudentToUpdate + ".");
        }
    }

    private void deleteStudent() {
        try {
            int id = getInt("Enter student id: ");
            studentService.deleteStudentById(id);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private void countAllStudents() {
        try {
            long totalStudents = studentService.getStudentCount();
            System.out.println("Total number of students: " + totalStudents);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void searchStudentByPattern() {
        String pattern = getString("Enter pattern: ");
        studentService.findStudentByPattern(pattern);
    }



    // Helper methods
    private int getInt(String prompt) {
        try {
            System.out.print(prompt);
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
            return -1;
        }
    }
    private String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private void showMenu() {
        System.out.println("\n=== Student Management System === ");
        System.out.println("1. Add a new student");
        System.out.println("2. Find student by ID");
        System.out.println("3. List all students");
        System.out.println("4. Update a student");
        System.out.println("5. Delete a student");
        System.out.println("6. Count all students");
        System.out.println("7. Search by name pattern");
        System.out.println("0. Exit");
        System.out.println("=================================");
    }
}

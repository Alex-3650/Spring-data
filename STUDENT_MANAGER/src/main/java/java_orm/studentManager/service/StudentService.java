package java_orm.studentManager.service;

import java_orm.studentManager.entity.Student;
import java_orm.studentManager.repository.StudentRepository;

import java.util.List;

//Authentication layer
public class StudentService {
    private final StudentRepository repository;


    public StudentService() {
        this.repository = new StudentRepository();
    }

    public void addNewStudent(String name, int age) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        if (age < 4 || age > 90) {
            throw new IllegalArgumentException("Student age must be between 4 and 90.");
        }
        Student student = new Student(name, age);
        repository.save(student);
    }

    public Student findStudentById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Student ID cannot be negative.");
        }
        Student student = repository.findById(id);
        if (student == null) {
            System.out.println("Student with id " + id + " not found.");
            return null;
        }
        System.out.println("Student was found successfully." + student);
        return student;


    }

    public List<Student> getAllStudents() {
      return repository.getAll();
    }
    public long getStudentCount() {
        try {
            return  repository.getStudentsCount();
        } catch (Exception e) {
            throw new RuntimeException("A problem occurred when trying to get the student count.");
        }
    }


    public void updateStudentById(int id,String name, int age) {

        if(id <= 0){
            throw new IllegalArgumentException("Student ID must be positive.");
        }
        Student student = findStudentById(id);
        if (student == null) {
            throw new IllegalArgumentException("Student with id " + id + " not found.");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }
        if (age < 4 || age > 90) {
            throw new IllegalArgumentException("Student age must be between 4 and 90.");
        }

        try{
            repository.updateById(id,name,age);
        }catch (Exception e){
          throw new RuntimeException("Failed to update student with id " + id + ".");
        }
     
    }

    public void deleteStudentById(int id) {
        if (id<=0){
            throw new IllegalArgumentException("Student ID must be positive.");
        }
        Student student = repository.findById(id);
        if (student == null) {
            throw new IllegalArgumentException("Student with id " + id + " not found.");
        }
        try {
            repository.delete(student);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to delete student with id " + id + ".");
        }


    }

    public void findStudentByPattern(String pattern) {
        if (pattern == null || pattern.isBlank()) {
            throw new IllegalArgumentException("Pattern cannot be empty.");
        }
        if (pattern.length() >30) {
            throw new IllegalArgumentException("Pattern must be less than 30 characters.");
        }

        List<Student> byPattern = repository.findByPattern(pattern);
        if (byPattern.isEmpty()) {
            System.out.println("Student with name " + pattern + " not found.");
        }else{
            byPattern.forEach(System.out::println);
        }
    }
}

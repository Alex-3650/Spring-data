package java_orm.studentManager.repository;

import jakarta.persistence.EntityManager;
import java_orm.studentManager.entity.Student;
import java_orm.studentManager.util.JpaUtil;

import java.util.List;

//CRUD operations layer
//AutoClosable interface close(destruct) the EntityManager instance when we commit the transaction to assure that the connection won't be closed during next transaction
public class StudentRepository implements  AutoCloseable {
    private  final EntityManager em;

    public StudentRepository() {
        this.em = JpaUtil.getEntityManager();
    }

    public void save(Student student)  {
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();

        }catch (Exception e) {
            System.out.println(e.getMessage());
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
        }
    }

    public Student findById(int id) {
        try {
         return em.find(Student.class,id);

        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Student> getAll() {
        try {
            return  em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        } catch (IllegalArgumentException e) {
            System.out.println("An error occurred while getting all students");
            return null;
        }
    }

    public void updateById(int id, String name, int age) {

        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class,id);
            if (student == null) {
                throw new IllegalArgumentException("Student with id " + id + " not found.");
            }
            student.setName(name);
            student.setAge(age);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
          if (em.getTransaction().isActive()){
              em.getTransaction().rollback();
          }
        }

    }

    public long getStudentsCount() {

        try {
            return em.createQuery("SELECT COUNT(s) FROM Student s", Long.class).getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void delete(Student student) {
        try {
            em.getTransaction().begin();
            em.remove(student);
            em.getTransaction().begin();
            System.out.println("Student with name "+student.getName()+"was deleted successfully");
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to delete student with id " + student.getId() + ".");
        }

    }

    public List<Student> findByPattern(String pattern) {
        pattern = "%" + pattern + "%";
        String jpql = "SELECT s FROM Student s WHERE s.name LIKE :pattern ORDER BY s.name ASC";
        return em.createQuery(jpql,Student.class).setParameter("pattern",pattern).getResultList();
    }

    @Override
    public void close() throws Exception {
        em.close();
    }
}

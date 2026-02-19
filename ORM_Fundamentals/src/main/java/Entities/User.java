package Entities;

import orm.Annotations.Column;
import orm.Annotations.Entity;
import orm.Annotations.Id;

import java.time.LocalDate;

@Entity(name="users")
public class User {
    @Id
    @Column(name="id")
    private long id;

    @Column(name="username")
    private String username;

    @Column(name="age")
    private int age;

    @Column(name="registration")
    private LocalDate registration;

    @Column(name="email")
    private String email;

    @Column(name="bankBalance")
    private int bankBalance;

    @Column(name="country")
    private String country;

    public User(String username, int age, LocalDate registration, String email, int bankBalance, String country) {
        this.username = username;
        this.age = age;
        this.registration = registration;
        this.email = email;
        this.bankBalance = bankBalance;
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getRegistration() {
        return registration;
    }

    public void setRegistration(LocalDate registration) {
        this.registration = registration;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", registration=" + registration +
                '}';
    }
}

package soft_uni.gameservice.dtos.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRegisterDto {

    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Please provide a valid email address!")
    private final String email;

    @NotBlank(message = "Password cannot be empty!")
    private  String password;

    @NotBlank(message = "Full name cannot be empty!")
    private final String fullName;

    public UserRegisterDto(String email, String password, String fullName) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

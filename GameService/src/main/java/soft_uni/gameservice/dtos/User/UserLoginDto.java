package soft_uni.gameservice.dtos.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserLoginDto {

    @NotBlank(message = "Email cannot be empty!")
    @Email(message = "Please provide a valid email address!")
    private final String email;

    @NotBlank(message = "Password cannot be empty!")
    private final String password;

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

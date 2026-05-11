package soft_uni.mvc.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class UserLoginDto {

    @Size(min = 2, max = 20)
    private String username;
    @Size(min = 2, max = 20)
    private String password;

    public UserLoginDto() {
    }

    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

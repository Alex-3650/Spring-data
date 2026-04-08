package soft_uni.jsonprocessing.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.antlr.v4.runtime.misc.NotNull;

public class UserImportDto {
    @JsonProperty("firstName")
    @NotBlank
    private String firstName;

    @JsonProperty("lastName")
    @NotBlank
    private String lastName;

    @JsonProperty("age")
    @Positive
    private Integer age;
    public UserImportDto() {}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

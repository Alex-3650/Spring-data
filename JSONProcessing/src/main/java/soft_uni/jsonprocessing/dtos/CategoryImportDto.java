package soft_uni.jsonprocessing.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class CategoryImportDto {

    @JsonProperty("name")
    @NotBlank
    private String name;

    public CategoryImportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

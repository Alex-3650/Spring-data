package soft_uni.mvc.dto;

import org.hibernate.validator.constraints.Length;



public class InputCompanyDto {

    @Length(min=3, max=255)
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputCompanyDto() {
    }
}

package soft_uni.jsonprocessing.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserWithSoldProductsDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private List<SoldProductDto> soldProducts;

    public UserWithSoldProductsDto() {
    }

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

    public List<SoldProductDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProductDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}

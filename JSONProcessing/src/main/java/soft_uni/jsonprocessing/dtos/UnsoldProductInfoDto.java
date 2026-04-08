package soft_uni.jsonprocessing.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import soft_uni.jsonprocessing.entities.Product;

import java.math.BigDecimal;

public class UnsoldProductInfoDto {

    @NotBlank
    @NotNull
    private String name;

    private double price;

    @NotBlank
    @NotNull
    private String seller;

    public UnsoldProductInfoDto() {
    }
    public UnsoldProductInfoDto(Product product) {
        this.name = product.getName();
        this.price = product.getPrice().doubleValue();
        String firstName = (product.getSeller().getFirstName()==null ? "" : product.getSeller().getFirstName() + " "  );
        this.seller = firstName +  product.getSeller().getLastName();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}

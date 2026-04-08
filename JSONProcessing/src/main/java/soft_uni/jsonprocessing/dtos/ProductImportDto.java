package soft_uni.jsonprocessing.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class ProductImportDto {

    @JsonProperty
    @NotBlank
   private String name;

    @JsonProperty
    @NotBlank
   private BigDecimal price;

    public ProductImportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

package soft_uni.gameservice.dtos.Game;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class GameInputDto {

    @NotBlank
    private final String title;

    private final String trailer;

    private final String thumbnail;

    @NotNull
    @Positive
    private final BigDecimal price;

    private final String description;

    public GameInputDto(String title, String trailer, String thumbnail, BigDecimal price, String description) {
        this.title = title;
        this.trailer = trailer;
        this.thumbnail = thumbnail;
        this.price = price;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}

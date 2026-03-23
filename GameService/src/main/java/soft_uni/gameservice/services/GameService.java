package soft_uni.gameservice.services;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import soft_uni.gameservice.dtos.Game.GameInputDto;
import soft_uni.gameservice.dtos.Game.GameDto;
import soft_uni.gameservice.entities.Game;

import java.util.List;

@Validated
public interface GameService {

    GameDto create(@Valid GameInputDto game);
    List<GameDto> all();
    Game getRequired(@Valid Long id);
}

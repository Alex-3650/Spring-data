package soft_uni.gameservice.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soft_uni.gameservice.dtos.Game.GameInputDto;
import soft_uni.gameservice.dtos.Game.GameDto;
import soft_uni.gameservice.entities.Game;
import soft_uni.gameservice.repositories.GameRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GameDto create(GameInputDto dto) {
        Game game = modelMapper.map(dto, Game.class);

        game.setReleaseDate(LocalDate.now());
        this.gameRepository.save(game);

        return this.modelMapper.map(game, GameDto.class);
    }

    @Override
    public List<GameDto> all() {
        List<Game> games = this.gameRepository.findAll();
        GameDto[] gamesDto = this.modelMapper.map(games, GameDto[].class);
        return new ArrayList<>(Arrays.asList(gamesDto));
    }

    @Override
    public Game getRequired(Long id) {
        return gameRepository.findById(id).orElseThrow();
    }
}

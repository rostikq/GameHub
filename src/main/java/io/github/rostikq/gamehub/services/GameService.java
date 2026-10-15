package io.github.rostikq.gamehub.services;

import io.github.rostikq.gamehub.dto.GameDto;
import io.github.rostikq.gamehub.entities.Game;
import io.github.rostikq.gamehub.exceptions.IncorrectArgs;
import io.github.rostikq.gamehub.mapper.GameMapper;
import io.github.rostikq.gamehub.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GameService
{
    final GameRepository repository;
    final GameMapper mapper;


    public GameService(GameRepository repository, GameMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void add(GameDto request) throws IncorrectArgs {
        if (request.name().isBlank() || request.genre().isBlank()){
            throw new IncorrectArgs("Incorrect GameDto argument");
        }
        Game game = mapper.dtoToEntity(request);

        if (request.description() == null){
            game.setDescription("");
        }
        if (request.imageUrl() == null){
            game.setImageUrl("");
        }

        repository.save(game);
    }

    public GameDto get(Long id){
        Game game = repository.findById(id).orElseThrow(() -> new NoSuchElementException("There's no entity with id " + id));
        return mapper.entityToDto(game);
    }

    public List<GameDto> getAll(){
        return repository.findAll().stream().map(mapper::entityToDto).toList();
    }

    public void remove(Long id){
        repository.deleteById(id);
    }
}

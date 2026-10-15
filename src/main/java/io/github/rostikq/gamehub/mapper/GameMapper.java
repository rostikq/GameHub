package io.github.rostikq.gamehub.mapper;

import io.github.rostikq.gamehub.dto.GameDto;
import io.github.rostikq.gamehub.entities.Game;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
    public GameDto entityToDto(@NonNull Game game){
        return new GameDto(
                game.getName(),
                game.getDescription(),
                game.getPublicationYear(),
                game.getGenre(),
                game.getImageUrl());
    }

    public Game dtoToEntity(@NonNull GameDto dto){
        return new Game(
                dto.name(),
                dto.genre(),
                dto.description(),
                dto.imageUrl(),
                dto.publicYear()
        );
    }
}

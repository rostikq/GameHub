package io.github.rostikq.gamehub.controllers;

import io.github.rostikq.gamehub.dto.GameDto;
import io.github.rostikq.gamehub.entities.Game;
import io.github.rostikq.gamehub.services.GameService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    public final GameService _service;

    public GameController(GameService service){

        _service = service;
    }

    @PostMapping("/admin/add")
    public String addGame(@RequestBody GameDto request){
        _service.add(request);
        String message = "Game " + request.name() + " has added at" + LocalDateTime.now();
        System.out.println(message);
        return message;
    }

    @DeleteMapping("/admin/remove/{id}")
    public String removeGame(@PathVariable("id") Long id){
        _service.remove(id);
        String message = "Attempted to remove " + id + " game";
        System.out.println(message);
        return message;
    }

    @GetMapping("/get/{id}")
    public GameDto getGame(@PathVariable("id") Long id){

        String message = "Attempted to get " + id + " game";
        System.out.println(message);

        return _service.get(id);
    }




    @GetMapping("/getall")
    public List<GameDto> getAllGames(){
        return _service.getAll();
    }

}

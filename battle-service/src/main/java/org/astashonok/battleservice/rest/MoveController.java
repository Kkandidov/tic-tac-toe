package org.astashonok.battleservice.rest;

import org.astashonok.battleservice.entities.Move;
import org.astashonok.battleservice.services.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/moves")
public class MoveController {

    @Autowired
    private MoveService moveService;

    @GetMapping
    public List<Move> getMoves(@RequestParam Long battleId) {
        return moveService.getMoves(battleId);
    }
}

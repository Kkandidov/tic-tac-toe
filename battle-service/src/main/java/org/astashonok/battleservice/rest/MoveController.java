package org.astashonok.battleservice.rest;

import org.astashonok.battleservice.dtos.MoveDto;
import org.astashonok.battleservice.services.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController("/api/moves")
public class MoveController {

    @Autowired
    private MoveService moveService;

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_battle.read')")
    public List<MoveDto> getMoves(@RequestParam UUID battleId) {
        return moveService.getMoves(battleId);
    }
}

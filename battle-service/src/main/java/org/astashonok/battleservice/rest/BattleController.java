package org.astashonok.battleservice.rest;

import org.astashonok.battleservice.dtos.BattleDto;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.models.BattleState;
import org.astashonok.battleservice.models.MoveForm;
import org.astashonok.battleservice.services.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/battles")
public class BattleController {

    @Autowired
    private BattleService battleService;

    @PostMapping("/make-move")
    public BattleState makeMove(@RequestBody MoveForm moveForm) {
        return battleService.makeMove(moveForm);
    }

    @GetMapping("/opened")
    public List<BattleDto> getOpenedBattles() {
        return battleService.getOpenedBattles();
    }

    @PostMapping("/{battleId}/join")
    public void joinBattle(@PathVariable UUID battleId) {
        battleService.join(battleId);
    }

    @PostMapping
    public BattleDto openBattle(@RequestBody BattleCreationForm form) {
        return battleService.openBattle(form);
    }
}

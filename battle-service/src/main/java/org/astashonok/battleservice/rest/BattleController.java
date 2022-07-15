package org.astashonok.battleservice.rest;

import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.constraints.annotations.JoiningBattleValidated;
import org.astashonok.battleservice.dtos.BattleDto;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.models.BattleState;
import org.astashonok.battleservice.models.MoveForm;
import org.astashonok.battleservice.services.BattleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/battles")
@Validated
@RequiredArgsConstructor
public class BattleController {

    private final BattleService battleService;

    @PostMapping("/make-move")
    public BattleState makeMove(@RequestBody @Validated MoveForm moveForm) {
        return battleService.makeMove(moveForm);
    }

    @GetMapping("/opened")
    public List<BattleDto> getOpenedBattles() {
        return battleService.getOpenedBattles();
    }

    @PostMapping("/{battleId}/join")
    public BattleDto joinBattle(@PathVariable @JoiningBattleValidated UUID battleId) {
        return battleService.join(battleId);
    }

    @PostMapping
    public BattleDto openBattle(@RequestBody @Validated BattleCreationForm form) {
        return battleService.openBattle(form);
    }
}

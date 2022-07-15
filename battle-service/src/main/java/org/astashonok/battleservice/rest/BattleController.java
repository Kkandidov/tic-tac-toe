package org.astashonok.battleservice.rest;

import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.constraints.annotations.JoiningBattleValidated;
import org.astashonok.battleservice.dtos.BattleDto;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.models.BattleState;
import org.astashonok.battleservice.models.MoveForm;
import org.astashonok.battleservice.services.BattleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/battles")
@Validated
@RequiredArgsConstructor
public class BattleController {

    private final BattleService battleService;

    @PostMapping("/make-move")
    @PreAuthorize("hasAuthority('SCOPE_battle.write')")
    public BattleState makeMove(@RequestBody @Validated MoveForm moveForm) {
        return battleService.makeMove(moveForm);
    }

    @GetMapping("/opened")
    @PreAuthorize("hasAuthority('SCOPE_battle.read')")
    public List<BattleDto> getOpenedBattles() {
        return battleService.getOpenedBattles();
    }

    @PostMapping("/{battleId}/join")
    @PreAuthorize("hasAuthority('SCOPE_battle.write')")
    public BattleDto joinBattle(@PathVariable @JoiningBattleValidated UUID battleId) {
        return battleService.join(battleId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_battle.write')")
    public BattleDto openBattle(@RequestBody @Validated BattleCreationForm form) {
        return battleService.openBattle(form);
    }
}

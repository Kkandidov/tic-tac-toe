package org.astashonok.battleservice.rest;

import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleInfo;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.models.MoveForm;
import org.astashonok.battleservice.services.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/battles")
public class BattleController {

    @Autowired
    private BattleService battleService;

    @PostMapping("/{battleId}/make-move")
    public void makeMove(@RequestBody MoveForm moveForm) {
        battleService.makeMove(moveForm);
    }

    @GetMapping("/opened")
    public List<Battle> getOpenedBattles() {
        return battleService.getOpenedBattles();
    }

    @PostMapping("/{battleId}/join")
    public void joinBattle(@PathVariable Long battleId) {
        battleService.join(battleId);
    }

    @PostMapping
    public Battle openBattle(@RequestBody BattleCreationForm form) {
        return battleService.openBattle(form);
    }

    @GetMapping
    public BattleInfo getBattleInfo(@RequestParam Long battleId) {
        return battleService.getBattleInfo(battleId);
    }
}

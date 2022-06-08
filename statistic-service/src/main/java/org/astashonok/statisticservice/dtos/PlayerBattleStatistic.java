package org.astashonok.statisticservice.dtos;

import lombok.Builder;
import lombok.Data;
import org.astashonok.statisticservice.models.PlayerResult;

import java.time.LocalDateTime;

@Data
@Builder
public class PlayerBattleStatistic {

    private String rivalFirstname;
    private String rivalLastname;
    private LocalDateTime gameOverDate;
    private PlayerResult playerResult;
}

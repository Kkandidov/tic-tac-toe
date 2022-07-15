package org.astashonok.statisticservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class BattleStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID id;

    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID firstPlayerId;
    private String firstPlayerName;
    private String firstPlayerLastname;

    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID secondPlayerId;
    private String secondPlayerName;
    private String secondPlayerLastname;

    private LocalDateTime gameOverDate;

    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID winnerId;
}

package org.astashonok.battleservice.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "moves")
@Getter
@Setter
@NoArgsConstructor
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID id;

    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID battleId;

    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID userId;
    private int xCoordinate;
    private int yCoordinate;

    private LocalDateTime time;
}

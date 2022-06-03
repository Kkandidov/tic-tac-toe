package org.astashonok.battleservice.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "moves")
@Getter
@Setter
@NoArgsConstructor
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long battleId;
    private Long userId;
    private int xCoordinate;
    private int yCoordinate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
}

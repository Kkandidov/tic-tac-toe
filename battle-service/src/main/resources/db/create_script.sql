CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE battles
(
    id                        UUID                  DEFAULT uuid_generate_v4(),
    board_height              INTEGER      NOT NULL,
    board_width               INTEGER      NOT NULL,
    board                     integer[][]  NOT NULL,
    winning_number_in_row     INTEGER      NOT NULL,
    status                    VARCHAR(255) NOT NULL DEFAULT 'OPENED' check (status in ('OPENED', 'IN_PROCESS', 'FINISHED')),
    x_participant_id          UUID       NOT NULL,
    o_participant_id          UUID,
    next_move_participant_id  UUID,
    remaining_free_move_count INTEGER      NOT NULL,
    winner_id                 UUID,

    PRIMARY KEY(id)
);

CREATE TABLE moves
(
    id           UUID                 DEFAULT uuid_generate_v4(),
    battle_id    UUID        NOT NULL,
    user_id      UUID        NOT NULL,
    x_coordinate INTEGER     NOT NULL,
    y_coordinate INTEGER     NOT NULL,
    time         TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_battle
        FOREIGN KEY (battle_id)
            REFERENCES battles (id)
            ON DELETE CASCADE
);
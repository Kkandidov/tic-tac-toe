CREATE TABLE battles
(
    id                        BIGSERIAL PRIMARY KEY,
    board_height              INTEGER      NOT NULL,
    board_width               INTEGER      NOT NULL,
    board                     integer[][]  NOT NULL,
    winning_number_in_row     INTEGER      NOT NULL,
    status                    VARCHAR(255) NOT NULL DEFAULT 'OPENED' check (status in ('OPENED', 'IN_PROCESS', 'FINISHED')),
    x_participant_id          BIGINT       NOT NULL,
    o_participant_id          BIGINT,
    next_move_participant_id  BIGINT,
    remaining_free_move_count INTEGER      NOT NULL,
    winner_id                 INTEGER
);

CREATE TABLE moves
(
    id           BIGSERIAL PRIMARY KEY,
    battle_id    BIGINT  NOT NULL,
    user_id      BIGINT  NOT NULL,
    x_coordinate INTEGER NOT NULL,
    y_coordinate INTEGER NOT NULL,
    time         TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
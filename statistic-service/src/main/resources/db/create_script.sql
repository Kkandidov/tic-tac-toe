CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE battle_statistic
(
    id                     UUID                  DEFAULT uuid_generate_v4(),
    first_player_id        UUID         NOT NULL,
    first_player_name      VARCHAR(255) NOT NULL,
    first_player_lastname  VARCHAR(255) NOT NULL,
    second_player_id       UUID         NOT NULL,
    second_player_name     VARCHAR(255) NOT NULL,
    second_player_lastname VARCHAR(255) NOT NULL,
    game_over_date         TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    winner_id              UUID         NOT NULL,

    PRIMARY KEY (id)
);
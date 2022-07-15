CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TYPE ROLE AS ENUM ('USER', 'ADMIN');
CREATE TYPE STATUS AS ENUM ('ACTIVE', 'BANNED');

CREATE TABLE users
(
    id         UUID                  DEFAULT uuid_generate_v4(),
    email      VARCHAR(255) NOT NULL,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    role       ROLE         NOT NULL DEFAULT 'USER',
    status     STATUS       NOT NULL DEFAULT 'ACTIVE'
);

CREATE UNIQUE INDEX user_email_uindex ON users (email);
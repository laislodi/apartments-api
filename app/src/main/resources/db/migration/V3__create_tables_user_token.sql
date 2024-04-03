CREATE TABLE "users" (
    id int NOT NULL,
    username text NOT NULL,
    password text NOT NULL,
    CONSTRAINT "users_pk" primary key ("id")
);

CREATE TABLE "tokens" (
    id int NOT NULL,
    username text NOT NULL,
    token text NOT NULL,
    created_at DATE NOT NULL,
    expired_at DATE NOT NULL,
    CONSTRAINT "tokens_pk" primary key ("id")
);

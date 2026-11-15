CREATE TABLE IF NOT EXISTS public.game_reviews
(
    id bigint NOT NULL PRIMARY KEY,
    rate DECIMAL(2,1) NOT NULL,
    comment varchar(512),
    game_id bigint NOT NULL,
    author_id bigint NOT NULL,
    CONSTRAINT fk_author_id
        FOREIGN KEY (author_id)
        REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_game_id
        FOREIGN KEY (game_id)
        REFERENCES games(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT limit_rate CHECK ( (rate >= 0) AND (rate <= 10) )
);
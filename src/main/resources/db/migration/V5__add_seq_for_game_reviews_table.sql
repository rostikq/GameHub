CREATE SEQUENCE IF NOT EXISTS game_reviews_id_seq;

SELECT setval(
               'game_reviews_id_seq',
               COALESCE((SELECT MAX(id) FROM game_reviews), 0) + 1,
               false
       );

ALTER TABLE game_reviews
    ALTER COLUMN id SET DEFAULT nextval('game_reviews_id_seq');
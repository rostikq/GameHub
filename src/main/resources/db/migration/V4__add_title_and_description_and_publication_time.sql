ALTER TABLE game_reviews
RENAME COLUMN comment TO description;

ALTER TABLE game_reviews
ADD COLUMN title varchar(64);

ALTER TABLE game_reviews
ADD COLUMN publication_time TIMESTAMP WITH TIME ZONE NOT NULL;
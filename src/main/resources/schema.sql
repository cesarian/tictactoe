CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS player CASCADE;
DROP TABLE IF EXISTS game CASCADE;

CREATE TABLE player (
  id SERIAL PRIMARY KEY,
  username TEXT NOT NULL UNIQUE,
  password TEXT NOT NULL
);

CREATE TABLE game (
	id SERIAL PRIMARY KEY,
	player1Id INT references player(id) NOT NULL,
	player2Id INT references player(id),
	game_type TEXT,
	game_state TEXT NOT null,
	winner_id INT references player(id),
	player_turn INT references player(id),
	game_data TEXT
);
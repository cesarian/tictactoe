# Tic-Tac-Toe

Play Tic Tac Toe Using REST

To play you will need to registed and login first.

Build: mvn clean install

Run on docker
1. Run: docker build ./ -t tictactoeapp
2. Run: docker-compose up

Database schema in src/main/resources/schema.sql

-------------------------------------------------------------------------

Register (POST)

URL: http://localhost:8080/player/register

Example request:

{
	"username": "user",
	"password": "pass"
}

-------------------------------------------------------------------------

Login (POST)

URL: http://localhost:8080/player/login

Example request:

{
	"username": "user",
	"password": "pass"
}

Login endpoint will send a JWT in response. Please add in as HTTP Request Authorization header for each call to any game endpoint

Example request header:

Authorization: Bearer %JWTTOKENFROMLOGIN%

-------------------------------------------------------------------------

Get all open games (GET)

URL: http://localhost:8080/game/opengames

-------------------------------------------------------------------------

Get all games (GET)

URL: http://localhost:8080/game/allgames

-------------------------------------------------------------------------

Start a new game (POST)

URL: http://localhost:8080/game/start

Empty request sufficient

-------------------------------------------------------------------------

Join a game (POST)

URL: http://localhost:8080/game/join

Example request: 

{
	"gameId": 43
}

-------------------------------------------------------------------------

Leave a game (POST)

URL: http://localhost:8080/game/leave

Example request: 

{
	"gameId": 43
}

-------------------------------------------------------------------------

Make a move (POST)

URL: http://localhost:8080/game/move

Example request: 

{
	"gameId": 43,
	"symbol": "x",
	"position": 9
}

Symbol is "x" for the game owner/starter (player 1) and "o" for the player that joined the game (player 2)

Position

There are 1 to 9 positions. Put a symbol in one of the positions. Position schema:

1 2 3

4 5 6

7 8 9

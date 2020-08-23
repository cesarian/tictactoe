# Tic-Tac-Toe

Play Tic Tac Toe Using REST

To play you will need to registed and login first.

Build: mvn clean install

Run on docker
1. Run: docker build ./ -t tictactoeapp
2. Run: docker-compose up

Database schema in src/main/resources/schema.sql

Register (POST)

URL: http://localhost:8080/player/http://localhost:8080/player/register

Example request to register: 

{

	"username": "user",
	
	"password": "pass"
	
}

Login (POST)

URL: http://localhost:8080/player/http://localhost:8080/player/login

Example request to login: 

{
	"username": "user",
	
	"password": "pass"
	
}

Login endpoint will send a JWT in response. Please add in as HTTP Request Authorization header for each call to any game endpoint

Example request header:

Authorization: Bearer %JWTTOKENFROMLOGIN%

Get all open games (GET)

URL: http://localhost:8080/game/opengames

Get all games (GET)

URL: http://localhost:8080/game/allgames

Start a new game (POST)

URL: http://localhost:8080/game/start

Empty request sufficient

Join a game (POST)

URL: http://localhost:8080/player/http://localhost:8080/player/login

Example request to login: 

{

	"gameId": 43
	
}

Make a move (POST)

URL: http://localhost:8080/player/http://localhost:8080/player/login

Example request to login: 

{

	"gameId": 43,
	
	"symbol": "x",
	
	"position": 9
	
}

Symbol is "x" for the game owner/starter (player 1) and "o" for the player that joined the game (player 2)
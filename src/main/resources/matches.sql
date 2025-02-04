CREATE TABLE IF NOT EXISTS MATCHES (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_player_id INT,
    second_player_id INT,
    winner_id INT,
    FOREIGN KEY(first_player_id) REFERENCES PLAYERS(id) ,
    FOREIGN KEY(second_player_id) REFERENCES PLAYERS(id) ,
    FOREIGN KEY(winner_id) REFERENCES PLAYERS(id)
);
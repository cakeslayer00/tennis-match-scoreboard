package com.vladsv.tennismatchscoreboard.model.score;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Score {
    private int sets;
    private int games;
    private int tieBreakCounter;
    private Point point = Point.ZERO;

    public void incrementSets() {
        sets++;
    }

    public void incrementGames() {
        games++;
    }

    public void incrementTieBreakCounter() {
        tieBreakCounter++;
    }

    public void incrementPoint() {
        point = point.next();
    }
}

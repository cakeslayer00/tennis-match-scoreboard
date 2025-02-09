package com.vladsv.tennismatchscoreboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Score {
    @Getter
    @Setter
    public static class PlayerScore {
        private int sets = 0;
        private int games = 0;
        private int tieBreakCounter = 0;
        private Point point = Point.ZERO;

        private PlayerScore() {
        }

        public void increaseGamesCounter() {
            games++;
        }

        public void increaseSetsCounter() {
            sets++;
        }

        public void increaseTieBreakCounter() {
            tieBreakCounter++;
        }
    }

    private Map<String, PlayerScore> playerScoreMap;

    public Score() {
        playerScoreMap = new HashMap<>();
        playerScoreMap.put("1", new PlayerScore());
        playerScoreMap.put("2", new PlayerScore());
    }
}

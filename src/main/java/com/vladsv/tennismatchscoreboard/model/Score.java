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
        private int wonSets = 0;
        private int wonGames = 0;
        private int tieBreakCounter = 0;
        private Point currentPoint = Point.ZERO;

        private PlayerScore() {
        }

        public void incrementWonGamesCount() {wonGames++;}
        public void incrementWonSetsCount() {wonSets++;}
        public void incrementTieBreakCounter() {tieBreakCounter++;}
    }

    private Map<String, PlayerScore> playerScoreMap;

    public Score() {
        playerScoreMap = new HashMap<>();
        playerScoreMap.put("1", new PlayerScore());
        playerScoreMap.put("2", new PlayerScore());
    }
}

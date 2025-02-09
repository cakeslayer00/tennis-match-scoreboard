package com.vladsv.tennismatchscoreboard.service;

import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import com.vladsv.tennismatchscoreboard.model.Score;
import com.vladsv.tennismatchscoreboard.model.MatchState;
import com.vladsv.tennismatchscoreboard.model.Point;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class MatchScoreCalculationService {

    public void updateScore(OngoingMatch ongoingMatch, String winnerId) {
        if (!winnerId.matches("[1-2]+")) {
            throw new IllegalArgumentException("Invalid winner id");
        }

        Score matchScore = ongoingMatch.getScore();
        Score.PlayerScore winnerScore = matchScore.getPlayerScoreMap().get(winnerId);
        Point winnerPoint = winnerScore.getPoint();

        switch (ongoingMatch.getMatchState()) {
            case ONGOING -> {
                if (winnerPoint.equals(Point.FORTY)) {
                    winnerScore.increaseGamesCounter();
                    setPlayersPoint(matchScore, Point.ZERO);
                } else {
                    winnerScore.setPoint(winnerPoint.next());
                }
            }
            case DEUCE -> {
                if (!hasOpponentAdvantage(matchScore, winnerId)) {
                    if (winnerPoint.equals(Point.ADVANTAGE)) {
                        winnerScore.increaseGamesCounter();
                        setPlayersPoint(matchScore, Point.ZERO);
                        ongoingMatch.setMatchState(MatchState.ONGOING);
                    } else {
                        winnerScore.setPoint(winnerPoint.next());
                    }
                } else {
                    setPlayersPoint(matchScore, Point.FORTY);
                }
            }
            case TIE_BREAK -> {
                winnerScore.increaseTieBreakCounter();
                if (hasPlayerWonTieBreak(matchScore, winnerId)) {
                    winnerScore.increaseSetsCounter();
                    resetPlayersWonGames(matchScore);
                    resetPlayersPoint(matchScore);
                    ongoingMatch.setMatchState(MatchState.ONGOING);
                }
            }
        }

        if (hasPlayerWonSet(matchScore, winnerId)) {
            winnerScore.increaseSetsCounter();
            resetPlayersTieBreakCounters(matchScore);
            resetPlayersWonGames(matchScore);
            resetPlayersPoint(matchScore);
            ongoingMatch.setMatchState(MatchState.ONGOING);
        }
    }

    public void updateMatchState(OngoingMatch ongoingMatch) {
        if (isDeuce(ongoingMatch.getScore())) {
            ongoingMatch.setMatchState(MatchState.DEUCE);
        }

        if (isTieBreak(ongoingMatch.getScore())) {
            ongoingMatch.setMatchState(MatchState.TIE_BREAK);
        }
    }

    public boolean isMatchFinished(OngoingMatch ongoingMatch) {
        return ongoingMatch.getScore().getPlayerScoreMap().entrySet().stream().filter(
                entry -> entry.getValue().getSets() >= 2
        ).findFirst().orElse(null) != null;
    }

    public boolean isTieBreak(Score score) {
        Map<String, Score.PlayerScore> playerScoreMap = score.getPlayerScoreMap();
        Score.PlayerScore firstPlayerScore = playerScoreMap.get("1");
        Score.PlayerScore secondPlayerScore = playerScoreMap.get("2");

        int diff = Math.abs(firstPlayerScore.getGames() - secondPlayerScore.getGames());
        return diff == 0 && firstPlayerScore.getGames() == 6;
    }

    private boolean isDeuce(Score score) {
        Map<String, Score.PlayerScore> playerScoreMap = score.getPlayerScoreMap();

        Score.PlayerScore firstPlayerScore = playerScoreMap.get("1");
        Score.PlayerScore secondPlayerScore = playerScoreMap.get("2");
        Point firstPlayerPoint = firstPlayerScore.getPoint();
        Point secondPlayerPoint = secondPlayerScore.getPoint();

        return firstPlayerPoint.equals(secondPlayerPoint) && firstPlayerPoint.equals(Point.FORTY);
    }

    private boolean hasOpponentAdvantage(Score score, String winner) {
        return score.getPlayerScoreMap().entrySet()
                .stream().filter(
                        entry -> !Objects.equals(entry.getKey(), winner)
                ).findFirst().orElseThrow().getValue().getPoint().equals(Point.ADVANTAGE);
    }

    private boolean hasPlayerWonTieBreak(Score score, String winner) {
        Score.PlayerScore winnerScore = score.getPlayerScoreMap().get(winner);
        Score.PlayerScore opponentScore = score.getPlayerScoreMap().entrySet()
                .stream().filter(
                        entry -> !Objects.equals(entry.getKey(), winner)
                ).findFirst().orElseThrow().getValue();

        int winnerPoints = winnerScore.getTieBreakCounter();
        int loserPoints = opponentScore.getTieBreakCounter();
        return (winnerPoints >= 7 || loserPoints >= 7) && Math.abs(winnerPoints - loserPoints) >= 2;
    }

    private boolean hasPlayerWonSet(Score score, String winner) {
        Map<String, Score.PlayerScore> playerScoreMap = score.getPlayerScoreMap();
        Score.PlayerScore winnerScore = playerScoreMap.get(winner);
        Score.PlayerScore opponentScore = score.getPlayerScoreMap().entrySet()
                .stream().filter(
                        entry -> !Objects.equals(entry.getKey(), winner)
                ).findFirst().orElseThrow().getValue();

        int diff = Math.abs(winnerScore.getGames() - opponentScore.getGames());
        return diff >= 2 && winnerScore.getGames() >= 6;
    }

    private void setPlayersPoint(Score score, Point point) {
        score.getPlayerScoreMap().forEach((currentPoint, scorePlayerScore) -> scorePlayerScore.setPoint(point));
    }

    private void resetPlayersTieBreakCounters(Score score) {
        score.getPlayerScoreMap().forEach(
                (currentPoint, scorePlayerScore) -> scorePlayerScore.setTieBreakCounter(0)
        );
    }

    private void resetPlayersWonGames(Score score) {
        score.getPlayerScoreMap().forEach(
                (currentPoint, scorePlayerScore) -> scorePlayerScore.setGames(0)
        );
    }

    private void resetPlayersPoint(Score score) {
        setPlayersPoint(score, Point.ZERO);
    }
}

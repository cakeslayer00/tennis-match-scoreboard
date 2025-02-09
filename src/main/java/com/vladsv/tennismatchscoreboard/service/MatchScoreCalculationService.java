package com.vladsv.tennismatchscoreboard.service;

import com.vladsv.tennismatchscoreboard.dao.OngoingMatchDao;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import com.vladsv.tennismatchscoreboard.model.Score;
import com.vladsv.tennismatchscoreboard.model.MatchState;
import com.vladsv.tennismatchscoreboard.model.Point;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class MatchScoreCalculationService {

    private final OngoingMatchDao ongoingMatchDao;

    public void updateScore(UUID matchId, String winnerId) {
        if (!winnerId.matches("[1-2]+")) {
            throw new IllegalArgumentException("Invalid winner id");
        }

        OngoingMatch ongoingMatch = ongoingMatchDao.findById(matchId).orElseThrow(
                () -> new RuntimeException("Could not find match with id " + matchId)
        );
        Score matchScore = ongoingMatch.getScore();
        Score.PlayerScore winnerScore = matchScore.getPlayerScoreMap().get(winnerId);
        Point winnerPoint = winnerScore.getCurrentPoint();

        switch (ongoingMatch.getMatchState()) {
            case ONGOING -> {
                if (winnerPoint.equals(Point.FORTY)) {
                    winnerScore.incrementWonGamesCount();
                    setPlayersPoint(matchScore, Point.ZERO);
                } else {
                    winnerScore.setCurrentPoint(winnerPoint.next());
                }
            }
            case DEUCE -> {
                if (!hasOpponentAdvantage(matchScore, winnerId)) {
                    if (winnerPoint.equals(Point.ADVANTAGE)) {
                        winnerScore.incrementWonGamesCount();
                        setPlayersPoint(matchScore, Point.ZERO);
                        ongoingMatch.setMatchState(MatchState.ONGOING);
                    } else {
                        winnerScore.setCurrentPoint(winnerPoint.next());
                    }
                } else {
                    setPlayersPoint(matchScore, Point.FORTY);
                }
            }
            case TIE_BREAK -> {
                if (hasPlayerWonTieBreak(matchScore, winnerId)) {
                    winnerScore.incrementWonSetsCount();
                    resetPlayersWonGames(matchScore);
                    resetPlayersPoint(matchScore);
                    ongoingMatch.setMatchState(MatchState.ONGOING);
                } else {
                    winnerScore.incrementTieBreakCounter();
                }
            }
        }

        if (hasPlayerWonSet(matchScore, winnerId)) {
            winnerScore.incrementWonSetsCount();
            resetPlayersWonGames(matchScore);
            resetPlayersPoint(matchScore);
            ongoingMatch.setMatchState(MatchState.ONGOING);
        }
    }

    public void updateMatchState(UUID matchId) {
        OngoingMatch ongoingMatch = ongoingMatchDao.findById(matchId).orElseThrow(
                () -> new RuntimeException("Could not find match with id " + matchId)
        );

        if (isDeuce(ongoingMatch.getScore())) {
            ongoingMatch.setMatchState(MatchState.DEUCE);
        }

        if (isTieBreak(ongoingMatch.getScore())) {
            ongoingMatch.setMatchState(MatchState.TIE_BREAK);
        }
    }

    public boolean hasPlayerWonMatch(UUID matchId , String winnerId) {
        OngoingMatch ongoingMatch = ongoingMatchDao.findById(matchId).orElseThrow(
                () -> new RuntimeException("Could not find match with id " + matchId)
        );

        return ongoingMatch.getScore().getPlayerScoreMap().get(winnerId).getWonSets() >= 2;
    }

    public boolean isTieBreak(Score score) {
        Map<String, Score.PlayerScore> playerScoreMap = score.getPlayerScoreMap();
        Score.PlayerScore firstPlayerScore = playerScoreMap.get("1");
        Score.PlayerScore secondPlayerScore = playerScoreMap.get("2");

        int diff = Math.abs(firstPlayerScore.getWonGames() - secondPlayerScore.getWonGames());
        return diff == 0 && firstPlayerScore.getWonGames() == 6;
    }

    private boolean isDeuce(Score score) {
        Map<String, Score.PlayerScore> playerScoreMap = score.getPlayerScoreMap();

        Score.PlayerScore firstPlayerScore = playerScoreMap.get("1");
        Score.PlayerScore secondPlayerScore = playerScoreMap.get("2");
        Point firstPlayerPoint = firstPlayerScore.getCurrentPoint();
        Point secondPlayerPoint = secondPlayerScore.getCurrentPoint();

        return firstPlayerPoint.equals(secondPlayerPoint) && firstPlayerPoint.equals(Point.FORTY);
    }

    private boolean hasOpponentAdvantage(Score score, String winner) {
        return score.getPlayerScoreMap().entrySet()
                .stream().filter(
                        entry -> !Objects.equals(entry.getKey(), winner)
                ).findFirst().orElseThrow().getValue().getCurrentPoint().equals(Point.ADVANTAGE);
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

        int diff = Math.abs(winnerScore.getWonGames() - opponentScore.getWonGames());
        return diff >= 2 && winnerScore.getWonGames() >= 6;
    }

    private void setPlayersPoint(Score score, Point point) {
        score.getPlayerScoreMap().forEach((currentPoint, scorePlayerScore) -> scorePlayerScore.setCurrentPoint(point));
    }

    private void resetPlayersWonGames(Score score) {
        score.getPlayerScoreMap().forEach((currentPoint, scorePlayerScore) -> scorePlayerScore.setWonGames(0));
    }

    private void resetPlayersPoint(Score score) {
        setPlayersPoint(score, Point.ZERO);
    }
}

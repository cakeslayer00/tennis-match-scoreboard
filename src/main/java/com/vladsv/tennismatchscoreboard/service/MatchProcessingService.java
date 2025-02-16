package com.vladsv.tennismatchscoreboard.service;

import com.vladsv.tennismatchscoreboard.dao.impl.OngoingMatchDao;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class MatchProcessingService {

    private final MatchScoreCalculationService calculationService;
    private final FinishedMatchService finishedMatchService;
    private final OngoingMatchDao ongoingMatchDao;

    public OngoingMatch getProcessedMatch(UUID matchId, String winnerId) {
        OngoingMatch ongoingMatch = ongoingMatchDao.findById(matchId).orElseThrow(
                () -> new IllegalArgumentException("Match with current id doesn't exist")
        );

        calculationService.updateMatchScore(ongoingMatch, winnerId);

        if (ongoingMatch.isMatchFinished()) {
            ongoingMatch.setWinnerPlayer(
                    calculationService.getWinnerInstance(ongoingMatch, winnerId)
            );
            finishedMatchService.mapToFinished(ongoingMatch);
            ongoingMatchDao.delete(matchId);
        }

        return ongoingMatch;
    }

}

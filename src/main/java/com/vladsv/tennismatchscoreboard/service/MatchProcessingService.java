package com.vladsv.tennismatchscoreboard.service;

import com.vladsv.tennismatchscoreboard.dao.impl.FinishedMatchDao;
import com.vladsv.tennismatchscoreboard.dao.impl.OngoingMatchDao;
import com.vladsv.tennismatchscoreboard.model.FinishedMatch;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.UUID;

@RequiredArgsConstructor
public class MatchProcessingService {

    private final MatchScoreCalculationService calculationService;
    private final OngoingMatchDao ongoingMatchDao;
    private final FinishedMatchDao finishedMatchDao;
    private final ModelMapper mapper = new ModelMapper();

    public OngoingMatch getProcessedMatch(UUID matchId, String winnerId) {
        OngoingMatch ongoingMatch = ongoingMatchDao.findById(matchId).orElseThrow(
                () -> new IllegalArgumentException("Match with current id doesn't exist")
        );

        calculationService.updateMatchScore(ongoingMatch, winnerId);

        if (ongoingMatch.isMatchFinished()) {
            ongoingMatch.setWinnerPlayer(calculationService.getWinnerInstance(ongoingMatch, winnerId));
            finishedMatchDao.persist(mapToFinished(ongoingMatch));
            ongoingMatchDao.delete(matchId);
        }

        return ongoingMatch;
    }

    private FinishedMatch mapToFinished(OngoingMatch ongoingMatch) {
        return mapper.map(ongoingMatch, FinishedMatch.class);
    }

}

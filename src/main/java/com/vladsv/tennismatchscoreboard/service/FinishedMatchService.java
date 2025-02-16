package com.vladsv.tennismatchscoreboard.service;

import com.vladsv.tennismatchscoreboard.dao.impl.FinishedMatchDao;
import com.vladsv.tennismatchscoreboard.model.FinishedMatch;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class FinishedMatchService {

    private final FinishedMatchDao finishedMatchDao;
    private final ModelMapper mapper = new ModelMapper();

    public void mapToFinished(OngoingMatch ongoingMatch) {
        FinishedMatch finishedMatch = mapper.map(ongoingMatch, FinishedMatch.class);
        finishedMatchDao.persist(finishedMatch);
    }

}

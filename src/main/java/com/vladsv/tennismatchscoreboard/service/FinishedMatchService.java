package com.vladsv.tennismatchscoreboard.service;

import com.vladsv.tennismatchscoreboard.dao.impl.FinishedMatchDao;
import com.vladsv.tennismatchscoreboard.model.FinishedMatch;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import org.modelmapper.ModelMapper;

public class FinishedMatchService {

    private final FinishedMatchDao finishedMatchDao;
    private final ModelMapper modelMapper;

    public FinishedMatchService(FinishedMatchDao finishedMatchDao) {
        this.finishedMatchDao = finishedMatchDao;
        modelMapper = new ModelMapper();
    }

    public void proceedMatch(OngoingMatch ongoingMatch) {
        FinishedMatch match = modelMapper.map(ongoingMatch, FinishedMatch.class);
        finishedMatchDao.persist(match);
    }

}

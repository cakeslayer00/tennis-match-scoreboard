package com.vladsv.tennismatchscoreboard.service;

import com.vladsv.tennismatchscoreboard.dao.impl.FinishedMatchDao;
import com.vladsv.tennismatchscoreboard.model.FinishedMatch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FinishedMatchesService {

    private static final int ELEMENTS_PER_PAGE = 6;
    private final FinishedMatchDao finishedMatchDao;

    public int getQuantityOfPages(String filter) {
        long matchesCount = filter.isEmpty()
                ? finishedMatchDao.getMatchesCount()
                : finishedMatchDao.getFilteredMatchesCount(filter);
        return (int) Math.ceil(matchesCount / (double) ELEMENTS_PER_PAGE);
    }

    public List<FinishedMatch> getMatches(String page, String filter) {
        return filter.isEmpty()
                ? finishedMatchDao.findByPage(Integer.parseInt(page), ELEMENTS_PER_PAGE)
                : finishedMatchDao.findByFilter(Integer.parseInt(page), ELEMENTS_PER_PAGE, filter);
    }

}

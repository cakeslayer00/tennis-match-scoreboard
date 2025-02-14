package com.vladsv.tennismatchscoreboard.utils;

import com.vladsv.tennismatchscoreboard.dto.OngoingMatchViewDto;
import com.vladsv.tennismatchscoreboard.dto.ScoreViewDto;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import com.vladsv.tennismatchscoreboard.model.Score;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

public class CustomizedMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        TypeMap<Score, ScoreViewDto> scoreTypeMap = modelMapper.createTypeMap(Score.class, ScoreViewDto.class);
        scoreTypeMap.addMappings(mapper -> {
            mapper.map(Score::getSets, ScoreViewDto::setSets);
            mapper.map(Score::getGames, ScoreViewDto::setGames);
            mapper.map(Score::getPoint, ScoreViewDto::setPoint);
        });

        TypeMap<OngoingMatch, OngoingMatchViewDto> matchTypeMap = modelMapper.createTypeMap(OngoingMatch.class, OngoingMatchViewDto.class);
        matchTypeMap.addMappings(mapper -> {
            mapper.map(src -> src.getFirstPlayer().getName(), OngoingMatchViewDto::setFirstPlayerName);
            mapper.map(src -> src.getSecondPlayer().getName(), OngoingMatchViewDto::setSecondPlayerName);
            mapper.map(src -> modelMapper.map(src.getFirstPlayerScore(), ScoreViewDto.class), OngoingMatchViewDto::setFirstPlayerScore);
            mapper.map(src -> modelMapper.map(src.getSecondPlayerScore(), ScoreViewDto.class), OngoingMatchViewDto::setSecondPlayerScore);
        });
    }

    public static OngoingMatchViewDto toOngoingMatchViewDto(OngoingMatch ongoingMatch) {
        return modelMapper.map(ongoingMatch, OngoingMatchViewDto.class);
    }
}

package com.vladsv.tennismatchscoreboard.service;

import com.vladsv.tennismatchscoreboard.dao.OngoingMatchDao;
import com.vladsv.tennismatchscoreboard.dao.PlayerDao;
import com.vladsv.tennismatchscoreboard.dto.NewMatchRequestDto;
import com.vladsv.tennismatchscoreboard.model.OngoingMatch;
import com.vladsv.tennismatchscoreboard.model.Player;
import com.vladsv.tennismatchscoreboard.model.Score;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class NewMatchService {

    private final PlayerDao playerDao;
    private final OngoingMatchDao ongoingMatchDao;

    public void initiateNewMatch(UUID matchId, NewMatchRequestDto requestDto) {
        String firstPlayerName = requestDto.getFirstPlayerName();
        String secondPlayerName = requestDto.getSecondPlayerName();

        Player firstPlayer = getExistingPlayer(firstPlayerName);
        Player secondPlayer = getExistingPlayer(secondPlayerName);

        OngoingMatch ongoingMatch = OngoingMatch.builder()
                .firstPlayerId(firstPlayer.getId())
                .secondPlayerId(secondPlayer.getId())
                .score(new Score(new HashMap<>()))
                .build();

        ongoingMatchDao.persist(matchId, ongoingMatch);
    }

    private Player getExistingPlayer(String playerName) {
        Optional<Player> player = playerDao.findByName(playerName);
        if (player.isEmpty()) {
            playerDao.persist(Player.builder().name(playerName).build());
        }
        return playerDao.findByName(playerName).orElseThrow(
                () -> new IllegalArgumentException("Player not found: " + playerName)
        );
    }
}

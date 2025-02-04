package com.vladsv.tennismatchscoreboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class NewMatchRequestDto {

    private String firstPlayerName;
    private String secondPlayerName;

}

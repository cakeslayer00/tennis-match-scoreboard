package com.vladsv.tennismatchscoreboard.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "match")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinishedMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "first_player_id", referencedColumnName = "id")
    private Player firstPlayer;

    @ManyToOne
    @JoinColumn(name = "second_player_id", referencedColumnName = "id")
    private Player secondPlayer;

    @ManyToOne
    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    private Player winner;

}

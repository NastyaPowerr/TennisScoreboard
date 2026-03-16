package org.roadmap.tennisscoreboard.domain;

import lombok.Getter;
import lombok.Setter;
import org.roadmap.tennisscoreboard.dto.PlayerDto;

import java.util.HashMap;
import java.util.Map;

@Getter
public class OngoingMatch {
    private final Score scoreModel;
    private final Map<Integer, SetScoreInfo> setsHistory;
    private final PlayerDto firstPlayer;
    private final PlayerDto secondPlayer;
    @Setter
    private boolean tiebreak;
    @Setter
    private boolean finished;

    public OngoingMatch(PlayerDto firstPlayer, PlayerDto secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.scoreModel = new Score();
        this.setsHistory = new HashMap<>();
        this.tiebreak = false;
        this.finished = false;
    }
}

package com.example.cryptoguess.service;


import com.example.cryptoguess.model.*;
import com.example.cryptoguess.util.Randoms;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final CoinGeckoClient coinGecko;
    private final BadgeService badgeService;

    public GameRound newRound(GameSession session) {
        List<Coin> top = coinGecko.fetchTop50();
        int[] pick = Randoms.twoDistinct(top.size());
        Coin A = top.get(pick[0]);
        Coin B = top.get(pick[1]);
        String correct = A.getPriceUsd() >= B.getPriceUsd() ? "A" : "B";
        GameRound round = GameRound.builder()
                .coinA(A).coinB(B).correct(correct)
                .answered(false).correctGuess(false).build();
        session.setCurrentRound(round);
        return round;
    }

    public GameRound answer(GameSession session, String choice) {
        GameRound r = session.getCurrentRound();
        if (r == null) return null;
        if (r.isAnswered()) return r;

        boolean ok = r.getCorrect().equalsIgnoreCase(choice);
        r.setAnswered(true);
        r.setCorrectGuess(ok);

        session.setRounds(session.getRounds() + 1);
        if (ok) {
            session.setScore(session.getScore() + 1);
            badgeService.updateBadges(session);
        }
        return r;
    }
}

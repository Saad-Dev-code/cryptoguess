package com.example.cryptoguess.service;



import com.example.cryptoguess.model.Badge;
import com.example.cryptoguess.model.GameSession;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

@Service
public class BadgeService {

    public void updateBadges(GameSession session) {
        Arrays.stream(Badge.values())
              .filter(b -> session.getScore() >= b.getThreshold())
              .forEach(session.getBadges()::add);
    }

    /** Highest badge already earned for this score */
    public Optional<Badge> currentBadge(int score) {
        return Arrays.stream(Badge.values())
                .filter(b -> score >= b.getThreshold())
                .max(Comparator.comparingInt(Badge::getThreshold));
    }

    /** Next badge that is not yet reached */
    public Optional<Badge> nextBadge(int score) {
        return Arrays.stream(Badge.values())
                .filter(b -> score < b.getThreshold())
                .min(Comparator.comparingInt(Badge::getThreshold));
    }

    /** Progress (0..100) from previous badge threshold to next badge threshold */
    public int progressToNext(int score) {
        int prev = currentBadge(score).map(Badge::getThreshold).orElse(0);
        int next = nextBadge(score).map(Badge::getThreshold).orElse(prev);
        if (next == prev) return 100;                 // already at max badge
        double pct = (double)(score - prev) / (next - prev);
        return (int)Math.max(0, Math.min(100, Math.round(pct * 100)));
    }

    /** How many correct left for the next badge (0 if none) */
    public int remainingToNext(int score) {
        return nextBadge(score).map(b -> Math.max(0, b.getThreshold() - score)).orElse(0);
    }
}

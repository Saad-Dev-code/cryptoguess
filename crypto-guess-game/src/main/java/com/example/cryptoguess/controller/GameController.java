package com.example.cryptoguess.controller;


import com.example.cryptoguess.model.GameRound;
import com.example.cryptoguess.model.GameSession;
import com.example.cryptoguess.service.BadgeService;
import com.example.cryptoguess.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class GameController {
    private final GameService gameService;
    private final GameSession session;
    private final BadgeService badgeService;

    @PostMapping("/reset")
    public String reset() {
        session.reset();
        return "redirect:/";
    }

    @GetMapping("/play")
    public String play(Model model) {
        if (session.getUsername() == null) return "redirect:/login";
        GameRound round = gameService.newRound(session);
        model.addAttribute("round", round);
        model.addAttribute("session", session);
        model.addAttribute("info", "Prices may be cached or demo if the API is rate-limited.");
        addHud(model);
        return "play";
    }

    @PostMapping("/answer")
    public String answer(@RequestParam("choice") String choice) {
        if (session.getUsername() == null) return "redirect:/login";
        gameService.answer(session, choice);
        return "redirect:/result";
    }

    @GetMapping("/result")
    public String result(Model model) {
        if (session.getUsername() == null) return "redirect:/login";
        GameRound r = session.getCurrentRound();
        if (r == null) return "redirect:/play";
        model.addAttribute("round", r);
        model.addAttribute("session", session);
        addHud(model);
        return "result";
    }

    private void addHud(Model model) {
        int score = session.getScore();
        model.addAttribute("score", score);
        model.addAttribute("currentBadge", badgeService.currentBadge(score).orElse(null));
        model.addAttribute("nextBadge", badgeService.nextBadge(score).orElse(null));
        model.addAttribute("progressPct", badgeService.progressToNext(score));
        model.addAttribute("remaining", badgeService.remainingToNext(score));
    }
}


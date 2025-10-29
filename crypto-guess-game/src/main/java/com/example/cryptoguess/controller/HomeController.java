package com.example.cryptoguess.controller;



import com.example.cryptoguess.model.GameSession;
import com.example.cryptoguess.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final GameSession session;
    private final BadgeService badgeService;

    @GetMapping("/")
    public String home(Model model) {
        if (session.getUsername() == null) return "redirect:/login";
        addHud(model);
        return "index";
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

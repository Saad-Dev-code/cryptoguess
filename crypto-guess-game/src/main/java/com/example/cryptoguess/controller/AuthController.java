package com.example.cryptoguess.controller;


import com.example.cryptoguess.model.GameSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final GameSession session;

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username) {
        if (username == null || username.isBlank()) return "redirect:/login?e=1";
        session.setUsername(username.trim());
        session.reset(); // start fresh on new login
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout() {
        session.setUsername(null);
        session.reset();
        return "redirect:/login";
    }
}

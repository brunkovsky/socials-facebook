package org.example.socials.facebook.controller;

import lombok.AllArgsConstructor;
import org.example.socials.facebook.repository.AccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "api/socials/facebook")
@AllArgsConstructor
public class UiController {

    private final AccountRepository accountRepository;

    @GetMapping("/ui")
    public String search(Model model) {
        model.addAttribute("facebookAccounts", accountRepository.findAll());
        return "accounts";
    }

}

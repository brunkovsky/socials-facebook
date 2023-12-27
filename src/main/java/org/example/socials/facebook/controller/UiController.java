package org.example.socials.facebook.controller;

import lombok.AllArgsConstructor;
import org.example.socials.facebook.model.FacebookAccount;
import org.example.socials.facebook.repository.AccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") String id) {
        accountRepository.deleteById(id);
        return "redirect:/api/socials/facebook/ui";
    }

    @PostMapping("/addAccount")
    public String addAccount(FacebookAccount facebookAccount) {
        accountRepository.save(facebookAccount);
        return "redirect:/api/socials/facebook/ui";
    }

}

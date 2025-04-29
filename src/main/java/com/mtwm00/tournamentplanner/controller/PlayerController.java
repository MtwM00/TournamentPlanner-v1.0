package com.mtwm00.tournamentplanner.controller;

import com.mtwm00.tournamentplanner.model.Player;
import com.mtwm00.tournamentplanner.service.PlayerService;
import com.mtwm00.tournamentplanner.service.TournamentService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlayerController {

    private final PlayerService playerService;
    private final TournamentService tournamentService;

    public PlayerController(PlayerService playerService, TournamentService tournamentService) {
        this.playerService = playerService;
        this.tournamentService = tournamentService;
    }



    @GetMapping("/add-player")
    public String showAddPlayerForm(Model model) {
        model.addAttribute("player", new Player());
        return "add-player";
    }

    @PostMapping("/add-player")
    public String addPlayer(@ModelAttribute Player player) {
        playerService.addPlayer(player);
        return "redirect:/tournaments";
    }

    @GetMapping("/players/search")
    public String searchPlayersByKeyword(Player player, Model model, String keyword) {
        model.addAttribute("keywordPlayers", playerService.getPlayersByKeyword(keyword));
        return "players";
    }

    @GetMapping("/players/all")
    public String showAllPlayers(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 3;
        Page<Player> playersPage = playerService.getAllPlayersPaginated(page, pageSize);
        model.addAttribute("players", playersPage);
        return "all-players";
    }



}

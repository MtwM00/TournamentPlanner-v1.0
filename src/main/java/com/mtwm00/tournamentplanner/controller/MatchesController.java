package com.mtwm00.tournamentplanner.controller;

import com.mtwm00.tournamentplanner.model.Tournament;
import com.mtwm00.tournamentplanner.model.TournamentPlayer;
import com.mtwm00.tournamentplanner.service.MatchService;
import com.mtwm00.tournamentplanner.service.PlayerService;
import com.mtwm00.tournamentplanner.service.TournamentPlayerService;
import com.mtwm00.tournamentplanner.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tournaments")
public class MatchesController {

    private final MatchService matchService;
    private final PlayerService playerService;
    private final TournamentService tournamentService;
    private final TournamentPlayerService tournamentPlayerService;

    public MatchesController(MatchService matchService, PlayerService playerService, TournamentService tournamentService, TournamentPlayerService tournamentPlayerService) {
        this.matchService = matchService;
        this.playerService = playerService;
        this.tournamentService = tournamentService;
        this.tournamentPlayerService = tournamentPlayerService;
    }

    @GetMapping("/{tournamentId}/matches")
    public String getMatches(@PathVariable Long tournamentId, Model model) {
        model.addAttribute("matchesByRound", matchService.getMatchesByRoundByTournamentId(tournamentId));
        model.addAttribute("tournamentId", tournamentId);

        return "matches";
    }

    @PostMapping("/generate-matches")
    public String generateMatches(Long tournamentId) {

        if (!matchService.checkIfMatchesAlreadyGeneratedForTournament(tournamentId)) {
            Tournament tournament = tournamentService.getTournamentById(tournamentId);
            List<TournamentPlayer> tournamentPlayers = playerService.getTournamentPlayersByTournamentId(tournamentId);
            matchService.generateSchedule(tournament, tournamentPlayers);
        }

        return "redirect:/tournaments/" + tournamentId + "/matches";
    }

    @PostMapping("/{tournamentId}/matches/update-winner")
    public String updateWinner(@PathVariable Long tournamentId, @RequestParam Long matchId, @RequestParam Long winnerId) {
        matchService.updateWinner(tournamentId, matchId, winnerId);

        return "redirect:/tournaments/{tournamentId}/matches";
    }


    @PostMapping("/{tournamentId}/delete-matches")
    public String deleteMatchesFromTournament(@PathVariable Long tournamentId) {
        matchService.deleteMatchesByTournamentId(tournamentId);
        tournamentPlayerService.resetTournamentPlayersScore(tournamentId);
        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        tournament.setMatchesGenerated(false);
        tournamentService.saveTournament(tournament);
        return "redirect:/tournaments/{tournamentId}/players";
    }


}

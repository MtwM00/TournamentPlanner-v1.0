package com.mtwm00.tournamentplanner.controller;

import com.mtwm00.tournamentplanner.model.Tournament;
import com.mtwm00.tournamentplanner.service.MatchService;
import com.mtwm00.tournamentplanner.service.PlayerService;
import com.mtwm00.tournamentplanner.service.TournamentPlayerService;
import com.mtwm00.tournamentplanner.service.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;
    private final TournamentPlayerService tournamentPlayerService;
    private final MatchService matchService;
    private final PlayerService playerService;

    public TournamentController(TournamentService tournamentService, TournamentPlayerService tournamentPlayerService, MatchService matchService, PlayerService playerService) {
        this.tournamentService = tournamentService;
        this.tournamentPlayerService = tournamentPlayerService;
        this.matchService = matchService;
        this.playerService = playerService;
    }

    @GetMapping
    public String getTournaments(Model model) {
        model.addAttribute("tournaments", tournamentService.getAllTournaments());
        return "tournaments";
    }

    @GetMapping("/new")
    public String showCreateTournamentForm(Model model) {
        model.addAttribute("tournament", new Tournament());
        return "create-tournament";
    }

    @PostMapping("/new")
    public String createNewTournament(@ModelAttribute Tournament tournament) {
        tournamentService.saveTournament(tournament);
        return "redirect:/tournaments";
    }

    @GetMapping("/{tournamentId}/summary")
    public String showTournamentSummary(@PathVariable Long tournamentId,
                                        @RequestParam(defaultValue = "0") int leaderboardPage,
                                        Model model,
                                        RedirectAttributes redirectAttributes) {

        Tournament tournament = tournamentService.getTournamentById(tournamentId);
        model.addAttribute("tournament", tournament);

        if (tournament.getMatchesGenerated()) {

            model.addAttribute("participants", tournamentPlayerService.getTournamentPlayersByTournamentId(tournamentId));
            model.addAttribute("matchesPlayed", matchService.getMatchesPlayedInTournament(tournamentId));
            model.addAttribute("totalMatches", matchService.getMatchesByTournamentId(tournamentId));
            Integer rounds = Collections.max(matchService.getMatchesByRoundByTournamentId(tournamentId).keySet());
            model.addAttribute("numberOfRounds", rounds);
            model.addAttribute("tournamentPlayersSorted", tournamentPlayerService.getTournamentPlayersByTournamentIdOrderedByScorePaginated(leaderboardPage, 10, tournamentId));
            Map<Long, Integer> matchesPlayedByPlayersMap = matchService.getMapOfMatchesPlayed(tournamentPlayerService.getTournamentPlayersByTournamentId(tournamentId), tournamentId);
            model.addAttribute("playersMatchesPlayedCount", matchesPlayedByPlayersMap);

            return "summary";
        } else {
            redirectAttributes.addFlashAttribute("errorTitle", "Cannot show summary");
            redirectAttributes.addFlashAttribute("errorMessage", "Matches must be generated before viewing the summary.");
            return "error-page";

        }
    }

    @PostMapping("/{tournamentId}/remove-player/{playerId}")
    public String removePlayerFromTournament(@PathVariable Long tournamentId,
                                             @PathVariable Long playerId,
                                             RedirectAttributes redirectAttributes) {

        Tournament tournament = tournamentService.getTournamentById(tournamentId);

        if (!tournament.getMatchesGenerated()) {
            tournamentPlayerService.removeTournamentPlayerFromTournament(tournamentId, playerId);
        }

        return "redirect:/tournaments/{tournamentId}/players";
    }

    @GetMapping("/{tournamentId}/players")
    public String showPlayersForm(@PathVariable Long tournamentId,
                                  @RequestParam(required = false) String keyword,
                                  @RequestParam(defaultValue = "0") int candidatesPage,
                                  @RequestParam(defaultValue = "0") int participantsPage,
                                  Model model) {
        Tournament tournament = tournamentService.getTournamentById(tournamentId);

        model.addAttribute("tournament", tournament);
        model.addAttribute("tournamentPlayers", playerService.getTournamentPlayersByTournamentIdPaginated(participantsPage, 6, tournamentId));

        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("availablePlayers", playerService.findAllPlayersNotInTournamentByKeywordPaginated(candidatesPage, 6, keyword, tournamentId));
        } else {
            model.addAttribute("availablePlayers", playerService.getUnassignedPlayersForTournamentPaginated(candidatesPage, 6 ,tournamentId));
        }
        model.addAttribute("keyword", keyword);
        return "players";
    }

    @PostMapping("/{tournamentId}/players")
    public String addExistingPlayers(@PathVariable Long tournamentId,
                                     @RequestParam List<Long> playerIds) {

        if (playerIds == null || playerIds.isEmpty()) {
            return "redirect:/tournaments/" + tournamentId + "/players";
        }
        Tournament tournament = tournamentService.getTournamentById(tournamentId);

        playerService.assignPlayersToTournament(tournamentId, playerIds);

        return "redirect:/tournaments/" + tournamentId + "/players";
    }

}

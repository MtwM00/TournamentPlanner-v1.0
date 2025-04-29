package com.mtwm00.tournamentplanner.service;

import com.mtwm00.tournamentplanner.model.Match;
import com.mtwm00.tournamentplanner.model.Tournament;
import com.mtwm00.tournamentplanner.model.TournamentPlayer;
import com.mtwm00.tournamentplanner.repository.MatchRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final PlayerService playerService;


    public MatchService(MatchRepository matchRepository, PlayerService playerService) {
        this.matchRepository = matchRepository;
        this.playerService = playerService;
    }

    public List<Match> getMatchesByTournamentId(Long tournamentId) {
        return matchRepository.findAllByTournament_Id(tournamentId);
    }

    public boolean checkIfMatchesAlreadyGeneratedForTournament(Long tournamentId) {
        return matchRepository.existsMatchByTournament_Id(tournamentId);
    }

    public Map<Integer, List<Match>> getMatchesByRoundByTournamentId(Long tournamentId) {
        return matchRepository.findAllByTournament_Id(tournamentId).stream().collect(Collectors.groupingBy(Match::getRound));
    }

    public Match getMatchById(long id) {
        return matchRepository.findById(id);
    }

    public void updateWinner(Long tournamentId, Long matchId, Long winnerId) {
        Match match = getMatchById(matchId);

        TournamentPlayer winnerCandidate = match.getPlayer1().getPlayer().getId().equals(winnerId)
                ? match.getPlayer1()
                : match.getPlayer2();

        match.setWinners(winnerCandidate);
        saveMatch(match);
        playerService.updatePlayerScoreByIdAndTournamentId(winnerId, tournamentId);

    }

    public List<Match> getMatchesPlayedInTournament(Long tournamentId) {
        return matchRepository.getMatchesByWinnerNotNullAndTournament_Id(tournamentId);
    }

    public Integer getMatchesPlayedByPlayerInTournament(Long playerId, Long tournamentId) {
        return matchRepository.countPlayedMatchesByPlayerInTournament(playerId, tournamentId);
    }

    public Map<Long, Integer> getMapOfMatchesPlayed(List<TournamentPlayer> tournamentPlayers, Long tournamentId) {

        Map<Long, Integer> map = new HashMap<>();
        tournamentPlayers.forEach(tournamentPlayer -> {
            map.put(tournamentPlayer.getPlayer().getId(), matchRepository.countPlayedMatchesByPlayerInTournament(tournamentPlayer.getPlayer().getId(), tournamentId));
        });
        return map;
    }

    public void saveMatch(Match match) {
        matchRepository.save(match);
    }

    public void generateSchedule(Tournament tournament, List<TournamentPlayer> players) {

        tournament.setMatchesGenerated(true);
        List<TournamentPlayer> allPlayers = new ArrayList<>(players);

        boolean hasBye = allPlayers.size() % 2 != 0;
        if (hasBye) {
            allPlayers.add(null);
        }

        int numPlayers = allPlayers.size();
        int numRounds = numPlayers - 1;
        int halfSize = numPlayers / 2;

        for (int round = 0; round < numRounds; round++) {
            for (int i = 0; i < halfSize; i++) {
                TournamentPlayer p1 = allPlayers.get(i);
                TournamentPlayer p2 = allPlayers.get(numPlayers - 1 - i);

                if (p1 != null && p2 != null) {
                    matchRepository.save(new Match(round + 1, p1, p2, tournament));
                }
            }
            TournamentPlayer last = allPlayers.remove(allPlayers.size() - 1);
            allPlayers.add(1, last);
        }
    }

    @Transactional
    public void deleteMatchesByTournamentId(Long tournamentId) {
        matchRepository.deleteAllByTournament_Id(tournamentId);

    }

}

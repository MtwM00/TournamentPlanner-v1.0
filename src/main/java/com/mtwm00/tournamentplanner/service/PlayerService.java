package com.mtwm00.tournamentplanner.service;

import com.mtwm00.tournamentplanner.model.Player;
import com.mtwm00.tournamentplanner.model.Tournament;
import com.mtwm00.tournamentplanner.model.TournamentPlayer;
import com.mtwm00.tournamentplanner.repository.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TournamentPlayerService tournamentPlayerService;
    private final TournamentService tournamentService;

    public PlayerService(PlayerRepository playerRepository, TournamentPlayerService tournamentPlayerService, TournamentService tournamentService) {
        this.playerRepository = playerRepository;
        this.tournamentPlayerService = tournamentPlayerService;
        this.tournamentService = tournamentService;
    }

    public void addPlayer(Player player) {
        playerRepository.save(player);
    }

    public void updatePlayerScoreByIdAndTournamentId(Long winnersId, Long tournamentId) {

        TournamentPlayer tournamentPlayer = tournamentPlayerService.getTournamentPlayerByPlayerIdAndTournamentId(winnersId, tournamentId);

        tournamentPlayer.setScore(tournamentPlayer.getScore() + 1);
        tournamentPlayerService.saveTournamentPlayer(tournamentPlayer);
    }

    public void assignPlayersToTournament(Long tournamentId, List<Long> playerIds) {

        Tournament tournament = tournamentService.getTournamentById(tournamentId);

        for (Long playerId : playerIds) {
            Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));
            // Check if player already added to tournament
            if (!tournamentPlayerService.checkIfTournamentPlayerExistsByPlayerIdAndTournamentId(playerId, tournamentId)) {
                tournamentPlayerService.saveTournamentPlayer(new TournamentPlayer(tournament, player));
            } else {
                throw new RuntimeException("Player already in tournament");
            }
        }
    }

    public List<Player> getUnassignedPlayersForTournament(Long tournamentId) {
        List<Player> playersInTournament = tournamentPlayerService.getPlayersFromOneTournamentByTournamentId(tournamentId);
        List<Player> difference = new ArrayList<>(playerRepository.findAll());
        difference.removeAll(playersInTournament);
        return difference;
    }

    public Page<Player> getUnassignedPlayersForTournamentPaginated(int page, int size, Long tournamentId) {
        Pageable pageable = PageRequest.of(page, size);
        return playerRepository.findAllPlayersNotInTournament(tournamentId, pageable);
    }

    public Page<Player> getAllPlayersPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return playerRepository.findAll(pageable);
    }

    public Page<Player> findAllPlayersNotInTournamentByKeywordPaginated(int page, int size, String keyword, Long tournamentId) {
        Pageable pageable = PageRequest.of(page, size);
        return playerRepository.findAllPlayersNotInTournamentByKeywordPaginated(keyword, tournamentId, pageable);
    }

    public Player getPlayerById(long id) {
        return playerRepository.getById(id);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public List<Long> getAllPlayersIds() {
        return playerRepository.findAll().stream().map(Player::getId).collect(Collectors.toList());
    }

    public String getPlayerNameAndSurnameById(long id) {
        Player player = playerRepository.findById(id);
        return player.getFirstName() + " " + player.getLastName();
    }

    public List<TournamentPlayer> getTournamentPlayersByTournamentId(Long tournamentId) {
        return tournamentPlayerService.getTournamentPlayersByTournamentId(tournamentId);
    }

    public Page<TournamentPlayer> getTournamentPlayersByTournamentIdPaginated(int page, int size, Long tournamentId) {
        return tournamentPlayerService.getTournamentPlayersByTournamentIdPaginated(page, size, tournamentId);
    }

    public List<Player> getPlayersByKeyword(String keyword) {

        return playerRepository.findPlayersByKeyword(keyword);
    }

}

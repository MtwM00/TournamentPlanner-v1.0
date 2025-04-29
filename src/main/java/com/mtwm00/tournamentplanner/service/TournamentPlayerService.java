package com.mtwm00.tournamentplanner.service;

import com.mtwm00.tournamentplanner.model.Player;
import com.mtwm00.tournamentplanner.model.TournamentPlayer;
import com.mtwm00.tournamentplanner.repository.TournamentPlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentPlayerService {

    private final TournamentPlayerRepository tournamentPlayerRepository;

    public TournamentPlayerService(TournamentPlayerRepository tournamentPlayerRepository) {
        this.tournamentPlayerRepository = tournamentPlayerRepository;
    }

    public void saveTournamentPlayer(TournamentPlayer tournamentPlayer) {
        tournamentPlayerRepository.save(tournamentPlayer);
    }

    public List<TournamentPlayer> getTournamentPlayersByTournamentId(Long id) {
        return tournamentPlayerRepository.getByTournament_Id(id);
    }

    public Page<TournamentPlayer> getTournamentPlayersByTournamentIdPaginated(int page, int size, Long tournamentId) {
        Pageable pageable = PageRequest.of(page, size);
        return tournamentPlayerRepository.getAllByTournament_Id(tournamentId, pageable);
    }

    public TournamentPlayer getTournamentPlayerByPlayerIdAndTournamentId(Long playerId, Long tournamentId) {
        return tournamentPlayerRepository.getByPlayer_IdAndTournament_Id(playerId, tournamentId);
    }

    public boolean checkIfTournamentPlayerExistsByPlayerIdAndTournamentId(Long playerId, Long tournamentId) {
        return tournamentPlayerRepository.existsByPlayer_IdAndTournament_Id(playerId, tournamentId);
    }

    public Player getPlayerFromTournamentPlayer(TournamentPlayer tournamentPlayer) {
        return tournamentPlayer.getPlayer();
    }

    public List<Player> getPlayersFromOneTournamentByTournamentId(Long tournamentId) {
        return tournamentPlayerRepository.getByTournament_Id(tournamentId).stream().map(TournamentPlayer::getPlayer).toList();
    }

    public Page<TournamentPlayer> getTournamentPlayersByTournamentIdOrderedByScorePaginated(int page, int size, Long tournamentId) {
        Pageable pageable = PageRequest.of(page, size);
        return tournamentPlayerRepository.getByTournament_IdOrderByScoreDesc(tournamentId, pageable);
    }

    public void resetTournamentPlayersScore(Long tournamentId) {
        List<TournamentPlayer> tournamentPlayerList = tournamentPlayerRepository.getByTournament_Id(tournamentId);
        for (TournamentPlayer tournamentPlayer : tournamentPlayerList) {
            tournamentPlayer.setScore(0);
        }
    }

    @Transactional
    public void removeTournamentPlayerFromTournament(Long tournamentId, Long playerId) {
        tournamentPlayerRepository.removeTournamentPlayerByTournament_IdAndPlayer_Id(tournamentId, playerId);
    }

}

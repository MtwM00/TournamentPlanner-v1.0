package com.mtwm00.tournamentplanner.repository;

import com.mtwm00.tournamentplanner.model.TournamentPlayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentPlayerRepository extends JpaRepository<TournamentPlayer, Long> {

    TournamentPlayer getByPlayer_IdAndTournament_Id(Long playerId, Long tournamentId);

    List<TournamentPlayer> getByTournament_Id(Long id);

    List<TournamentPlayer> getByTournament_IdOrderByScoreDesc(Long tournamentId);

    Page<TournamentPlayer> getByTournament_IdOrderByScoreDesc(Long tournamentId, Pageable pageable);

    Page<TournamentPlayer> getAllByTournament_Id(Long tournamentId, Pageable pageable);

    void removeTournamentPlayerByTournament_IdAndPlayer_Id(Long tournamentId, Long playerId);

    boolean existsByPlayer_IdAndTournament_Id(Long playerId, Long tournamentId);

}

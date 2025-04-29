package com.mtwm00.tournamentplanner.repository;


import com.mtwm00.tournamentplanner.model.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {

    List<Match> findAllByTournament_Id(Long tournamentId);

    Match findById(long id);

    List<Match> findAll();

    Boolean existsMatchByTournament_Id(Long tournamentId);

    void deleteAllByTournament_Id(Long tournamentId);

    List<Match> getMatchesById(Long id);

    List<Match> getMatchesByWinnerNotNullAndTournament_Id(Long tournamentId);

    @Query(value = "SELECT COUNT(*) FROM Match m WHERE (m.player1.player.id = :playerId OR m.player2.player.id = :playerId) AND m.tournament.id = :tournamentId AND m.winner IS NOT NULL")
    Integer countPlayedMatchesByPlayerInTournament(Long playerId, Long tournamentId);

}

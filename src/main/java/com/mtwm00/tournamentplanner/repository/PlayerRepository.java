package com.mtwm00.tournamentplanner.repository;

import com.mtwm00.tournamentplanner.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player findById(long id);

    Player findDistinctByFirstNameAndLastName(String firstName, String lastName);

    List<Player> findAll();

    Page<Player> findAll(Pageable pageable);
//    Page<Player> findAllPaginated(Pageable pageable);

    @Query(value = """
            SELECT p FROM  Player p WHERE p.id NOT IN (
                SELECT tp.player.id FROM TournamentPlayer tp WHERE tp.tournament.id = :tournamentId
                )
    """)
    Page<Player> findAllPlayersNotInTournament(Long tournamentId, Pageable pageable);

    Player getById(long id);

    @Query(value = """
            SELECT p FROM Player p WHERE \
            LOWER(p.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR \
            LOWER(p.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))""")
    List<Player> findPlayersByKeyword(@Param("keyword") String keyword);

    @Query(value = """
            SELECT p FROM Player p WHERE 
            LOWER(p.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR 
            LOWER(p.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) AND 
            p.id NOT IN (
                SELECT tp.player.id FROM TournamentPlayer tp WHERE tp.tournament.id = :tournamentId
                )            
    """)
    Page<Player> findAllPlayersNotInTournamentByKeywordPaginated(@Param("keyword") String keyword, Long tournamentId, Pageable pageable);


}

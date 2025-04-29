package com.mtwm00.tournamentplanner.repository;

import com.mtwm00.tournamentplanner.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    List<Tournament> findAll();

    Tournament getById(Long id);

    Optional<Tournament> findById(Long id);

}

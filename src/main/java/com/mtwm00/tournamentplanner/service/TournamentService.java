package com.mtwm00.tournamentplanner.service;

import com.mtwm00.tournamentplanner.model.Tournament;
import com.mtwm00.tournamentplanner.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void saveTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    public Tournament getTournamentById(Long tournamentId) {
        return tournamentRepository.findById(tournamentId).orElseThrow(() -> new RuntimeException("Tournament not found"));
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }


}

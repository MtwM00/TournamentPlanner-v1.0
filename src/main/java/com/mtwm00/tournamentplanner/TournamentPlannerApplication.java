package com.mtwm00.tournamentplanner;


import com.mtwm00.tournamentplanner.model.Player;
import com.mtwm00.tournamentplanner.model.Tournament;
import com.mtwm00.tournamentplanner.repository.PlayerRepository;
import com.mtwm00.tournamentplanner.repository.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TournamentPlannerApplication {

    private static final Logger log = LoggerFactory.getLogger(TournamentPlannerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TournamentPlannerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(PlayerRepository playerRepository, TournamentRepository tournamentRepository) {

        return (args) -> {
            playerRepository.save(new Player("Jack", "Bauer"));
            playerRepository.save(new Player("Chloe", "O'Brian"));
            playerRepository.save(new Player("Kim", "Bauer"));
            playerRepository.save(new Player("David", "Palmer"));
            playerRepository.save(new Player("Michelle", "Dessler"));
            playerRepository.save(new Player("Erin", "Hooper"));
            playerRepository.save(new Player("Alan", "Greer"));
            playerRepository.save(new Player("Justin", "Fletcher"));
            playerRepository.save(new Player("Justin", "Spaulding"));
            playerRepository.save(new Player("Kathleen", "Hagan"));
            playerRepository.save(new Player("Tonya", "Wallace"));
            playerRepository.save(new Player("Whitney", "Ingram"));
            playerRepository.save(new Player("Debora", "Petty"));
            playerRepository.save(new Player("Eduardo", "Monroe"));

            tournamentRepository.save(new Tournament("Champions Clash 2024"));
            tournamentRepository.save(new Tournament("Spring Showdown"));
            tournamentRepository.save(new Tournament("Legends Arena Cup"));
            tournamentRepository.save(new Tournament("Ultimate Duel Series"));



        };
    }

}

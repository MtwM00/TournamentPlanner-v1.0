package com.mtwm00.tournamentplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Tournament {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Boolean matchesGenerated = false;
    @OneToMany
    private List<Match> listOfMatches = new ArrayList<>();

    public Tournament() {
    }

    public Tournament(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", tournamentName='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Match> getListOfMatches() {
        return listOfMatches;
    }

    public Boolean getMatchesGenerated() {
        return matchesGenerated;
    }

    public void setMatchesGenerated(Boolean matchesGenerated) {
        this.matchesGenerated = matchesGenerated;
    }
}

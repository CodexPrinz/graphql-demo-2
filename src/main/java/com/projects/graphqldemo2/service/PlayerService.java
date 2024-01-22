package com.projects.graphqldemo2.service;

import com.projects.graphqldemo2.model.Player;
import com.projects.graphqldemo2.model.Team;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerService {

    private List<Player> players = new ArrayList<>();
    AtomicInteger id = new AtomicInteger(0);

    public List<Player> findAll(){
        return players;
    }

    public Optional<Player> findOne(Integer id){
        return players.stream()
                .filter(player -> Objects.equals(player.id(), id))
                .findFirst();
    }

    public Player create(String name, Team team){
        Player player = new Player(id.incrementAndGet(), name, team);
        players.add(player);
        return player;
    }

    public Player delete(Integer id){
        Player player = players.stream()
                .filter(c -> Objects.equals(c.id(), id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        players.remove(player);
        return player;
    }

    public Player update(Integer id, String name, Team team){
        Player updatedPlayer = new Player(id, name, team);
        Optional<Player> optional = players.stream()
                .filter(player -> Objects.equals(player.id(), id))
                .findFirst();

        if (optional.isPresent()){
            Player player = optional.get();
            int index = players.indexOf(player);
            players.set(index, updatedPlayer);
        } else {
            throw new IllegalArgumentException("Invalid Player");
        }
        return updatedPlayer;
    }

    @PostConstruct
    private void init(){
        players.add(new Player(id.incrementAndGet(), "MS Dhoni", Team.MI));
        players.add(new Player(id.incrementAndGet(), "Dan Quat", Team.CSK));
        players.add(new Player(id.incrementAndGet(), "Lebron James", Team.DC));
        players.add(new Player(id.incrementAndGet(), "Tom Bradey", Team.GT));
        players.add(new Player(id.incrementAndGet(), "Steph Curry", Team.CSK));

    }
}

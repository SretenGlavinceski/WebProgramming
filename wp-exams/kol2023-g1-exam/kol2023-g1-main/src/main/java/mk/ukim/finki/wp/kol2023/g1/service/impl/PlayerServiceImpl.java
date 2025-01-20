package mk.ukim.finki.wp.kol2023.g1.service.impl;

import mk.ukim.finki.wp.kol2023.g1.model.Player;
import mk.ukim.finki.wp.kol2023.g1.model.PlayerPosition;
import mk.ukim.finki.wp.kol2023.g1.model.exceptions.InvalidPlayerIdException;
import mk.ukim.finki.wp.kol2023.g1.repository.PlayerRepository;
import mk.ukim.finki.wp.kol2023.g1.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamServiceImpl teamService;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamServiceImpl teamService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
    }

    @Override
    public List<Player> listAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player findById(Long id) {
        return playerRepository.findById(id).orElseThrow(InvalidPlayerIdException::new);
    }

    @Override
    public Player create(String name, String bio, Double pointsPerGame, PlayerPosition position, Long team) {
        Player player = new Player(name, bio, pointsPerGame, position, teamService.findById(team));
        playerRepository.save(player);
        return player;
    }

    @Override
    public Player update(Long id, String name, String bio, Double pointsPerGame, PlayerPosition position, Long team) {
        Player player = findById(id);
        player.setName(name);
        player.setBio(bio);
        player.setPointsPerGame(pointsPerGame);
        player.setPosition(position);
        player.setTeam(teamService.findById(team));

        playerRepository.save(player);
        return player;
    }

    @Override
    public Player delete(Long id) {
        Player player = findById(id);
        playerRepository.delete(player);
        return player;
    }

    @Override
    public Player vote(Long id) {
        Player player = findById(id);
        player.setVotes(player.getVotes() + 1);
        playerRepository.save(player);
        return player;
    }

    @Override
    public List<Player> listPlayersWithPointsLessThanAndPosition(Double pointsPerGame, PlayerPosition position) {
        List<Player> players = listAllPlayers();

        if (pointsPerGame != null) {
            players = players.stream()
                    .filter(player -> player.getPointsPerGame() < pointsPerGame)
                    .toList();
        }

        if (position != null) {
            players = players.stream()
                    .filter(player -> player.getPosition() == position)
                    .toList();
        }

        return players;
    }
}

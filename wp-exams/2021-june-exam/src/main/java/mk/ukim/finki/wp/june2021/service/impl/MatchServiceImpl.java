package mk.ukim.finki.wp.june2021.service.impl;

import mk.ukim.finki.wp.june2021.model.Match;
import mk.ukim.finki.wp.june2021.model.MatchType;
import mk.ukim.finki.wp.june2021.model.exceptions.InvalidMatchIdException;
import mk.ukim.finki.wp.june2021.repository.MatchRepository;
import mk.ukim.finki.wp.june2021.service.MatchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {
    private final MatchRepository matchRepository;
    private final MatchLocationServiceImpl matchLocationService;

    public MatchServiceImpl(MatchRepository matchRepository, MatchLocationServiceImpl matchLocationService) {
        this.matchRepository = matchRepository;
        this.matchLocationService = matchLocationService;
    }

    @Override
    public List<Match> listAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public Match findById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(InvalidMatchIdException::new);
    }

    @Override
    public Match create(String name, String description, Double price, MatchType type, Long location) {
        Match match = new Match(name, description, price, type, matchLocationService.findById(location));
        matchRepository.save(match);
        return match;
    }

    @Override
    public Match update(Long id, String name, String description, Double price, MatchType type, Long location) {
        Match match = findById(id);
        match.setName(name);
        match.setDescription(description);
        match.setPrice(price);
        match.setType(type);
        match.setLocation(matchLocationService.findById(location));

        matchRepository.save(match);
        return match;
    }

    @Override
    public Match delete(Long id) {
        Match match = findById(id);
        matchRepository.delete(match);
        return match;
    }

    @Override
    public Match follow(Long id) {
        Match match = findById(id);
        match.setFollows(match.getFollows() + 1);
        matchRepository.save(match);
        return match;
    }

    @Override
    public List<Match> listMatchesWithPriceLessThanAndType(Double price, MatchType type) {
        List<Match> matches = listAllMatches();

        if (price != null) {
            matches = matches.stream()
                    .filter(match -> match.getPrice() < price)
                    .collect(Collectors.toList());
        }

        if (type != null) {
            matches = matches.stream()
                    .filter(match -> match.getType() == type)
                    .collect(Collectors.toList());
        }

        return matches;
    }
}

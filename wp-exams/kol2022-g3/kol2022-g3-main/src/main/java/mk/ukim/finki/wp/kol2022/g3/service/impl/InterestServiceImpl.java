package mk.ukim.finki.wp.kol2022.g3.service.impl;

import mk.ukim.finki.wp.kol2022.g3.model.Interest;
import mk.ukim.finki.wp.kol2022.g3.model.exceptions.InvalidForumUserIdException;
import mk.ukim.finki.wp.kol2022.g3.repository.InterestRepository;
import mk.ukim.finki.wp.kol2022.g3.service.InterestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestServiceImpl implements InterestService {
    private final InterestRepository interestRepository;

    public InterestServiceImpl(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    @Override
    public Interest findById(Long id) {
        return interestRepository.findById(id).orElseThrow(InvalidForumUserIdException::new);
    }

    @Override
    public List<Interest> listAll() {
        return interestRepository.findAll();
    }

    @Override
    public Interest create(String name) {
        Interest interest = new Interest(name);
        interestRepository.save(interest);
        return interest;
    }
}

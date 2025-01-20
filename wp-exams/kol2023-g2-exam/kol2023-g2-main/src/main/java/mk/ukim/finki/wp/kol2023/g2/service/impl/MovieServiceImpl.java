package mk.ukim.finki.wp.kol2023.g2.service.impl;

import mk.ukim.finki.wp.kol2023.g2.model.Genre;
import mk.ukim.finki.wp.kol2023.g2.model.Movie;
import mk.ukim.finki.wp.kol2023.g2.model.exceptions.InvalidMovieIdException;
import mk.ukim.finki.wp.kol2023.g2.repository.MovieRepository;
import mk.ukim.finki.wp.kol2023.g2.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final DirectorServiceImpl directorService;

    public MovieServiceImpl(MovieRepository movieRepository, DirectorServiceImpl directorService) {
        this.movieRepository = movieRepository;
        this.directorService = directorService;
    }

    @Override
    public List<Movie> listAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElseThrow(InvalidMovieIdException::new);
    }

    @Override
    public Movie create(String name, String description, Double rating, Genre genre, Long director) {
        Movie movie = new Movie(name, description, rating, genre, directorService.findById(director));
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public Movie update(Long id, String name, String description, Double rating, Genre genre, Long director) {
        Movie movie = findById(id);
        movie.setName(name);
        movie.setDescription(description);
        movie.setRating(rating);
        movie.setGenre(genre);
        movie.setDirector(directorService.findById(director));

        movieRepository.save(movie);
        return movie;
    }

    @Override
    public Movie delete(Long id) {
        Movie movie = findById(id);
        movieRepository.delete(movie);
        return movie;
    }

    @Override
    public Movie vote(Long id) {
        Movie movie = findById(id);
        movie.setVotes(movie.getVotes() + 1);
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public List<Movie> listMoviesWithRatingLessThenAndGenre(Double rating, Genre genre) {
        return listAllMovies().stream()
                .filter(movie -> rating == null || movie.getRating() < rating)
                .filter(movie -> genre == null || movie.getGenre() == genre)
                .toList();
    }
}

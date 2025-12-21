package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.payload.CreateMovieListDTO;
import de.adrianwalter.movie_list_rest_api.repository.MovieListRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieListService {

    private final MovieListRepository movieListRepository;

    public MovieListService(MovieListRepository movieListRepository) {
        this.movieListRepository = movieListRepository;
    }

    public Page<MovieList> findAll(Pageable pageable){
        return movieListRepository.findAll( pageable );
    }

    public MovieList findById( Long id ){

        Optional<MovieList> movieList = movieListRepository.findById( id );

        if( movieList.isEmpty() ){
            throw new ResourceNotFoundException();
        }

        return movieList.get();
    }

    public void deleteById(Long id) {
        movieListRepository.deleteById( id );
    }

    public Optional<MovieList> findByMovieListName(String name) {
        return movieListRepository.findByMovieListName( name );
    }

    public MovieList create(CreateMovieListDTO request) {
        Optional<MovieList> existingMovieList = findByMovieListName( request.getMovieListName());

        if (existingMovieList.isPresent()) {
            throw new NameAlreadyExistsException();
        }

        MovieList movieList = new MovieList();
        movieList.setMovieListName( request.getMovieListName());

        return movieListRepository.save(movieList);
    }

}

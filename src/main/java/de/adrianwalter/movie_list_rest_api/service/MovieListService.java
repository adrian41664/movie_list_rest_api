package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.payload.PostMovieListByUserIdDTO;
import de.adrianwalter.movie_list_rest_api.payload.PostMovieListByUserNameDTO;
import de.adrianwalter.movie_list_rest_api.payload.PostMovieListDTO;
import de.adrianwalter.movie_list_rest_api.repository.MovieListRepository;
import de.adrianwalter.movie_list_rest_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieListService {

    private final MovieListRepository movieListRepository;

    private final UserRepository userRepository;

    @Autowired
    public MovieListService(MovieListRepository movieListRepository, UserRepository userRepository) {
        this.movieListRepository = movieListRepository;
        this.userRepository = userRepository;
    }

//    public Page<MovieList> findAll(Pageable pageable) {
//        return movieListRepository.findAll(pageable);
//    }

    public MovieList findById(Long id) {

        Optional<MovieList> movieList = movieListRepository.findById(id);

        if (movieList.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return movieList.get();
    }

    public void deleteById(Long id) {
        movieListRepository.deleteById(id);
    }

    public Optional<MovieList> findByMovieListName(String name) {
        return movieListRepository.findByMovieListName(name);
    }

    public Optional<MovieList> findByMovieListId(Long id) {
        return movieListRepository.findByMovieListId(id);
    }


    public MovieList create(PostMovieListDTO movieListDTO) {

        if (movieListDTO instanceof PostMovieListByUserIdDTO idDTO) {

            Optional<User> existingUser = userRepository.findByUserId(idDTO.getUserId());

            if (existingUser.isPresent()) {

                MovieList movieList = new MovieList();

                movieList.setUser(existingUser.get());

                movieList.setMovieListName( idDTO.getMovieListName() );
                movieList.setDescription( idDTO.getDescription() );

                return movieListRepository.save(movieList);

            } else {
                throw new IllegalArgumentException("UserId not found!");
            }

        } else if (movieListDTO instanceof PostMovieListByUserNameDTO nameDTO) {

            Optional<User> existingUser = userRepository.findByUserName(nameDTO.getUserName());

            if (existingUser.isPresent()) {

                MovieList movieList = new MovieList();

                movieList.setUser(existingUser.get());

                movieList.setMovieListName( nameDTO.getMovieListName() );
                movieList.setDescription( nameDTO.getDescription() );

                return movieListRepository.save(movieList);

            } else {
                throw new IllegalArgumentException("UserName not found!");
            }

        } else {
            throw new IllegalArgumentException("DTO is invalid!");
        }

    }

}

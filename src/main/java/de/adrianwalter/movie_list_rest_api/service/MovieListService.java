package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.payload.MovieListReadResponseDto;
import de.adrianwalter.movie_list_rest_api.payload.MovieListCreateByUserIdBodyDto;
import de.adrianwalter.movie_list_rest_api.payload.MovieListCreateByUserNameBodyDto;
import de.adrianwalter.movie_list_rest_api.payload.MovieListCreateDto;
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

    public MovieListReadResponseDto create( MovieList movieList ){

        //
        return null;
    }

    private MovieListReadResponseDto mapToGetMovieListResponseDTO( MovieList movieList ){

        MovieListReadResponseDto dto = new MovieListReadResponseDto();

        dto.setMovieListId( movieList.getMovieListId() );
        dto.setMovieListName( movieList.getMovieListName() );

        dto.setUserId( movieList.getUser().getUserId() );
        dto.setUserName( movieList.getUser().getUserName() );

        dto.setDescription( movieList.getDescription() );
        dto.setMovies( movieList.getMovies() );

        return dto;
    }




    public MovieListReadResponseDto findById( Long id) {

        Optional<MovieList> movieList = movieListRepository.findByMovieListId( id );

        if( movieList.isPresent() ){

            MovieListReadResponseDto getMovieListResponseDTO;
            getMovieListResponseDTO = mapToGetMovieListResponseDTO( movieList.get() );

            return getMovieListResponseDTO;

        } else {
            throw new ResourceNotFoundException();
        }
    }

    public void deleteById(Long id) {
        movieListRepository.deleteById(id);
    }

    /* ToDo: In MovieList Names are not unique, how to find MovieList by Name?
        Is method needed for planned endpoint?

    public Optional<MovieList> findByMovieListName(String name) {
        return movieListRepository.findByMovieListName(name);
    }
    */


    public MovieList create( MovieListCreateDto movieListDTO) {

        // ToDo: Refactoring; DRY

        if (movieListDTO instanceof MovieListCreateByUserIdBodyDto idDTO) {

            Optional<User> user = userRepository.findByUserId(idDTO.getUserId());

            if ( user.isPresent() ) {

                MovieList movieList = new MovieList();

                movieList.setUser(user.get());

                movieList.setMovieListName( idDTO.getMovieListName() );
                movieList.setDescription( idDTO.getDescription() );

                return movieListRepository.save(movieList);

            } else {
                throw new IllegalArgumentException("UserId not found!");
            }

        } else if (movieListDTO instanceof MovieListCreateByUserNameBodyDto nameDTO) {

            Optional<User> user = userRepository.findByUserName(nameDTO.getUserName());

            if (user.isPresent()) {

                MovieList movieList = new MovieList();

                movieList.setUser(user.get());

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


    //    public Page<MovieList> findAll(Pageable pageable) {
    //        return movieListRepository.findAll(pageable);
    //    }


}

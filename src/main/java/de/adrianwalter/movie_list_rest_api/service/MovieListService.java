package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.payload.MovieListResponseDto;
import de.adrianwalter.movie_list_rest_api.payload.MovieListCreateByUserIdBodyDto;
import de.adrianwalter.movie_list_rest_api.payload.MovieListCreateByUserNameBodyDto;
import de.adrianwalter.movie_list_rest_api.payload.MovieListCreateDto;
import de.adrianwalter.movie_list_rest_api.repository.MovieListRepository;
import de.adrianwalter.movie_list_rest_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieListService {

    private final MovieListRepository movieListRepository;

    private final UserRepository userRepository;


    @Autowired
    public MovieListService( MovieListRepository movieListRepository, UserRepository userRepository ) {
        this.movieListRepository = movieListRepository;
        this.userRepository = userRepository;
    }


    private MovieListResponseDto mapToGetMovieListResponseDto( MovieList movieList ) {

        MovieListResponseDto dto = new MovieListResponseDto();

        dto.setMovieListId( movieList.getMovieListId() );
        dto.setMovieListName( movieList.getMovieListName() );

        dto.setUserId( movieList.getUser().getUserId() );
        dto.setUserName( movieList.getUser().getUserName() );

        dto.setDescription( movieList.getDescription() );
        dto.setMovies( movieList.getMovies() );

        return dto;
    }


    public MovieListResponseDto findById( Long movieListId ) {

        MovieList movieList = movieListRepository.findByMovieListId( movieListId )
                .orElseThrow( () -> new EntityNotFoundException(
                        "cant find MovieList with ID " + movieListId ) );

            return mapToGetMovieListResponseDto( movieList );
    }

    public void deleteById( Long id ) {
        movieListRepository.deleteById( id );
    }

    /* ToDo: In MovieList Names are not unique, how to find MovieList by Name?
        Is method needed for planned endpoint?

    public Optional<MovieList> findByMovieListName(String name) {
        return movieListRepository.findByMovieListName(name);
    }
    */


    public MovieListResponseDto create( MovieListCreateDto movieListCreateDto ) {

        MovieList movieList = this.mapToMovieList( movieListCreateDto );

        movieListRepository.save( movieList );

        return this.mapToGetMovieListResponseDto( movieList );
    }

    private MovieList mapToMovieList( MovieListCreateDto movieListCreateDto ){

        User user = this.findUser( movieListCreateDto );

        MovieList movieList = new MovieList();

        movieList.setUser( user );
        movieList.setMovieListName( movieListCreateDto.getMovieListName() );
        movieList.setDescription( movieListCreateDto.getDescription() );

        return movieList;
    }


    private User findUser( MovieListCreateDto movieListCreateDto ) {

        if ( movieListCreateDto instanceof MovieListCreateByUserNameBodyDto nameDTO ) {

            return userRepository.findByUserName( nameDTO.getUserName() )
                    .orElseThrow( () -> new EntityNotFoundException(
                            "cant find User with name " + nameDTO.getUserName() ) );

        } else if ( movieListCreateDto instanceof MovieListCreateByUserIdBodyDto idDto ) {

            return userRepository.findByUserId( idDto.getUserId() )
                    .orElseThrow( () -> new EntityNotFoundException(
                            "cant find User with ID " + idDto.getUserId() ) );

        }

        throw new IllegalArgumentException( "Body is invalid!" );
    }


}

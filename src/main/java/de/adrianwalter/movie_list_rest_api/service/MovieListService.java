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

    public MovieListReadResponseDto create( MovieList movieList ) {

        //
        return null;
    }

    private MovieListReadResponseDto mapToGetMovieListResponseDTO( MovieList movieList ) {

        MovieListReadResponseDto dto = new MovieListReadResponseDto();

        dto.setMovieListId( movieList.getMovieListId() );
        dto.setMovieListName( movieList.getMovieListName() );

        dto.setUserId( movieList.getUser().getUserId() );
        dto.setUserName( movieList.getUser().getUserName() );

        dto.setDescription( movieList.getDescription() );
        dto.setMovies( movieList.getMovies() );

        return dto;
    }


    public MovieListReadResponseDto findById( Long id ) {

        Optional< MovieList > movieList = movieListRepository.findByMovieListId( id );

        if ( movieList.isPresent() ) {

            MovieListReadResponseDto getMovieListResponseDTO;
            getMovieListResponseDTO = mapToGetMovieListResponseDTO( movieList.get() );

            return getMovieListResponseDTO;

        } else {
            throw new ResourceNotFoundException();
        }
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


    public MovieList create( MovieListCreateDto movieListCreateDto ) {

        User user = this.findUser( movieListCreateDto );

        MovieList movieList = new MovieList();

        movieList.setUser( user );
        movieList.setMovieListName( movieListCreateDto.getMovieListName() );
        movieList.setDescription( movieListCreateDto.getDescription() );

        return movieListRepository.save( movieList );
    }


    private User findUser( MovieListCreateDto movieListCreateDto ) {

        if ( movieListCreateDto instanceof MovieListCreateByUserNameBodyDto nameDTO ) {

            return userRepository.findByUserName( nameDTO.getUserName() )
                    .orElseThrow( () -> new EntityNotFoundException(
                            "cant find User with name" + nameDTO.getUserName() ) );

        } else if ( movieListCreateDto instanceof MovieListCreateByUserIdBodyDto idDto ) {

            return userRepository.findByUserId( idDto.getUserId() )
                    .orElseThrow( () -> new EntityNotFoundException(
                            "cant find User with ID" + idDto.getUserId() ) );

        }

        throw new IllegalArgumentException( "Body is invalid!" );
    }

    private MovieList mapToMovieList( MovieListCreateByUserNameBodyDto nameDto ){


        User user = userRepository.findByUserName( nameDto.getUserName() )
                .orElseThrow( () -> new EntityNotFoundException(
                        "cant find User with name" + nameDto.getUserName() ) );

        MovieList movieList = new MovieList();

        movieList.setUser( user );

        movieList.setMovieListName( nameDto.getMovieListName() );
        movieList.setDescription( nameDto.getDescription() );

        return movieList;
    }


    //    public Page<MovieList> findAll(Pageable pageable) {
    //        return movieListRepository.findAll(pageable);
    //    }


}

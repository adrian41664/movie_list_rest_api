package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.exception.InvalidBodyException;
import de.adrianwalter.movie_list_rest_api.exception.NameAlreadyExistsException;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.payload.MovieListResponseDto;
import de.adrianwalter.movie_list_rest_api.payload.MovieListCreateByUserIdBodyDto;
import de.adrianwalter.movie_list_rest_api.payload.MovieListCreateByUserNameBodyDto;
import de.adrianwalter.movie_list_rest_api.payload.MovieListCreateDto;
import de.adrianwalter.movie_list_rest_api.repository.MovieListRepository;
import de.adrianwalter.movie_list_rest_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieListService {

    @Autowired
    private UserService userService;

    @Autowired
    private final MovieListRepository movieListRepository;

    private final UserRepository userRepository;


    @Autowired
    public MovieListService( MovieListRepository movieListRepository, UserRepository userRepository ) {
        this.movieListRepository = movieListRepository;
        this.userRepository = userRepository;
    }


    private MovieListResponseDto mapToMovieListResponseDto( MovieList movieList ) {

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
                .orElseThrow( () -> new ResourceNotFoundException(
                        "cant find MovieList with ID " + movieListId ) );

        return mapToMovieListResponseDto( movieList );
    }


    public void deleteById( Long id ) {
        movieListRepository.deleteById( id );
    }


    private boolean userHasMovieListWithName( long userId, String movieListName ) {

        Optional< MovieList > movieListSearch = movieListRepository
                .findByUser_UserIdAndMovieListName( userId, movieListName );

        return movieListSearch.isPresent();
    }

    private boolean userHasMovieListWithName( String userName, String movieListName ) {

        Optional< MovieList > movieListSearch = movieListRepository
                .findByUser_UserNameAndMovieListName( userName, movieListName );

        return movieListSearch.isPresent();
    }


    public MovieListResponseDto create( MovieListCreateDto movieListCreateDto ) {

        MovieList movieList = this.mapToMovieList( movieListCreateDto );

        long userId = movieList.getUser().getUserId();
        String newMovieListName = movieList.getMovieListName();

        if ( this.userHasMovieListWithName( userId, newMovieListName ) ) {

            throw new NameAlreadyExistsException(
                    "Cant create new MovieList; User already owns MovieList with the given name" );
        }

        movieListRepository.save( movieList );

        return this.mapToMovieListResponseDto( movieList );
    }


    private MovieList mapToMovieList( MovieListCreateDto movieListCreateDto ) {

        User user = this.findUser( movieListCreateDto );

        MovieList movieList = new MovieList();

        movieList.setUser( user );
        movieList.setMovieListName( movieListCreateDto.getMovieListName() );
        movieList.setDescription( movieListCreateDto.getDescription() );

        return movieList;
    }


    private User findUser( MovieListCreateDto movieListCreateDto ) {

        if ( movieListCreateDto instanceof MovieListCreateByUserNameBodyDto nameDto ) {

            return this.userService.findUserByName( nameDto.getUserName() );

        } else if ( movieListCreateDto instanceof MovieListCreateByUserIdBodyDto idDto ) {

            return this.userService.findUserById( idDto.getUserId() );
        }

        throw new InvalidBodyException( "Body is invalid!" );
    }


    public List< MovieListResponseDto > getUsersMovieLists( long userId ) {

        User user = this.userService.findUserById( userId );

        return mapToResponseDtoLists( user.getMovieLists() );
    }


    public List< MovieListResponseDto > getUsersMovieLists( String userName ) {

        User user = this.userService.findUserByName( userName );

        return mapToResponseDtoLists( user.getMovieLists() );
    }


    private List< MovieListResponseDto > mapToResponseDtoLists( List< MovieList > movieLists ) {

        // ToDo: Expect nested-JSON issue, if Movies of each MovieList is not longer empty
        // ToDo: Create specific DTO [?] cause every MovieList repeats UserName und UserId

        List< MovieListResponseDto > responseDtoLists = movieLists
                .stream()
                .map( ( MovieList movieList ) -> this.mapToMovieListResponseDto( movieList ) )
                .toList();

        return responseDtoLists;
    }


    public MovieListResponseDto findByNameAndUserName( String movieListName, String userName ) {

        if ( this.userService.userIsExisting( userName ) ) {

            MovieList movieList = this.findMovieListByNameAndUserName( movieListName, userName );

            return this.mapToMovieListResponseDto( movieList );

        } else {

            throw new ResourceNotFoundException( "cant find user " + userName );
        }
    }


    private MovieList findMovieListByNameAndUserName( String movieListName, String userName ) {

        return this.movieListRepository.findByUser_UserNameAndMovieListName( userName, movieListName )
                .orElseThrow( () -> new ResourceNotFoundException( "cant find MovieList " + movieListName ) );
    }

}

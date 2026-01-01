package de.adrianwalter.movie_list_rest_api.service;

import de.adrianwalter.movie_list_rest_api.entity.MovieList;
import de.adrianwalter.movie_list_rest_api.entity.User;
import de.adrianwalter.movie_list_rest_api.exception.ResourceNotFoundException;
import de.adrianwalter.movie_list_rest_api.payload.GetMovieListResponseDTO;
import de.adrianwalter.movie_list_rest_api.payload.PostMovieListByUserIdBodyDTO;
import de.adrianwalter.movie_list_rest_api.payload.PostMovieListByUserNameBodyDTO;
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

    public GetMovieListResponseDTO create( MovieList movieList ){

        //
        return null;
    }

    private GetMovieListResponseDTO mapToGetMovieListResponseDTO( MovieList movieList ){

        GetMovieListResponseDTO dto = new GetMovieListResponseDTO();

        dto.setMovieListId( movieList.getMovieListId() );
        dto.setMovieListName( movieList.getMovieListName() );

        dto.setUserId( movieList.getUser().getUserId() );
        dto.setUserName( movieList.getUser().getUserName() );

        dto.setDescription( movieList.getDescription() );
        dto.setMovies( movieList.getMovies() );

        return dto;
    }




    public GetMovieListResponseDTO findById(Long id) {

        Optional<MovieList> movieList = movieListRepository.findByMovieListId( id );

        if( movieList.isPresent() ){

            GetMovieListResponseDTO getMovieListResponseDTO;
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


    public MovieList create(PostMovieListDTO movieListDTO) {

        // ToDo: Refactoring; DRY

        if (movieListDTO instanceof PostMovieListByUserIdBodyDTO idDTO) {

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

        } else if (movieListDTO instanceof PostMovieListByUserNameBodyDTO nameDTO) {

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

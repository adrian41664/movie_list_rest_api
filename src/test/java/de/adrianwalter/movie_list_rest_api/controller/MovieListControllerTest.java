package de.adrianwalter.movie_list_rest_api.controller;

import de.adrianwalter.movie_list_rest_api.service.movielist.MovieListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest( controllers = MovieListController.class )
class MovieListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private MovieListService movieListService;

}
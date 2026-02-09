# Movie List Rest API

A Spring Boot application for tracking watched movies, developed as a portfolio demonstration project.

## About This Project

This application digitizes my personal movie-watching notebook. The core concept was to minimize custom frontend 
development by leveraging existing tools and applications where possible.
Beyond its practical use, this project serves as a demonstration of my Spring Boot development skills and architectural 
decision-making.

## Current Status

The project is currently at MVP stage and functional for personal use. Some features are still in development, and the 
API may evolve as new functionality is added.

**Next Priority:** 

Implementing comprehensive test coverage to enable confident refactoring and early error detection.

## Getting Started:

The application needs a MySQL-Database. Start one using Docker:

```bash
<<<<<<< HEAD
docker run --name movie-list-db -e MYSQL_ROOT_PASSWORD=rootpassword -e MYSQL_DATABASE=movie_list -p 3307:3306 -d mysql:9.4.0
=======
docker run --name movie-list-db -e MYSQL_ROOT_PASSWORD=rootpassword -e MYSQL_DATABASE=movie_list_db -p 3307:3306 -d mysql:9.4.0
>>>>>>> feature/createMovieListEndpoints
```

### API Documentation

The application includes Swagger UI for API exploration (available after startup).
Swagger UI: http://localhost:8080/swagger-ui/index.html

### Quick Start Guide

1. **Create a User** via `POST /users`
2. **Create a Movie List** via `POST /movie-lists`
3. **Add Movies** via `POST /movies` or `POST /movies/batch` for multiple entries
4. **Search your collection** via `GET /movie-lists/{movieListId}/search`

**Recommended Tool:** [Postman](https://www.postman.com/downloads/) for testing the API endpoints.

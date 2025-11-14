package de.adrianwalter.movie_list_rest_api.repository;

import de.adrianwalter.movie_list_rest_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
}

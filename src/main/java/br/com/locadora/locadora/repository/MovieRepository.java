package br.com.locadora.locadora.repository;

import br.com.locadora.locadora.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Long> {

}

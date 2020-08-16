package br.com.locadora.locadora.service;

import br.com.locadora.locadora.model.Movie;
import br.com.locadora.locadora.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> findAll(){
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(Long id){
        return movieRepository.findById(id);
    }

    public Optional<Movie> updateQtd(Long id, int newQtd){
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setQtd(newQtd);
                    Movie newQtdUpdated = movieRepository.save(movie);
                    return newQtdUpdated;
                });
    }
}

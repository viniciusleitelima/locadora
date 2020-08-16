package br.com.locadora.locadora.service;

import br.com.locadora.locadora.model.Location;
import br.com.locadora.locadora.model.Movie;
import br.com.locadora.locadora.model.User;
import br.com.locadora.locadora.repository.LocationRepository;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    public Location create(Location location){

        Location located = location;

        userService.findById(location.getUser().getId())
                .orElseThrow(() -> new RuntimeException(String.format("User with id '%s' not found.", location.getUser().getId())));

        Optional<Movie> movie = Optional.ofNullable(movieService.findById(location.getMovie().getId())
                .orElseThrow(() -> new RuntimeException(String.format("Movie with '%s' not found.", location.getMovie().getId()))));

        if(movie.get().getQtd()  <= 0){
            throw new RuntimeException("Filme não disponivel");
        }

        try {
            int newQtd = movie.get().getQtd() - 1;
            movieService.updateQtd(location.getMovie().getId(),newQtd);
            located = locationRepository.save(location);
        }catch (RuntimeException re){
            throw new RuntimeException("Filme não disponivel");
        }

        return located;
    }

    public Optional<Location> devolution(Long id){

        Optional <Location> devolvido = locationRepository.findById(id)
                .map(location -> {
                    location.setDateDevolution(LocalDateTime.now());
                    Location returned = locationRepository.save(location);
                    return returned;
                });

        return devolvido;

    }
}

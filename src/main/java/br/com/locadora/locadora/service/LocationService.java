package br.com.locadora.locadora.service;

import br.com.locadora.locadora.enums.Message;
import br.com.locadora.locadora.model.Location;
import br.com.locadora.locadora.model.Movie;
import br.com.locadora.locadora.repository.LocationRepository;
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

        Location located = new Location();

        userService.findById(location.getUser().getId())
                .orElseThrow(Message.CUSTOMER_NOT_FOUND::asBusinessException);

        Location finalLocated = located;
        Optional.ofNullable(movieService.findById(location.getMovie().getId())
                .orElseThrow(Message.MOVIE_NOT_FOUND::asBusinessException))
                .ifPresent(m -> {
                    if(m.getQtd() <= 0){
                        throw new RuntimeException(Message.MOVIE_UNAVAILABLE.getMessage());
                    }else{
                        m.setQtd(m.getQtd() - 1);
                        movieService.updateQtd(location.getMovie().getId(), m.getQtd());
                        finalLocated.setMovie(m);
                    }
                });

        return locationRepository.save(location);
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

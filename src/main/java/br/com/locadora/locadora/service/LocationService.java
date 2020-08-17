package br.com.locadora.locadora.service;

import br.com.locadora.locadora.dto.LocationResponseDTO;
import br.com.locadora.locadora.dto.MovieResponseDTO;
import br.com.locadora.locadora.dto.UserResponseDTO;
import br.com.locadora.locadora.enums.Message;
import br.com.locadora.locadora.model.Location;
import br.com.locadora.locadora.model.Movie;
import br.com.locadora.locadora.model.User;
import br.com.locadora.locadora.repository.LocationRepository;
import org.springframework.beans.BeanUtils;
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

    public LocationResponseDTO create(Location location){

        var located = new Location();
        var userResponse = new UserResponseDTO();
        var movieResponse = new MovieResponseDTO();

        var user = userService.findById(location.getUser().getId())
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

        located = locationRepository.save(location);
        BeanUtils.copyProperties(user, userResponse);
        BeanUtils.copyProperties(finalLocated.getMovie(), movieResponse);

        return LocationResponseDTO.builder()
                .id(located.getId())
                .user(userResponse)
                .movie(movieResponse)
                .build();
    }

    public Optional<Location> devolution(Long id){

        return locationRepository.findById(id)
                .map(location -> {
                    location.setDateDevolution(LocalDateTime.now());
                    Location returned = locationRepository.save(location);
                    return returned;
                });

    }
}

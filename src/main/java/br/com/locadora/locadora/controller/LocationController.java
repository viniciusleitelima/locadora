package br.com.locadora.locadora.controller;

import br.com.locadora.locadora.model.Location;
import br.com.locadora.locadora.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locate")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<?> createLocate(@Validated @RequestBody Location request) {
        return new ResponseEntity<>(locationService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("{id}/devolution")
    public ResponseEntity<?> devolutionLocate(@PathVariable Long id) {
        return new ResponseEntity<>(locationService.devolution(id), HttpStatus.OK);
    }
}

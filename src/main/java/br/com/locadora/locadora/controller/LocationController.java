package br.com.locadora.locadora.controller;

import br.com.locadora.locadora.model.Location;
import br.com.locadora.locadora.service.LocationService;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locate")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<?> createLocate(@Validated @RequestBody Location request) throws NotFound {
        return new ResponseEntity<>(locationService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("{id}/devolution")
    public ResponseEntity<?> devolutionLocate(@PathVariable Long id) throws NotFound {
        return new ResponseEntity<>(locationService.devolution(id), HttpStatus.OK);
    }
}

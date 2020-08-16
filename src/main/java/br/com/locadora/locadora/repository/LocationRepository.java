package br.com.locadora.locadora.repository;

import br.com.locadora.locadora.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {

}

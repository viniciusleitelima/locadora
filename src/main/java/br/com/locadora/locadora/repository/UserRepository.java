package br.com.locadora.locadora.repository;

import br.com.locadora.locadora.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}

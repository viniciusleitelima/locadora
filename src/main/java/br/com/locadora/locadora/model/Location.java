package br.com.locadora.locadora.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Location {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @OneToOne
    private User user;

    @NotNull
    @OneToOne
    private Movie movie;

    @Column
    private LocalDateTime dateLocation = LocalDateTime.now();

    @Column
    private LocalDateTime dateDevolution;

}

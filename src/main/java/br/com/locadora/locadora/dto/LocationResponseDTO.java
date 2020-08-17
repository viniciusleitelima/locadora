package br.com.locadora.locadora.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseDTO {

    private Long id;
    private UserResponseDTO user;
    private MovieResponseDTO movie;

}

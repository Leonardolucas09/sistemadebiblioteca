package br.unisinos.library.dto;

import jakarta.validation.constraints.NotBlank;

public record AutorRequestDTO(

    @NotBlank
    String nome
    
) {}

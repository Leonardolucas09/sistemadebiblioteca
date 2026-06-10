package br.unisinos.library.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(

    @NotBlank
    String nome
)

{}

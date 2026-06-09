package br.unisinos.library.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroRequestDTO (

    @NotBlank
    String titulo,

    @NotBlank
    String editora,

    @NotBlank
    String numeroPaginas,

    Long isbn,

    @NotBlank
    String autorNome,

    @NotNull
    Long categoriaId

) {}

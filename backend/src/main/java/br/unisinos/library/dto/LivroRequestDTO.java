package br.unisinos.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroRequestDTO (

    @NotBlank
    String titulo,

    @NotBlank
    String nomeAutor,

    @NotBlank
    String editora,

    @NotBlank
    String numeroPaginas,

    Long isbn,

    @NotNull
    Long categoriaId

) {}

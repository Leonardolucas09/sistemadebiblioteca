package br.unisinos.library.dto;

public record LivroResponseDTO (

    Long id,
    String titulo,
    String editora,
    String numeroPaginas,
    Long isbn,
    Long autorId,
    String nomeAutor,
    Long categoriaId,
    String nomeCategoria

) {}

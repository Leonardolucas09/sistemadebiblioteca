package br.unisinos.library.dto;

public record LivroResponseDTO (

    Long id,
    String titulo,
    String nomeAutor,
    String editora,
    String numeroPaginas,
    Long isbn,
    Long categoriaId,
    String nomeCategoria

) {}

package br.unisinos.library.dto;

import java.time.LocalDate;

public record EmprestimoResponseDTO(

    Long id,
    Long usuarioId,
    String nomeUsuario,
    Long livroId,
    String tituloLivro,
    Long isbn,
    String nomeAutor,
    String editora,
    Long categoriaId,
    String nomeCategoria,
    LocalDate dataEmprestimo,
    LocalDate dataPrevistaDevolucao,
    LocalDate dataDevolucao,
    Boolean devolvido

) {}

package br.unisinos.library.dto;

public record PerfilUsuarioResponseDTO(
    Long id,
    String nome,
    EmprestimoResponseDTO ultimoEmprestimo
) {}

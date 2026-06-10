package br.unisinos.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unisinos.library.entity.Emprestimo;

public interface EmprestimoRepository extends JpaRepository <Emprestimo, Long> {

    boolean existsByUsuarioIdAndDevolvidoFalse(Long usuarioId);
    boolean existsByLivroIdAndDevolvidoFalse(Long livroId);

}

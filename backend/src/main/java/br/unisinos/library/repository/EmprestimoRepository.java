package br.unisinos.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.unisinos.library.entity.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    boolean existsByUsuarioIdAndDevolvidoFalse(Long usuarioId);
    boolean existsByLivroIdAndDevolvidoFalse(Long livroId);
    List<Emprestimo> findByUsuarioId(Long usuarioId);
    List<Emprestimo> findByUsuarioIdOrderByDataEmprestimoDescIdDesc(Long usuarioId);

    @Query("""
        select e
        from Emprestimo e
        join fetch e.usuario u
        join fetch e.livro l
        left join fetch l.autor a
        left join fetch l.categoria c
        order by e.devolvido asc, e.dataEmprestimo desc, e.id desc
    """)
    List<Emprestimo> listarTodosComRelacionamentos();

    @Query("""
        select e
        from Emprestimo e
        join fetch e.usuario u
        join fetch e.livro l
        left join fetch l.autor a
        left join fetch l.categoria c
        where (:busca is null
            or lower(l.titulo) like lower(concat('%', :busca, '%'))
            or lower(a.nome) like lower(concat('%', :busca, '%'))
            or cast(l.isbn as string) like concat('%', :busca, '%'))
        and (:categoriaId is null or c.id = :categoriaId)
        and (:editora is null or lower(l.editora) = lower(:editora))
        order by e.devolvido asc, e.dataEmprestimo desc, e.id desc
    """)
    List<Emprestimo> buscarComFiltros(
        @Param("busca") String busca,
        @Param("categoriaId") Long categoriaId,
        @Param("editora") String editora);

}
